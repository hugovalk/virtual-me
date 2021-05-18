(ns virtual-me.bot.test-util
  (:require [virtual-me.bot.core :as b]
            [virtual-me.bot.specs :as bspec]
            [virtual-me.bot.intents :as bi]
            [virtual-me.bot.messages :as bmes])
  (:import (java.util UUID)
           (java.time Instant)))

(defn now [] (Instant/now))
(defn new-session [] (UUID/randomUUID))
(defn message [session text]
  {::bspec/author    "test"
   ::bspec/content   text
   ::bspec/session-id session
   ::bspec/message-id (UUID/randomUUID)
   ::bspec/timestamp  (Instant/now)})

;(defn new-intents-bot []
;  (b/->IntentsChatBot (bmes/init-inmemory-chat-message-store)
;                      bi/intents))

;(defn test-intents
;  ([prompt-with-intents] (test-intents prompt-with-intents (new-session) (new-intents-bot)))
;  ([prompt-with-intents session bot]
;   (b/receive bot session [(message session "test")])))
