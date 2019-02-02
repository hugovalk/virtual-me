(ns virtual-me.httpkit
  (:use [org.httpkit.server :only [run-server]])
  (:require [virtual-me.web.routes :refer [app]]))

(defn -main [& args]
  (run-server (app true) {:port 8080}))
