(ns virtual-me.bot.specs
  (:require [clojure.spec.alpha :as spec]))

(spec/def ::session-id uuid?)
(spec/def ::message-id uuid?)
(spec/def ::intentimestamp inst?)
(spec/def ::author string?)
(spec/def ::content string?)
(spec/def ::message (spec/keys :req [::message-id
                                     ::session-id
                                     ::author
                                     ::content]))
