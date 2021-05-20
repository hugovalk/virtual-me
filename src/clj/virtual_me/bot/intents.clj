(ns virtual-me.bot.intents
  (:require [virtual-me.bot.specs :as bspec]))


(def intents
  {:greeting {::bspec/tag "greeting"
              ::bspec/intent-type ::bspec/text-intent
              ::bspec/pattern ["hi" "hello" "how are you" "good day" "good morning" "good evening" "good afternoon"]
              ::bspec/responses ["Hi!" "Hello, how are you?" "Good to see you!"]}
   :goodbye {::bspec/tag "goodbye"
             ::bspec/intent-type ::bspec/text-intent
             ::bspec/pattern ["see you later" "goodbye" "good night" "have a nice day"]
             ::bspec/responses ["See you later" "Thanks for visiting" "Have a nice day" "Bye!"]}
   :thanks {::bspec/tag "thanks"
            ::bspec/intent-type ::bspec/text-intent
            ::bspec/pattern ["thanks" "thank you" "that's helpful"]
            ::bspec/responses ["My pleasure" "Don't mention it" "Any time" "Happy to help"]}})

