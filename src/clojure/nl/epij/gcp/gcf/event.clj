(ns nl.epij.gcp.gcf.event
  (:import [com.google.cloud.functions Context]))

(defn event
  [message ^Context context]
  {::message    message
   ::id         (.eventId context)
   ::timestamp  (.timestamp context)
   ::resource   (.resource context)
   ::attributes (.attributes context)
   ::event-type (.eventType context)})
