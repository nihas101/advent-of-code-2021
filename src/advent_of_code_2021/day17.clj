(ns advent-of-code-2021.day17
  (:require
   [advent-of-code-2021.utils :as u]
   [clojure.string :as string]))

(defn- parse-range [r]
  (mapv #(Long/parseLong %) (string/split r #"\.\.")))

(defn parse-target-area [target-area]
  (let [[x y] (rest (string/split target-area #"(,)?\s+(x|y)="))
        [^long x1 ^long x2] (parse-range x)
        [^long y1 ^long y2] (parse-range y)]
    {:target-area (set (for [xx (range x1 (inc x2))
                             yy (range y1 (inc y2))]
                         [xx yy]))
     :limit [[x1 (inc x2)]
             [y1 (inc y2)]]}))

(defn probe [dx dy]
  {:position [0 0]
   :velocity [dx dy]})

(defn- simulate-step [{[^long dx ^long dy] :velocity :as p}]
  (-> p
      (update ,,, :position (fn [[^long x ^long y]]
                              [(+ x dx) (+ y dy)]))
      (update ,,, :velocity (fn [[^long dx ^long dy]]
                              [(cond (pos? dx) (dec dx)
                                     (neg? dx) (inc dx)
                                     :else dx)
                               (dec dy)]))))

(defn- within-limit? [{[^long x ^long y] :position}
                      [[^long x1 ^long x2] [^long y1 _]]]
  (if (pos? x2)
    ;; We are shooting a probe into a trench, so the target are will always be beneath us (and in front)
    (and (< x x2) (< y1 y))
    ;; We do not need to continue if we lost all forward momentum and are not within the target
    (< x1 x))) 

(defn- simulate-launch [[dx dy] {:keys [target-area limit]}]
  (loop [p (probe dx dy)
         pos [(:position p)]]
    (let [new-p (simulate-step p)]
      (cond
        (contains? target-area (:position new-p)) (conj pos (:position new-p))
        (within-limit? new-p limit) (recur new-p
                                           (conj pos (:position new-p)))
        :else nil))))

(defn- velocities [{[[_ x2] [y1 _]] :limit}]
  [[1 x2]
   [y1 (u/abs y1)]])

(defn- target-velocities [target-area velocities]
  (transduce
   (comp
    (map #(simulate-launch % target-area))
    (remove nil?))
   conj []
   velocities))

(defn- max-y-pos [p]
  (apply max (mapv second p)))

(defn day17-1
  "https://adventofcode.com/2021/day/17"
  ([] (day17-1 (parse-target-area "target area: x=128..160, y=-142..-88")))
  ([target-area]
   (let [[[x1 x2] [y1 y2]] (velocities target-area)]
     (transduce
      (map second)
      (fn
        ([] Long/MIN_VALUE)
        ([a] a)
        ([^long a ^long b] (max a b)))
      (apply max-key max-y-pos
             (target-velocities target-area
                                (for [x (range x1 x2)
                                      y (range y1 y2)]
                                  [x y])))))))


(defn day17-2
  "https://adventofcode.com/2021/day/17"
  ([] (day17-2 (parse-target-area "target area: x=128..160, y=-142..-88")))
  ([target-area]
   (let [[[x1 x2] [y1 y2]] (velocities target-area)]
     (count (target-velocities target-area
                               (for [x (range x1 x2)
                                     y (range y1 y2)]
                                 [x y]))))))