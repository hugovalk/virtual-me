(ns virtual-me.dl4j.ui
  (:import (org.deeplearning4j.ui.storage InMemoryStatsStorage)
           (org.deeplearning4j.ui.api UIServer)))

(def in-memory-stats-storage
  (atom (InMemoryStatsStorage.)))

(defn start-ui-server []
  (let [ui-server (UIServer/getInstance)]
    (.attach ui-server @in-memory-stats-storage)))