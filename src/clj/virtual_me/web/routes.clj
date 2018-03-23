(ns virtual-me.web.routes
  (:use compojure.core
        virtual-me.web.views
        virtual-me.web.api)
  (:require [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults api-defaults]]))

(defroutes main-routes
  (GET "/" [] (index-page))
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (routes
    (wrap-defaults api-routes api-defaults)
    (wrap-defaults main-routes site-defaults)))
