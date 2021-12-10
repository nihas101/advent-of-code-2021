(ns advent-of-code-2021.day10
  (:require
   [clojure.set :as set]
   [clojure.string :as string]
   [advent-of-code-2021.utils :as u]))

(def ^:private open-paren->close-paren
  {\( \)
   \[ \]
   \{ \}
   \< \>})

(def ^:private close-paren->open-paren (set/map-invert open-paren->close-paren))

(def ^:private closing-parens? (set (vals open-paren->close-paren)))

(defn- syntax-state [[start & program]]
  (loop [[c & cs] program
         pairs-stack (list start)]
    (cond
      ;; We are done
      (and (nil? c) (empty? pairs-stack)) [:complete]
      (nil? c) [:incomplete (mapv open-paren->close-paren pairs-stack)]
      ;; We found another (nested) paren
      (or (= c \() (= c \[) (= c \{) (= c \<))
      (recur cs (conj pairs-stack c))
      ;; We found a closing paren
      (and (closing-parens? c) (= (first pairs-stack)
                                  (close-paren->open-paren c)))
      (recur cs (rest pairs-stack))
      ;; We found a closing paren that does not fit to the last opened paren
      :else [:corrupted c])))

(defn- parse-program [program]
  (mapv (fn [program] {:program program
                       :state (syntax-state program)})
        (string/split program u/line-endings)))

(def ^:private program (parse-program (slurp "resources/program.txt")))

(def ^:private syntax-paren->points {\) 3 \] 57 \} 1197 \> 25137})

(defn- score-program-errors [filter-fn score-fn reduce-fn init-val program]
  (transduce
   (comp
    (map :state)
    filter-fn
    (map second)
    score-fn)
   reduce-fn init-val program))

(defn day10-1
  "https://adventofcode.com/2021/day/10"
  ([] (day10-1 program))
  ([program]
   (score-program-errors
    (filter (comp #{:corrupted} first))
    (map syntax-paren->points)
    + 0 program)))

(def ^:private autocomplete-paren->points {\) 1 \] 2 \} 3 \> 4})

(defn- autocomplete-points [autocomplete]
  (reduce (fn [^long score c] (+ (* score 5) ^long (autocomplete-paren->points c)))
          0 autocomplete))

(defn day10-2
  "https://adventofcode.com/2021/day/10"
  ([] (day10-2 program))
  ([program]
   (let [sorted-scores (sort (score-program-errors
                              (remove (comp #{:corrupted} first))
                              (map autocomplete-points)
                              conj [] program))]
     (nth sorted-scores (quot (count sorted-scores) 2)))))