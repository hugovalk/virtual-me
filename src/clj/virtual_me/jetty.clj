(ns virtual-me.jetty
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [virtual-me.web.routes :refer [app]])
  (:gen-class))

(defn -main [& args]
  (run-jetty app {:port (Integer/valueOf (or (System/getenv "port") "8080"))}))