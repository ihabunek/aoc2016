(ns aoc2016.day07-test
  (:require [clojure.test :refer :all]
            [aoc2016.day07 :refer :all]))

(deftest test-abba
  (is (abba? "abba"))
  (is (abba? "bddb"))
  (is (abba? "xyyx"))
  (is (abba? "ioxxoj"))

  (is (not (abba? "mnop")))
  (is (not (abba? "qrst")))
  (is (not (abba? "abcd")))
  (is (not (abba? "aaaa")))
  (is (not (abba? "qwer")))
  (is (not (abba? "tyui")))
  (is (not (abba? "asdfgh")))
  (is (not (abba? "zxcvbn"))))

(deftest test-abas
  (is (= ["zaz" "zbz"] (abas "zazbz"))))

(deftest test-ssl?
  (is (ssl? "aba[bab]xyz"))
  (is (ssl? "aaa[kek]eke"))
  (is (ssl? "zazbz[bzb]cdb"))
  (is (ssl? "zazbz[abzb]cdb"))
  (is (ssl? "zazbz[abzba]cdb"))
  (is (not (ssl? "xyx[xyx]xyx"))))