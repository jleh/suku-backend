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
