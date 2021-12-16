(ns advent-of-code-2021.day09-test
  (:require
   [advent-of-code-2021.utils :as u]
   [clojure.test :refer :all]
   [advent-of-code-2021.day09 :refer :all]))

(defonce ^:private example-height-map (str "2199943210\n"
                                           "3987894921\n"
                                           "9856789892\n"
                                           "8767896789\n"
                                           "9899965678\n"))

(deftest parse-height-map-test
  (testing "Day 9 Part 1 - parse-height-map"
    (is (= {[0 0] 2 [0 1] 3 [0 2] 9 [0 3] 8 [0 4] 9 [1 0] 1 [1 1] 9 [1 2] 8
            [1 3] 7 [1 4] 8 [2 0] 9 [2 1] 8 [2 2] 5 [2 3] 6 [2 4] 9 [3 0] 9
            [3 1] 7 [3 2] 6 [3 3] 7 [3 4] 9 [4 0] 9 [4 1] 8 [4 2] 7 [4 3] 8
            [4 4] 9 [5 0] 4 [5 1] 9 [5 2] 8 [5 3] 9 [5 4] 6 [6 0] 3 [6 1] 4
            [6 2] 9 [6 3] 6 [6 4] 5 [7 0] 2 [7 1] 9 [7 2] 8 [7 3] 7 [7 4] 6
            [8 0] 1 [8 1] 2 [8 2] 9 [8 3] 8 [8 4] 7 [9 0] 0 [9 1] 1 [9 2] 2
            [9 3] 9 [9 4] 8
            :height 5 :width 10}
           (u/parse-positional-map example-height-map)))))

(deftest day9-1-example-test
  (testing "Day 9 Part 1 - example"
    (is (= 15
           (day9-1 (u/parse-positional-map example-height-map))))))

(deftest day9-1-test
  (testing "Day 9 Part 1"
    (is (= 570 (day9-1)))))

(deftest day9-2-example-test
  (testing "Day 9 Part 2 - example"
    (is (= 1134
           (day9-2 (u/parse-positional-map example-height-map))))))

(deftest day9-2-test
  (testing "Day 9 Part 2"
    (is (= 899392 (day9-2)))))