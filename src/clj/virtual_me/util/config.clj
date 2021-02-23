(ns virtual-me.util.config
  (:require [cprop.core :as cp]))

(def ^{:private true} config (atom {}))

(defn load-config-from-disk []
  (reset! config (cp/load-config)))

(defn config-get [& path]
  (if (= @config {})
    (load-config-from-disk))
  (get-in @config path))

