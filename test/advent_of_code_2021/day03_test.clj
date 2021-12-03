(ns advent-of-code-2021.day03-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2021.day03 :refer :all]))

(deftest day3-1-example-test
  (testing "Day 3 Part 1 - Example"
    (is (= 198
           (day3-1 ["00100"
                    "11110"
                    "10110"
                    "10111"
                    "10101"
                    "01111"
                    "00111"
                    "11100"
                    "10000"
                    "11001"
                    "00010"
                    "01010"])))))

(deftest day3-1-test
  (testing "Day 3 Part 1"
    (is (= 741950 (day3-1)))))

(deftest day3-2-example-test
  (testing "Day 3 Part 2 - Example"
    (is (= 230
           (day3-2 ["00100"
                    "11110"
                    "10110"
                    "10111"
                    "10101"
                    "01111"
                    "00111"
                    "11100"
                    "10000"
                    "11001"
                    "00010"
                    "01010"])))))

(deftest day3-2-test
  (testing "Day 3 Part 2"
    (is (= 903810 (day3-2)))))