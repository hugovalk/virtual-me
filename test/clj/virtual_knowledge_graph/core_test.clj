(ns virtual-knowledge-graph.core-test
  (:use midje.sweet
        virtual-knowledge-graph.asami.core)
  (:require [clojure.spec.alpha :as spec]
            [virtual-knowledge-graph.core :as kg])
  (:import (java.util UUID)))

(defn create-node []
  {::kg/node-id (UUID/randomUUID)
   ::kg/node-name :father})

(fact-group "Asami GraphStore tests"
            (fact "A node is properly converted to Asami."
                  (let [node (create-node)
                        asami-entity (to-asami-entity node)]
                    asami-entity => {:db/ident (::kg/node-id node)
                                   ::kg/node-name :father}))
            (fact "A Asami entity is properly converted to a node."
                  (let [id (UUID/randomUUID)
                        asami-entity {::kg/node-name :father}
                        node (to-kg-node id asami-entity)]
                    node => {::kg/node-id id ::kg/node-name :father}))
            (fact "An entity can be stored and retrieved."
                  (let [node (create-node)
                        graph (init-inmemory-graph-store)
                        id (save-node graph node)]
                    (get-node graph (::kg/node-id node)) => node)))
