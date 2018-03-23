(ns virtual-me.core
  (:use virtual-scrummaster.core)
  (:require [clojure.tools.logging :as log])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (log/info "Started...")
  (launch)
  (Thread/sleep 10000)
  (println "Hello, World!")
  (System/exit 0))
