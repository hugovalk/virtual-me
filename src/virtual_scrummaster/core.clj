(ns virtual-scrummaster.core
  (:require [hara.io.scheduler :as s])
  (:require [clojure.tools.logging :as log])
  (:require [virtual-scrummaster.jira.core :as jira]))

(defn check-the-backlog
  "Check the backlog for actions"
  []
  (log/info "checking the backlog...")
  (jira/fetch-project))

(defn ask-for-refinement
  "Ask team members to refine a user story"
  []
  (log/info "asking..."))

(def check-the-backlog-task
  {:handler  (fn [t]
               (log/info "checking the backlog...")
               (check-the-backlog))
   :schedule "/2 * * * * * *"})

(def ask-for-refinement-task
  {:handler  (fn [t] (ask-for-refinement))
   :schedule "/5 * * * * * *"})

(defn launch
  []
  (jira/read-jira-password)
  (s/start! (s/scheduler {:check-the-backlog-task check-the-backlog-task}))
  (s/start! (s/scheduler {:ask-for-refinement-task ask-for-refinement-task})))
