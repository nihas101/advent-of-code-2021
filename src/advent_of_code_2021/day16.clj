(ns advent-of-code-2021.day16
  (:require
   [clojure.string :as string]))

(defonce ^:private hex->bin
  {\0 "0000"
   \1 "0001"
   \2 "0010"
   \3 "0011"
   \4 "0100"
   \5 "0101"
   \6 "0110"
   \7 "0111"
   \8 "1000"
   \9 "1001"
   \A "1010"
   \B "1011"
   \C "1100"
   \D "1101"
   \E "1110"
   \F "1111"})

(defonce ^:private typeId->type
  {0 :sum
   1 :product
   2 :minimum
   3 :maximum
   4 :literal-value
   5 :greater-than
   6 :less-than
   7 :equal-to})

(defonce ^:private header-length 6)

(defn- header [{:keys [bin-packet]}]
  (let [hdr (subs bin-packet 0 header-length)
        version (Long/parseLong (subs hdr 0 3) 2)
        type (typeId->type (Long/parseLong (subs hdr 3) 2))]
    [version type]))

(defn- packet->bin-packet [packet]
  {:bin-packet (string/join (mapv hex->bin packet))
   :packets []})

(defmulti parse-packet (comp second header))

(defn- packet-content [bin-packet]
  (subs bin-packet header-length))

(defn- take-until-last-packet [f]
  (fn ([res] res)
    ([res [i :as is]]
     (if (= i \0)
       (reduced (f res is))
       (f res is)))))

(defn- read-bytes [content]
  (Long/parseLong (string/join content) 2))

(defmethod parse-packet :literal-value [{:keys [bin-packet] :as packets}]
  (let [[version type] (header packets)
        content (packet-content bin-packet)
        bytes (transduce
               (comp
                (partition-all 5)
                take-until-last-packet
                (map rest))
               into [] content)]
    (-> packets
        (update ,,, :packets conj [version type (read-bytes bytes)])
        (update ,,, :bin-packet subs (+ header-length
                                        (count bytes)
                                        (quot (count bytes) 4))))))

(defn- parse-by-length [packets [version type] content]
  (let [subcontent-length (+ 16 (read-bytes (subs content 1 16)))]
    (loop [{:keys [bin-packet] :as subpackets}
           (-> packets
               (assoc ,,, :bin-packet (subs content 16 subcontent-length))
               (assoc ,,, :packets []))]
      (if (seq bin-packet)
        (recur (parse-packet subpackets))
        (-> subpackets
            (assoc ,,, :bin-packet (subs content subcontent-length))
            (update ,,, :packets (fn [subps]
                                   (conj (:packets packets) [version type subps]))))))))

(defn- parse-by-count [packets [version type] content]
  (let [subcontent-count (read-bytes (subs content 1 12))
        subpackets (reduce (fn [p _] (parse-packet p))
                           (-> packets
                               (assoc ,,, :bin-packet (subs content 12))
                               (assoc ,,, :packets []))
                           (range subcontent-count))]
    (update subpackets :packets (fn [subps]
                                  (conj (:packets packets)
                                        [version type subps])))))

(defmethod parse-packet :default [{:keys [bin-packet] :as packets}]
  (let [hdr (header packets)
        [c :as content] (packet-content bin-packet)]
    (cond
      (= c \0) (parse-by-length packets hdr content)
      (= c \1) (parse-by-count packets hdr content))))

(def ^:private packets
  (packet->bin-packet (slurp "resources/packets.txt")))

(defn- versions [[version _ pkts]]
  (if (and (number? version) (coll? pkts))
    (into [version] (mapcat versions pkts))
    [version]))

(defn day16-1
  "https://adventofcode.com/2021/day/16"
  ([] (day16-1 packets))
  ([packet]
   (reduce + (-> packet
                 parse-packet
                 :packets
                 first
                 versions))))

(defmulti process-packet second)

(defmethod process-packet :sum [[_ _ pkts]]
  (reduce + (map process-packet pkts)))

(defmethod process-packet :literal-value [[_ _ pkts]] pkts)

(defmethod process-packet :product [[_ _ pkts]]
  (reduce * (map process-packet pkts)))

(defmethod process-packet :minimum [[_ _ pkts]]
  (apply min (map process-packet pkts)))

(defmethod process-packet :maximum [[_ _ pkts]]
  (apply max (map process-packet pkts)))

(defmethod process-packet :greater-than [[_ _ [a b]]]
  (if (> (process-packet a) (process-packet b)) 
    1 0))

(defmethod process-packet :less-than [[_ _ [a b]]]
  (if (< (process-packet a) (process-packet b))
    1 0))

(defmethod process-packet :equal-to [[_ _ [a b]]]
  (if (= (process-packet a) (process-packet b))
    1 0))

(defn day16-2
  "https://adventofcode.com/2021/day/16"
  ([] (day16-2 packets))
  ([packet]
   (-> packet
       parse-packet
       :packets
       first
       process-packet)))