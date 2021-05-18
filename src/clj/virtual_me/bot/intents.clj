(ns virtual-me.bot.intents
  (:require [virtual-me.bot.specs :as bs]))


(def intents
  {:greeting {::bs/tag "greeting"
              ::bs/pattern ["hi" "hello" "how are you" "good day" "good morning" "good evening" "good afternoon"]
              ::bs/responses ["Hi!" "Hello, how are you?" "Good to see you!"]}
   :goodbye {::bs/tag "goodbye"
             ::bs/pattern ["see you later" "goodbye" "good night" "have a nice day"]
             ::bs/responses ["See you later" "Thanks for visiting" "Have a nice day" "Bye!"]}
   :thanks {::bs/tag "thanks"
            ::bs/pattern ["thanks" "thank you" "that's helpful"]
            ::bs/responses ["My pleasure" "Don't mention it" "Any time" "Happy to help"]}})

