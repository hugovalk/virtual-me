(ns virtual-me.bot.intents.intents-bot
  (:require [clojure.string :as s]
            [clojure.tools.logging :refer [info]]
            [virtual-me.bot.core :refer [ChatBot create-message]]
            [virtual-me.bot.messages :as ms]
            [virtual-me.bot.specs :as bspec]
            [virtual-me.bot.intents.specs :as ibspec]))

(defn- prompt-matches-intent? [prompt intent]
  (let [patterns (::ibspec/pattern intent)]
    (some #(= % (s/lower-case prompt)) patterns)))


(defn- find-matching-intent [prompt intents]
  (some #(when (prompt-matches-intent? prompt %) %) (vals intents)))


(defn- get-answer-from-intent [intent]
  (let [intent-type (::ibspec/intent-type intent)]
    (case intent-type
      ::ibspec/text-intent (rand-nth (::ibspec/responses intent))
      ::ibspec/function-intent (let [func (::ibspec/function intent)]
                                (func)))))


(defn- match-intent [last-message all-intents session]
  (if-let [matching-intent  (find-matching-intent (::bspec/content last-message) all-intents)]
    (get-answer-from-intent matching-intent)
    "I don't understand."))


(defrecord IntentsChatBot [session-store all-intents]
  ChatBot
  (respond [_ session]
    (info "IntentsChatBot responding on session %s." session)
    (let [last-message (last (ms/query-by-session-id session-store session))
          answer-content (match-intent last-message all-intents session)
          answer (create-message session answer-content)]
      (ms/save session-store [answer])
      answer))
  (receive [_ session messages]
    (ms/save session-store messages)))

(def default-intents
  {:greeting {::ibspec/tag "greeting"
              ::ibspec/intent-type ::ibspec/text-intent
              ::ibspec/pattern ["hi" "hello" "how are you" "good day" "good morning" "good evening" "good afternoon"]
              ::ibspec/responses ["Hi!" "Hello, how are you?" "Good to see you!"]}
   :goodbye {::ibspec/tag "goodbye"
             ::ibspec/intent-type ::ibspec/text-intent
             ::ibspec/pattern ["see you later" "goodbye" "good night" "have a nice day"]
             ::ibspec/responses ["See you later" "Thanks for visiting" "Have a nice day" "Bye!"]}
   :thanks {::ibspec/tag "thanks"
            ::ibspec/intent-type ::ibspec/text-intent
            ::ibspec/pattern ["thanks" "thank you" "that's helpful"]
            ::ibspec/responses ["My pleasure" "Don't mention it" "Any time" "Happy to help"]}})
