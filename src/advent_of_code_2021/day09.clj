(ns advent-of-code-2021.day09
  (:require
   [advent-of-code-2021.utils :as u]))

(defn- neighbours [[^long x ^long y]]
  [[x (inc y)] [x (dec y)] [(inc x) y] [(dec x) y]])

(defn- low-point? [height-map [x y :as p]]
  (let [h (height-map [x y])]
    (every? (fn [n] (< ^long h ^long (get height-map n 9))) (neighbours p))))

(defn- low-points [{:keys [height width] :as height-map}]
  (filter (partial low-point? height-map)
          (for [y (range height)
                x (range width)]
            [x y])))

(def ^:private height-map (u/parse-positional-map
                           (slurp "resources/height_map.txt")))

(defn day9-1
  "https://adventofcode.com/2021/day/9"
  ([] (day9-1 height-map))
  ([height-map]
   (transduce
    (comp
     (map height-map)
     (map inc))
    +
    (low-points height-map))))

(defn- basin [height-map low-points]
  (let [new-basin-points
        (remove (fn [bp] (= 9 (height-map bp 9)))
                (remove (set low-points)
                        (set (mapcat neighbours low-points))))]
    (if (empty? new-basin-points)
      low-points
      (recur height-map (apply conj low-points new-basin-points)))))

(defn day9-2
  "https://adventofcode.com/2021/day/9"
  ([] (day9-2 height-map))
  ([height-map]
   (->> height-map
        low-points
        (transduce
         (comp
          (map #(basin height-map [%]))
          (map count))
         conj [])
        (sort >)
        (take 3)
        (reduce *))))