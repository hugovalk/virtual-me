(ns virtual-me.bot.core
  (:require [clojure.spec.alpha :as spec]))

(spec/def ::author string?)
(spec/def ::message-content string?)
(spec/def ::message (spec/keys :req [::author ::message-content]))

(def bot-name "Botty")

(defn calculate-response [result next-message]
  (if (spec/valid? ::message next-message)
    {::author bot-name ::message-content (::message-content next-message)}
    (throw (new IllegalArgumentException (str next-message " is not a valid message.")))))

(defn respond [messages]
  (reduce calculate-response "" messages))