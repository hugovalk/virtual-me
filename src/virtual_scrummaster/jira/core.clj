(ns virtual-scrummaster.jira.core
  (:require [clj-http.client :as http])
  (:require [virtual-me.tools :as tools])
  (:import (java.util Scanner)))

(def jira-password (atom nil))
(def jira-user (atom nil))

(defn read-jira-user []
  (tools/swap-atom-via-prompt jira-user "Jira user:"))

(defn read-jira-password []
  (tools/swap-atom-via-prompt jira-password "Jira password:"))

(defn read-jira-credentials []
  (read-jira-user)
  (read-jira-password))


(defn fetch-project
  []
  (http/get "https://randstad.prepend.net/jira/rest/api/2/project/10302"
               {:basic-auth [@jira-user @jira-password]}))
