(ns advent-of-code-2021.day01-test
  (:require [clojure.test :refer :all]
            [advent-of-code-2021.day01 :refer :all]))

(deftest day1-1-example-test
  (testing "Day 1 Part 1 - Example"
    (is (= 7
           (day1-1 [199
                    200
                    208
                    210
                    200
                    207
                    240
                    269
                    260
                    263])))))

(deftest day1-1-test
  (testing "Day 1 Part 1"
    (is (= 1752 (day1-1)))))

(deftest day1-2-example-test
  (testing "Day 1 Part 2 - Example"
    (is (= 5
           (day1-2 [199
                    200
                    208
                    210
                    200
                    207
                    240
                    269
                    260
                    263])))))

(deftest day1-2-test
  (testing "Day 1 Part 2"
    (is (= 1781 (day1-2)))))