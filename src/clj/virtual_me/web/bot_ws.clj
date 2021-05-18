(ns virtual-me.web.bot-ws
  (:require [taoensso.sente :as sente]
            [virtual-me.bot.specs :as bspec]
            [virtual-me.bot.core :as bot]
            [virtual-me.bot.intents :as bintents]
            [virtual-me.bot.messages :as ms]
            [taoensso.sente.server-adapters.http-kit :refer (get-sch-adapter)]
            [clojure.core.async :as async :refer [<! <!! >! >!! put! chan go go-loop]])
  (:import (java.util UUID)))

(let [{:keys [ch-recv send-fn connected-uids
              ajax-post-fn ajax-get-or-ws-handshake-fn]}
      (sente/make-channel-socket-server!
        (get-sch-adapter) {:packer :edn})]

  (def ring-ajax-post                ajax-post-fn)
  (def ring-ajax-get-or-ws-handshake ajax-get-or-ws-handshake-fn)
  (def ch-chsk                       ch-recv) ; ChannelSocket's receive channel
  (def chsk-send!                    send-fn) ; ChannelSocket's send API fn
  (def connected-uids                connected-uids)) ; Watchable, read-only atom

(def bot (bot/->IntentsChatBot (ms/init-inmemory-chat-message-store)
                               bintents/intents))

(defmulti -event-msg-handler
          "Multimethod to handle Sente `event-msg`s"
          :id)                                              ; Dispatch on event-id

(defn event-msg-handler
  "Wraps `-event-msg-handler` with logging, error catching, etc."
  [{:as ev-msg :keys [id ?data event]}]
  (-event-msg-handler ev-msg))                              ; Handle event-msgs on a single thread
;; (future (-event-msg-handler ev-msg)) ; Handle event-msgs on a thread pool

(defmethod -event-msg-handler
  :default                                                  ; Default/fallback case (no other matching handler)
  [{:as ev-msg :keys [event id ?data ring-req ?reply-fn send-fn]}]
  (let [session (:session ring-req)
        uid (:uid session)]
    (println "Unhandled event: %s" event)
    (when ?reply-fn
      (?reply-fn {:umatched-event-as-echoed-from-server event}))))

(def session (UUID/randomUUID))
(defmethod -event-msg-handler ::bspec/message
  [{:as ev-msg :keys [event ?reply-fn]}]
  (let [[id message] event]
    (println (str "Calculating response for" event))
    (let [message-session (assoc message ::bspec/session-id session)]
      (bot/receive bot session [message-session])
      (?reply-fn (bot/respond bot session)))))

(defonce router_ (atom nil))
(defn stop-router! [] (when-let [stop-fn @router_] (stop-fn)))
(defn start-router! []
  (stop-router!)
  (reset! router_
          (sente/start-server-chsk-router!
           ch-chsk event-msg-handler)))
