(ns virtual-me.bot.data
  (:import (java.util UUID)))

(defrecord Session [session-id name])

(defprotocol ChatSessionStore
  (new-session [chat-store name] "Create a new chat session with the name of the user.")
  (load-session [chat-store session-id] "Load an existing chat session.")
  (delete-session [chat-store session-id] "Delete a chat session from the store."))

(defrecord InMemoryChatSessionStore [store]
  ChatSessionStore
  (new-session [chat-store name]
    (let [new-session (Session. (.toString (UUID/randomUUID)) name)]
      (swap! (:store chat-store) assoc (keyword (:session-id new-session)) new-session)
      new-session))
  (load-session [chat-store session-id]
    ((keyword session-id) @(:store chat-store)))
  (delete-session [chat-store session-id]
    (swap! (:store chat-store) dissoc (keyword session-id))
    session-id))

(defn init-inmemory-chat-session-store
  ([] (init-inmemory-chat-session-store (atom {})))
  ([store] (InMemoryChatSessionStore. store)))