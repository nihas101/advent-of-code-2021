(ns advent-of-code-2021.day13
  (:require
   [clojure.string :as string]
   [advent-of-code-2021.utils :as u]))

(defn- parse-dots [dots]
  (reduce (fn [ds xy] (conj ds
                            (mapv #(Long/parseLong %)
                                  (string/split xy #"\,"))))
          #{} (string/split dots u/line-endings)))

(defn- parse-fold-instructions [fold-instr]
  (transduce
   (comp
    (map #(subs % 11))
    (map (fn [f-i]
           (let [[d p] (string/split f-i #"=")]
             [d (Long/parseLong p)]))))
   conj []
   (string/split fold-instr u/line-endings)))

(defn- parse-origami [fold-instructions]
  (let [[dots fold-instr] (u/split-sections fold-instructions)]
    {:dots (parse-dots dots)
     :fold-instructions (parse-fold-instructions fold-instr)}))

(defn- fold-axis [axis ^long pos ^longs dots]
  (reduce (fn [ndots d] (conj ndots
                              (update d axis #(- pos (u/abs (- pos ^long %))))))
          #{} dots))

(defn- fold-paper [{:keys [dots fold-instructions]}]
  (reduce (fn [ds [dir pos]]
            (condp = dir
              "x" (fold-axis 0 pos ds)
              "y" (fold-axis 1 pos ds)))
          dots fold-instructions))

(defonce ^:private fold-instructions
  (parse-origami (slurp "resources/fold_instructions.txt")))

(defn day13-1
  "https://adventofcode.com/2021/day/13"
  ([] (day13-1 fold-instructions))
  ([fold-instructions]
   (count
    (fold-paper (update fold-instructions :fold-instructions
                        (fn [fi] [(first fi)]))))))

(defn- lines->strs [positions]
  (let [width (inc ^long (reduce max 0 (mapv first positions)))
        height (inc ^long (reduce max 0 (mapv second positions)))]
   (mapv (fn [xs]
           (string/join " "
                        (mapv (fn [p] (if (contains? positions p) "1" "0"))
                              xs)))
         (for [y (range height)]
           (for [x (range width)]
             [x y])))))

(defn- fold->p1 [positions]
  (let [width (inc ^long (reduce max 0 (mapv first positions)))
        height (inc ^long (reduce max 0 (mapv second positions)))]
    (str "P1\n" width " " height "\n"
         (string/join "\n" (lines->strs positions)))))

(defn day13-2
  "https://adventofcode.com/2021/day/13"
  ([] (day13-2 fold-instructions))
  ([fold-instructions]
   (fold->p1 (fold-paper fold-instructions))))