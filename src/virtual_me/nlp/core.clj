(ns virtual-me.nlp.core
  (:require [virtual-me.nlp.en :as en])
  (:require [virtual-me.nlp.nl :as nl]))

(defmulti tokenize (fn [text] (:language text)))
(defmethod tokenize :nl
  [text] (nl/tokenize (:text text)))
(defmethod tokenize :en
  [text] (en/tokenize (:text text)))

(defmulti detect-sentences (fn [text] (:language text)))
(defmethod detect-sentences :nl
  [text] (nl/get-sentences (:text text)))
(defmethod detect-sentences :en
  [text] (en/get-sentences (:text text)))

(defmulti tag-pos (fn [tokenized-text] (:language [tokenized-text])))
(defmethod tag-pos :nl
  [tokenized-text] (nl/pos-tag (:tokens tokenized-text)))
(defmethod tag-pos :en
  [tokenized-text] (en/pos-tag (:tokens tokenized-text)))

(defmulti tag-persons (fn [tokenized-text] (:language [tokenized-text])))
(defmethod tag-persons :nl
  [tokenized-text] (nl/person-find (:tokens tokenized-text)))
(defmethod tag-persons :en
  [tokenized-text] (en/person-find (:tokens tokenized-text)))

(defmulti tag-organizations (fn [tokenized-text] (:language [tokenized-text])))
(defmethod tag-organizations :nl
  [tokenized-text] (nl/organization-find (:tokens tokenized-text)))
(defmethod tag-organizations :en
  [tokenized-text] (en/organization-find (:tokens tokenized-text)))

(defmulti tag-locations (fn [tokenized-text] (:language [tokenized-text])))
(defmethod tag-locations :nl
  [tokenized-text] (nl/location-find (:tokens tokenized-text)))
(defmethod tag-locations :en
  [tokenized-text] (en/location-find (:tokens tokenized-text)))

(defmulti tag-dates (fn [tokenized-text] (:language [tokenized-text])))
(defmethod tag-dates :nl
  [tokenized-text] (list))
(defmethod tag-dates :en
  [tokenized-text] (en/date-find (:tokens tokenized-text)))


