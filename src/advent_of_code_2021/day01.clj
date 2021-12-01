(ns advent-of-code-2021.day01
  (:require
   [advent-of-code-2021.utils :as u]))

(defn day1-1
  "https://adventofcode.com/2021/day/1"
  ([] (day1-1
       (u/read-longs (slurp "resources/depth_measurements.txt")
                     u/line-endings)))
  ([depth-measurements]
   (count
    (transduce
     (filter (partial apply <))
     conj []
     (partition 2 1 depth-measurements)))))

(defn day1-2
  "https://adventofcode.com/2021/day/1"
  ([] (day1-2
       (u/read-longs (slurp "resources/depth_measurements.txt")
                     u/line-endings)))
  ([depth-measurements]
   (day1-1
    (eduction
     (map (partial apply +))
     (partition 3 1 depth-measurements)))))