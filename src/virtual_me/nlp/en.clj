(ns virtual-me.nlp.en
  (:use opennlp.nlp)
  (:require [clojure.java.io :as io]))

(def get-sentences (make-sentence-detector (.getPath (io/resource "en-sent.bin"))))
(def tokenizer (make-tokenizer (.getPath (io/resource "en-token.bin"))))
(def pos-tagger (make-pos-tagger (.getPath (io/resource "en-pos-maxent.bin"))))
(def person-finder (make-name-finder (.getPath (io/resource "en-ner-person.bin"))))
(def organization-finder (make-name-finder (.getPath (io/resource "en-ner-organization.bin"))))
(def location-finder (make-name-finder (.getPath (io/resource "en-ner-location.bin"))))
(def date-finder (make-name-finder (.getPath (io/resource "en-ner-date.bin"))))
