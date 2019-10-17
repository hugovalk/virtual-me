(ns virtual-me-scripts.nlp.models-downloader
  (:require [clj-http.client :as http]
            [clojure.java.io :as io]
            [virtual-me.tools :as tools]))

(def models-path (atom "."))
(def base-url "http://opennlp.sourceforge.net/models-1.5")

(defn model-url [name]
  (str base-url "/" name ".bin"))

(defn download-model! [name]
  (let [response (http/get (model-url name) {:as :byte-array :throw-exceptions :false})]
    (if (= (:status response) 200)
      (:body response))))

(defn save-model! [folder name data]
  (if (and
        (not (nil? data))
        (not (nil? name)))
    (with-open [w (io/output-stream (str folder "/" name ".bin"))]
      (.write w data))))

(defn fetch-model! [folder name]
  (let [data (download-model! name)]
    (save-model! folder name data)))

(defn models-en []
  (str @models-path "/en"))
(defn models-nl []
  (str @models-path "/nl"))

(defn models []
  [{:name "en-chunker" :folder (models-en)}
   {:name"en-ner-date" :folder (models-en)}
   {:name"en-ner-location" :folder (models-en)}
   {:name"en-ner-money" :folder (models-en)}
   {:name"en-ner-organization" :folder (models-en)}
   {:name"en-ner-percentage" :folder (models-en)}
   {:name"en-ner-person" :folder (models-en)}
   {:name"en-ner-time" :folder (models-en)}
   {:name"en-parser-chunking" :folder (models-en)}
   {:name"en-pos-maxent" :folder (models-en)}
   {:name"en-pos-perceptron" :folder (models-en)}
   {:name"en-sent" :folder (models-en)}
   {:name"en-token" :folder (models-en)}
   {:name"nl-ner-location" :folder (models-nl)}
   {:name"nl-ner-misc" :folder (models-nl)}
   {:name"nl-ner-organization" :folder (models-nl)}
   {:name"nl-ner-person" :folder (models-nl)}
   {:name"nl-pos-maxent" :folder (models-nl)}
   {:name"nl-pos-perceptron" :folder (models-nl)}
   {:name"nl-sent" :folder (models-nl)}
   {:name"nl-token" :folder (models-nl)}])

(defn -main []
  (do
    (tools/swap-atom-via-prompt! models-path "Models directory:")
    (last
      (for [model (models)]
        (let [{:keys [name folder]} model]
          (println "downloading" name "in" folder "...")
          (fetch-model! folder name))))
    (println "done...")))