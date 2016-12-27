(ns aoc2016.day04
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.pprint :refer [pprint]]))

(def input-file (-> "day4.in" io/resource io/file))

(defn parse-line [line]
  (let [[_ cypher number hash] (re-find #"^([a-z-]+)-(\d+)\[([a-z]+)\]$" line)]
    { :cypher cypher
      :letters (str/replace cypher "-" "")
      :number (read-string number)
      :hash hash }))

(defn parse-input [input]
  (->> input str/split-lines (map parse-line)))

(defn compare-fn
  "Sorting function, compares by frequency, then alphabetically."
  [one other]
  (let [by-freq (compare (second other) (second one))]
    (if (zero? by-freq)
      (compare (first one) (first other))
      by-freq)))

(defn calculate-hash [room]
  (->> (str/replace (:cypher room) "-" "")
    (frequencies)
    (sort compare-fn)
    (take 5)
    (map first)
    (str/join)))

(defn real? [room]
  (= (:hash room) (calculate-hash room)))

(defn sum-real [rooms]
  (apply + (->> rooms (filter real?) (map :number))))

(defn rotate [letter number]
  (if (= letter \-) letter
    (-> (int letter)
      (+ number -97)
      (mod 26)
      (+ 97)
      (char))))

(defn decrypt [cypher number]
  (str/join
    (map #(rotate % number) (char-array cypher))))

(defn decrypt-room [room]
  (assoc room :name
    (decrypt (:cypher room) (:number room))))

(defn main []
  (let [rooms (parse-input (slurp input-file))]
    (println "Advent of Code, day 4")
    (println "=====================")
    (println "Sum of real room numbers is:" (sum-real rooms))
    (println "The room in question is: "
      (filter #(= (:name %) "northpole-object-storage") (map decrypt-room rooms)))
))

