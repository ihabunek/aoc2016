(ns aoc2016.day02
  (:require [clojure.java.io :as io]
            [clojure.string :refer [split split-lines]]
            [clojure.pprint :refer [pprint]]
            [aoc2016.util :refer [index-of]]))

(def input-file (-> "day2.in" io/resource io/file))

(defn parse-line [line]
  (map keyword (split line #"")))

(defn parse-input [input]
  (->> input split-lines (map parse-line)))

; Keypad grid
;------------
;   x 0 1 2
; y   -----
; 0 | 1 2 3
; 1 | 4 5 6
; 2 | 7 8 9

(def starting-position '(1 1))

(def movements { :U '(0 -1) :D '(0 1)
                 :L '(-1 0) :R '(1 0) })

(def digits { '(0 0) 1
              '(1 0) 2
              '(2 0) 3
              '(0 1) 4
              '(1 1) 5
              '(2 1) 6
              '(0 2) 7
              '(1 2) 8
              '(2 2) 9 })

(defn next-position [start direction]
  (let [movement (get movements direction)]
    (->> (map + start movement)
      (map #(max % 0))
      (map #(min % 2)) )))

(defn follow-directions [start directions]
  (loop [directions directions position start result [start]]
    (if (empty? directions) result
      (let [next-position (next-position position (first directions))]
        (recur (rest directions) next-position (conj result next-position))))))

(defn get-code [inputs start]
  (loop [inputs inputs start start result []]
    (if (empty? inputs) result
      (let [position (last (follow-directions start (first inputs)))]
        (recur (rest inputs) position
          (conj result (get digits position)))))))

(defn main []
  (let [inputs (parse-input (slurp input-file))]

    (println "Advent of Code, day 2")
    (println "=====================")
    (println "The code is: " (get-code inputs starting-position))
))
