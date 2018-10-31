(ns suku-backend.gedcom
  (:require [gedcom.core :as gedcom]))

(defn read-data [path]
  (gedcom/parse-gedcom path))

(defn data []
  (read-data "suku.ged"))

(defn get-individual-keys [data]
  (filter #(re-matches #"@I.*" %) (keys data)))

(defn get-individuals [data]
  (map #(get data %) (get-individual-keys data)))

(defn get-first [data key]
  (first (get data key)))

(defn get-name [person]
  (let [name-data (get-first person "NAME")]
    {:first (:data (get-first name-data "GIVN"))
     :last (:data (get-first name-data "SURN"))}))

(defn get-date-and-place [event]
  {:date (:data (get-first event "DATE"))
   :place (:data (get-first event "PLAC"))})

(defn get-birth [individual]
  (get-date-and-place (get-first individual "BIRT")))

(defn get-death [individual]
  (get-date-and-place (get-first individual "DEAT")))

(defn to-person [individual]
  {:name (get-name individual)
   :sex (:data (get-first individual "SEX"))
   :birth (get-birth individual)
   :death (get-death individual)})

(defn list-persons [individuals]
  (map #(to-person %) individuals))
