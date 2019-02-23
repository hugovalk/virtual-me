(ns virtual-me.bot.core
  (:require [clojure.spec.alpha :as spec])
  (:import (java.util UUID)))

(spec/def ::session-id uuid?)
(spec/def ::message-id uuid?)
(spec/def ::timestamp inst?)
(spec/def ::author string?)
(spec/def ::content string?)
(spec/def ::message (spec/keys :req [::message-id
                                     ::session-id
                                     ::author
                                     ::content]))

(def bot-name "Botty")

(defn calculate-response [result next-message]
  (if (spec/valid? ::message next-message)
    {::message-id (UUID/randomUUID)
     ::author     bot-name
     ::content    (::content next-message)
     ::session-id (::session-id next-message)}
    (throw (new IllegalArgumentException (str next-message " is not a valid message.")))))

(defn respond [messages]
  (reduce calculate-response "" messages))

;(defprotocol ChatBot
;  (respond [bot])
;  (receive [bot messages]))
;
;(defrecord EchoChatBot [session-store]
;  ChatBot
;  (respond [bot] ())
;  (receive [bot messages]))