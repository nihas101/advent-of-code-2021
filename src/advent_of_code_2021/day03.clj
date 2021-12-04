(ns advent-of-code-2021.day03
  (:require
   [advent-of-code-2021.utils :as u]
   [clojure.string :as string]))

(def ^:private diagnostic-report
  (string/split (slurp "resources/diagnostic_report.txt") u/line-endings))

(defn- rate-fn [comparison-fn]
  (fn [bit-frequencies]
    (-> (mapv #(ffirst (sort-by second comparison-fn %)) bit-frequencies)
        string/join
        (Long/parseLong ,,, 2))))

(def ^::private gamma-rate (rate-fn >))

(def ^::private epsilon-rate (rate-fn <))

(defn- bit-frequencies [diagnostic-report]
  (mapv frequencies
        (apply mapv vector diagnostic-report)))

(defn day3-1
  "https://adventofcode.com/2021/day/3"
  ([] (day3-1 diagnostic-report))
  ([diagnostic-report]
   (let [bit-frequencies (bit-frequencies diagnostic-report)]
     (* ^long (epsilon-rate bit-frequencies)
        ^long (gamma-rate bit-frequencies)))))

(defn- bit [diagnostic-report n bit-criteria]
  (-> diagnostic-report
      bit-frequencies
      (nth ,,, n)
      bit-criteria
      str))

(defn- rating-fn [bit-criteria]
  (fn rating
    ([diagnostic-report] (rating diagnostic-report "" 0))
    ([diagnostic-report ^String bit-pattern ^long n]
     (if (= 1 (count diagnostic-report))
       (-> (first diagnostic-report)
           string/join
           (Long/parseLong ,,, 2))
       (let [bit (bit diagnostic-report n bit-criteria)
             bit-pattern (str bit-pattern bit)]
         (recur (filter #(string/starts-with? % bit-pattern)
                        diagnostic-report)
                bit-pattern
                (inc n)))))))

(defn- most-common [{^long one \1 ^long zero \0}]
  (if (< one zero) 0 1))

(def ^:private oxygen-generator-rating (rating-fn most-common))

(defn- least-common [{^long one \1 ^long zero \0}]
  (if (< one zero) 1 0))

(def ^:private co2-scrub-rating (rating-fn least-common))

(defn day3-2
  "https://adventofcode.com/2021/day/3"
  ([] (day3-2 diagnostic-report))
  ([diagnostic-report]
   (* ^long (oxygen-generator-rating diagnostic-report)
      ^long (co2-scrub-rating diagnostic-report "" 0))))