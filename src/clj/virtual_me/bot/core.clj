(ns virtual-me.bot.core
  (:require [clojure.spec.alpha :as spec]
            [virtual-me.bot.specs :as bspec]
            [virtual-me.bot.messages :as ms]
            [virtual-me.bot.intents :as intents]
            [clojure.string :as s])
  (:import (java.util UUID)))

(def bot-name "Botty")

(defn create-message [session-id content]
  {::bspec/author     bot-name
   ::bspec/message-id (UUID/randomUUID)
   ::bspec/session-id session-id
   ::bspec/content    content})

(defn echo-response [_ next-message]
  (create-message
    (::bspec/session-id next-message)
    (::bspec/content next-message)))

(defprotocol ChatBot
  (respond [_ session])
  (receive [_ session messages]))

(defn default [session-id]
  (create-message session-id "I don't understand."))

(defrecord EchoChatBot [session-store]
  ChatBot
  (respond [_ session]
    (let [answer (reduce echo-response (default session) (ms/query-by-session-id session-store session))]
      (ms/save session-store [answer])
      answer))
  (receive [_ session messages]
    (ms/save session-store messages)))

(defn match-intent [last-message]
  (let [all-intents intents/intents
        match (some #(when ((fn [i]
                              (let [patterns (::intents/pattern i)]
                                (some
                                  (fn [x] (= x (s/lower-case (::bspec/content last-message))))
                                  patterns)))
                            %) %) (vals all-intents))]
    (println match)
    (rand-nth (::intents/responses match))))

(defrecord IntentsChatBot [session-store]
  ChatBot
  (respond [_ session]
    (let [last-message (last (ms/query-by-session-id session-store session))
          answer-content (match-intent last-message)
          answer (if answer-content
                   (create-message session answer-content)
                   (default session))]
      (ms/save session-store [answer])
      answer))
  (receive [_ session messages]
    (ms/save session-store messages)))