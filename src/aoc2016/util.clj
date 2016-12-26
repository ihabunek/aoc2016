(ns aoc2016.util)

(defn index-of [e coll]
  (first (keep-indexed #(if (= e %2) %1) coll)))
