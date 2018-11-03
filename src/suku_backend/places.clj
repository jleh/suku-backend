(ns suku-backend.places
  (:require [clojure.java.jdbc :as jdbc]
            [suku-backend.db :as db]))

(defn child? [place other]
  (= (:id place) (:parent other)))

(defn get-child-places [place places]
  (filter #(child? place %) places))

(defn get-places-by-type [places type]
  (filter #(= type (:type %)) places))

(defn place-with-children [place places]
  (assoc place :children (get-child-places place places)))

(defn map-children [places group]
  (map #(place-with-children % places) group))

(defn map-sub-places-to-places [places]
  (let [buildings (get-places-by-type places "building")
        farms (map-children buildings (get-places-by-type places "farm"))
        villages (map-children farms (get-places-by-type places "village"))]
    (map-children villages (get-places-by-type places "city"))))

(defn fetch-places-from-db []
  (jdbc/query db/db ["select * from place"]))

(defn get-all-places [fetch]
  (map-sub-places-to-places (fetch)))

(defn get-places []
  (get-all-places fetch-places-from-db))
