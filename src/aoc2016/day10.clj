(ns aoc2016.day10
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]))

(def input-file (-> "day10.in" io/resource io/file))

(defn parse-assignment [line]
  (let [pattern #"value (\d+) goes to bot (\d+)"
        matches (re-find pattern line)]
    (map read-string (rest matches))))

(defn parse-transfer [line]
  (let [pattern #"^bot (\d+) gives low to (bot|output) (\d+) and high to (bot|output) (\d+)$"
        matches (re-find pattern line)]
    [(read-string (nth matches 1))
      [[(keyword (nth matches 2)) (read-string (nth matches 3))]
       [(keyword (nth matches 4)) (read-string (nth matches 5))]]]))

(defn parse-input [input]
  (let [lines (str/split-lines input)
        assignments (->> lines
                         (filter #(str/starts-with? % "value"))
                         (map parse-assignment))

        transfers (->> lines
                       (filter #(str/starts-with? % "bot"))
                       (map parse-transfer))]

    [assignments (into {} transfers)]))

(defn add-value
  "Given a list of lists, add the given value to sub-list at given index."
  [coll index value]
  (let [values (nth coll index)]
    (assoc coll index (cons value values))))

(defn initial-state [assignments bots]
  (loop [assignments assignments bots bots]
    (if (empty? assignments) bots
      (let [[value bot-id] (first assignments)]
        (recur (rest assignments)
               (add-value bots bot-id value))))))

(defn next-bot
  "Given a list of bots, returns the first one which has 2 chips as the next
   bot to make a move."
  [bots]
  (first
    (keep-indexed #(if (> (count %2) 1) (list %1 %2)) bots)))

(defn give
  "Add the given value either to a bot or to an output."
  [value target bots outputs]
  (case (first target)
    :bot    [(add-value bots (second target) value) outputs]
    :output [bots (add-value outputs (second target) value)]))

(defn step [transfers bots outputs]
  (let [bot (next-bot bots)]
    (if (some? bot)
      (let [[bot-id values] bot
            values (sort values)
            targets (get transfers bot-id)
            [v1 v2] values
            [t1 t2] targets

            ; Clear values from source bot and add them to targets
            bots (assoc bots bot-id '())
            [bots outputs] (give v1 t1 bots outputs)
            [bots outputs] (give v2 t2 bots outputs)]

        (if (= values [17 61])
          (println (str "Bot " bot-id " is comparing 17 and 61")))

        [bots outputs]))))

(defn run [transfers bots outputs]
  (loop [bots bots outputs outputs]
    (let [result (step transfers bots outputs)]
      (if (nil? result) outputs
        (recur (nth result 0) (nth result 1))))))

(defn main []
  (let [input (slurp input-file)
        [assignments transfers] (parse-input (slurp input-file))
        bots (vec (repeat 210 '()))
        bots (initial-state assignments bots)
        outputs (vec (repeat 21 '()))]

    (println "Advent of Code, day 10")
    (println "======================")

    (let [outputs (run transfers bots outputs)]
      (println "Product of first 3 outputs:"
        (->> outputs (take 3) (map first) (apply *))))
  ))
