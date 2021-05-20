(ns virtual-weather-reporter.intents-test
  (:use midje.sweet)
  (:require [virtual-weather-reporter.intents :as wi]
            [virtual-me.bot.core :as b]
            [virtual-me.bot.specs :as bspec]
            [virtual-me.bot.messages :as bmes]
            [virtual-me.bot.test-util :as butil]))

(let [session (butil/new-session)
      bot (butil/new-intents-bot wi/intents)]
  (facts "Intents bot with weather intents facts"
         (butil/validate-intents wi/intents "weather intents")
         (let [intent (:temperature wi/intents)]
           (butil/test-prompt-for-intent bot session "What is the temperature?" intent)
           (butil/test-prompt-for-intent bot session "How warm is it?" intent)
           (butil/test-prompt-for-intent bot session "How cold is it?" intent))))
