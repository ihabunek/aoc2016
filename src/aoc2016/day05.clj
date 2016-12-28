(ns aoc2016.day05
  (:require [digest :refer [md5]]
            [clojure.string :as str]
            [clojure.pprint :refer [pprint]]))

(defn find-next-hash [door-id start]
  (loop [number start]
    (let [hash (md5 (str door-id number))]
      (if (str/starts-with? hash "00000")
        [number hash]
        (recur (inc number))))))

(defn find-hashes [door-id n]
  (loop [number 0 hashes []]
    (let [[number hash] (find-next-hash door-id number)
          hashes (conj hashes hash)]
      (println number hash)
      (if (= (count hashes) n)
        hashes
        (recur (inc number) hashes)))))

(defn get-code [door-id]
  (->> (find-hashes door-id 8)
    (map #(subs % 5 6))
    (str/join)))

(defn main []
  (println "Advent of Code, day 5")
  (println "=====================")
  (println "The door code is: " (get-code "wtnhxymk"))
)
