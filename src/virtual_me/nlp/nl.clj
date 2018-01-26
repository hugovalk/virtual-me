(ns virtual-me.nlp.nl
  (:use opennlp.nlp)
  (:require [clojure.java.io :as io]))

(def get-sentences (make-sentence-detector (.getPath (io/resource "nl-sent.bin"))))
(def tokenize (make-tokenizer (.getPath (io/resource "nl-token.bin"))))
(def pos-tag (make-pos-tagger (.getPath (io/resource "nl-pos-maxent.bin"))))
(def person-find (make-name-finder (.getPath (io/resource "nl-ner-person.bin"))))
(def organization-find (make-name-finder (.getPath (io/resource "nl-ner-organization.bin"))))
(def location-find (make-name-finder (.getPath (io/resource "nl-ner-location.bin"))))

(println (tokenize "Schat user stories"))