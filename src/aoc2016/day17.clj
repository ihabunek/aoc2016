(ns aoc2016.day17
  (:require [digest :refer [md5]]
            [clojure.string :refer [join]]))

; (def input "vwbaicqe") ; my input
(def input "ihgpwlah")
; (def input "kglvqrro")
; (def input "ulqzkmiv")

(def directions [\U \D \L \R])

(def grid-size 4)

(def target-pos (repeat 2 (dec grid-size)))

(defn door-open?
  "Door is open if character from hash is a leter and not a number"
  [chr]
  (> (int chr) 64))

(defn in-bounds? [[x y]]
  (and (>= x 0) (>= y 0) (< x grid-size) (< y grid-size)))

(defn open-doors
  "Given current position and moves taken so far, returns directions of open
  doors. Does not take bounds into account."
  [pos moves]
  (->> moves
       (concat input)
       (apply str)
       (md5)
       (take 4)
       (map door-open?)
       ; The following could possibly be nicer with keep-indexed
       (map vector directions)
       (filter second)
       (map first)))

(defn neighbour-map [[x y]]
  (into {}
    (filter #(in-bounds? (val %))
      { \U [x (dec y)]
        \D [x (inc y)]
        \L [(dec x) y]
        \R [(inc x) y]})))

(defn possible-moves [pos path]
  (into ()
    (select-keys
      (neighbour-map pos)
      (open-doors pos path))))

(defn walk [pos path solutions limit]
  (let [possible-moves (possible-moves pos path)]
    (cond
      (= pos target-pos) (concat solutions [path]) ; found a path
      (empty? possible-moves) nil  ; no moves possible
      (> (count path) limit)  nil  ; length limit reached
      :else
        (mapcat
          (fn [[direction next-pos]]
            (walk next-pos (conj path direction) solutions limit))
          possible-moves))))

(defn main []
  (let [paths (walk '(0 0) [] [] 12)]
    (->> paths
        (map join)
        (sort-by count)
        (println))))
