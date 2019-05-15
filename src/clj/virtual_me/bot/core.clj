(ns virtual-me.bot.core
  (:require [clojure.spec.alpha :as spec]
            [virtual-me.bot.specs :as bspec]
            [virtual-me.bot.messages :as ms])
  (:import (java.util UUID)))

(def bot-name "Botty")

(defn echo-response [result next-message]
  {::bspec/message-id (UUID/randomUUID)
   ::bspec/author     bot-name
   ::bspec/content    (::bspec/content next-message)
   ::bspec/session-id (::bspec/session-id next-message)})

(defprotocol ChatBot
  (respond [_ session])
  (receive [_ session messages]))

(defn default [session]
      {::bspec/session-id session
       ::bspec/message-id (UUID/randomUUID)
       ::bspec/author bot-name
       ::bspec/content "I don't understand."})

(defrecord EchoChatBot [session-store]
  ChatBot
  (respond [_ session]
    (let [answer (reduce echo-response (default session) (ms/query-by-session-id session-store session))]
      (ms/save session-store [answer])
      answer))
  (receive [_ session messages]
    (ms/save session-store messages)))
