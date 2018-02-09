(ns virtual-me.nlp.core
  (:require [virtual-me.nlp.en :as en])
  (:require [virtual-me.nlp.nl :as nl]))

(defn dutch
  [text] {:language :nl :text text})
(defn english
  [text] {:language :en :text text})


(defmulti tokenize (fn [text] (:language text)))
(defmethod tokenize :nl
  [text] {:language :nl :tokens (nl/tokenizer (:text text))})
(defmethod tokenize :en
  [text] {:language :en :tokens (en/tokenizer (:text text))})

(defmulti detect-sentences (fn [text] (:language text)))
(defmethod detect-sentences :nl
  [text] (nl/get-sentences (:text text)))
(defmethod detect-sentences :en
  [text] (en/get-sentences (:text text)))

(defmulti tag-pos (fn [tokenized-text] (:language [tokenized-text])))
(defmethod tag-pos :nl
  [tokenized-text] (nl/pos-tagger (:tokens tokenized-text)))
(defmethod tag-pos :en
  [tokenized-text] (en/pos-tagger (:tokens tokenized-text)))

(defmulti tag-persons (fn [tokenized-text] (:language [tokenized-text])))
(defmethod tag-persons :nl
  [tokenized-text] (nl/person-finder (:tokens tokenized-text)))
(defmethod tag-persons :en
  [tokenized-text] (en/person-finder (:tokens tokenized-text)))

(defmulti tag-organizations (fn [tokenized-text] (:language [tokenized-text])))
(defmethod tag-organizations :nl
  [tokenized-text] (nl/organization-finder (:tokens tokenized-text)))
(defmethod tag-organizations :en
  [tokenized-text] (en/organization-finder (:tokens tokenized-text)))

(defmulti tag-locations (fn [tokenized-text] (:language [tokenized-text])))
(defmethod tag-locations :nl
  [tokenized-text] (nl/location-finder (:tokens tokenized-text)))
(defmethod tag-locations :en
  [tokenized-text] (en/location-finder (:tokens tokenized-text)))

(defmulti tag-dates (fn [tokenized-text] (:language [tokenized-text])))
(defmethod tag-dates :nl
  [tokenized-text] (list))
(defmethod tag-dates :en
  [tokenized-text] (en/date-finder (:tokens tokenized-text)))


