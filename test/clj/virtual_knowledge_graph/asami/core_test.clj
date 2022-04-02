(ns virtual-knowledge-graph.asami.core-test
  (:use midje.sweet
        virtual-knowledge-graph.asami.core)
  (:require [clojure.spec.alpha :as spec]
            [virtual-knowledge-graph.core :as kg])
  (:import (java.util UUID)))

(defn create-node [name]
  {::kg/node-id (UUID/randomUUID)
   ::kg/node-name name})

(defn create-and-save [name graph]
  (let [node (create-node name)]
    (kg/save-node graph node)
    (::kg/node-id node)))

(fact-group "Asami GraphStore node tests."
            (fact "A node is properly converted to Asami."
                  (let [node (create-node :father)
                        asami-entity (to-asami-entity node)]
                    asami-entity => {:db/ident (::kg/node-id node)
                                   ::kg/node-name :father}))
            (fact "A Asami entity is properly converted to a node."
                  (let [id (UUID/randomUUID)
                        asami-entity {::kg/node-name :father}
                        node (to-kg-node id asami-entity)]
                    node => {::kg/node-id id ::kg/node-name :father}))
            (fact "An node can be stored and retrieved."
                  (let [node (create-node :father)
                        graph (init-inmemory-graph-store)
                        id (kg/save-node graph node)]
                    (kg/get-node graph (::kg/node-id node)) => node)))

(fact-group "Asami GraphStore relation tests."
            (fact "A relation between two nodes can be stored."
                  (let [graph (init-inmemory-graph-store)
                        n1-id (create-and-save :John graph)
                        n2-id (create-and-save :Pete graph)
                        relation {::kg/rel-from n1-id
                                  ::kg/rel-to n2-id
                                  ::kg/rel-name :son-of}
                        relation2 {::kg/rel-from n1-id
                                  ::kg/rel-to n2-id
                                  ::kg/rel-name :son-of2}
                        id (kg/save-relation graph relation)
                        id2 (kg/save-relation graph relation2)]
                    (kg/relations-for-node graph n1-id) => [:son-of :son-of2])))
