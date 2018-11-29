(ns virtual-me.messages
  (:require [reagent.core :as r]))

(defonce messages (r/atom []))
(defonce current-message (r/atom ""))

(defn update-current-message [new]
  (reset! current-message new))

(defn push-message [message]
  (reset! messages (conj @messages
                         {:key (+ 1 (reduce (fn [a,b] (max a (:key b))) 0 @messages))
                          :text message})))

(defn show-messages []
  [:div.messages-list
   (for [message @messages]
     [:div {:key (:key message)} (:text message)])])

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



