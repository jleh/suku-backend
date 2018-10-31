(ns user
  (:require [ragtime.jdbc :as jdbc]
            [ragtime.repl :as repl]))

(require '[environ.core :refer [env]])

(defn load-config []
  {:datastore  (jdbc/sql-database {:connection-uri (env :database-url)})
   :migrations (jdbc/load-resources "migrations")})

(defn migrate []
  (repl/migrate (load-config)))

(defn rollback []
  (repl/rollback (load-config)))
