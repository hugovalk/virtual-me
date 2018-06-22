(ns virtual-me.bot.data
  (:import (java.util UUID)))

(defrecord Session [session-id name])

(defprotocol ChatSessionStore
  (new-session [x name] "Create a new chat session with the name of the user.")
  (load-session [x session-id] "Load an existing chat session.")
  (delete-session [x session-id] "Delete a chat session from the store."))

(defrecord InMemoryChatSessionStore [store]
  ChatSessionStore
  (new-session [x name]
    (let [new-session (Session. (.toString (UUID/randomUUID)) name)]
      (swap! store assoc (keyword (:session-id new-session)) new-session)
      new-session))
  (load-session [x session-id]
    ((keyword session-id) @store))
  (delete-session [x session-id]
    (swap! store dissoc (keyword session-id))
    session-id))

(defn init-inmemory-chat-session-store
  ([] (init-inmemory-chat-session-store (atom {})))
  ([store] (InMemoryChatSessionStore. store)))