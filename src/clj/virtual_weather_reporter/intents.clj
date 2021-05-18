(ns virtual-weather-reporter.intents
  (:use virtual-weather-reporter.core)
  (:require [virtual-me.bot.specs :as bspec]))

(def intents
  {:temperature {::bspec/tag "temperature"
                 ::bspec/pattern ["what is the temperature?" "what's the temperature?" "how warm is it?" "how cold is it?"]
                 ::bspec/responses ["I don't know the temperature"]}})
