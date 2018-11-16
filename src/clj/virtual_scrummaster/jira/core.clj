(ns virtual-scrummaster.jira.core
  (:require [clj-http.client :as http])
  (:require [clj-http.cookies :as cookies])
  (:require [virtual-me.tools :as tools])
  (:require [cheshire.core :as json]))

(def jira-password (atom nil))
(def jira-user (atom nil))
(def base-url "https://randstad.prepend.net/jira/rest")

(def jira-cookies (atom (cookies/cookie-store)))

(defn read-jira-user []
  (tools/swap-atom-via-prompt! jira-user "Jira user:"))

(defn read-jira-password []
  (tools/swap-atom-via-prompt! jira-password "Jira password:"))

(defn read-jira-credentials []
  (read-jira-user)
  (read-jira-password))

(defn authenticate
  []
  (let [cs @jira-cookies]
    (http/post (str base-url "/auth/1/session")
               {:body (json/generate-string {:username @jira-user :password @jira-password})
                :content-type "application/json"
                :cookie-store cs})))

(defn fetch-project
  []
  (let [cs @jira-cookies]
    (http/get (str base-url "/api/2/project/10302")
              {:cookie-store cs})))

(defn launch
  []
  (read-jira-credentials)
  (authenticate)
  (fetch-project))