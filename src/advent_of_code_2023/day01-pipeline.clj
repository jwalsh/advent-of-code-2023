(ns day01-pipeline
  (:require [clojure.string :as str]))


(defn parse-integer 
  "Parse integer from string"
  [s]
  (Integer/parseInt s))

;; Test

(parse-integer "123") ; 123


(defn get-first-and-last-number 
  "Get first and last number from line as string"
  [line]
  (let [digits (re-seq #"\d" line)]
    (str (first digits) (last digits))))

;; Test
(get-first-and-last-number "123abc456e") ; "16"


(defn convert-named-numbers-to-digits
  "Convert numbered words to digits"
  [line]
  (str/replace line "one" "1"))

;; Test  
(convert-named-numbers-to-digits "one two three") ; "1 two three"



(defn expand-number-portmanteaus
  "Replace number portmanteaus with expanded form"
  [line]
  (str/replace line "zerone" "zeroone"))

;; Tests
(expand-number-portmanteaus "zeronetwone") ; "zeroonetwoone"


;; Test
(parse-integer "15") ; 15

(defn solve 
  "Solve puzzle"
  [input]
  (->> input
       str/split-lines
       (map expand-number-portmanteaus)
       (map convert-named-numbers-to-digits)
       (map get-first-and-last-number)
       (map parse-integer)
       (reduce +)))
