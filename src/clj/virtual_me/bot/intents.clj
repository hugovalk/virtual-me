(ns virtual-me.bot.intents
  (:require [clojure.spec.alpha :as spec]))

(spec/def ::tag string?)
(spec/def ::pattern (spec/* string?))
(spec/def ::responses (spec/* string?))
(spec/def ::intent (spec/keys :req [::tag
                                    ::pattern
                                    ::responses]))

(def intents
  {:greeting {::tag "greeting"
              ::pattern ["hi" "hello" "how are you" "good day" "good morning" "good evening" "good afternoon"]
              ::responses ["Hi!" "Hello, how are you?" "Good to see you!"]}
   :goodbye {::tag "goodbye"
             ::pattern ["see you later" "goodbye" "good night" "have a nice day"]
             ::responses ["See you later" "Thanks for visiting" "Have a nice day" "Bye!"]}
   :thanks {::tag "thanks"
            ::pattern ["thanks" "thank you" "that's helpful"]
            ::responses ["My pleasure" "Don't mention it" "Any time" "Happy to help"]}})

