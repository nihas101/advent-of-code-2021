(ns advent-of-code-2021.day04
  (:require
   [clojure.string :as string]
   [advent-of-code-2021.utils :as u]))

(defn- parse-numbers [numbers]
  (u/read-longs numbers #"\,"))

(defn- board-map [brd]
  (transduce
   (mapcat (fn [[row y]]
             (mapv (fn [num x] [num [x y]]) row (range))))
   conj {}
   (mapv vector brd (range))))

(defn- board [brd]
  (let [parsed-brd (mapv #(u/read-longs % #"\s+")
                         (string/split brd u/line-endings))]
    {:marked-positions #{}
     :board (board-map parsed-brd)
     :board-size (count parsed-brd)
     :board-content (set (flatten parsed-brd))}))

(defn- mark-number [n {:keys [board board-content] :as brd}]
  (if (contains? board-content n)
    (update brd :marked-positions conj (get board n))
    brd))

(defn- draw-number [[[n & numbers] boards]]
  [numbers (mapv (partial mark-number n) boards) n])

(defn- rows [{:keys [board-size]}]
  (for [x (range board-size)]
    (for [y (range board-size)]
      [x y])))

(defn- cols [{:keys [board-size]}]
  (for [x (range board-size)]
    (for [y (range board-size)]
      [y x])))

(defn- bingo? [{:keys [marked-positions] :as board}]
  (some #(every? (partial contains? marked-positions) %)
        (concat (rows board) (cols board))))

(defn- no-board-won? [[numbers boards]]
  (and (not (some bingo? boards))
       (seq numbers)))

(defn- bingo-state [numbers boards]
  (-> (drop-while no-board-won?
                  (iterate draw-number
                           [numbers boards nil]))
      first))

(defn- board->score ^long [{:keys [board marked-positions]}]
  (transduce
   (comp
    (remove (comp marked-positions second))
    (map first))
   +
   board))

(defn- winning-board-score [[boards ^long winning-number]]
  (->> boards
       (drop-while (complement bingo?))
       first
       board->score
       (* winning-number)))

(def ^:private bingo-input
  (u/split-sections (slurp "resources/bingo.txt")))

(defn day4-1
  "https://adventofcode.com/2021/day/4"
  ([] (day4-1 (parse-numbers (first bingo-input))
              (mapv board (rest bingo-input))))
  ([numbers boards]
   (->> boards
        (bingo-state numbers)
        ((juxt second #(nth % 2)))
        winning-board-score)))

(defn day4-2
  "https://adventofcode.com/2021/day/4"
  ([] (day4-2 (parse-numbers (first bingo-input))
              (mapv board (rest bingo-input))))
  ([numbers boards]
   (if (or (= 1 (count boards))
           (empty? numbers))
     (winning-board-score ((juxt second #(nth % 2))
                           (bingo-state numbers boards)))
     (let [[nmbrs brds] (bingo-state numbers boards)]
       (recur nmbrs (remove bingo? brds))))))