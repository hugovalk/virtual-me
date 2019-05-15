(ns virtual-me.bot.core
  (:require [clojure.spec.alpha :as spec]
            [virtual-me.bot.specs :as bspec]
            [virtual-me.bot.messages :as ms])
  (:import (java.util UUID)))

(def bot-name "Botty")

(defn calculate-response [result next-message]
  {::bspec/message-id (UUID/randomUUID)
   ::bspec/author     bot-name
   ::bspec/content    (::bspec/content next-message)
   ::bspec/session-id (::bspec/session-id next-message)})

(defprotocol ChatBot
  (respond [_ session])
  (receive [_ session messages]))

(defn default [session]
      {::bspec/author bot-name
       ::bspec/message-id (UUID/randomUUID)
       ::bspec/content "I don't understand."
       ::bspec/session-id session})

(defrecord EchoChatBot [session-store]
  ChatBot
  (respond [_ session]
    (reduce calculate-response (default session) (ms/query-by-session-id session-store session)))
  (receive [_ session messages]
    (ms/save session-store messages)))
