(ns advent-of-code-2021.day02
  (:require
   [clojure.string :as string]))

(defn- parse-course [input]
  (mapv (fn [dir+steps]
          (let [[dir step] (string/split dir+steps #"\s+")]
            [(keyword dir) (Long/parseLong step)]))
        (string/split input #"\r?\n")))

(def ^:private course (parse-course (slurp "resources/submarine_course.txt")))

(defmulti execute-instruction (fn [{:keys [aim]} [dir _]]
                                (if aim [:aim dir] dir)))

(defmethod execute-instruction :forward [pos [_ x]]
  (update pos :horizontal-pos + x))

(defmethod execute-instruction :down [pos [_ x]]
  (update pos :depth + x))

(defmethod execute-instruction :up [pos [_ x]]
  (update pos :depth - x))

(defn- execute-instructions [pos instructions]
  (transduce
   (comp
    (remove (comp #{:aim} first))
    (map second))
   *
   (reduce execute-instruction pos instructions)))

(defn day2-1
  "https://adventofcode.com/2021/day/2"
  ([] (day2-1 course))
  ([instructions]
   (day2-1 {:horizontal-pos 0 :depth 0} instructions))
  ([pos instructions]
   (execute-instructions pos instructions)))

(defmethod execute-instruction [:aim :forward] [{:keys [aim] :as pos} [_ x]]
  (-> pos
      (update ,,, :horizontal-pos + x)
      (update ,,, :depth + (* aim x))))

(defmethod execute-instruction [:aim :down] [pos [_ x]]
  (update pos :aim + x))

(defmethod execute-instruction [:aim :up] [pos [_ x]]
  (update pos :aim - x))

(defn day2-2
  "https://adventofcode.com/2021/day/2"
  ([] (day2-2 course))
  ([instructions]
   (day2-2 {:horizontal-pos 0 :depth 0 :aim 0} instructions))
  ([pos instructions]
   (execute-instructions pos instructions)))