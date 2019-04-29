(ns virtual-me.bot.core-test
  (:use midje.sweet)
  (:require [virtual-me.bot.specs :as bspec]
            [virtual-me.bot.core :as b]
            [virtual-me.bot.messages :as ms])
  (:import (java.util UUID)
           (java.time Instant)))

(defn ms [session t]
  {::bspec/author     "test"
   ::bspec/content    t
   ::bspec/session-id session
   ::bspec/message-id (UUID/randomUUID)
   ::bspec/timestamp  (Instant/now)})


(let [session (UUID/randomUUID)
      bot (b/->EchoChatBot (ms/init-inmemory-chat-message-store))]
  (facts "EchoBot bot facts"
         (fact "Bot echoes last message from messages list"
               (b/receive bot session [(ms session "test") (ms session "test2") (ms session "latest")])
               (let [response (b/respond bot session)]
                 (::bspec/author response) => "Botty"
                 (::bspec/content response) => "latest"))
         (fact "Function receive fails on invalid message"
               (b/receive bot session [{}])
               => (throws IllegalArgumentException))))