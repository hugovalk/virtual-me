(ns virtual-me.httpkit
  (:use [org.httpkit.server :only [run-server]])
  (:require [virtual-me.web.routes :refer [handler]]
            [virtual-me.web.bot-ws :refer [start-router!]]
            [ring.middleware.reload :as reload]))

(defn app
  ([] (app false))
  ([is-dev?]
   (if is-dev?
     (reload/wrap-reload handler)
     handler)))

(def app-figwheel
  (let [r (start-router!)]
    (app true)))

(defn -main [& args]
  (start-router!)
  (run-server (app true) {:port 8080}))
