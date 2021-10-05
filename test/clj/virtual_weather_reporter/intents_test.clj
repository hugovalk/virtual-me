(ns virtual-weather-reporter.intents-test
  (:use midje.sweet)
  (:require [virtual-weather-reporter.intents :as wi]
            [virtual-me.bot.core :as b]
            [virtual-me.bot.specs :as bspec]
            [virtual-me.bot.messages :as bmes]
            [virtual-me.bot.test-util :as butil]))

(let [session (butil/new-session)
      bot (butil/new-intents-bot wi/intents)]
  (fact-group "Intents bot with weather intents facts"
         (butil/validate-intents wi/intents "weather intents" :integration)
         (butil/test-prompt-for-function bot session "What is the temperature?" #"The temperature is .*" :integration)
         (butil/test-prompt-for-function bot session "How warm is it?" #"The temperature is .*" :integration)
         (butil/test-prompt-for-function bot session "How cold is it?" #"The temperature is .*" :integration)))
