(ns virtual-me.bot.intents.specs
  (:require [clojure.spec.alpha :as spec]))

(spec/def ::tag string?)
(spec/def ::intent-type #{::text-intent ::function-intent ::just-reply-intent ::answer-intent})
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
(defmethod intent-type ::just-reply-intent [_]
  (spec/keys :req [::tag
                   ::intent-type
                   ::responses]))
(defmethod intent-type ::answer-intent [_]
  (spec/keys :req [::tag
                   ::intent-type
                   ::function]))


(spec/def ::intent (spec/multi-spec intent-type ::intent-type))
