(ns suku-backend.world-events
  (:require [clojure.java.jdbc :as jdbc]))

(def db {:dbtype "postgresql"
         :dbname "postgres"
         :host "localhost"
         :user "postgres"
         :password "postgres"})

(defn query-world-events []
  (jdbc/query db ["select * from world_events order by start_year"]))

(defn get-world-events []
  (query-world-events))
