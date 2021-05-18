(ns virtual-weather-reporter.intents-test
  (:use midje.sweet)
  (:require [virtual-weather-reporter.intents :as wi]
            [virtual-me.bot.core :as b]
            [virtual-me.bot.specs :as bspec]
            [virtual-me.bot.messages :as bmes])
  (:import (java.util UUID)
           (java.time Instant)))

(defn ms [session text]
  {::bspec/author    "test"
   ::bspec/content   text
   ::bspec/session-id session
   ::bspec/message-id (UUID/randomUUID)
   ::bspec/timestamp  (Instant/now)})

(let [session (UUID/randomUUID)
      bot (b/->IntentsChatBot (bmes/init-inmemory-chat-message-store)
                              wi/intents)]
  (facts "Intents bot with weather intents facts"
         (fact "Bot answers the temperature"
               (b/receive bot session [(ms session "What is the temperature?")])
               (let [response (::bspec/content (b/respond bot session))
                     possible-responses (::bspec/responses (:temperature wi/intents))]
                 (some #(= % response) possible-responses) => true))))
