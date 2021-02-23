(ns virtual-me-scripts.autotest
  (:require [midje.repl :as m]))

(defn -main []
  (m/autotest :dirs "test/clj" "src/clj"))
