(ns suku-backend.places
  (:require [clojure.java.jdbc :as jdbc]
            [suku-backend.db :as db]))

(defn get-places []
  (jdbc/query db/db ["select * from place"]))
