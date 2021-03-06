(ns virtual-me.web.routes
  (:require [compojure.api.sweet :refer :all]
            [compojure.route :as route]
            [virtual-me.bot.core :as bot]
            [virtual-me.web.bot-ws :as bot-ws]
            [ring.util.http-response :refer :all]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults api-defaults]]
            [virtual-me.web.views :refer :all]))

(defroutes dev-index-routes
  (GET "/" [] (index-page true)))

(defroutes index-routes
  (GET "/" [] (index-page false)))

(defroutes sente-routes
  (GET "/chsk" req (bot-ws/ring-ajax-get-or-ws-handshake req))
  (POST "/chsk" req (bot-ws/ring-ajax-post req)))

(defroutes main-routes
  (route/resources "/")
  sente-routes
  dev-index-routes
  (route/not-found "Page not found"))

(defroutes dev-main-routes
  (route/resources "/")
  sente-routes
  dev-index-routes
  (route/not-found "Page not found"))


(defapi api-routes
  (swagger-routes {:ui   "/api-docs"
                   :spec "/swagger.json"
                   :data {:info {:version     "G.A"
                                 :title       "Virtual Me API"
                                 :description "API to use the Virtual Me Chatbot services."
                                 :contact     {:name "Hugo Valk"
                                               :url  "https://github.com/hugovalk/virtual-me"}}
                          :tags [{:name "maintenance" :description "Endpoints used for system maintenance."}
                                 {:name "messages" :description "Endpoints for exchanging messages with the chatbot."}
                                 {:name "sessions" :description "Endpoints for managing chat sessions."}]}})
  (context "/api" []
    (GET "/ping/:echo" [echo]
      :tags ["maintenance"]
      (ok {:echo echo}))
    (context "/bot" []
      (GET "/name" []
        (ok {:name bot/bot-name})))))

(def handler
  (routes
   (wrap-defaults api-routes api-defaults)
   (wrap-defaults main-routes site-defaults)))

(def dev-handler
    (routes
     (wrap-defaults api-routes api-defaults)
     (wrap-defaults dev-main-routes site-defaults)))


