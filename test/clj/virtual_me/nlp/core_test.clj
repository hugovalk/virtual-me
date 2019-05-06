(ns virtual-me.nlp.core-test
  (:use midje.sweet)
  (:require [virtual-me.nlp.core :refer :all]))

(fact "tokenize splits text in its separate tokens"
  (:tokens (tokenize (dutch "Een hele mooie Nederlandse zin.")) =>
    {:language :nl :tokens ["Een" "hele" "mooie" "Nederlandse" "zin" "."]})
  (:tokens (tokenize (english "A very nice sentence in English.")) =>
    {:language :en :tokens ["A" "very" "nice" "sentence" "in" "English" "."]}))

(fact "tag-persons can find person names in Dutch sentences"
  (tag-persons (tokenize (dutch "De vriend van Jan Smits heet Jan."))) => ["Jan Smits", "Jan"])
