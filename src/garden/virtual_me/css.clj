(ns virtual-me.css
  (:use garden.units)
  (:require [garden.def :refer [defstyles]]))

(defstyles screen
  [:body
   {:font-family "sans-serif"
    :margin 0
    :font-size (px 16)
    :line-height 1.5}]
  [:header
   {:background-color "#333"
    :height (em 2)}])
