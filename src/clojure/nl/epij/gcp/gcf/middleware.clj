(ns nl.epij.gcp.gcf.middleware
  (:require [cheshire.core :as json]
            [nl.epij.gcp.gcf.event :as event]
            [nl.epij.gcp.gcf.message :as message]))

(defn wrap-json-data
  "Middleware that parses the JSON data of the Pubsub message"
  [handler]
  (fn [{::event/keys [message] :as event}]
    (let [{::message/keys [data]} message
          event' (assoc-in event
                           [::event/message
                            ::message/data]
                           (json/parse-string data))]
      (handler event'))))
