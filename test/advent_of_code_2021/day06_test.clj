(ns advent-of-code-2021.day06-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2021.day06 :refer :all]))

(def ^:private example-fish (frequencies [3 4 3 1 2]))

(deftest day6-1-example-1-test
  (testing "Day 6 Part 1 - simulate-fish-spawn 1"
    (is (= (frequencies [2,3,2,0,1])
           (#'advent-of-code-2021.day06/simulate-fish-spawn
            example-fish 1)))))

(deftest day6-1-example-2-test
  (testing "Day 6 Part 1 - simulate-fish-spawn 2"
    (is (= (frequencies [1,2,1,6,0,8])
           (#'advent-of-code-2021.day06/simulate-fish-spawn
            example-fish 2)))))

(deftest day6-1-example-3-test
  (testing "Day 6 Part 1 - simulate-fish-spawn 3"
    (is (= (frequencies [0,1,0,5,6,7,8])
           (#'advent-of-code-2021.day06/simulate-fish-spawn
            example-fish 3)))))

(deftest day6-1-example-4-test
  (testing "Day 6 Part 1 - simulate-fish-spawn 4"
    (is (= (frequencies [6,0,6,4,5,6,7,8,8])
           (#'advent-of-code-2021.day06/simulate-fish-spawn
            example-fish 4)))))

(deftest day6-1-example-18-test
  (testing "Day 6 Part 1 - simulate-fish-spawn 18"
    (is (= (frequencies [6 0 6 4 5 6 0 1 1 2 6 0 1 1 1 2 2 3 3 4 6 7 8 8 8 8])
           (#'advent-of-code-2021.day06/simulate-fish-spawn
            example-fish 18)))))

(deftest day6-1-example-80-test
  (testing "Day 6 Part 1 - Example"
    (is (= 5934 (day6 example-fish 80)))))

(deftest day6-1-test
  (testing "Day 6 Part 1"
    (is (= 388739 (day6 80)))))

(deftest day6-2-example-256-test
  (testing "Day 6 Part 2 - Example"
    (is (= 26984457539 (day6 example-fish 256)))))

(deftest day6-2-test
  (testing "Day 6 Part 1"
    (is (= 1741362314973 (day6 256)))))