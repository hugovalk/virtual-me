(ns virtual-me.web.ping-test
  (:use midje.sweet)
  (:require [ring.mock.request :as mock]
            [cheshire.core :as cheshire]
            [virtual-me.httpkit :refer [app]]))

(defn parse-json [request]
  (cheshire/parse-string (slurp (:body request)) true))

(defn json-get [key request]
  ((keyword key) (parse-json request)))

(fact-group "Ping service tests"
  (fact "/api/ping service returns OK"
    (let [response ((app false) (-> (mock/request :get "/api/ping/test")))]
      (:status response) => 200))
  (fact "/api/ping/Botty returns Botty in id field"
    (let [response ((app false) (-> (mock/request :get "/api/ping/Botty")))]
      (json-get :echo response)) => "Botty"))
