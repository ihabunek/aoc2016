(ns aoc2016.day07
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]))

(def input-file (-> "day7.in" io/resource io/file))

(defn parse-input [input]
  (->> input
    str/split-lines))

(defn divide
  "Divide an address into non-bracketed items (outside) and bracketed items (inside)."
  [address]
  (let [items (str/split address #"\[|\]")]
    [(take-nth 2 items)            ; outside
     (take-nth 2 (rest items))]))  ; inside

(defn abba?
  "Check if the given string contains an 'abba' pattern."
  [string]
  (let [found (re-find #"(.)(.)\2\1" string)]
    (and found
      (not= (nth found 1) (nth found 2)))))

(defn tls?
  "Check if the given address supports TLS."
  [address]
  (let [[outside inside] (divide address)]
    (and
      (some abba? outside)
      (not-any? abba? inside))))

(defn abas
  "Return all aba patterns found in given string"
  [string]
  (->> (re-seq #"([a-z])[a-z](?=\1)" string)
    (map str/join)))

(defn bab
  "Invert an aba pattern to form a bab pattern."
  [aba]
  (str (second aba) (first aba) (second aba)))

(defn ssl?
  "Check if the given address supports SSL."
  [address]
  (let [[outside inside] (divide address)
        abas (apply concat (map abas outside))
        babs (map bab abas)]
      (some some?
        (for [bab babs]
          (some #(str/index-of % bab) inside)))))

(defn main []
  (println "Advent of Code, day 7")
  (println "=====================")
  (let [addresses (parse-input (slurp input-file))]
    (println "Number of addresses:    " (count addresses))
    (println "Number of TLS addresses:" (count (filter tls? addresses)))

    ; TODO: this gives the wrong result
    (println "Number of SSL addresses:" (count (filter ssl? addresses)))
  ))
