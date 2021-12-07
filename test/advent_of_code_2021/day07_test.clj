(ns advent-of-code-2021.day07-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2021.day07 :refer :all]))

(def ^:private example-crabs [16 1 2 0 4 2 7 1 2 14])

(deftest day7-1-example-test
  (testing "Day 7 Part 1 - Example"
    (is (= 37 (day7-1 example-crabs)))))

(deftest day7-1-test
  (testing "Day 7 Part 1"
    (is (= 345035 (day7-1)))))

(deftest day7-2-example-test
  (testing "Day 7 Part 2 - Example"
    (is (= 168 (day7-2 example-crabs)))))

(deftest day7-2-test
  (testing "Day 7 Part 2"
    (is (= 97038163 (day7-2)))))