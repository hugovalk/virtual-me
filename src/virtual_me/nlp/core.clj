(ns virtual-me.nlp.core
  (:require [virtual-me.nlp.en :as en])
  (:require [virtual-me.nlp.nl :as nl]))

(defmulti tokenize (fn [text] (:language text)))
(defmulti detect-sentences (fn [text] (:language text)))
(defmulti tag-pos (fn [tokenized-text] (:language [tokenized-text])))
(defmulti tag-persons (fn [tokenized-text] (:language [tokenized-text])))
(defmulti tag-organizations (fn [tokenized-text] (:language [tokenized-text])))
(defmulti tag-locations (fn [tokenized-text] (:language [tokenized-text])))
(defmulti tag-dates (fn [tokenized-text] (:language [tokenized-text])))

(defmethod tokenize :nl
  [text] (nl/tokenize (:content text)))
(defmethod tokenize :en
  [text] (en/tokenize (:content text)))

