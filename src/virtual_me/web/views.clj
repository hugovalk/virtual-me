(ns virtual-me.web.views
  (:use [hiccup core page]))

(defn index-page []
  (html5
    [:head
     [:title "Hwllo World"]
     (include-css "/css/style.css")]
    [:body
     [:header ]
     [:h1 "Hello World"]]))