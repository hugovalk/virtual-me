(ns virtual-me.bot.messages-test
  (:use midje.sweet
        virtual-me.bot.messages)
  (:require [virtual-me.bot.specs :as bspec])
  (:import (java.util UUID)))

(defn rand-str [len]
  (apply str (take len (repeatedly #(char (+ (rand 26) 65))))))

(defn msg
  ([] (msg (UUID/randomUUID) (rand-str 20)))
  ([session-id] (msg session-id (rand-str 20)))
  ([session-id content]
   {::bspec/session-id session-id
    ::bspec/message-id (UUID/randomUUID)
    ::bspec/author     "Hugo"
    ::bspec/content    content}))

(fact-group "The InMemoryChatMessageStore"
  (fact "Can save message to the collection with the correct session id"
    (let [store (atom {})
          repo (init-inmemory-chat-message-store store)
          session-id (UUID/randomUUID)
          message (msg session-id)]
      (save repo [message])
      (get @store session-id) => [message]))
  (fact "Will not save anything when an invalid message is sent"
    (let [store (atom {})
          repo (init-inmemory-chat-message-store store)
          session-id (UUID/randomUUID)
          message (msg session-id)]
      (save repo [message {}]) => (throws IllegalArgumentException)
      (get @store session-id) => nil))
  (fact "Can retrieve the messages from a session"
    (let [repo (init-inmemory-chat-message-store)
          id1 (UUID/randomUUID)
          id2 (UUID/randomUUID)
          messages [(msg id1) (msg id2) (msg id1) (msg id2) (msg id1)]]
      (save repo messages)
      (count (query-by-session-id repo id1)) => 3
      (count (query-by-session-id repo id2)) => 2)))
