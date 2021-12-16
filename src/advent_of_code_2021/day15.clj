(ns advent-of-code-2021.day15
  (:require
   [shams.priority-queue :as pq]
   [advent-of-code-2021.utils :as u]))

(defn- neighbours [[^long x ^long y] visited ^long width ^long height]
  (eduction
   (comp
    (remove visited)
    (filter (fn [[xx yy]] (and (< -1 xx width) (< -1 yy height)))))
   [[(dec x) y] [x (dec y)] [(inc x) y] [x (inc y)]]))

(defn- step-to-neighbours [p nz cost-fn]
  (mapv (fn [n]
          (-> p
              (update ,,, :path conj n)
              (update ,,, :cost + (cost-fn n)))) nz))

(defn- path-state [{:keys [path]} visited ^long width ^long height]
  (cond
    (= [(dec width) (dec height)] (peek path)) :complete
    (empty? (neighbours (peek path) visited width height)) :dead-end
    :else :incomplete))

(defrecord Path [path cost estimated-cost])

(defn- lowest-risk-path [{:keys [width height]} cost-fn]
  (loop [pque (pq/priority-queue :cost
                                 :elements [(Path. [[0 0]] 0 0)]
                                 :priority-comparator <)
         visited #{[0 0]}]
    (if (seq pque)
      (let [p (peek pque)
            nbrs (neighbours (peek (:path p)) visited width height)
            new-ps (step-to-neighbours p nbrs cost-fn)
            {:keys [complete incomplete]} (group-by #(path-state % visited
                                                                 width height)
                                                    new-ps)]
        (if (seq complete)
          (first complete)
          (recur (into (pop pque) incomplete)
                 (into visited nbrs))))
      [])))

(def ^:private ceiling-map
  (u/parse-positional-map (slurp "resources/ceiling.txt")))

(defn day15-1
  "https://adventofcode.com/2021/day/15"
  ([] (day15-1 ceiling-map))
  ([ceiling-map]
   (:cost (lowest-risk-path ceiling-map ceiling-map))))

(defn- additional-cost
  ([c x xm y ym]
   (let [cc (+ c (quot x xm) (quot y ym))]
     (if (< 9 cc) (max 1 (- cc 9)) cc))))

(defn- cost-fn-2 [{:keys [^long width ^long height] :as ceiling}]
  (fn [[^long x ^long y]]
    (cond
      (and (<= width x)
           (<= height y)) (additional-cost
                           (ceiling [(mod x width) (mod y height)])
                           x width y height)
      (<= width x) (additional-cost
                    (ceiling [(mod x width) y])
                    x width y height)
      (<= height y) (additional-cost
                     (ceiling [x (mod y height)])
                     x width y height)
      :else (ceiling [x y]))))

(defn day15-2
  "https://adventofcode.com/2021/day/15"
  ([] (day15-2 ceiling-map))
  ([ceiling-map]
   (-> ceiling-map
       (update ,,, :height * 5)
       (update ,,, :width * 5)
       (lowest-risk-path ,,, (cost-fn-2 ceiling-map))
       :cost)))