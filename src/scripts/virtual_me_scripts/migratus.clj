(ns virtual-me-scripts.migratus
  (:require [virtual-me.util.config :refer [config-get]]))

{:store :database
 :migration-dir "db-migrations"
 :db (str "jdbc:sqlite:" (config-get :db :path))}
