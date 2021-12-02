(ns advent-of-code-2021.day02-test
    (:require
     [clojure.test :refer :all]
     [advent-of-code-2021.day02 :refer :all]))

(deftest day2-1-example-test
  (testing "Day 2 Part 1 - Example"
    (is (= 150
           (day2-1 [[:forward 5]
                    [:down 5]
                    [:forward 8]
                    [:up 3]
                    [:down 8]
                    [:forward 2]])))))

(deftest parse-course-test
  (testing "parse-course"
    (is (= [[:forward 5]
            [:up 10]]
           (#'advent-of-code-2021.day02/parse-course "forward 5\nup 10")))))

(deftest day2-1-test
  (testing "Day 2 Part 1"
    (is (= 1507611 (day2-1)))))

(deftest day2-2-example-test
  (testing "Day 2 Part 2 - Example"
    (is (= 900
           (day2-2 [[:forward 5]
                    [:down 5]
                    [:forward 8]
                    [:up 3]
                    [:down 8]
                    [:forward 2]])))))

(deftest day2-2-test
  (testing "Day 2 Part 2"
    (is (= 1880593125 (day2-2)))))