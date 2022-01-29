(ns virtual-me-scripts.nlp.w2v-trainer
  (:require [virtual-me.tools :as tools]
            [virtual-me-scripts.nlp.dl4j.ui :as ui]
            [clojure.java.io :as io])
  (:import (org.deeplearning4j.text.sentenceiterator LineSentenceIterator SentencePreProcessor)
           (org.deeplearning4j.text.tokenization.tokenizerfactory DefaultTokenizerFactory)
           (org.deeplearning4j.text.tokenization.tokenizer.preprocessor CommonPreprocessor)
           (org.deeplearning4j.models.word2vec Word2Vec Word2Vec$Builder)
           (org.deeplearning4j.models.embeddings.loader WordVectorSerializer)
           (java.io File)
           (org.deeplearning4j.plot BarnesHutTsne BarnesHutTsne$Builder)
           (java.util ArrayList)
           (org.nd4j.linalg.factory Nd4j)))

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
    (.build b)))

(defn build-cache-list! [vocab-cache]
  (let [array (ArrayList.)
        r (take (.numWords vocab-cache) (range))]
    (doseq [i r]
      (.add array (.wordAtIndex vocab-cache i)))
    array))

(defn tsne-model []
  (let [b (BarnesHutTsne$Builder.)]
    (.setMaxIter b 100)
    (.theta b 0.5)
    (.normalize b true)
    (.learningRate b 500)
    (.useAdaGrad b false)
    (.build b)))

(defn fit-tsne [vec]
  (let [tsne (tsne-model)
        vectors (iterator-seq (.vectors (.lookupTable vec)))]
    (.fit tsne (reduce
                 (fn [a b] (Nd4j/vstack (list a b)))
                 vectors))
    tsne))

(defn -main []
  (do
    (ui/start-ui-server)
    (tools/swap-atom-with-path! w2v-corpus-path "Corpus text file path")
    (tools/swap-atom-with-path! model-path "Models directory")
    (.mkdirs (.getParentFile (File. @model-path)))
    (let [vec (word2vec (create-sentence-iterator @w2v-corpus-path)
                        (create-tokenizer-factory))]
      (.fit vec)
      (WordVectorSerializer/writeWord2VecModel vec (str @model-path "/word-vector-model.txt"))
      (let [tsne (fit-tsne vec)]
        (.saveAsFile tsne
                     (build-cache-list! (.vocab vec))
                     (str @model-path "/word-vector-tsne.txt"))))
    (println "done...")))
