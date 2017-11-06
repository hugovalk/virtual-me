(ns virtual-me.core
  (:use virtual-scrummaster.core)
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Started...")
  (launch)
  (Thread/sleep 10000)
  (println "Hello, World!")
  (System/exit 0))
