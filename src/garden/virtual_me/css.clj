(ns virtual-me.css
  (:use garden.units)
  (:use garden.def)
  (:require [garden.def :refer [defstyles]]
            [garden.color :as color]))

(def light-gray (color/rgb [230 230 230]))
(def dark-gray (color/rgb [40 40 40]))
(def white (color/rgb [255 255 255]))

(defstyles screen
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
    :padding [(em 1)]}]

  [:div#app
   {:max-width (em 40)
    :margin "0 auto"
    :padding "5px"}
   [:div.message-input
    {:background-color light-gray
     :padding (em 1)}
    [:textarea
     {:width "100%"}]]])
