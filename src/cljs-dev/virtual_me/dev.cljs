(ns ^:figwheel-hooks virtual-me.dev
  (:require [virtual-me.js]
            [reagent.dom :as d]))

(enable-console-print!)

(defn fig-reload []
  (println "test2")
  (d/force-update-all))
