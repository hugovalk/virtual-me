(ns virtual-me-scripts.autotest
  (:require [midje.repl :as m]))

(defn -main []
  (m/autotest :filter (complement :integration) :dirs "test/clj" "src/clj"))
