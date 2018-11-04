(ns suku-backend.places
  (:require [clojure.java.jdbc :as jdbc]
            [suku-backend.db :as db]
            [ring.util.response :refer [response status]]
            [clojure.tools.logging :as log]))

(use 'clojure.tools.logging)

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

(defn place-id->gedcom-id [id]
  (format "P%04d" id))

(defn gedcom-id->place-id [id]
  (Integer/valueOf (last (last (re-seq #"(^P[0]*)(\d*)" id)))))

(defn fetch-places-from-db []
  (jdbc/query db/db ["select * from place"]))

(defn fetch-place-from-db [id]
  (jdbc/query db/db ["select * from place where id = ?" (gedcom-id->place-id id)]))

(defn update-place-db [data]
  (jdbc/update! db/db :place {:name (:name data)
                              :lat (:lat data)
                              :lng (:lng data)
                              :cover_image (:cover_image data)
                              :image (:image data)
                              :url (:url data)} ["id = ?" (:id data)]))

(defn transform-ids [places]
  (map #(assoc %
          :id (place-id->gedcom-id (:id %))
          :parent (if (some? (:parent %)) (place-id->gedcom-id (:parent %)))) places))

(defn get-all-places [fetch]
  (map-sub-places-to-places (transform-ids (fetch))))

(defn get-places []
  (get-all-places fetch-places-from-db))

(defn update-place [db-place values]
  (log/info "Updated place id: " (:id db-place))
  (update-place-db (merge db-place values)))

(defn update-place-request [requrest]
  (let [data (:body requrest)
        id (:id (:route-params requrest))
        place (first (fetch-place-from-db id))]
    (if (nil? place)
      (status (response "Place not found") 404)
      (do
        (update-place place data)
        (response "Place updated")))))
