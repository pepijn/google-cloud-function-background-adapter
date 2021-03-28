(defproject nl.epij/google-cloud-function-background-adapter "0.1.0-alpha2"
  :description "A Clojure adapter for background functions on GCP's Cloud Function Java Runtime"
  :url "https://github.com/pepijn/google-cloud-function-background-adapter"
  :license {:name "MIT"
            :url  "https://opensource.org/licenses/MIT"}
  :source-paths ["src/clojure"]
  :java-source-paths ["src/java"]
  :javac-options ["-target" "11" "-source" "11"]
  :resource-paths ["src/resources"]
  :dependencies [[org.clojure/clojure "1.10.3" :scope "provided"]
                 [com.google.cloud.functions/functions-framework-api "1.0.4"]
                 [com.google.cloud/google-cloud-core "1.94.4"]
                 [net.logstash.logback/logstash-logback-encoder "6.6"]
                 [ch.qos.logback/logback-classic "1.2.3"]
                 [cheshire/cheshire "5.10.0"]
                 [com.fasterxml.jackson.core/jackson-core "2.12.1"]])
