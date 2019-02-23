(ns virtual-me.bot.core-test
  (:use midje.sweet)
  (:require [virtual-me.bot.core :as b])
  (:import (java.util UUID)
           (java.time Instant)))

(defn ms [t]
  {::b/author          "test"
   ::b/content         t
   ::b/session-id      (UUID/randomUUID)
   ::b/message-id      (UUID/randomUUID)
   ::b/timestamp       (Instant/now)})

(facts "Basic bot facts"
  (fact "Bot echoes last message from messages list"
    (let [response (b/respond [(ms "test") (ms "test2") (ms "latest")])]
      (::b/author response) => "Botty"
      (::b/content response) => "latest"))
  (fact "Function respond fails on invalid message"
    (b/respond ["test"]) => (throws IllegalArgumentException)))