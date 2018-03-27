(ns virtual-me.js
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(enable-console-print!)

(defonce timer (r/atom (js/Date.)))

(defonce time-updater (js/setInterval
                        #(reset! timer (js/Date.)) 1000))

(defn greeting []
  (let [ping (r/atom "")]
    (r/create-class
      {:component-did-mount (fn []
                              (go (let [{{id :id} :body} (<! (http/get "/api/ping/Botty"))]
                                    (reset! ping id))))
       :display-name "greeting"
       :reagent-render (fn []
                         [:h2 "you are talking with: " @ping])})))

(defn clock []
  (let [time-str (-> @timer .toTimeString (clojure.string/split " ") first)]
    [:div.example-clock time-str]))

(defn message-input []
  [:div.message-input
   [:textarea {:type "text" :rows 3}]])

(defn chat-window []
  [:div.chat-window
   [greeting]
   [message-input]
   [clock]])

(defn ^:export run []
  (r/render [chat-window]
            (js/document.getElementById "app")))