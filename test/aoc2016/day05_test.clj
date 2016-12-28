(ns aoc2016.day05-test
  (:require [clojure.test :refer :all]
            [aoc2016.day05 :refer :all]))

(deftest test-get-code
  (is (= "18f47a30" (get-code "abc"))))
