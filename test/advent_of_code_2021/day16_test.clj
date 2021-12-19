(ns advent-of-code-2021.day16-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2021.day16 :refer :all]))

(deftest parse-packet-example-1-test
  (testing "Day 16 - parse-packet example 1"
    (is (= {:bin-packet "000"
            :packets [[6 :literal-value 2021]]}
           (parse-packet
            (#'advent-of-code-2021.day16/packet->bin-packet "D2FE28"))))))

(deftest parse-packet-example-2-test
  (testing "Day 16 - parse-packet example 2"
    (is (= {:bin-packet "0000000"
            :packets [[1 :less-than
                       [[6 :literal-value 10]
                        [2 :literal-value 20]]]]}
           (parse-packet
            (#'advent-of-code-2021.day16/packet->bin-packet "38006F45291200"))))))

(deftest parse-packet-example-3-test
  (testing "Day 16 - parse-packet example 3"
    (is (= {:bin-packet "00000"
            :packets [[7 :maximum
                       [[2 :literal-value 1]
                        [4 :literal-value 2]
                        [1 :literal-value 3]]]]}
           (parse-packet
            (#'advent-of-code-2021.day16/packet->bin-packet "EE00D40C823060"))))))

(deftest day16-1-example-1-test
  (testing "Day 16 1 - example 1"
    (is (= 16
           (day16-1
            (#'advent-of-code-2021.day16/packet->bin-packet "8A004A801A8002F478"))))))

(deftest day16-1-example-2-test
  (testing "Day 16 1 - example 2"
    (is (= 12
           (day16-1
            (#'advent-of-code-2021.day16/packet->bin-packet "620080001611562C8802118E34"))))))

(deftest day16-1-example-3-test
  (testing "Day 16 1 - example 3"
    (is (= 23
           (day16-1
            (#'advent-of-code-2021.day16/packet->bin-packet "C0015000016115A2E0802F182340"))))))

(deftest day16-1-example-4-test
  (testing "Day 16 1 - example 4"
    (is (= 31
           (day16-1
            (#'advent-of-code-2021.day16/packet->bin-packet "A0016C880162017C3686B18A3D4780"))))))

(deftest day16-1-test
  (testing "Day 16 1"
    (is (= 889 (day16-1)))))

(deftest day16-2-example-1-test
  (testing "Day 16 2 - example 1"
    (is (= 3
           (day16-2
            (#'advent-of-code-2021.day16/packet->bin-packet "C200B40A82"))))))

(deftest day16-2-example-2-test
  (testing "Day 16 2 - example 2"
    (is (= 54
           (day16-2
            (#'advent-of-code-2021.day16/packet->bin-packet "04005AC33890"))))))

(deftest day16-2-example-3-test
  (testing "Day 16 2 - example 3"
    (is (= 7
           (day16-2
            (#'advent-of-code-2021.day16/packet->bin-packet "880086C3E88112"))))))

(deftest day16-2-example-4-test
  (testing "Day 16 2 - example 4"
    (is (= 9
           (day16-2
            (#'advent-of-code-2021.day16/packet->bin-packet "CE00C43D881120"))))))

(deftest day16-2-example-5-test
  (testing "Day 16 2 - example 5"
    (is (= 1
           (day16-2
            (#'advent-of-code-2021.day16/packet->bin-packet "D8005AC2A8F0"))))))

(deftest day16-2-example-6-test
  (testing "Day 16 2 - example 6"
    (is (= 0
           (day16-2
            (#'advent-of-code-2021.day16/packet->bin-packet "F600BC2D8F"))))))

(deftest day16-2-example-7-test
  (testing "Day 16 2 - example 7"
    (is (= 0
           (day16-2
            (#'advent-of-code-2021.day16/packet->bin-packet "9C005AC2F8F0"))))))

(deftest day16-2-example-8-test
  (testing "Day 16 2 - example 8"
    (is (= 1
           (day16-2
            (#'advent-of-code-2021.day16/packet->bin-packet "9C0141080250320F1802104A08"))))))

(deftest day16-2-test
  (testing "Day 16 2"
    (is (= 739303923668 (day16-2)))))