(ns suku-backend.world-events
  (:require [clojure.java.jdbc :as jdbc]
            [suku-backend.db :as db]))

(defn query-world-events []
  (jdbc/query db/db ["select * from world_events order by start_year"]))

(defn get-world-events []
  (query-world-events))
