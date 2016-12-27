(ns aoc2016.day01
  (:require [clojure.java.io :as io]
            [clojure.string :refer [split]]
            [aoc2016.util :refer [index-of]]))

(def input-file (-> "day1.in" io/resource io/file))

; Cardinal directions ordered so that turning right traverses the list forward
(def directions [:N :E :S :W])

(defn get-input []
  (let [data (-> input-file slurp (split #", "))]
    (partition 2
      (interleave
        (map #(keyword (subs % 0 1)) data)
        (map #(read-string (subs % 1)) data) ))))

(defn next-direction
  "Given a currently facing directon (N/S/E/W) and a turning direction (L/R)
  return the direction facing after turning"
  [direction turn]
  (let [dir-index (index-of direction directions)
        next-index (+ dir-index (if (= turn :R) 1 -1))]
    (nth directions (mod next-index 4))))

(defn move
  "For a given cardinal direction and distance returns the movement vector."
  [direction distance]
  (case direction
    :N [0 distance]
    :E [distance 0]
    :S [0 (- distance)]
    :W [(- distance) 0]))

(defn bi-range [start stop]
  (range start stop (if (>= stop start) 1 -1)))

(defn points-between
  "Returns points between two points on a straight line"
  [p1 p2]
  (let [x1 (first p1) y1 (second p1) x2 (first p2) y2 (second p2)]
    (cond
      (= x1 x2) (partition 2 (interleave (repeat x1) (bi-range y1 y2)))
      (= y1 y2) (partition 2 (interleave (bi-range x1 x2) (repeat y1)))
      :else (throw (Exception. "Points must be on a vertical or horizontal line")) )))

(def starting-point [0 0])

(def starting-direction :N)

(defn sparse-travel-path
  "For given input directions return the path taken on the map.
  Contains only turning points."
  [input-data]
  (loop [data input-data
         direction starting-direction
         point starting-point
         path []]
    (if (empty? data) path
      (let [turn (-> data first first)
            distance (-> data first second)
            next-direction (next-direction direction turn)
            move (move next-direction distance)
            next-point (map + point move)]
        (recur
          (rest data)
          next-direction
          next-point
          (conj path next-point))))))

(defn dense-travel-path
  "For given input directions return the path taken on the map.
  Contains all points traveled."
  [input-data]
  (loop [data input-data
         direction starting-direction
         point starting-point
         path []]
    (if (empty? data)
      (concat path [point])
      (let [turn (-> data first first)
            distance (-> data first second)
            next-direction (next-direction direction turn)
            move (move next-direction distance)
            next-point (map + point move)]
        (recur
          (rest data)
          next-direction
          next-point
          (concat path (points-between point next-point)))))))

(defn first-duplicate
  "Returns the first item which appears twice in the given collection"
  [coll]
  (loop [coll coll seen {}]
    (let [item (first coll)]
      (if (contains? seen item) item
        (recur (rest coll) (assoc seen item 1))))))

(defn distance-to
  "Calculates distance along the grid from the starting point (0 0) to given point"
  [point]
  (let [sorted (sort (map #(Math/abs %) point))
        smaller (first sorted)
        larger (second sorted)]
    (+ (* 2 smaller) (- larger smaller))))

(defn main []
  (let [path (dense-travel-path (get-input))
        target (last path)]
    (println "Easter Bunny HQ coordinates: ", target)
    (println "Distance to target: ", (distance-to target))
    (println "First intersection: " (first-duplicate path))
    (println "Distance to first intersection: " (distance-to (first-duplicate path)))
))
