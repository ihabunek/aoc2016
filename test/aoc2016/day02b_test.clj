(ns aoc2016.day02b-test
  (:require [clojure.test :refer :all]
            [aoc2016.day02b :refer :all]))

(deftest test-get-digit
  (is (= 1 (get-digit [2 0])))
  (is (= nil (get-digit [0 0])))
  (is (= 8 (get-digit [3 2])))
  (is (= \C (get-digit [3 3])))
  (is (= nil (get-digit [3 4])))
  (is (= nil (get-digit [50 50])))
  (is (= nil (get-digit [-50 -50]))))


(deftest test-next-position
  (is (= [2 0] (next-position [2 0] :U)))
  (is (= [2 1] (next-position [2 0] :D)))
  (is (= [2 0] (next-position [2 0] :L)))
  (is (= [2 0] (next-position [2 0] :R)))

  (is (= [1 1] (next-position [1 1] :U)))
  (is (= [1 2] (next-position [1 1] :D)))
  (is (= [1 1] (next-position [1 1] :L)))
  (is (= [2 1] (next-position [1 1] :R)))

  (is (= [2 1] (next-position [2 2] :U)))
  (is (= [2 3] (next-position [2 2] :D)))
  (is (= [1 2] (next-position [2 2] :L)))
  (is (= [3 2] (next-position [2 2] :R))))
