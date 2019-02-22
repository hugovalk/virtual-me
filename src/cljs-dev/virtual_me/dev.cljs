(ns virtual-me.dev
  (:require [virtual-me.js]
            [reagent.core :as r]))

(enable-console-print!)

(defn fig-reload []
  (r/force-update-all))
