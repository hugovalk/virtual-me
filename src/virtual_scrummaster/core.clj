(ns virtual-scrummaster.core
  (:require [hara.io.scheduler :as s]))


(defn check-the-backlog
  "Check the backlog for actions"
  []
  (println "checking the backlog..."))

(defn ask-for-refinement
  "Ask team members to refine a user story"
  []
  (println "asking..."))

(def check-the-backlog-task
  {:handler (fn [t] (check-the-backlog))
   :schedule "/2 * * * * * *"})

(def ask-for-refinement-task
  (:handler (fn [t] (ask-for-refinement))
    :schedule "/5 * * * * * *"))

(defn launch
  []
  (s/start! (s/scheduler (:check-the-backlog-task check-the-backlog-task)))
  (s/start! (s/scheduler (:ask-for-refinement-task ask-for-refinement-task))))
