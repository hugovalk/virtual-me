(ns virtual-me.web.views
  (:use [hiccup core page])
  (:require [ring.middleware.anti-forgery :as anti-forgery]))

(defn index-page [is-dev?]
  (html5
    [:head
     [:title "My Virtual Assistant"]
     (include-css "/css/screen.css")
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]]
    [:body
     (let [csrf-token
           (force anti-forgery/*anti-forgery-token*)]
       [:div#sente-csrf-token {:data-csrf-token csrf-token}])
     [:header
      [:h1 "My Virtual Assistant"]]
     [:div#app]
     (if is-dev?
       [:script {:src "/cljs-out/dev-main.js"}]
       [:script {:src "/cljs-out/main.js"}])
     [:script "virtual_me.js.run();"]]))
