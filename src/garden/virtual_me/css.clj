(ns virtual-me.css
  (:use garden.units)
  (:use garden.def)
  (:require [garden.def :refer [defstyles]]
            [garden.color :as color]))

(def light-blue (color/rgb [200 230 255]))
(def dark-blue (color/rgb [40 120 230]))
(def light-gray (color/rgb [230 230 230]))
(def dark-gray (color/rgb [40 40 40]))
(def white (color/rgb [255 255 255]))

(defstyles
  ^{:garden {:output-to "target/public/css/screen.css"}}
  screen
  [:*
   {:margin 0
    :padding 0}]

  [:body
   {:font-family "sans-serif"
    :margin 0
    :font-size (px 16)
    :line-height 1.5}]

  [:header
   {:background-color dark-gray
    :color white
    :height "10vh"}]

  [:div#app
   {:max-width (em 40)
    :margin "0 auto"
    :height "90vh"
    :overflow-y "auto"
    :padding "5px"}]

  [:div.message-input
   {:background-color dark-blue
    :border-radius (em 0.5)
    :padding (em 1)}
   [:textarea
    {:width "100%"}]]

  [:div.messages-list
   {:display :inline-block
    :width "100%"}
   [:div.message
    {:display :inline-block
     :width "100%"}
    [:div
     {:float :left
      :min-width "40%"
      :max-width "70%"
      :padding (em 0.5)
      :border-radius (em 0.5)
      :background-color light-blue}]
    [:div.myself
     {:float :right
      :color white
      :background-color dark-blue}]]]
  [:div.message-bottom
   {:display :inline-block
    :height (px 1)}])
