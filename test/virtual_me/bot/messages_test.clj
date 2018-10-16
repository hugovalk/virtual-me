(ns virtual-me.bot.messages-test
  (:use midje.sweet
        virtual-me.bot.messages)
  (:require [virtual-me.bot.core :as b])
  (:import (java.util UUID)))

(facts "The InMemoryChatMessageStore"
  (fact "Can save message to the collection with the correct session id"
     (let [store (atom {})
           repo (init-inmemory-chat-message-store store)
           session-id (UUID/randomUUID)
           message {::b/session-id session-id
                    ::b/author "Hugo"}]
       (save repo [message])
       (get @store session-id) => [{::b/session-id session-id
                                    ::b/author "Hugo"}])))
