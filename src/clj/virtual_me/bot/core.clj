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

(defn default-response [session-id]
  (create-message session-id "I don't understand."))

(defrecord EchoChatBot [session-store]
  ChatBot
  (respond [_ session]
    (let [answer (reduce echo-response (default-response session) (ms/query-by-session-id session-store session))]
      (ms/save session-store [answer])
      answer))
  (receive [_ session messages]
    (ms/save session-store messages)))

(defn- prompt-matches-intent? [prompt intent]
  (let [patterns (::bspec/pattern intent)]
    (some #(= % (s/lower-case prompt)) patterns)))

(defn- find-matching-intent [prompt intents]
  (some #(when (prompt-matches-intent? prompt %) %) (vals intents)))

(defn- get-answer-from-intent [intent]
  (let [intent-type (::bspec/intent-type intent)]
    (case intent-type
      ::bspec/text-intent (rand-nth (::bspec/responses intent))
      ::bspec/function-intent (let [func (::bspec/function intent)]
                                (func)))))

(defn match-intent [last-message all-intents session]
  (if-let [matching-intent  (find-matching-intent (::bspec/content last-message) all-intents)]
    (get-answer-from-intent matching-intent)
    "I don't understand."))

(defrecord IntentsChatBot [session-store all-intents]
  ChatBot
  (respond [_ session]
    (let [last-message (last (ms/query-by-session-id session-store session))
          answer-content (match-intent last-message all-intents session)
          answer (create-message session answer-content)]
      (ms/save session-store [answer])
      answer))
  (receive [_ session messages]
    (ms/save session-store messages)))
