(ns utils
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

;; https://adventofcode.com/2023/day/1/input -> resources/day01-input.txt
(defn download-input
  "Downloads the input for a given day."
  [day]
  (let [url (str "https://adventofcode.com/2023/day/" day "/input")
        filename (str "resources/day" (format "%02d" day) "-input.txt")]
    (if (io/file filename)
      (println (str "File " filename " already exists."))
      (do
        (println (str "Downloading " url " to " filename))
        (io/make-parents filename)
        (spit filename "")
        (io/copy (io/input-stream url) (io/file filename))))))

(defn get-input
  "Gets the input for a given day."
  [day]
  (slurp (io/resource (str "day" (format "%02d" day) "-input.txt"))))

(defn read-lines
  "Reads lines from a string."
  [s]
  (str/split-lines s))
