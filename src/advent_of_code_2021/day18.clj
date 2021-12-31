(ns advent-of-code-2021.day18
  (:require
   [advent-of-code-2021.utils :as u]
   [clojure.string :as string]))

(defn- parse-snailfish-numbers [snailfish-numbers]
  (mapv read-string (string/split snailfish-numbers u/line-endings)))

(defn- tree [snailfish-number]
  (if (coll? snailfish-number)
    (let [[l r] snailfish-number]
      (u/internal-node (tree l) (tree r)))
    (u/leaf-node snailfish-number)))

(defn- pair? [{:keys [left right]}]
  (and (number? (:value left))
       (number? (:value right))))

(defn- add-from-left [val {:keys [value left right] :as tree}]
  (cond
    (number? (:value left)) (update-in tree [:left :value] + val)
    value (update tree :value + val)
    :else (if-let [exl (add-from-left val left)]
            (assoc tree :left exl)
            (when-let [exr (add-from-left val right)]
              (assoc tree :right exr)))))

(defn- add-from-right [val {:keys [value left right] :as tree}]
  (cond
    (number? (:value right)) (update-in tree [:right :value] + val)
    value (update tree :value + val)
    :else (if-let [exr (add-from-right val right)]
            (assoc tree :right exr)
            (when-let [exl (add-from-right val left)]
              (assoc tree :left exl)))))

(defn- explode-tree
  ([snailfish-tree] (explode-tree 0 snailfish-tree))
  ([^long depth {:keys [left right value] :as tree}]
   (cond
     ;; We do not need to look further than a depth of 4
     (or value (< 4 depth)) [nil tree]
     ;; We explode this pair
     (and (= depth 4)
          (pair? tree))
     [{:lvalue (:value left) :rvalue (:value right)}
      (u/leaf-node 0)]
     ;; We need to look deeper into the tree
     (or left right)
     (let [[{:keys [rvalue] :as lval} lt] (explode-tree (inc depth) left)
           [{:keys [lvalue] :as rval} rt] (explode-tree (inc depth) right)]
       (cond
         ;; A node in the left subtree exploded
         rvalue (if-let [nrtree (add-from-left rvalue right)]
                  [(dissoc lval :rvalue) (-> tree
                                             (assoc ,,, :left lt)
                                             (assoc ,,, :right nrtree))]
                  [lval (assoc tree :left lt)])
         ;; A node in a subtree may have exploded, but 'our' value
         ;; has already been processed
         lval [lval (assoc tree :left lt)]
         ;; A node in the right subtree exploded
         lvalue (if-let [nltree (add-from-right lvalue left)]
                  [(dissoc rval :lvalue) (-> tree
                                             (assoc ,,, :right rt)
                                             (assoc ,,, :left nltree))]
                  [rval (assoc tree :right rt)])
         ;; A node in a subtree may have exploded, but 'our' value
         ;; has already been processed
         rval [rval (assoc tree :right rt)]
         :else [(or lval rval) (-> tree
                                   (assoc ,,, :right rt)
                                   (assoc ,,, :left lt))])))))

(defn- split-tree [{:keys [left right ^long value] :as tree}]
  (cond
    ;; Split the the node
    (and value (< 9 value)) (let [nv (/ value 2)]
                              [true (u/internal-node
                                     (u/leaf-node (long (Math/floor nv)))
                                     (u/leaf-node (long (Math/ceil nv))))])
    value [false tree]
    :else (let [[split-left? lt] (split-tree left)
                [split-right? rt] (split-tree right)]
            (cond
              split-left? [split-left? (assoc tree :left lt)]
              split-right? [split-right? (assoc tree :right rt)]
              :else [false tree]))))

(defn- reduce-tree [tree]
  (let [[exploded? e-tree] (explode-tree tree)]
    (if exploded?
      (recur e-tree)
      (let [[split? s-tree] (split-tree tree)]
        (if split?
          (recur s-tree)
          tree)))))

(defn- magnitude ^long [{:keys [left right ^long value]}]
  (or value
      (+ (* 3 (magnitude left)) (* 2 (magnitude right)))))

(defn reduce-trees
  ([[sn & snailfish-numbers]]
   (reduce-trees (tree sn) snailfish-numbers))
  ([sn-tree [sn & snailfish-numbers]]
   (if sn
     (recur (reduce-tree (u/internal-node sn-tree (tree sn)))
            snailfish-numbers)
     sn-tree)))

(def snailfish-homework (parse-snailfish-numbers
                         (slurp "resources/snailfish_homework.txt")))

(defn day18-1
  ([] (day18-1 snailfish-homework))
  ([snailfish-numbers]
   (magnitude (reduce-trees snailfish-numbers))))

(defn- every-snailfish-pair
  ([ps] (every-snailfish-pair ps []))
  ([[p & ps] res]
   (if (< 1 (count ps))
     (recur ps (into res (mapv vector (repeat p) ps)))
     (let [original-pairs (conj res [p (first ps)])]
       ;; Snailfish-addition is not commutativ
       (into original-pairs
             (mapv reverse original-pairs))))))

(defn day18-2
  ([] (day18-2 snailfish-homework))
  ([snailfish-numbers]
   (apply max (mapv day18-1 (every-snailfish-pair snailfish-numbers)))))