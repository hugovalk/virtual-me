(ns virtual-me.web.ping-test
  (:use midje.sweet
        virtual-me.web.routes)
  (:require [ring.mock.request :as mock]
            [cheshire.core :as cheshire]))

(defn parse-json [request]
  (cheshire/parse-string (slurp (:body request)) true))

(defn json-get [key request]
  ((keyword key) (parse-json request)))

(facts "Ping service tests"
  (fact "/api/ping service returns OK"
    (let [response (app (-> (mock/request :get "/api/ping/test")))]
      (:status response) => 200))
  (fact "/api/ping/Botty returns Botty in id field"
    (let [response (app (-> (mock/request :get "/api/ping/Botty")))]
      (json-get :id response)) => "Botty"))
