(ns advent-of-code-2021.day07
  (:require
   [advent-of-code-2021.utils :as u]))

(def ^:private crabs (u/read-longs
                      (slurp "resources/crabs.txt") #"\,"))

(defn- crab-range [crabs]
  ((juxt (partial apply min)
         (partial apply max)) crabs))

(defn- fuel-per-position-fn [fuel-for-position]
  (fn [[^long x1 ^long x2] ^long crab]
    (reduce (fn [p->f x] (assoc p->f x (fuel-for-position crab x)))
            {} (range x1 (inc x2)))))

(defn- align-crabs-fuel-consumption [crabs fuel-per-position]
  (->> crabs
       (mapv (partial fuel-per-position (crab-range crabs)))
       (apply merge-with +)
       (apply min-key second)
       second))

(defn- linear-fuel-consumption [^long x1 ^long x2] (u/abs (- x1 x2)))

(defn day7-1
  "https://adventofcode.com/2021/day/7"
  ([] (day7-1 crabs))
  ([crabs]
   (align-crabs-fuel-consumption crabs
                                 (fuel-per-position-fn 
                                  linear-fuel-consumption))))

(defn- exponential-fuel-consumption [^long x1 ^long x2]
  (let [n (u/abs (- x1 x2))]
    (quot (* n (inc n)) 2)))

(defn day7-2
  "https://adventofcode.com/2021/day/7"
  ([] (day7-2 crabs))
  ([crabs]
   (align-crabs-fuel-consumption crabs
                                 (fuel-per-position-fn
                                  exponential-fuel-consumption))))