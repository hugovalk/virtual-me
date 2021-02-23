(ns virtual-me-scripts.test
  (:require [midje.repl :as m]))

(defn -main []
  (m/load-facts :all))
