(ns virtual-me.js
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(enable-console-print!)

(defonce timer (r/atom (js/Date.)))

(defonce time-updater (js/setInterval
                        #(reset! timer (js/Date.)) 1000))

(defn clock []
  (let [time-str (-> @timer .toTimeString (clojure.string/split " ") first)]
    [:div.example-clock time-str]))

(defn greeting []
  (let [ping (r/atom "")]
    (r/create-class
      {:component-did-mount (fn []
                              (go (let [{{id :id} :body} (<! (http/get "/api/ping/Botty"))]
                                    (reset! ping id))))
       :display-name "greeting"
       :reagent-render (fn []
                         [:h2 "you are talking with: " @ping])})))


(defonce messages (r/atom []))

(defn push-message [message]
  (reset! messages (conj @messages
                         {:key (+ 1 (reduce (fn [a,b] (max a (:key b))) 0 @messages))
                          :text message})))


(defn show-messages []
  (for [message @messages]
    [:div {:key (:key message)} (:text message)]))

(defn message-input []
  (let [cur-mess (r/atom "")]
    (fn []
      [:div.message-input
       [:textarea {:type "text" :rows 3
                   :value @cur-mess
                   :on-change (fn [e]
                                (reset! cur-mess (-> e .-target .-value)))
                   :on-key-press (fn [e]
                                   (when (= (.-key e) "Enter")
                                     (do
                                       (.preventDefault e)
                                       (push-message @cur-mess)
                                       (reset! cur-mess ""))))}]])))

(defn chat-window []
  [:div.chat-window
   [greeting]
   [:div.messages-list
    (show-messages)]
   [message-input]
   [clock]])

(defn ^:export run []
  (r/render [chat-window]
            (js/document.getElementById "app")))