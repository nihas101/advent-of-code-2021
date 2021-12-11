(ns advent-of-code-2021.day11
  (:require
   [clojure.string :as string]
   [advent-of-code-2021.utils :as u]))

(def neighbours 
  (memoize
   (fn [[^long x ^long y] width height]
     (for [xx (range (dec x) (+ x 2))
           yy (range (dec y) (+ y 2))
           :when (and (< -1 xx width) (< -1 yy height)
                      (or (not= xx x) (not= yy y)))]
       [xx yy]))))

(def all-positions
  (memoize
   (fn [xx yy]
     (for [y (range yy)
           x (range xx)]
       [x y]))))

(defn- energy-step [{:keys [width height] :as octopus-levels}]
  (reduce (fn [ol p] (update-in ol [:energy-levels p] inc))
          octopus-levels
          (all-positions width height)))

(defn- flash-neighbours [octopus-levels nbrs]
  (reduce (fn [ol nbr] (update-in ol [:energy-levels nbr] inc)) 
          octopus-levels nbrs))

(defn- new-flashed-octopy [octopus-level flashed neighbours]
  (transduce
   (comp
    (filter #(< 9 ^long (get-in octopus-level [:energy-levels %])))
    (remove flashed))
   conj [] neighbours))

(defn- flash-step [{:keys [width height] :as octopus-levels}]
  (loop [ol octopus-levels
         [p & ps :as pps] (filter #(< 9 ^long (get-in ol [:energy-levels %]))
                                  (all-positions width height))
         flashed (set pps)]
    (if (nil? p)
      (update ol :flashes (partial + (count flashed)))
      (let [nbrs (neighbours p width height)
            nol (flash-neighbours ol nbrs)
            nfl (new-flashed-octopy nol flashed nbrs)]
        (recur nol
               (apply conj ps nfl)
               (apply conj flashed nfl))))))

(defn- reset-energy-levels [{:keys [width height] :as octopus-levels}]
  (reduce
   (fn [ol p] (update-in ol [:energy-levels p] 
                         (fn [^long lvl] (if (< 9 lvl) 0 lvl))))
   octopus-levels (all-positions width height)))

(defn- parse-octopus-row [lvls y]
  (mapv (fn [lvl x] [[x y] (Long/parseLong (str lvl))])
        lvls (range)))

(defn- parse-octopus-levels [octopus-levels]
  (let [lvl-rows (string/split octopus-levels u/line-endings)
        energy-levels (apply conj {}
                             (mapcat parse-octopus-row lvl-rows (range)))]
    (-> {:energy-levels energy-levels}
        (assoc ,,, :width (count (first lvl-rows)))
        (assoc ,,, :height (count lvl-rows))
        (assoc ,,, :flashes 0))))

(def ^:private octopus-energy-levels
  (parse-octopus-levels (slurp "resources/octopus_energy_levels.txt")))

(defn day11-1
  "https://adventofcode.com/2021/day/11"
  ([] (day11-1 octopus-energy-levels 100))
  ([octopus-levels ^long steps]
   (if (pos? steps)
     (recur (-> octopus-levels energy-step flash-step reset-energy-levels)
            (dec steps))
     (:flashes octopus-levels))))

(defn- synchronized? [octopus-levels]
  (= (-> octopus-levels :energy-levels vals set count)
     1))

(defn day11-2
  "https://adventofcode.com/2021/day/11"
  ([] (day11-2 octopus-energy-levels))
  ([octopus-levels] (day11-2 octopus-levels 0))
  ([octopus-levels ^long steps]
   (if (synchronized? octopus-levels)
     steps
     (recur (-> octopus-levels energy-step flash-step reset-energy-levels)
            (inc steps)))))