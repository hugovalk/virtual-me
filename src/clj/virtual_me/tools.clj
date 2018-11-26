(ns virtual-me.tools
  (:require [clojure.string :as str]
            [clojure.java.io :as io])
  (:import (java.util Scanner)))

(def current-directory-as-string
  (str/join ""
            (drop-last 2 (.getAbsolutePath (io/file ".")))))

(defn do-prompt! [prompt]
  (if (nil? (System/console))
    (do
      (print prompt)
      (flush)
      (.next (new Scanner (System/in))))
    (String/valueOf (.readLine (System/console) prompt nil))))


(defn swap-atom-via-prompt!
  [value prompt]
  (swap! value #{(do-prompt! prompt)}))

(defn change-path!
  [value description]
  (let [yn (do-prompt! (str "Do you want to change " (.toLowerCase description) " (" @value ") y/n? "))]
    (cond
      (= yn "y") true
      (= yn "n") false
      :else (change-path! value description))))

(defn swap-atom-with-path!
  [value description]
  (if (change-path! value description)
      (swap-atom-via-prompt! value (str description ": "))))