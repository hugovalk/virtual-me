(ns virtual-me.bot.echo.echo-bot
  (:require [clojure.tools.logging :refer [info]]
            [virtual-me.bot.core :refer [ChatBot default-response create-message]]
            [virtual-me.bot.messages :as ms]
            [virtual-me.bot.specs :as bspec]))

(defn echo-response [_ next-message]
  (create-message
    (::bspec/session-id next-message)
    (::bspec/content next-message)))

(defrecord EchoChatBot [session-store]
  ChatBot
  (respond [_ session]
    (info "EchoChatBot responding on a running session.")
    (let [answer (reduce echo-response (default-response session) (ms/query-by-session-id session-store session))]
      (ms/save session-store [answer])
      answer))
  (receive [_ session messages]
    (ms/save session-store messages)))

