(ns aoc2016.day12
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]))

(def input-file (-> "day12.in" io/resource io/file))

(defn parse-token [token]
  (let [token (read-string token)]
    (if (symbol? token)
      (keyword token) token)))

(defn parse-command [string]
  (->> (str/split string #" ")
       (map parse-token)))

(defn parse-input [input]
  (->> input
    (str/split-lines)
    (map parse-command)))

(defn get-val [regs source]
  (if (keyword? source)
    (get regs source)
    source))

(defn -cpy [[source target] line regs]
  (let [regs (assoc regs target (get-val regs source))]
    [(inc line) regs]))

(defn -jnz
  "If condition is zero, jump to delta lines away, otherwise proceed to next line."
  [[condition delta] line regs]
  (let [zero (zero? (get-val regs condition))
        delta (if zero 1 delta)]
    [(+ line delta) regs]))

(defn -inc
  "Increment the given register."
  [[r] line regs]
  [(inc line)
   (assoc regs r (inc (get regs r)))])

(defn -dec
  "Decrement the given register."
  [[r] line regs]
  [(inc line)
   (assoc regs r (dec (get regs r)))])

(defn step
  "Runs a command on the given line and returns the modified registers and line."
  [commands line regs]
  (let [command (nth commands line)
        op (first command)
        args (rest command)]
    (case op
      :cpy (-cpy args line regs)
      :jnz (-jnz args line regs)
      :inc (-inc args line regs)
      :dec (-dec args line regs))))

(defn main []
  (let [input (slurp input-file)
        commands (parse-input input)
        limit (count commands)
        regs { :a 0 :b 0 :c 0 :d 0 }]

    (println "Advent of Code, day 12")
    (println "======================")

    (loop [commands commands line 0 regs regs]
      (if (= line limit)
        (println "\nDone:\n" regs)
        (let [[line regs] (step commands line regs)]
          (recur commands line regs))))
  ))
