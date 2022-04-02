(ns virtual-knowledge-graph.asami.core
  (:require [asami.core :as as]
            [virtual-knowledge-graph.core :as kg]
            [virtual-me.util.config :as cf]
            [clojure.spec.alpha :as spec]
            [clojure.set :refer [rename-keys]]))

(defn to-asami-entity [node]
  (rename-keys node {::kg/node-id :db/ident}))
(defn to-kg-node [id asami-entity]
  (assoc asami-entity ::kg/node-id id))

(defrecord AsamiGraphStore [conn]
  kg/GraphStore
  (save-node [_ node]
    (if (spec/valid? ::kg/node node)
      (as/transact conn {:tx-data (to-asami-entity node)})
      nil))
  (save-relation [_ relation]
    (as/transact conn {:tx-data [[:db/add
                       (::kg/rel-from relation)
                       (::kg/rel-name relation)
                       (::kg/rel-to relation)]]}))
  (get-node [_ id]
    (let [node (as/entity (as/db conn) id)]
      (to-kg-node id node)))
  (relations-for-node [this id]
    (let [query `[:find ?rel :where [~id ?rel]]
          res (as/q query (as/db conn))]
      (vec (flatten res))))
  (query [_ query] true))

(defn- init-graph-store [uri]
  (as/create-database uri)
  (let [conn (as/connect uri)]
    (AsamiGraphStore. conn)))

(defn init-inmemory-graph-store []
  (init-graph-store (str "asami:mem://graphstore" (java.util.UUID/randomUUID))))

(defn init-local-graph-store []
  (let [uri (str "asami:local://" (cf/config-get :graph :db :path))]
    (init-graph-store uri)))
