(ns suku-backend.places
  (:require [clojure.java.jdbc :as jdbc]
            [suku-backend.db :as db]))

(defn child? [place other]
  (= (:id place) (:parent other)))

(defn get-child-places-ids [place places]
  (map #(:id %) (filter #(child? place %) places)))

(defn map-including-places-ids-to-places [places]
  (map #(assoc % :children (get-child-places-ids % places)) places))

(defn fetch-places-from-db []
  (jdbc/query db/db ["select * from place"]))

(defn get-all-places [fetch]
  (map-including-places-ids-to-places (fetch)))

(defn get-places []
  (get-all-places fetch-places-from-db))
