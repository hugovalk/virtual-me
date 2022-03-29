(ns virtual-knowledge-graph.core
  (:require [clojure.spec.alpha :as spec]))

(spec/def ::node-id uuid?)
(spec/def ::node-name keyword?)
(spec/def ::node (spec/keys :req [::node-id
                                  ::node-name]))

(spec/def ::rel-name string?)
(spec/def ::rel-from uuid?)
(spec/def ::rel-to uuid?)
(spec/def ::relation (spec/keys :req [::rel-name
                                      ::rel-from
                                      ::rel-to]))


(defprotocol GraphStore
  (save-node [this node] "Save or update a node in the graph.")
  (save-relation [this relation] "Save or update a relation between two nodes in the graph.")
  (get-node [this id] "Get a node from the graph.")
  (query [this query] "Query the graph."))
