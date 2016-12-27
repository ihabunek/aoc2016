(ns aoc2016.day03
  (:require [clojure.java.io :as io]
            [clojure.string :refer [split split-lines trim]]
            [clojure.pprint :refer [pprint]]))

(def input-file (-> "day3.in" io/resource io/file))

(defn parse-line [line]
  (map read-string (split (trim line) #" +")))

(defn parse-input [input]
  (->> input split-lines (map parse-line)))

(defn rotate [triangles]
  (apply concat
    (map #(apply map list %)
      (partition 3 triangles))))

(defn is-valid-triangle [sides]
  (let [sides (sort sides)
        a (first sides)
        b (second sides)
        c (last sides)]
    (> (+ a b) c)))

(defn main []
  (let [triangles (parse-input (slurp input-file))]
    (println "Advent of Code, day 3")
    (println "=====================")
    (println "The number of valid triangles horizontally is: "
      (count (filter is-valid-triangle triangles)))
    (println "The number of valid triangles vertically is: "
      (count (filter is-valid-triangle (rotate triangles))))
))
