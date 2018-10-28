(ns user)

(ns user
  (:require [ragtime.jdbc :as jdbc]))

(def config
  {:datastore  (jdbc/sql-database {:connection-uri "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres"})
   :migrations (jdbc/load-resources "migrations")})
