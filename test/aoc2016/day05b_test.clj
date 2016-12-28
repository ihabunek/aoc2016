(ns aoc2016.day05b-test
  (:require [clojure.test :refer :all]
            [aoc2016.day05b :refer :all]))

(deftest test-get-code
  (is (= "05ace8e3" (get-code "abc"))))
