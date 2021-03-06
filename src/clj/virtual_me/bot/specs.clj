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

(spec/def ::tag string?)
(spec/def ::intent-type #{::text-intent ::function-intent})
(spec/def ::pattern (spec/* string?))
(spec/def ::responses (spec/* string?))
(spec/def ::function (spec/fspec :args (spec/cat)
                                 :ret string?))

(defmulti intent-type ::intent-type)
(defmethod intent-type ::text-intent [_]
  (spec/keys :req [::tag
                   ::intent-type
                   ::pattern
                   ::responses]))
(defmethod intent-type ::function-intent [_]
  (spec/keys :req [::tag
                   ::intent-type
                   ::pattern
                   ::function]))

(spec/def ::intent (spec/multi-spec intent-type ::intent-type))

