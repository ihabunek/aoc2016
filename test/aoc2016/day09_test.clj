(ns aoc2016.day09-test
  (:require [clojure.test :refer :all]
            [aoc2016.day09 :refer :all]))

(deftest test-expand
  (is (= "ADVENT" (expand "ADVENT")))
  (is (= "ABBBBBC" (expand "A(1x5)BC")))
  (is (= "XYZXYZXYZ" (expand "(3x3)XYZ")))
  (is (= "ABCBCDEFEFG" (expand "A(2x2)BCD(2x2)EFG")))
  (is (= "(1x3)A" (expand "(6x1)(1x3)A")))
  (is (= "X(3x3)ABC(3x3)ABCY" (expand "X(8x2)(3x3)ABCY")))
)