(ns virtual-weather-reporter.core
  (:require [virtual-me.util.config :refer [config-get]]
            [clj-http.client :as http]
            [virtual-weather-reporter.specs :as ws]
            [clojure.spec.alpha :as spec]
            [clojure.tools.logging :refer [debug info]]))

(def base-url "http://api.openweathermap.org/data/2.5/weather?q=gouda&appid=")

(defn kelvin-to-celsius [kelvin]
  (if kelvin
    (- kelvin 273.15)
    nil))

(defn fetch-from-openweathermap []
  (let [api-key (config-get :api-keys :open-weather-api-key)]
    (info "Getting temperature response from Openweathermap.")
    (http/get (str base-url api-key) {:as :json})))

(defn to-weather [input]
  (let [{:keys [temp temp_min temp_max]} (:main input)
        ctemp (kelvin-to-celsius temp)
        ctemp_min (kelvin-to-celsius temp_min)
        ctemp_max (kelvin-to-celsius temp_max)]
    {::ws/temperature ctemp
     ::ws/today {::ws/min ctemp_min
                 ::ws/max ctemp_max}}))

(defn test-response! [weather]
  (if (spec/valid? ::ws/weather weather)
    weather
    (throw (AssertionError. "Weather response is invalid."))))

(defn get-weather-info []
  (->> (fetch-from-openweathermap)
       (:body)
       (to-weather)
       (test-response!)))
