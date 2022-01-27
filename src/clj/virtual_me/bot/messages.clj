(ns virtual-me.bot.messages
  (:require [virtual-me.bot.specs :as bspec]
            [clojure.spec.alpha :as spec]))

(defn merge-messages [message-store messages]
  (reduce-kv (fn [result k v]
               (assoc result k (concat (get result k) v)))
             message-store
             messages))

(defprotocol ChatMessageStore
  (save-one [message-store message] "Save one message in the message store and returns the message.")
  (save [message-store messages] "Save a collection of messages in the message store and returns the collection.")
  (query-by-session-id [message-store session-id] "Get all messages exchanged in a certain chat session."))

(defrecord InMemoryChatMessageStore [store]
  ChatMessageStore
  (save-one [message-store message]
    (save message-store [message])
    message)
  (save [message-store messages]
    (doseq [message messages]
      (if (not (spec/valid? ::bspec/message message))
        (throw (new IllegalArgumentException (str message " is not a valid message.")))))
    (swap! (:store message-store)
           (fn [store] (let [new-messages (group-by ::bspec/session-id messages)]
                         (merge-messages store new-messages))))
    messages)
  (query-by-session-id [message-store session-id]
    (get @(:store message-store) session-id)))

(defn init-inmemory-chat-message-store
  ([] (init-inmemory-chat-message-store (atom {})))
  ([store] (InMemoryChatMessageStore. store)))
