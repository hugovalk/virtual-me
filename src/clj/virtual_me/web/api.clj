(ns virtual-me.web.api
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [virtual-me.bot.core :as bot]))

(defapi api-routes
  (swagger-routes {:ui "/api-docs"
                   :spec "/swagger.json"
                   :data {:info {:version "G.A"
                                 :title "Virtual Me API"
                                 :description "API to use the Virtual Me Chatbot services."
                                 :contact {:name "Hugo Valk"
                                           :url "https://github.com/hugovalk/virtual-me"}}
                          :tags [{:name "maintenance" :description "Endpoints used for system maintenance."}
                                 {:name "messages" :description "Endpoints for exchanging messages with the chatbot."}
                                 {:name "sessions" :description "Endpoints for managing chat sessions."}]}})
  (context "/api" []
    (GET "/ping/:id" [id]
      :tags ["maintenance"]
      (ok {:id id}))
    (context "/bot" []
      (GET "/name" []
        (ok {:name bot/bot-name}))
      (POST "/messages" [ms]
        :tags ["messages"]
        (ok {:messages (bot/respond ms)})))))