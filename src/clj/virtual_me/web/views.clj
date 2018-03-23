(ns virtual-me.web.views
  (:use [hiccup core page]))

(defn index-page []
  (html5
    [:head
     [:title "Hwllo World"]
     (include-css "/css/screen.css")]
    [:body
     [:header]
     [:h1 "Hello World"]
     [:div#app]
     [:script {:src "/js/main.js"}]
     [:script "virtual_me.js.run();"]]))