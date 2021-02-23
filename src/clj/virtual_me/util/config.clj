(ns virtual-me.util.config
  (:require [cprop.core :as cp]))

(def ^{:private true} config (atom {}))

(defn config-get [& path]
  (let [c @config]
    (if (= @config {})
      (load-config-from-disk))
    (get-in c path)))

(defn load-config-from-disk []
  (reset! config (cp/load-config)))
