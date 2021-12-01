(ns advent-of-code-2021.day01
  (:require
   [advent-of-code-2021.utils :as u]))


(defn- inc-if-increased
  ([] 0)
  ([cnt] cnt)
  ([cnt [_ change]]
   (if (= change :increased)
     (inc cnt)
     cnt)))

(defn day1-1
  "https://adventofcode.com/2021/day/1"
  ([] (day1-1
       (u/read-longs (slurp "resources/depth_measurements.txt")
                     u/line-endings)))
  ([depth-measurements]
   (transduce
    (map (fn [[bef aft]]
           [aft (if (< bef aft) :increased :decreased)]))
    inc-if-increased
    (partition 2 1 depth-measurements))))

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