(ns nl.epij.gcp.gcf.background
  (:require [nl.epij.gcp.gcf.message :as message]
            [nl.epij.gcp.gcf.event :as event]
            [nl.epij.gcp.gcf.log :as log])
  (:import (com.google.cloud.functions Context)))

(defn exit-with-error!
  "Exit the background function by logging and rethrowing the exception."
  [e event-id]
  (log/error (format "Exception while running function: %s" (.getMessage e))
             {:event-id  event-id
              :exception (Throwable->map e)})
  (throw (ex-info "Exception thrown, check logs for original exception and stack trace"
                  {:message (.getMessage e)
                   :cause   (.getCause e)})))

(defn run-handler!
  [handler event]
  (try (handler event)
       (catch RuntimeException ^RuntimeException e
         (exit-with-error! e (::event/id event)))))

(defn adapter
  [message ^Context context handler]
  (let [message' (message/parse message)
        event    (event/event message' context)]
    (run-handler! handler event)))

(comment

 (run-handler! (fn [evt]
                 #_(/ 1 0)
                 (throw (ex-info "Hallo" {:bla 1})))
               {}))
