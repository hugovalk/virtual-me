(ns virtual-me.bot.core
  (:require [virtual-me.bot.specs :as bspec])
  (:import java.util.UUID))

(def bot-name "Botty")

(defn create-message [session-id content]
  {::bspec/author     bot-name
   ::bspec/message-id (UUID/randomUUID)
   ::bspec/session-id session-id
   ::bspec/content    content})

(defn default-response [session-id]
  (create-message session-id "I don't understand."))

(defprotocol ChatBot
  (respond [_ session] "Respond with an answer based on the messages that have been exchanged so far in the session.")
  (receive [_ session messages] "Receive a new message for the session."))

