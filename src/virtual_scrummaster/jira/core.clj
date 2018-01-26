(ns virtual-scrummaster.jira.core
  (:require [clj-http.client :as http])
  (:import (java.util Scanner)))

(def jira-password (atom nil))
(def jira-user (atom nil))

(defn read-jira-user
  []
  (swap! jira-user
         (fn [current-state]
           (if (nil? (System/console))
             (do
               (print "Jira user:")
               (flush)
               (.next (new Scanner (System/in))))
             (String/valueOf (.readPassword (System/console) "Jira user:" nil))))))

(defn read-jira-password
  []
  (swap! jira-password
         (fn [current-state]
           (if (nil? (System/console))
             (do
               (print "Jira password:")
               (flush)
               (.next (new Scanner (System/in))))
             (String/valueOf (.readPassword (System/console) "Jira password:" nil))))))

(defn fetch-project
  []
  (http/get "https://randstad.prepend.net/jira/rest/api/2/project/10302"
               {:basic-auth [@jira-user, @jira-password]}))
