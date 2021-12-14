(ns advent-of-code-2021.day14
  (:require
   [clojure.string :as string]
   [advent-of-code-2021.utils :as u]))

(defn- parse-pair-insertions [pair-insertions]
  (reduce (fn [ir rule] (conj ir (string/split rule #"\s+->\s")))
          {} (string/split pair-insertions u/line-endings)))

(defn- start->pairs-map [start]
  (reduce (fn [pm p] (update pm (string/join p) (fnil inc 0)))
          {} (partition 2 1 start)))

(defn- parse-polymer-input [polymer-input]
  (let [[start pair-insertions] (u/split-sections polymer-input)]
    {:input start
     :polymer (start->pairs-map start)
     :insertion-rules (parse-pair-insertions pair-insertions)}))

(defn- insertion [{:keys [polymer insertion-rules] :as p+i}]
  (->> (reduce (fn [pol [r sub]] (if (pos? ^long (get polymer r 0))
                                   (-> pol
                                       (update ,,, (str (first r) sub)
                                                   (fnil + 0) (get polymer r))
                                       (update ,,, (str sub (second r))
                                                   (fnil + 0) (get polymer r)))
                                   pol))
               {} insertion-rules)
       (assoc p+i :polymer)))

(defn- create-polymer [polymer iterations]
  (->> (drop iterations (iterate insertion polymer))
       first
       :polymer
       (u/remove-vals (comp pos? second))))

(defn- polymer->elements [polymer input]
  (->
   (reduce (fn [eles [[a _] freq]]
             (update eles a (fnil + 0) freq))
           {}  polymer)
   ;; We discarded the last char in the polymer in the prevous step
   ;; so count it extra
   (update ,,, (last input) inc)))

(def ^:private polymer-input
  (parse-polymer-input (slurp "resources/polymer.txt")))

(defn day14-1
  "https://adventofcode.com/2021/day/14"
  ([] (day14-1 polymer-input))
  ([polymer] (day14-1 polymer 10))
  ([{:keys [input] :as polymer} iterations]
   (let [occurences
         (sort (mapv second
                     (polymer->elements (create-polymer polymer iterations)
                                        input)))]
     (- ^long (last occurences)
        ^long (first occurences)))))

(defn day14-2
  "https://adventofcode.com/2021/day/14"
  ([] (day14-2 polymer-input))
  ([polymer] (day14-1 polymer 40)))