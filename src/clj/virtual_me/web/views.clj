(ns virtual-me.web.views
  (:use [hiccup core page]))

(defn index-page []
  (html5
    [:head
     [:title "My Virtual Assistant"]
     (include-css "/css/screen.css")
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]]
    [:body
     [:header
      [:h1 "My Virtual Assistant"]]
     [:div#app]
     [:script {:src "/js/main.js"}]
     [:script "virtual_me.js.run();"]]))