(ns virtual-weather-reporter.core
  (:require [virtual-me.util.config :refer [config-get]]
            [clj-http.client :as http]
            [virtual-weather-reporter.specs :as ws]
            [clojure.spec.alpha :as spec]))

(def base-url "http://api.openweathermap.org/data/2.5/weather?q=gouda&appid=")

(defn fetch-from-openweathermap []
  (let [api-key (config-get :api-keys :open-weather-api-key)]
    (http/get (str base-url api-key) {:as :json})))

(defn to-weather [input]
  (let [{:keys [temp temp_min temp_max]} (:main input)]
    {::ws/temperature temp
     ::ws/today {::ws/min temp_min
                 ::ws/max temp_max}}))

(defn test-response! [weather]
  (if (= (spec/valid? ::ws/weather weather) true)
    weather
    (throw (AssertionError. "Weather response is invalid."))))

(defn get-weather-info []
  (->> (fetch-from-openweathermap)
       (:body)
       (to-weather)
       (test-response!)))
