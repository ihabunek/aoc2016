(ns aoc2016.day03-test
  (:require [clojure.test :refer :all]
            [aoc2016.day03 :refer :all]))

(deftest test-valid-triangle
  (is (= false (is-valid-triangle [5 10 25])))
  (is (= false (is-valid-triangle [25 10 5])))
  (is (= false (is-valid-triangle [10 5 25])))
  (is (= false (is-valid-triangle [5 10 15])))
  (is (= true (is-valid-triangle [6 10 15])))
  (is (= true (is-valid-triangle [5 11 15])))
  (is (= true (is-valid-triangle [5 10 14])))
)