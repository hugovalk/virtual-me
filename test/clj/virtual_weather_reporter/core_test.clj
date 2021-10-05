(ns virtual-weather-reporter.core-test
  (:use midje.sweet
        virtual-weather-reporter.core)
  (:require [virtual-weather-reporter.specs :as ws]
            [clojure.spec.alpha :as spec]))

(def response
  {:coord {:lon 4.7083, :lat 52.0167},
   :timezone 3600,
   :cod 200,
   :name "Gouda",
   :dt 1613639136,
   :wind {:speed 8.23, :deg 150},
   :id 2755420,
   :weather [{:id 804, :main "Clouds", :description "overcast clouds", :icon "04d"}],
   :clouds {:all 100},
   :sys {:type 1, :id 1541, :country "NL", :sunrise 1613631018, :sunset 1613667610},
   :base "stations",
   :main
   {:temp 281.14,
    :feels_like 273.89,
    :temp_min 280.37,
    :temp_max 282.15,
    :pressure 1007,
    :humidity 71},
   :visibility 10000})

(fact-group "Weather reporter tests"
       (fact "Valid openweathermap response can be parsed to valid weather specs"
             (let [result (to-weather response)]
               (::ws/max (::ws/today result)) => 9.0
               (spec/valid? ::ws/weather result) => true))
       (fact "Empty openweathermap response map does not result in error"
             (let [result (to-weather {})]
               (spec/valid? ::ws/weather result) => false)))

(fact-group "Weather reporter integration tests"
       (fact :integration "Call to openweathermap results in valid weather data"
             (let [result (get-weather-info)]
               (spec/valid? ::ws/weather result) => true)))
