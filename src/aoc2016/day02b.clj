(ns aoc2016.day02b
  (:require [clojure.java.io :as io]
            [clojure.string :refer [split split-lines join]]
            [clojure.pprint :refer [pprint]]
            [aoc2016.util :refer [index-of]]))

(def input-file (-> "day2.in" io/resource io/file))

(defn parse-line [line]
  (map keyword (split line #"")))

(defn parse-input [input]
  (->> input split-lines (map parse-line)))

;         x ------------------->   ; y
(def grid [[nil nil  1  nil nil]   ; |
           [nil  2   3   4  nil]   ; |
           [ 5   6   7   8   9 ]   ; |
           [nil \A  \B  \C  nil]   ; |
           [nil nil \D  nil nil]]) ; v

(def movements { :U [0 -1] :D [0 1] :L [-1 0] :R [1 0] })

(defn get-digit
  "For a given position [x y] on the keypad returns the digit or nil if none exists."
  [position]
  (-> grid
    (nth (second position) [])
    (nth (first position) nil)))

(defn next-position [start direction]
  (let [next-if-exists (map + start (get movements direction))]
    (if (nil? (get-digit next-if-exists))
      start
      next-if-exists)))

(defn follow-directions [start directions]
  (loop [directions directions
         position start
         result [start]]
    (if (empty? directions) result
      (let [next-position (next-position position (first directions))]
        (recur (rest directions) next-position (conj result next-position))))))

(defn get-code [inputs start]
  (loop [inputs inputs start start result []]
    (if (empty? inputs) result
      (let [position (last (follow-directions start (first inputs)))]
        (recur (rest inputs) position
          (conj result (get-digit position)))))))

(defn main []
  (let [inputs (parse-input (slurp input-file))
        start [0 2]]

    (println "Advent of Code, day 2, part 2")
    (println "=============================")
    (println "The code is: " (join (get-code inputs start)))
))
