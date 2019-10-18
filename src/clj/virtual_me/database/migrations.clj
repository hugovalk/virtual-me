(ns virtual-me.database.migrations
  (:require [migratus.core :as migratus]))

(defn config [db]
  {:store :database
   :migration-dir "resources/migrations/"
   :init-script "init.sql"
   :init-in-transaction? true
   :migration-table-name "db_migrations"
   :db db})

