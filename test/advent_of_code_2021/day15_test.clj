(ns advent-of-code-2021.day15-test
  (:require
   [advent-of-code-2021.utils :as u]
   [clojure.test :refer :all]
   [advent-of-code-2021.day15 :refer :all])
  (:import
   [advent_of_code_2021.day15 Path]))

(defonce ^:private example-ceiling
  (str "1163751742\n"
       "1381373672\n"
       "2136511328\n"
       "3694931569\n"
       "7463417111\n"
       "1319128137\n"
       "1359912421\n"
       "3125421639\n"
       "1293138521\n"
       "2311944581\n"))

(deftest parse-polymer-input-test
  (testing "Day 14 Part 1 - parse-polymer-input"
    (is (= {[8 8] 2 [7 6] 4 [8 7] 3 [9 8] 1 [7 1] 6 [8 9] 8 [4 3] 9 [2 2] 3
            [0 0] 1 [3 9] 1 [7 7] 6 [2 8] 9 [1 0] 1 [8 4] 1 [2 3] 9 [2 5] 1
            [7 2] 3 [6 7] 1 [7 4] 1 [8 3] 6 [0 6] 1 [3 3] 4 [5 4] 1 [1 1] 3
            [6 3] 1 [0 5] 1 [3 4] 3 [7 3] 5 [8 6] 2 [4 2] 5 [7 8] 5 [3 0] 3
            [9 0] 2 [6 6] 2 [9 6] 1 [1 9] 3 [5 3] 3 [9 9] 1 [9 3] 9 [4 7] 4
            [4 9] 9 [2 9] 1 [6 5] 8 [0 9] 2 [8 0] 4 [4 1] 3 [5 2] 1 [4 6] 9
            [1 4] 4 [5 7] 2 [8 2] 2 [1 3] 6 [4 8] 1 [1 5] 3 [1 8] 2 [1 7] 1
            [6 4] 7 [8 1] 7 [0 3] 3 [5 1] 7 [6 1] 3 [5 6] 1 [5 8] 3 [8 5] 3
            [0 7] 3 [6 8] 8 [5 5] 2 [7 9] 5 [2 7] 2 [5 9] 4 [2 4] 6 [3 6] 9
            [9 2] 8 [4 5] 1 [9 1] 2 [9 7] 9 [7 0] 7 [0 2] 2 [6 9] 4 [2 0] 6
            [0 4] 7 [3 1] 1 [2 1] 8 [9 5] 7 [3 8] 3 [9 4] 1 [1 6] 3 [4 4] 4
            [3 7] 5 [7 5] 1 [2 6] 5 [5 0] 5 [6 2] 1 [6 0] 1 [1 2] 1 [3 5] 9
            [0 8] 1 [3 2] 6 [0 1] 1 [4 0] 7
            :height 10
            :width 10}
           (u/parse-positional-map example-ceiling)))))

(deftest lowest-risk-path-1x1-test
  (testing "Day 15 Part 1 - 1x1 example"
    (is (= []
           (let [m (u/parse-positional-map "1")]
            (#'advent-of-code-2021.day15/lowest-risk-path m m))))))

(deftest lowest-risk-path-2x2-test
  (testing "Day 15 Part 1 - 2x2 example"
    (is (= (Path.
            [[0 0] [1 0] [1 1]]
            2 0)
           (let [m (u/parse-positional-map "11\n21")]
            (#'advent-of-code-2021.day15/lowest-risk-path m m))))))

(deftest lowest-risk-path-4x4-test
  (testing "Day 15 Part 1 - 4x4 example"
    (is (= (Path.
            [[0 0] [1 0] [1 1] [1 2] [1 3] [2 3] [3 3]]
            9 0)
           (let [m (u/parse-positional-map "1111\n2134\n5153\n4231")]
            (#'advent-of-code-2021.day15/lowest-risk-path m m))))))

(deftest lowest-risk-path-example-test
  (testing "Day 15 Part 1 - example"
    (is (= (Path.
            [[0 0] [0 1] [0 2] [1 2] [2 2] [3 2] [4 2] [5 2] [6 2]
             [6 3] [7 3] [7 4] [8 4] [8 5] [8 6] [8 7] [8 8] [9 8] [9 9]]
            40 0)
           (let [m (u/parse-positional-map example-ceiling)]
             (#'advent-of-code-2021.day15/lowest-risk-path m m))))))

(deftest day15-1-example-test
  (testing "Day 15 Part 1 - example"
    (is (= 40
           (day15-1
            (u/parse-positional-map example-ceiling))))))

(deftest day15-1-test
  (testing "Day 15 Part 1"
    (is (= 423 (day15-1)))))

(deftest day15-2-example-test
  (testing "Day 15 Part 2 - example"
    (is (= 315
           (day15-2
            (u/parse-positional-map example-ceiling))))))

(deftest day15-2-test
  (testing "Day 15 Part 2"
    (is (= 2778  (day15-2)))))