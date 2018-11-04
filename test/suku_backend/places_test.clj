(ns suku-backend.places-test
  (:require [clojure.test :refer :all]
            [suku-backend.places :refer :all]))

(defn fetch-places []
  (list {:id 1 :name "Foo" :type "city"} {:id 2 :name "Bar" :parent 1 :type "village"}))

(deftest places
  (testing "Sub places are mapped to places"
    (is (= 1 (count (get-all-places fetch-places))))))
