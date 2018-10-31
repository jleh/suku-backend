(ns suku-backend.db
  (:require [ragtime.jdbc :as jdbc]
            [ragtime.repl :as repl]))

(require '[environ.core :refer [env]])

(def db (env :database-url))

(defn load-config []
  {:datastore  (jdbc/sql-database db)
   :migrations (jdbc/load-resources "migrations")})

(defn migrate []
  (repl/migrate (load-config)))
