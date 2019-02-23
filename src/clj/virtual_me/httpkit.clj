(ns virtual-me.httpkit
  (:use [org.httpkit.server :only [run-server]])
  (:require [virtual-me.web.routes :refer [app start-router!]]))

(defn -main [& args]
  (start-router!)
  (run-server (app true) {:port 8080}))
