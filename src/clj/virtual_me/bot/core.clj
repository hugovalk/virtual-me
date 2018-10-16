(ns virtual-me.bot.core
  (:require [clojure.spec.alpha :as spec]))

(spec/def ::session-id uuid?)
(spec/def ::timestamp inst?)
(spec/def ::author string?)
(spec/def ::message-content string?)
(spec/def ::message (spec/keys :req [::session-id
                                     ::timestamp
                                     ::author
                                     ::message-content]))

(def bot-name "Botty")

(defn calculate-response [result next-message]
  (if (spec/valid? ::message next-message)
    {::author bot-name ::message-content (::message-content next-message)}
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