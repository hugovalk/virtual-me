(ns virtual-me.bot.test-util
  (:use midje.sweet)
  (:require [virtual-me.bot.core :as b]
            [virtual-me.bot.echo.echo-bot :as ebot]
            [virtual-me.bot.intents.intents-bot :as ibot]
            [virtual-me.bot.specs :as bspec]
            [virtual-me.bot.intents.specs :as ibspec]
            [virtual-me.bot.messages :as bmes]
            [clojure.spec.alpha :as spec])
  (:import (java.util UUID)
           (java.time Instant)))

(defn now [] (Instant/now))
(defn new-session [] (UUID/randomUUID))
(defn create-message [session text]
  {::bspec/author     "test"
   ::bspec/content    text
   ::bspec/session-id session
   ::bspec/message-id (UUID/randomUUID)
   ::bspec/timestamp  (Instant/now)})

(defn new-echo-bot []
  (ebot/->EchoChatBot (bmes/init-inmemory-chat-message-store)))

(defn new-intents-bot [intents]
  (ibot/->IntentsChatBot (bmes/init-inmemory-chat-message-store)
                      (merge ibot/default-intents intents)))

(defn validate-response [response intent]
  (let [possible-responses (::bspec/responses intent)]
    (some #(= % response) possible-responses) => true))

(defmacro validate-intents
  ([intents intents-name] `(validate-intents ~intents ~intents-name false))
  ([intents intents-name tag]   
   (let [m-v (gensym 'v)]
     `(fact ~(if tag tag) ~(str "All " intents-name " intents are valid.")
            (doseq [~m-v (vals ~intents)]
              (spec/conform ::ibspec/intent ~m-v) => ~m-v)))))

(defmacro test-message-for-intent [bot message intent]
  (let [m-response (gensym 'response)
        m-possible-responses (gensym 'possible-responses)]
    `(fact (str ~(str "Bot answers correctly for message: " message " with intent: ") (::ibspec/tag ~intent))
           (b/receive ~bot (::bspec/session-id ~message) [~message])
           (let [~m-response (::bspec/content (b/respond ~bot (::bspec/session-id ~message)))
                 ~m-possible-responses (::ibspec/responses ~intent)]
             (some #(= % ~m-response) ~m-possible-responses) => true))))

(defmacro test-prompt-for-intent [bot session prompt intent]
  (let [m-response (gensym 'response)
        m-possible-responses (gensym 'possible-responses)]
    `(fact (str ~(str "Bot answers correctly for prompt: " prompt " with intent: ") (::ibspec/tag ~intent))
           (b/receive ~bot ~session [(butil/create-message ~session ~prompt)])
           (let [~m-response (::bspec/content (b/respond ~bot ~session))
                 ~m-possible-responses (::ibspec/responses ~intent)]
             (some #(= % ~m-response) ~m-possible-responses) => true))))

(defmacro test-prompt-for-function
  ([bot session prompt answer-regex] `(test-prompt-for-function ~bot ~session ~prompt ~answer-regex false))
  ([bot session prompt answer-regex tag]
   (let [m-response (gensym 'response)]
     `(fact ~(if tag tag) (str ~(str "Bot answers correctly from function for prompt: " prompt " with: " answer-regex))
            (b/receive ~bot ~session [(butil/create-message ~session ~prompt)])
            (let [~m-response (::bspec/content (b/respond ~bot ~session))]
              ~m-response => #"The temperature is .*")))))

