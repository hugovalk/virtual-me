(ns virtual-me.nlp.core-test
  (:use midje.sweet)
  (:require [virtual-me.nlp.core :refer :all]))

(fact "tokenize splits text in its separate tokens"
  (:tokens (tokenize {:language :nl :text "Een hele mooie Nederlandse zin."})) => ["Een" "hele" "mooie" "Nederlandse" "zin" "."]
  (:tokens (tokenize {:language :en :text "A very nice sentence in English."})) => ["A" "very" "nice" "sentence" "in" "English" "."])

(fact "tag-persons can find person names in Dutch sentences"
  (tag-persons (tokenize (dutch "De vriend van Jan Smits heet Jan."))) => ["Jan Smits", "Jan"])

