(ns virtual-weather-reporter.specs
  (:require [clojure.spec.alpha :as spec]))

(spec/def ::temperature number?)
(spec/def ::max number?)
(spec/def ::min number?)
(spec/def ::today (spec/keys :req [::max
                                   ::min]))
(spec/def ::weather (spec/keys :req [::temperature
                                      ::today]))

