(ns virtual-weather-reporter.intents
  (:use virtual-weather-reporter.core)
  (:require [virtual-me.bot.intents.specs :as ibspec]
            [virtual-weather-reporter.specs :as wspec]
            [clojure.spec.alpha :as spec]))


(defn temperature-response []
  (let [weather-info (get-weather-info)]
    (format "The temperature is %.1f °C." (::wspec/temperature weather-info))))

(spec/fdef temperature-response
  :args (spec/cat)
  :ret string?)


(def intents
  {:temperature {::ibspec/tag "temperature"
                 ::ibspec/intent-type ::ibspec/function-intent
                 ::ibspec/pattern ["what is the temperature?" "what's the temperature?"
                                  "how warm is it?" "how cold is it?"]
                 ::ibspec/function temperature-response}})
