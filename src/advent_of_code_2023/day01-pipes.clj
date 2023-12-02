(ns advent-of-code-2023.day01-pipes
  (:require [advent-of-code-2023.utils :as utils]
            [clojure.string :as str]))

(defn parse-numbers
  "Extract numbers as string"
  [text]
  (clojure.string/join (re-seq #"\d+" text)))

(parse-numbers "a1b2c3")

(defn get-first-last-number
  "Get concat string of first and last number in text"
  [nums]
  (str (first nums) (last nums)))

(get-first-last-number "12345")

(defn convert-number-words
  "Convert all number words to digits"
  [text]
  (reduce
   (fn [text [old new]]
     (clojure.string/replace text old new))
   text
   {"one" "1"
    "two" "2"}))

(convert-number-words "oneonetwo3")

(def portmanteau-replacements
  {"zerone" "zeroone"
   "oneight" "oneeight"})

(defn expand-portmanteaus
  [text]
  (reduce
   (fn [text [pattern replacement]]
     (clojure.string/replace text pattern replacement))
   text
   portmanteau-replacements))

(expand-portmanteaus "aoneightb")

(defn solve-part1
  "Solve part 1"
  [input]
  (->> input
       str/split-lines
       (map parse-numbers)
       (map get-first-last-number)
       (map #(Integer/parseInt %))
       (reduce +)))

(solve-part1 "1a2\nb3c")

(defn solve-part2
  "Solve part 2"
  [input]
  (->> input
       str/split-lines
       (map expand-portmanteaus)
       (map convert-number-words)
       (map parse-numbers)
       (map get-first-last-number)
       (map #(Integer/parseInt %))
       (reduce +)))
