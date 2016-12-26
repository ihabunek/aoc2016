(ns aoc2016.day01
  (:require [clojure.java.io :as io]
            [clojure.string :refer [split]]
            [aoc2016.util :refer [index-of]]))

(def input-file (-> "day1.in" io/resource io/file))

(def directions [:N :E :S :W])

(defn get-input []
  (let [data (-> input-file slurp (split #", "))]
    (partition 2
      (interleave
        (map #(keyword (subs % 0 1)) data)
        (map #(read-string (subs % 1)) data) ))))

(defn next-direction [direction turn]
  (let [dir-index (index-of direction directions)
        next-index (+ dir-index (if (= turn :R) 1 -1))]
    (nth directions (mod next-index 4))))

(defn move [direction distance]
  (case direction
    :N [0 distance]
    :E [distance 0]
    :S [0 (- distance)]
    :W [(- distance) 0]))

(defn target-coordinates [data]
  (loop [data data direction :N x 0 y 0]
    (if (empty? data)
      [x, y]
      (let [turn (-> data first first)
            distance (-> data first second)
            next-direction (next-direction direction turn)
            move (move next-direction distance)]
        (recur
          (rest data)
          next-direction
          (+ x (first move))
          (+ y (second move)))))))

(defn distance-to [coordinates]
  (let [sorted (sort (map #(Math/abs %) coordinates))
        smaller (first sorted)
        larger (second sorted)]
    (+ (* 2 smaller) (- larger smaller))))

(defn main []
  (distance-to (target-coordinates (get-input))))
