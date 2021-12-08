(ns advent-of-code-2021.day08
  (:require
   [clojure.string :as string]
   [advent-of-code-2021.utils :as u]
   [clojure.set :as set]))

(defn- parse-signal-pattern [pattern]
  (let [[signals output] (string/split pattern #"\s+\|\s+")]
    {:signals (mapv (comp string/join sort) (string/split signals #"\s+"))
     :output (mapv (comp string/join sort) (string/split output #"\s+"))}))

(defn- parse-signal-patterns [pattern]
  (mapv parse-signal-pattern (string/split pattern u/line-endings)))

(def ^:private signal-patterns
  (parse-signal-patterns (slurp "resources/signal_patterns.txt")))

(defn day8-1
  "https://adventofcode.com/2021/day/8"
  ([] (day8-1 signal-patterns))
  ([signals]
   (transduce
    (comp
     (mapcat :output)
     (map count)
     (filter #{2   ; 1
               4   ; 4
               3   ; 7
               7}) ; 8
     (map (constantly 1)))
    +
    signals)))

(defn- deduce-number-fn [signals]
  (fn [mapping [num signal-count rem-signal]]
    (let [rem (set (get mapping rem-signal ""))]
      (assoc mapping num
             (first (drop-while #(not= signal-count (count (remove rem %)))
                                (remove (set (vals mapping)) signals)))))))

(defn- solve-signal [{:keys [signals output]}]
  [(set/map-invert
    (reduce (deduce-number-fn signals)
            ;; [number count-after-removing-segments rem-segments]
            ;; Always deduce the signals in the same order:
            ;; 1 4 7 8 3 6 5 2 9 0
            ;; The other signals after 1 4 7 and 8 can be deduced by
            ;; removing the segments of an already deduced number and
            ;; to produce a unique count
            {} [[1 2 nil]
                [4 4 nil]
                [7 3 nil]
                [8 7 nil]
                [3 2 7]
                [6 4 7]
                [5 0 6]
                [2 5 nil]
                [9 2 4]
                [0 1 9]]))
   output])

(defn- map-signal [[signals output]]
  (mapv signals output))

(defn day8-2
  "https://adventofcode.com/2021/day/8"
  ([] (day8-2 signal-patterns))
  ([signals]
   (transduce
    (comp
     (map solve-signal)
     (map map-signal)
     (map string/join)
     (map #(Long/parseLong %)))
    + signals)))