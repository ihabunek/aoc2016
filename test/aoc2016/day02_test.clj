(ns aoc2016.day02-test
  (:require [clojure.test :refer :all]
            [aoc2016.day02 :refer :all]))

(deftest test-next-position
  (is (= [0 0] (next-position [0 0] :U)))
  (is (= [0 1] (next-position [0 0] :D)))
  (is (= [0 0] (next-position [0 0] :L)))
  (is (= [1 0] (next-position [0 0] :R)))

  (is (= [1 0] (next-position [1 1] :U)))
  (is (= [1 2] (next-position [1 1] :D)))
  (is (= [0 1] (next-position [1 1] :L)))
  (is (= [2 1] (next-position [1 1] :R)))

  (is (= [2 1] (next-position [2 2] :U)))
  (is (= [2 2] (next-position [2 2] :D)))
  (is (= [1 2] (next-position [2 2] :L)))
  (is (= [2 2] (next-position [2 2] :R))))

(deftest test-follow-directions
  (let [positions (follow-directions [1 1] [:U :L :L])]
    (is [5 2 1 1] (map digits positions))))