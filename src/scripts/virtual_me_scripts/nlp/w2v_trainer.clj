(ns virtual-me-scripts.nlp.w2v-trainer
  (:require [virtual-me.tools :as tools]))

(def w2v-corpus-path (atom "."))
(def models-path (atom "."))

(defn -main []
  (do
    (tools/swap-atom-via-prompt! w2v-corpus-path "corpus text file path:")
    (tools/swap-atom-via-prompt! models-path "Models directory:")
    (println "done...")))