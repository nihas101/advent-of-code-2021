(ns advent-of-code-2021.day12
  (:require
   [clojure.string :as string]
   [advent-of-code-2021.utils :as u]))

(defn- parse-map [m]
  (reduce (fn [path-map [source sink]]
            (-> path-map
                (update ,,, source (fnil conj #{}) sink)
                (update ,,, sink (fnil conj #{}) source)))
          {} (mapv #(string/split % #"\-")
                   (string/split m u/line-endings))))

(defonce ^:private starting-paths [{:path ["start"]
                                    :visited #{}}])

(def small-cave?
  (memoize (fn [cave]
             (when ((complement #{"end" "start"}) cave)
               (every? #(Character/isLowerCase ^Character %) cave)))))

(def large-cave?
  (memoize (fn [cave]
             (every? #(Character/isUpperCase ^Character %) cave))))

(defn- visit-large-cave [path cave]
  (update path :path conj cave))

(defn- visit-end [path caves]
  (if (some #{"end"} caves) [(update path :path conj "end")] []))

(defn- visit-caves [p visit-cave? visit-cave caves]
  (transduce
   (comp
    (filter visit-cave?)
    (map (partial visit-cave p)))
   conj [] caves))

(defn- distinct-paths [path-map select-caves visit-small-cave]
  (loop [[p & ps] starting-paths
         completed-paths #{}]
    (if (nil? p)
      completed-paths
      (let [caves (select-caves path-map p)]
        (recur
         (reduce conj ps
                 (concat (visit-caves p small-cave? visit-small-cave caves)
                         (visit-caves p large-cave? visit-large-cave caves)))
         (reduce conj completed-paths (visit-end p caves)))))))

(def ^:private cave-paths
  (parse-map (slurp "resources/cave_paths.txt")))

(defn- select-caves-1 [path-map {:keys [path visited]}]
  (remove visited (path-map (peek path))))

(defn- visit-small-cave-1 [path cave]
  (-> path
      (update ,,, :path conj cave)
      (update ,,, :visited conj cave)))

(defn day12-1
  "https://adventofcode.com/2021/day/12"
  ([] (day12-1 cave-paths))
  ([cave-map]
   (count (distinct-paths cave-map select-caves-1 visit-small-cave-1))))

(defn- select-caves-2 [path-map {:keys [path visited visited-twice]}]
  (if visited-twice
    (remove visited (path-map (peek path)))
    (path-map (peek path))))

(defn- visit-small-cave-2 [{:keys [visited visited-twice] :as p} cave]
  (if
   (and (contains? visited cave) (not visited-twice))
    (-> p
        (visit-small-cave-1 ,,, cave)
        (assoc ,,, :visited-twice true))
    (visit-small-cave-1 p cave)))

(defn day12-2
  "https://adventofcode.com/2021/day/12"
  ([] (day12-2 cave-paths))
  ([cave-map]
   (count (distinct-paths cave-map select-caves-2 visit-small-cave-2))))