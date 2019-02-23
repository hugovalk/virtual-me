(ns virtual-me.web.api-docs-test
  (:use midje.sweet
        virtual-me.web.routes)
  (:require [ring.mock.request :as mock]))

(facts "Swagger API docs tests"
  (fact "/swagger.json exists"
    (let [response ((app false) (-> (mock/request :get "/swagger.json")))]
      (:status response) => 200))

  (fact "/api-docs redirects to /api-docs/index.html exists"
    (let [response ((app false) (-> (mock/request :get "/api-docs")))]
      (:status response) => 200
        (get (:headers response) "Location")) => (contains "/api-docs/index.html"))

  (fact "Swagger UI exists"
    (let [response ((app false) (-> (mock/request :get "/api-docs/index.html")))]
      (:status response) => 200)))