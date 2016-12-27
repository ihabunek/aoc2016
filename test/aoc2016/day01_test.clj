(ns aoc2016.day01-test
  (:require [clojure.test :refer :all]
            [aoc2016.day01 :refer :all]))

(deftest test-next-direction
  (is (= :E (next-direction :N :R)))
  (is (= :S (next-direction :E :R)))
  (is (= :W (next-direction :S :R)))
  (is (= :N (next-direction :W :R)))
  (is (= :W (next-direction :N :L)))
  (is (= :N (next-direction :E :L)))
  (is (= :E (next-direction :S :L)))
  (is (= :S (next-direction :W :L))))

(deftest test-points-between
  (is (= '((1 0) (1 1) (1 2) (1 3) (1 4)) (points-between [1 0] [1 5])))
  (is (= '((5 1) (4 1) (3 1) (2 1)) (points-between [5 1] [1 1]))))

(deftest test-first-duplicate
  (is (= 5 (first-duplicate [1 2 3 4 5 6 7 8 9 5 4 3 2 1]))))
