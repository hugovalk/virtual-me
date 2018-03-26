(ns virtual-me.web.api
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]))

(defapi api-routes
  (swagger-routes {:ui "/api-docs" :spec "/swagger.json"})
  (context "/api" []
    (GET "/ping/:id" [id]
      (ok (let [value {:id id}]
            (Thread/sleep 1000)
            value)))))