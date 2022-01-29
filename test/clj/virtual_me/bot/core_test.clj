(ns virtual-me.bot.core-test
  (:use midje.sweet)
  (:require [virtual-me.bot.specs :as bspec]
            [virtual-me.bot.core :as b]
            [virtual-me.bot.intents.intents-bot :as ibot]
            [virtual-me.bot.intents.specs :as ibspec]
            [virtual-me.bot.test-util :as butil]
            [clojure.spec.alpha :as spec]))

(let [session (butil/new-session)
      bot (butil/new-echo-bot)]
  (fact-group "EchoBot bot facts"
         (fact "Bot echoes last message from messages list"
               (b/receive bot session [(butil/create-message session "test")
                                       (butil/create-message session "test2")
                                       (butil/create-message session "latest")])
               (let [response (b/respond bot session)]
                 (::bspec/author response) => "Botty"
                 (::bspec/content response) => "latest"))
         (fact "Function receive fails on invalid message"
               (b/receive bot session [{}])
               => (throws IllegalArgumentException))))

(let [session (butil/new-session)
      intents ibot/default-intents
      bot (butil/new-intents-bot intents)]
  (fact-group "Intents bot with default intents only facts"
         (butil/validate-intents intents "default intents")
         (fact "Bot has a default 'do not understand' message"
               (let [intent (:dont_understand intents)]
                 (butil/test-prompt-for-intent bot session "qqqqq" intent)))
         (fact "Bot responds correctly to a greeting"
               (let [intent (:greeting intents)]
                 (butil/test-prompt-for-intent bot session "Hello" intent)
                 (butil/test-prompt-for-intent bot session "Hi" intent)))
         (fact "Bot responds correctly to an answer"
               (let [intent (:acknowledged intents)
                     msg (butil/create-message session "answer")
                     answer-message (assoc msg ::bspec/answering :test-intent)]
                 (butil/test-message-for-intent bot answer-message intent)))))
