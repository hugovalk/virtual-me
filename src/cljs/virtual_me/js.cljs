(ns virtual-me.js
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [virtual-me.clock :as clock]
            [virtual-me.messages :as ms]))

(enable-console-print!)

(defn greeting []
  (let [bot-name (r/atom "")]
    (r/create-class
      {:component-did-mount #(go (let [{{name :name} :body} (<! (http/get "/api/bot/name"))]
                                    (reset! bot-name name)))
       :display-name "greeting"
       :reagent-render (fn [] [:h2 "you are talking with: " @bot-name])})))

(defn chat-window []
  [:div.chat-window
   [greeting]
   [:div.messages-list
    (ms/show-messages)]
   [ms/message-input]
   [clock/clock]])

(defn ^:export run []
  (r/render [chat-window]
            (js/document.getElementById "app")))