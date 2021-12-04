(ns advent-of-code-2021.day04-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2021.day04 :refer :all]))

(def ^:private numbers
  [7 4 9 5 11 17 23 2 0 14 21 24 10 16 13 6 15 25 12 22 18 20 8 19 3 26 1])

(def ^:private board-1
  {:board {0 [4 0] 1 [0 4] 2 [1 1] 3 [2 3] 4 [3 1] 5 [4 3] 6 [0 3] 7 [4 2]
           8 [0 1] 9 [1 2] 10 [1 3] 11 [3 0] 12 [1 4] 13 [1 0] 14 [2 2]
           15 [3 4] 16 [3 2] 17 [2 0] 18 [3 3] 19 [4 4] 20 [2 4] 21 [0 2]
           22 [0 0] 23 [2 1] 24 [4 1]}
   :board-size 5
   :marked-positions #{}
   :board-content #{0 1 2 3 4 5 6 7 8 9 10 11 12
                    13 14 15 16 17 18 19 20 21 22 23 24}})

(def ^:private board-2
  {:board {0 [2 0] 2 [3 0] 3 [0 0] 4 [4 3] 5 [4 1] 6 [4 4] 7 [2 2] 8 [1 2]
           9 [0 1] 10 [2 3] 11 [1 3] 12 [3 4] 13 [2 1] 14 [0 4] 15 [1 0]
           16 [2 4] 17 [3 1] 18 [1 1] 19 [0 2] 20 [0 3] 21 [1 4] 22 [4 0]
           23 [4 2] 24 [3 3] 25 [3 2]}
   :board-size 5
   :marked-positions #{}
   :board-content #{0 2 3 4 5 6 7 8 9 10 11 12
                    13 14 15 16 17 18 19 20 21 22 23 24 25}})

(def ^:private board-3
  {:board {0 [1 4] 2 [0 4] 3 [3 4] 4 [4 0] 5 [4 3] 6 [3 3] 7 [4 4] 8 [1 2]
           9 [3 1] 10 [0 1] 11 [1 3] 12 [2 4] 13 [2 3] 14 [0 0] 15 [2 1]
           16 [1 1] 17 [2 0] 18 [0 2] 19 [4 1] 20 [4 2] 21 [1 0] 22 [0 3]
           23 [2 2] 24 [3 0] 26 [3 2]}
   :board-size 5
   :marked-positions #{}
   :board-content #{0 2 3 4 5 6 7 8 9 10 11 12
                    13 14 15 16 17 18 19 20 21 22 23 24 26}})

(deftest board-1-test
  (testing "Day 4 Part 1 - Board 1"
    (is (= board-1
           (#'advent-of-code-2021.day04/board
            (str "22 13 17 11  0\n"
                 "8   2 23  4 24\n"
                 "21  9 14 16  7\n"
                 " 6 10  3 18  5\n"
                 " 1 12 20 15 19"))))))

(deftest board-2-test
  (testing "Day 4 Part 1 - Board 2"
    (is (= board-2
           (#'advent-of-code-2021.day04/board
            (str " 3 15  0  2 22\n"
                 " 9 18 13 17  5\n"
                 "19  8  7 25 23\n"
                 "20 11 10 24  4\n"
                 "14 21 16 12  6\n"))))))

(deftest board-3-test
  (testing "Day 4 Part 1 - Board 3"
    (is (= board-3
           (#'advent-of-code-2021.day04/board
            (str "14 21 17 24  4\n"
                 "10 16 15  9 19\n"
                 "18  8 23 26 20\n"
                 "22 11 13  6  5\n"
                 " 2  0 12  3  7"))))))

(deftest day4-1-example-5-test
  (testing "Day 4 Part 1 - Example - First 5 numbers"
    (is (= [#{[1 2] [3 0] [3 1] [4 2] [4 3]}
            #{[0 1] [1 3] [2 2] [4 1] [4 3]}
            #{[1 3] [3 1] [4 0] [4 3] [4 4]}]
           (mapv :marked-positions 
                 (first
                  ((juxt second #(nth % 2))
                   (#'advent-of-code-2021.day04/bingo-state
                    (take 5 numbers) [board-1 board-2 board-3]))))))))

(deftest day4-1-example-11-test
  (testing "Day 4 Part 1 - Example - First 11 numbers"
    (is (= [#{[0 2] [1 1] [1 2] [2 0] [2 1] [2 2]
              [3 0] [3 1] [4 0] [4 2] [4 3]}
            #{[0 1] [0 4] [1 3] [1 4] [2 0] [2 2]
              [3 0] [3 1] [4 1] [4 2] [4 3]}
            #{[0 0] [0 4] [1 0] [1 3] [1 4] [2 0]
              [2 2] [3 1] [4 0] [4 3] [4 4]}]
           (mapv :marked-positions
                 (first
                  ((juxt second #(nth % 2))
                   (#'advent-of-code-2021.day04/bingo-state
                    (take 11 numbers)
                    [board-1 board-2 board-3]))))))))

(deftest day4-1-example-test
  (testing "Day 4 Part 1 - Example"
    (is (= 4512
           (day4-1 numbers [board-1 board-2 board-3])))))

(deftest day4-1-test
  (testing "Day 4 Part 1"
    (is (= 16674 (day4-1)))))

(deftest day4-2-example-test
  (testing "Day 4 Part 2 - Example"
    (is (= 1924
           (day4-2 numbers [board-1 board-2 board-3])))))

(deftest day4-2-test
  (testing "Day 4 Part 2"
    (is (= 7075 (day4-2)))))