(ns advent-of-code-2021.day17-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2021.day17 :refer :all]))

(def example-target-area "target area: x=20..30, y=-10..-5")

(deftest parse-target-area-test
  (testing "Day 17 1 - parse-target-area"
    (is (= {:target-area #{[20 -10] [20 -9] [20 -8] [20 -7] [20 -6] [20 -5]
                           [21 -10] [21 -9] [21 -8] [21 -7] [21 -6] [21 -5]
                           [22 -10] [22 -9] [22 -8] [22 -7] [22 -6] [22 -5]
                           [23 -10] [23 -9] [23 -8] [23 -7] [23 -6] [23 -5]
                           [24 -10] [24 -9] [24 -8] [24 -7] [24 -6] [24 -5]
                           [25 -10] [25 -9] [25 -8] [25 -7] [25 -6] [25 -5]
                           [26 -10] [26 -9] [26 -8] [26 -7] [26 -6] [26 -5]
                           [27 -10] [27 -9] [27 -8] [27 -7] [27 -6] [27 -5]
                           [28 -10] [28 -9] [28 -8] [28 -7] [28 -6] [28 -5]
                           [29 -10] [29 -9] [29 -8] [29 -7] [29 -6] [29 -5]
                           [30 -10] [30 -9] [30 -8] [30 -7] [30 -6] [30 -5]}
            :limit [[20 31] [-10 -4]]}
           (#'advent-of-code-2021.day17/parse-target-area example-target-area)))))

(deftest simulate-launch-example-1-test
  (testing "Day 17 1 - simulate-launch"
    (is (= [[0 0] [7 2] [13 3] [18 3] [22 2] [25 0] [27 -3] [28 -7]]
           (#'advent-of-code-2021.day17/simulate-launch
            [7 2]
            (#'advent-of-code-2021.day17/parse-target-area example-target-area))))))

(deftest simulate-launch-example-2-test
  (testing "Day 17 1 - simulate-launch"
    (is (= [[0 0] [6 3] [11 5] [15 6] [18 6] [20 5] [21 3] [21 0] [21 -4] [21 -9]]
           (#'advent-of-code-2021.day17/simulate-launch
            [6 3]
            (#'advent-of-code-2021.day17/parse-target-area example-target-area))))))

(deftest simulate-launch-example-3-test
  (testing "Day 17 1 - simulate-launch"
    (is (= [[0 0] [9 0] [17 -1] [24 -3] [30 -6]]
           (#'advent-of-code-2021.day17/simulate-launch
            [9 0]
            (#'advent-of-code-2021.day17/parse-target-area example-target-area))))))

(deftest simulate-launch-example-4-test
  (testing "Day 17 1 - simulate-launch"
    (is (= nil
           (#'advent-of-code-2021.day17/simulate-launch
            [17 -4]
            (#'advent-of-code-2021.day17/parse-target-area example-target-area))))))

(deftest simulate-launch-example-5-test
  (testing "Day 17 1 - simulate-launch"
    (is (= [[0 0] [6 9] [11 17] [15 24] [18 30] [20 35] [21 39] [21 42] [21 44]
            [21 45] [21 45] [21 44] [21 42] [21 39] [21 35] [21 30] [21 24]
            [21 17] [21 9] [21 0] [21 -10]]
           (#'advent-of-code-2021.day17/simulate-launch
            [6 9]
            (#'advent-of-code-2021.day17/parse-target-area example-target-area))))))

(deftest day-17-1-example-test
  (testing "Day 17 1 - example"
    (is (= 45
           (day17-1
            (#'advent-of-code-2021.day17/parse-target-area example-target-area))))))

(deftest day-17-1-test
  (testing "Day 17 1"
    (is (= 10011 (day17-1)))))

(deftest day-17-2-test
  (testing "Day 17 2"
    (is (= 2994 (day17-2)))))