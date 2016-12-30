(ns aoc2016.day09
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]))

(def input-file (-> "day9.in" io/resource io/file))

(defn parse-marker [re-found]
  (let [[marker length times] re-found]
     [marker (read-string length) (read-string times)]))

(defn expand-1
  "Search for the first marker in input and expand it.
  Return the expanded portion and remaining input."
  [input]
  (let [found (re-find #"\((\d+)x(\d+)\)" input)]
    (if (not found) [nil input]
      (let [[marker length times] (parse-marker found)
            marker-pos (str/index-of input marker)
            start (+ marker-pos (count marker))
            end (+ start length)]
        [(str/join
          (cons
            (subs input 0 marker-pos)
            (repeat times (subs input start end))))
        (subs input end)]))))

(defn expand
  "Expand all markers in input"
  [input]
  (loop [input input result ""]
    (let [[expanded remainder] (expand-1 input)]
      (if (nil? expanded)
        (str result remainder)
        (recur remainder (str result expanded))))))

(defn main []
  (let [input (slurp input-file)]
    (println "Advent of Code, day 9")
    (println "=====================")
    (println "Length of expanded input is" (count (expand input)))
  ))
