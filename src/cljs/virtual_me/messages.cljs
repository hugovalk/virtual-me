(ns virtual-me.messages
  (:require-macros
    [cljs.core.async.macros :as asyncm :refer (go go-loop)])
  (:require
    [reagent.core :as r]
    [cljs.core.async :as async :refer (<! >! put! chan)]
    [taoensso.sente  :as sente :refer (cb-success?)]))

(def ?csrf-token
  (when-let [el (.getElementById js/document "sente-csrf-token")]
    (.getAttribute el "data-csrf-token")))

(let [{:keys [chsk ch-recv send-fn state]}
      (sente/make-channel-socket-client! "/chsk"
                                  ?csrf-token
                                  {:type :auto
                                   :packer :edn})]

  (def chsk       chsk)
  (def ch-chsk    ch-recv) ; ChannelSocket's receive channel
  (def chsk-send! send-fn) ; ChannelSocket's send API fn
  (def chsk-state state))   ; Watchable, read-only atom

(defonce messages (r/atom []))
(defonce current-message (r/atom ""))

(defn update-current-message [new]
  (reset! current-message new))

(defn to-message [text]
  {:virtual-me.bot.specs/message-id (random-uuid)
   :virtual-me.bot.specs/session-id (random-uuid)
   :virtual-me.bot.specs/author "Hugo"
   :virtual-me.bot.specs/content text})

(defn append-to-conversation [message]
  (reset! messages (conj @messages message)))

(defn push-message [text]
  (let [message (to-message text)]
    (println message)
    (chsk-send!
      [:virtual-me.bot.specs/message message]
      8000
      (fn [reply]
        (if (sente/cb-success? reply)
          (append-to-conversation reply)
          (println "Booh!"))))
    (append-to-conversation message)))

(defn show-messages []
  [:div.messages-list
   (for [message @messages]
     [:div {:key (str (:virtual-me.bot.specs/message-id message))} (:virtual-me.bot.specs/content message)])])

(defn push-current-message-on-key-press [event]
  (when (= (.-key event) "Enter")
    (do
      (.preventDefault event)
      (push-message @current-message)
      (reset! current-message ""))))

(defn message-input []
  [:div.message-input
   [:textarea {:type "text" :rows 3
               :value @current-message
               :on-change #(update-current-message (-> % .-target .-value))
               :on-key-press #(push-current-message-on-key-press %)}]])
