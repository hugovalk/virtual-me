(ns virtual-knowledge-graph.asami.core
  (:require [asami.core :as as]))

(def db-uri "asami:mem://dbname")

(as/create-database db-uri)
(def conn (as/connect db-uri))

(def first-movies [{:movie/title "Explorers"
                    :movie/genre "adventure/comedy/family"
                    :movie/release-year 1985}
                   {:movie/title "Demolition Man"
                    :movie/genre "action/sci-fi/thriller"
                    :movie/release-year 1993}
                   {:movie/title "Johnny Mnemonic"
                    :movie/genre "cyber-punk/action"
                    :movie/release-year 1995}
                   {:movie/title "Toy Story"
                    :movie/genre "animation/adventure"
                    :movie/release-year 1995}])


@(as/transact conn {:tx-data first-movies})

(def db (as/db conn))
(as/q '[:find ?movie-title
        :where [?m :movie/title ?movie-title]] db)

(as/q '[:find ?title ?year ?genre
        :where
        [?m :movie/title ?title]
        [?m :movie/release-year ?year]
        [?m :movie/genre ?genre]
        [(> ?year 1990)]] db)
