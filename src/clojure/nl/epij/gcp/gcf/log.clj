(ns nl.epij.gcp.gcf.log
  (:require [clojure.walk :as walk]
            [cheshire.core :as json]
            [cheshire.generate :refer [add-encoder encode-str]])
  (:import [org.slf4j LoggerFactory Logger]
           [net.logstash.logback.argument StructuredArguments]
           [com.fasterxml.jackson.core JsonGenerationException]
           [jdk.internal.net.http HttpRequestImpl HttpClientFacade]))

(def ^Logger logger
  (LoggerFactory/getLogger ^String (.toString *ns*)))

(add-encoder HttpRequestImpl encode-str)
(add-encoder HttpClientFacade encode-str)

(defn log
  [level ^String message data]
  (let [data'                (-> (walk/stringify-keys data)
                                 (assoc "severity" level)
                                 (assoc "revision" "TODO"))
        structured-arguments (mapcat (fn [[k v]]
                                       (cond (nil? v)
                                             []

                                             (coll? v)
                                             (try
                                               [(StructuredArguments/raw k (json/generate-string v))]
                                               (catch JsonGenerationException ^JsonGenerationException e
                                                 (log "WARNING" "Couldn't generate JSON map for logging" (Throwable->map e))
                                                 [(StructuredArguments/keyValue k (.toString v))]))

                                             (string? v)
                                             [(StructuredArguments/keyValue k v)]

                                             (number? v)
                                             [(StructuredArguments/keyValue k v)]

                                             :else
                                             [(StructuredArguments/keyValue k (.toString v))]))
                                     data')]
    (.error logger
            message
            (to-array structured-arguments))))

(defn info
  [message data]
  (log "INFO" message data))

(defn warning
  [message data]
  (log "WARNING" message data))

(defn error
  [message data]
  (log "ERROR" message data))

(comment

 (error "berichtje"
        {:event-id   1337
         :api-params {:a 42 :b "yo"}})

 )
