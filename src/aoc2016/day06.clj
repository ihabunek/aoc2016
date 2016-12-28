(ns aoc2016.day06
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]))

(def input-file (-> "day6.in" io/resource io/file))

(defn parse-input [input]
  (->> input str/split-lines))

(defn most-frequent [freq-map]
  (->> (into [] freq-map)
    (sort-by second >)
    (first)
    (first)))

(defn least-frequent [freq-map]
  (->> (into [] freq-map)
    (sort-by second)
    (first)
    (first)))

(defn decode1 [messages]
  (->> (apply map list messages)
    (map frequencies)
    (map most-frequent)
    (str/join)))

(defn decode2 [messages]
  (->> (apply map list messages)
    (map frequencies)
    (map least-frequent)
    (str/join)))

(defn main []
  (println "Advent of Code, day 6")
  (println "=====================")
  (let [messages (parse-input (slurp input-file))]
    (println "First part: " (decode1 messages))
    (println "Second part:" (decode2 messages))
))
