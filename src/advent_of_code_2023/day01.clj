(ns day01
  (:require [utils :as utils]
            [clojure.string :as clojure.string]))

;; Part 1: Just the integers

(def part1-input "1abc2\npqr3stu8vwx\na1b2c3d4e5f\ntreb7uchet")
(def input part1-input)
(def part1-output 142)

;; Return str with first and last number in line
(defn parse-line-0
  "Each line should have at least one number. The first number is the first number in the line, the last number is the last number in the line."
  [line]
  (let [numbers (re-seq #"\d" line)]
    (println "parse-line:" line "->" (str (first numbers) (last numbers)))
    (str (first numbers) (last numbers))))

(defn parse-line-integers
  "Parse line to first and last integers"
  [line]
  (let [numbers (re-seq #"\d" line)]
    (str (first numbers) (last numbers))))

(quote
 (parse-line-0 "1abc2e")
 ;; 12
 )
;; Part 2: Use string representations 

(def part2-input "two1nine\neightwothree\nabcone2threexyz\nxtwone3four\n4nineeightseven2\nzoneight234\n7pqrstsixteen")

(defn- convert-number [n]
  (get
   { ;; "zero" "0"
    "one" "1"
    "two" "2"
    "three" "3"
    "four" "4"
    "five" "5"
    "six" "6"
    "seven" "7"
    "eight" "8"
    "nine" "9"}
   n
   n))

(convert-number "naone2three4five")

(defn parse-line-2
  "Each line should have at least one number. The first number is the first number in the line, the last number is the last number in the line."
  [line]
  (let [numbers (re-seq #"\d|zero|one|two|three|four|five|six|seven|eight|nine" line)
        result (str (convert-number (first numbers)) (convert-number (last numbers)))]
    (println "parse-line:" line "->" result)
    result))

(defn parse-line-spelled-numbers
  "Parse line to first and last spelled out numbers"
  [line]
  (let [numbers (re-seq #"\d|zero|one|two|three|four|five|six|seven|eight|nine" line)
        result  (str (convert-number (first numbers))
                     (convert-number (last numbers)))]
    result))

(quote
 (parse-line-2 "11one3eightabc2ninetwo")
 ;; Expect: 12
 )

;; Part 2: Absurd encoding
;; TODO: twone nineight eightwone fiveightwoneightwoneightwone... threeighthreeight...
;; Also, it should be noted that this isn't performed consistently like 
;; parse-line: eightnine2eightnineeight -> 88
;; parse-line: ncqpkzh5twooneoneqfxlqbjjhqsrlkhvdnvtbzpcbj -> 51
;; fbqeightwoonefive72ninecxfscgxxjlr7
;; sdjtwonenine5mlrc9seven

;; Approach 1: Fix as preprocessor 
(defn- pipeline-fix-portmanteau [n]
  (-> n
      (clojure.string/replace "zerone" "zeroone")
      (clojure.string/replace "oneight" "oneeight")
      (clojure.string/replace "twone" "twoone")
      (clojure.string/replace "threeight" "threeeight")
      (clojure.string/replace "fiveight" "fiveeight")
      (clojure.string/replace "eightwo" "eighttwo")
      (clojure.string/replace "nineight" "nineeight")))

;; Approach 1a: Build out the replacement 
(def expanded-portmanteaus
  {"zerone" "zeroone"
   "oneight" "oneeight"
   "twone" "twoone"
   "threeight" "threeeight"
   "fiveight" "fiveeight"
   "eightwo" "eighttwo"
   "nineight" "nineeight"})

(defn pipeline-expand-portmanteaus [text]
  (reduce
   (fn [acc [short long]]
     (clojure.string/replace acc short long))
   text
   expanded-portmanteaus))

(pipeline-expand-portmanteaus "zeronetwone")
; -> "zeroonetwoone"

;; Approach 1c: Build out the replacement dynamically 
(def numbers ["zero" "one" "two" "three" "four" "five" "six" "seven" "eight" "nine"])

;; Dynamically
(def expanded-portmanteaus-dynamic
  (->> (partition 2 1 numbers)
       (map (fn [[w1 w2]] [(keyword (str w1 w2)) (str w1 w2)]))
       (into {})))

;; 
(->> (utils/get-input 1)
     clojure.string/split-lines
     (map pipeline-expand-portmanteaus)
     (map parse-line-2)
     (map parse-line-integers)
     (map #(Integer/parseInt %))
     (reduce +))





;; Approach 2: Split at replace 
(def numbers-encoded
  {"zerone" ["0" "1"]
   "oneight" ["1" "8"]
   "twone" ["2" "1"]
   "threeight" ["3" "8"]
   "fiveight" ["5" "8"]
   "eightwo" ["8" "2"]
   "nineight" ["9" "8"]
   "zero" ["0"]
   "one" ["1"]
   "two" ["2"]
   "three" ["3"],
   "four" ["4"]
   "five" ["5"]
   "six" ["6"]
   "seven" ["7"]
   "eight" ["8"]
   "nine" ["9"]})

(defn parse-line-3
  "Each line should have at least one number. The first number is the first number in the line, the last number is the last number in the line."
  [line]
  (let [numbers (re-seq #"\d|one|two|three|four|five|six|seven|eight|nine" line)
        result (str (convert-number (first numbers)) (convert-number (last numbers)))]
    (println "parse-line:" line "->" result)
    result))

(defn pipeline-line-to-digits [line]
  (re-seq #"\d|one|two|three|four|five|six|seven|eight|nine" line))

(quote
 '((parse-line-3 "11one3eightabc2ninetwoneight")
   ;; Expect: 18
   (parse-line-3 "threeightwo")
   ;; Expect: 32
   (parse-line-3 "threeight")
   ;; Expect: 38
   ))
;; Edge cases for parseInt 
(quote
 '((parse-line-integers "00")))

;; Runner 

(defn solve [input]
  (->> (clojure.string/split-lines input)
       (map parse-line-0)
       (map #(Integer/parseInt %))
       (reduce +)))

(println (solve (utils/get-input 1)))
