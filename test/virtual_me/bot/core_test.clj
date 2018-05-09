(ns virtual-me.bot.core-test
  (:use midje.sweet)
  (:require [virtual-me.bot.core :as b]))

(defn ms [t] {::b/author "test" ::b/message-content t})

(facts "Basic bot facts"
  (fact "Bot echoes last message from messages list"
    (b/respond [(ms "test") (ms "test2") (ms "latest")]) => {::b/author "Botty" ::b/message-content "latest"})
  (fact "Function respond fails on invalid message"
    (b/respond ["test"]) => (throws IllegalArgumentException)))