(ns advent-of-code-2021.day05
  (:require
   [clojure.string :as string]
   [advent-of-code-2021.utils :as u]))

(defn- parse-coordinates [coord]
  (mapv #(Long/parseLong %) (string/split coord #"\,")))

(defn- parse-pairs [pairs]
  (mapv parse-coordinates (string/split pairs #"\s+->\s+")))

(defn- parse-lines [lines-input]
  (mapv parse-pairs (string/split lines-input u/line-endings)))

(defn- generate-positions [^long d1 ^long d2]
  (if (< d1 d2)
    (range d1 (inc d2))
    (range d2 (inc d1))))

(defn- vertical-line [x1 y1 y2]
  (mapv #(vector x1 %) (generate-positions y1 y2)))

(defn- horizontal-line [y1 x1 x2]
  (mapv #(vector % y1) (generate-positions x1 x2)))

(defn- diagonal-line [[_ ^long y1 :as p1] [_ ^long y2 :as p2]]
  (let [[[^long x1 ^long y1] 
         [^long x2 ^long y2]] (if (< y1 y2) [p1 p2] [p2 p1])]
    (mapv vector
          (range x1 (if (< x1 x2) (inc x2) (dec x2))
                 (if (< x1 x2) 1 -1))
          (range y1 (inc y2)))))

(defn- extrapolate-line [[[x1 y1 :as p1] [x2 y2 :as p2]]]
  (cond
    (= x1 x2) (vertical-line x1 y1 y2)
    (= y1 y2) (horizontal-line y1 x1 x2)
    :else (diagonal-line p1 p2)))

(def ^:private lines
  (parse-lines (slurp "resources/lines.txt")))

(defn day5-1
  ([] (day5-1 lines))
  ([coords]
   (->> coords
        (filter (fn [[[x1 y1] [x2 y2]]] (or (= x1 x2) (= y1 y2))))
        (mapcat extrapolate-line) 
        frequencies
        (filter #(< 1 ^long (second %)))
        count)))

(defn day5-2
  ([] (day5-2 lines))
  ([coords]
   (->> coords
        (mapcat extrapolate-line)
        frequencies
        (filter #(< 1 ^long (second %)))
        count)))