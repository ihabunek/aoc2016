(ns aoc2016.day05b
  (:require [digest :refer [md5]]
            [clojure.string :as str]
            [clojure.pprint :refer [pprint]]))

(defn find-next-hash [door-id start]
  (loop [number start]
    (let [hash (md5 (str door-id number))]
      (if (str/starts-with? hash "00000")
        [number hash]
        (recur (inc number))))))

(defn parse-int [string]
  (if (re-find #"^\d$" string)
    (read-string string) nil))

(defn update?
  "Whether to update the code at given position.
  Yes if position is valid and not already set."
  [code position]
  (and (some? position)
       (< position (count code))
       (not (nth code position))))

(defn update-code [code hash]
  (let [position (parse-int (subs hash 5 6)) ; 6th place is the position
        character (subs hash 6 7)]           ; 7th place is the character
    (if (update? code position)
      (assoc code position character)
      code)))

(defn get-code [door-id]
  (loop [number 0 code (vec (repeat 8 nil))]
    (let [[number hash] (find-next-hash door-id number)
          code (update-code code hash)]
      (println number hash code)
      (if (some nil? code)
        (recur (inc number) code)
        (str/join code)))))

(defn main []
  (println "Advent of Code, day 5, part 2")
  (println "=============================")
  (println "The door code is: " (get-code "wtnhxymk"))
)
