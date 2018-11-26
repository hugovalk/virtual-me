(ns virtual-me-scripts.nlp.w2v-trainer
  (:require [virtual-me.tools :as tools]
            [clojure.java.io :as io])
  (:import (org.deeplearning4j.text.sentenceiterator LineSentenceIterator SentencePreProcessor)
           (org.deeplearning4j.text.tokenization.tokenizerfactory DefaultTokenizerFactory)
           (org.deeplearning4j.text.tokenization.tokenizer.preprocessor CommonPreprocessor)
           (org.deeplearning4j.models.word2vec Word2Vec Word2Vec$Builder)
           (org.deeplearning4j.models.embeddings.loader WordVectorSerializer)
           (java.io File)))

(def w2v-corpus-path (atom (str tools/current-directory-as-string "/dev-resources/w2v/english.txt")))
(def model-path (atom (str tools/current-directory-as-string "/models/en/w2v")))

(defn sentence-preprocessor []
  (reify SentencePreProcessor
    (preProcess [this sentence]
      (.toLowerCase sentence))))

(defn create-sentence-iterator [path]
  (let [iter (LineSentenceIterator. (io/file path))]
    (.setPreProcessor iter (sentence-preprocessor))
    iter))

(defn create-tokenizer-factory []
  (let [f (DefaultTokenizerFactory.)]
    (.setTokenPreProcessor f (CommonPreprocessor.))
    f))

(defn word2vec [sentence-iterator tokenizer-factory]
  (let [b (Word2Vec$Builder.)]
    (.minWordFrequency b 5)
    (.layerSize b 100)
    (.seed b 42)
    (.windowSize b 5)
    (.epochs b 1)
    (.iterations b 1)
    (.iterate b sentence-iterator)
    (.tokenizerFactory b tokenizer-factory)
    (.build b)));

(defn -main []
  (do
    (tools/swap-atom-with-path! w2v-corpus-path "Corpus text file path")
    (tools/swap-atom-with-path! model-path "Models directory")
    (.mkdirs (File. @model-path))
    (let [vec (word2vec (create-sentence-iterator @w2v-corpus-path)
                        (create-tokenizer-factory))]
      (.fit vec)
      (WordVectorSerializer/writeWord2VecModel vec (str @model-path "/word-vector-model.txt")))
    (println "done...")))