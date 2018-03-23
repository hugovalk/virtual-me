(ns virtual-me.nlp.nl
  (:use opennlp.nlp)
  (:require [clojure.java.io :as io]))

(def get-sentences (make-sentence-detector (.getPath (io/resource "nl-sent.bin"))))
(def tokenizer (make-tokenizer (.getPath (io/resource "nl-token.bin"))))
(def pos-tagger (make-pos-tagger (.getPath (io/resource "nl-pos-maxent.bin"))))
(def person-finder (make-name-finder (.getPath (io/resource "nl-ner-person.bin"))))
(def organization-finder (make-name-finder (.getPath (io/resource "nl-ner-organization.bin"))))
(def location-finder (make-name-finder (.getPath (io/resource "nl-ner-location.bin"))))
