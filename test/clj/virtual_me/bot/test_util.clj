(ns virtual-me.bot.test-util
  (:use midje.sweet)
  (:require [virtual-me.bot.core :as b]
            [virtual-me.bot.specs :as bspec]
            [virtual-me.bot.intents :as bi]
            [virtual-me.bot.messages :as bmes]
            [clojure.spec.alpha :as spec])
  (:import (java.util UUID)
           (java.time Instant)))

(defn now [] (Instant/now))
(defn new-session [] (UUID/randomUUID))
(defn create-message [session text]
  {::bspec/author    "test"
   ::bspec/content   text
   ::bspec/session-id session
   ::bspec/message-id (UUID/randomUUID)
   ::bspec/timestamp  (Instant/now)})

(defn new-echo-bot []
  (b/->EchoChatBot (bmes/init-inmemory-chat-message-store)))

(defn new-intents-bot [intents]
  (b/->IntentsChatBot (bmes/init-inmemory-chat-message-store)
                      (merge bi/intents intents)))

(defn validate-response [response intent]
  (let [possible-responses (::bspec/responses intent)]
    (some #(= % response) possible-responses) => true))

(defmacro validate-intents [intents intents-name]
  (let [m-v (gensym 'v)]
    `(fact ~(str "All " intents-name " intents are valid.")
           (doseq [~m-v (vals ~intents)]
             (spec/conform ::bspec/intent ~m-v) => ~m-v))))

(defmacro test-prompt-for-intent [bot session prompt intent]
  (let [m-response (gensym 'response)
        m-possible-responses (gensym 'possible-responses)]
    `(fact (str ~(str "Bot answers correctly for prompt: " prompt " with intent: ") (::bspec/tag ~intent))
           (b/receive ~bot ~session [(butil/create-message ~session ~prompt)])
           (let [~m-response (::bspec/content (b/respond ~bot ~session))
                 ~m-possible-responses (::bspec/responses ~intent)]
             (some #(= % ~m-response) ~m-possible-responses) => true))))


