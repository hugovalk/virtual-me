(ns virtual-me.bot.messages
  (:require [virtual-me.bot.core :as b]))

(defn merge-messages [message-store messages]
  (reduce-kv (fn [result k v]
               (assoc result k (concat (get result k) v)))
             message-store
             messages))

(defprotocol ChatMessageStore
  (save [message-store messages])
  (query-by-session-id [message-store session-id]))

(defrecord InMemoryChatMessageStore [store]
  ChatMessageStore
  (save [message-store messages]
    (swap! (:store message-store)
           (fn [store] (let [new-messages (group-by ::b/session-id messages)]
                         (merge-messages store new-messages)))))
  (query-by-session-id [message-store session-id]
    (get @(:store message-store) session-id)))

(defn init-inmemory-chat-message-store
  ([] (init-inmemory-chat-message-store (atom {})))
  ([store] (InMemoryChatMessageStore. store)))
