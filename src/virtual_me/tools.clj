(ns virtual-me.tools
  (:import (java.util Scanner)))

(defn swap-atom-via-prompt
  [value prompt]
  (swap! value
         (fn [current-state]
           (if (nil? (System/console))
             (do
               (print prompt)
               (flush)
               (.next (new Scanner (System/in))))
             (String/valueOf (.readPassword (System/console) prompt nil))))))
