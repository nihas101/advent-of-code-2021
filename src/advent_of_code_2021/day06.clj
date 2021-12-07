(ns advent-of-code-2021.day06
  (:require
   [advent-of-code-2021.utils :as u]))

(def ^:private fish (frequencies
                     (u/read-longs (slurp "resources/fish.txt") #"\,")))

(defn- next-day [[^longs fish ^long days]]
  [(reduce (fn [nf [^long ds ^long f]]
             (if (pos? ds)
               (update nf (dec ds) (fnil + 0) f)
               (-> nf
                   (update ,,, 6 (fnil + 0) f)
                   (update ,,, 8 (fnil + 0) f))))
           {} fish)
   (dec days)])

(defn- simulate-fish-spawn [fish days]
  (ffirst
   (drop-while (comp pos? second)
               (iterate next-day [fish days]))))

(defn day6
  "https://adventofcode.com/2021/day/6"
  ([days] (day6 fish days))
  ([fish days]
   (reduce + (vals (simulate-fish-spawn fish days)))))