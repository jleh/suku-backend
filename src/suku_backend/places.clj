(ns suku-backend.places
  (:require [clojure.java.jdbc :as jdbc]
            [suku-backend.db :as db]))

(defn child? [place other]
  (= (:id place) (:parent other)))

(defn get-child-places [place places]
  (map #(:id %) (filter #(child? place %) places)))

(defn get-places []
  (let [places (jdbc/query db/db ["select * from place"])]
    (map #(assoc % :children (get-child-places % places)) places)))
