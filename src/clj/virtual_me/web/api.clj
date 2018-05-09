(ns virtual-me.web.api
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [virtual-me.bot.core :as bot]))

(defapi api-routes
  (swagger-routes {:ui "/api-docs" :spec "/swagger.json"})
  (context "/api" []
    (GET "/ping/:id" [id]
      (ok {:id id}))
    (GET "/bot/name" []
      (ok {:name bot/bot-name}))))