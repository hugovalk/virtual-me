(ns virtual-knowledge-graph.intents
  (:use virtual-knowledge-graph.core)
  (:require [virtual-me.bot.intents.specs :as ibspec]
            [clojure.spec.alpha :as spec]))

(defn add-relation-to-entity []
  "Relation is added to the knowledge graph.")

(spec/fdef add-relation-to-entity
  :args (spec/cat)
  :ret string?)

(def intents
  {:add-relation-to-node {::ibspec/tag "add-relation-to-node"
                          ::ibspec/intent-type ::ibspec/function-intent
                          ::ibspec/pattern [""]
                          ::ibspec/function add-relation-to-entity}})
