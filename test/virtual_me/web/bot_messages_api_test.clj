(ns virtual-me.web.bot-messages-api-test
  (:use midje.sweet)
  (:require [ring.mock.request :as mock]
            [cheshire.core :as cheshire]
            [virtual-me.httpkit :refer [app]]))

(facts "Tests for the /bot/messages api."
  (fact "posting an empty array of messages returns 200 ok"
    (let [response ((app false) (-> (mock/request :post "/api/bot/messages")
                                    (mock/json-body {:messages []})))]
      (:status response) => 200)))
