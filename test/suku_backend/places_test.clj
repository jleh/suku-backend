(ns suku-backend.places-test
  (:require [clojure.test :refer :all]
            [suku-backend.places :refer :all]))

(defn fetch-places []
  (list {:id 1 :name "Foo"} {:id 2 :name "Bar" :parent 1}))

(deftest places
  (testing "Sub places are mapped to places"
    (is (= 2 (count (get-all-places fetch-places))))))
