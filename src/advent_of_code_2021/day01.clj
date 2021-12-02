(ns advent-of-code-2021.day01
  (:require
   [advent-of-code-2021.utils :as u]))

(def ^:private depth-measurements
  (u/read-longs (slurp "resources/depth_measurements.txt")
                u/line-endings))

(defn day1-1
  "https://adventofcode.com/2021/day/1"
  ([] (day1-1 depth-measurements))
  ([depth-measurements]
   (count
    (transduce
     (filter (partial apply <))
     conj []
     (partition 2 1 depth-measurements)))))

(defn day1-2
  "https://adventofcode.com/2021/day/1"
  ([] (day1-2 depth-measurements))
  ([depth-measurements]
   (day1-1
    (eduction
     (map (partial apply +))
     (partition 3 1 depth-measurements)))))