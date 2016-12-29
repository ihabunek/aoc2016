(ns aoc2016.day08
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]))

(def input-file (-> "day8.in" io/resource io/file))

; Commands and their regex patterns
(def patterns '(
  (:rect #"^rect (\d+)x(\d+)$")
  (:rrow #"^rotate row y=(\d+) by (\d+)$")
  (:rcol #"^rotate column x=(\d+) by (\d+)$")))

(defn make-grid [width height]
  (vec (repeat height
    (vec (repeat width nil)))))

(defn parse-command
  "Attempt to parse a line using the given regex. Return the parsed command or nil."
  [line command pattern]
  (let [matches (re-find pattern line)]
    (when matches
      (cons command
        (map read-string
          (rest matches))))))

(defn parse-line [line]
  (->> patterns
    (map #(apply parse-command (cons line %)))
    (filter some?)
    (first)))

(defn parse-input [input]
  (->> input
    str/split-lines
    (map parse-line)))

(defn format-row [row]
  (str/join (map #(if % "#" ".") row)))

(defn format-grid [grid]
  (str/join "\n"
    (map format-row grid)))

(defn print-grid
  "Print the grid, and return the grid for chaining."
  [grid]
  (println (format-grid grid) "\n")
  grid)

(defn draw-point
  "Draw a point on the grid at given coordinates"
  [grid x y]
  (assoc grid y
    (assoc (nth grid y) x true)))

(defn draw-rect
  "Draw a rectangle of the given size on the grid."
  [grid width height]
  (let [points (for [x (range width) y (range height)] [x y])]
    (loop [grid grid points points]
      (if (empty? points)
        grid
        (let [[x y] (first points)]
          (recur (draw-point grid x y) (rest points)))))))

(defn transpose [grid]
  (vec (apply map vector grid)))

(defn rotate-row [grid y delta]
  (let [row (nth grid y)
        cutoff (- (count row) delta)]
    (assoc grid y
      (vec (concat
        (subvec row cutoff)
        (subvec row 0 cutoff))))))

(defn rotate-column [grid y delta]
  (-> grid
    transpose
    (rotate-row y delta)
    transpose))

(defn execute
  "Execute a command and return the resulting grid."
  [grid command]
  (let [op (first command) args (rest command)]
    (case op
      :rect (apply draw-rect (cons grid args))
      :rrow (apply rotate-row (cons grid args))
      :rcol (apply rotate-column (cons grid args)))))

(defn execute-many
  "Execute a list of commands and return the resulting grid."
  [grid commands]
  (loop [commands commands
          grid (make-grid 50 6)]
      (if (empty? commands) grid
        (recur
          (rest commands)
          (execute grid (first commands))))))

(defn main []
  (println "Advent of Code, day 8")
  (println "=====================")

  (let [commands (parse-input (slurp input-file))
        empty-grid (make-grid 50 6)
        grid (execute-many empty-grid commands)]

    (println "The resulting grid:")
    (print-grid grid)

    (println "The number of lit pixels:"
      (count (filter true? (for [row grid cell row] cell))))
  ))
