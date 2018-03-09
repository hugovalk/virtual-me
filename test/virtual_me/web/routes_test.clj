(ns virtual-me.web.routes-test
  (:use midje.sweet
        virtual-me.web.routes)
  (:require [ring.mock.request :as mock]))

(facts "Swagger API docs tests"
  (fact "Test that /swagger.json exists"
    (let [response (app (-> (mock/request :get "/swagger.json")))]
      (:status response) => 200))

  (fact "Test that /api-docs redirects to /api-docs/index.html exists"
    (let [response (app (-> (mock/request :get "/api-docs")))]
      (:status response) => 200
        (get (:headers response) "Location")) => (contains "/api-docs/index.html"))

  (fact "Test that Swagger UI exists"
    (let [response (app (-> (mock/request :get "/api-docs/index.html")))]
      (:status response) => 200)))