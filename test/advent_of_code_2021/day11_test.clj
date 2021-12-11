(ns advent-of-code-2021.day11-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2021.day11 :refer :all]))

(defonce ^:private example-energy-levels-1
  (str "11111\n"
       "19991\n"
       "19191\n"
       "19991\n"
       "11111"))

(defonce ^:private example-energy-levels-2
  (str "5483143223\n"
       "2745854711\n"
       "5264556173\n"
       "6141336146\n"
       "6357385478\n"
       "4167524645\n"
       "2176841721\n"
       "6882881134\n"
       "4846848554\n"
       "5283751526\n"))

(deftest parse-octopus-levels-test
  (testing "Day 11 Part 1 - parse-octopus-levels"
    (is (= {:height 5 :width 5 :flashes 0
            :energy-levels
            {[0 0] 1 [0 1] 1 [0 2] 1 [0 3] 1 [0 4] 1 [1 0] 1 [1 1] 9
             [1 2] 9 [1 3] 9 [1 4] 1 [2 0] 1 [2 1] 9 [2 2] 1 [2 3] 9
             [2 4] 1 [3 0] 1 [3 1] 9 [3 2] 9 [3 3] 9 [3 4] 1 [4 0] 1
             [4 1] 1 [4 2] 1 [4 3] 1 [4 4] 1}}
           (#'advent-of-code-2021.day11/parse-octopus-levels
            example-energy-levels-1)))))

(deftest day11-1-example-1-test
  (testing "Day 11 Part 1 - example 1"
    (are [x y] (= x y)
      9 (day11-1 (#'advent-of-code-2021.day11/parse-octopus-levels
                  example-energy-levels-1) 1)

      9 (day11-1 (#'advent-of-code-2021.day11/parse-octopus-levels
                  example-energy-levels-1) 2))))

(deftest day11-1-example-2-test
  (testing "Day 11 Part 1 - example 2"
    (are [x y] (= x y)
      0 (day11-1 (#'advent-of-code-2021.day11/parse-octopus-levels
                  example-energy-levels-2) 1)
      35 (day11-1 (#'advent-of-code-2021.day11/parse-octopus-levels
                   example-energy-levels-2) 2)
      204 (day11-1 (#'advent-of-code-2021.day11/parse-octopus-levels
                    example-energy-levels-2) 10)
      1656 (day11-1 (#'advent-of-code-2021.day11/parse-octopus-levels
                     example-energy-levels-2) 100)
      3025 (day11-1 (#'advent-of-code-2021.day11/parse-octopus-levels
                     example-energy-levels-2) 193)
      3025 (day11-1 (#'advent-of-code-2021.day11/parse-octopus-levels
                     example-energy-levels-2) 194)
      3125 (day11-1 (#'advent-of-code-2021.day11/parse-octopus-levels
                     example-energy-levels-2) 195))))

(deftest day11-1-test
  (testing "Day 11 Part 1"
    (is (= 1642 (day11-1)))))

(deftest day11-2-example-test
  (testing "Day 11 Part 2 - example"
    (is (= 195 (day11-2 (#'advent-of-code-2021.day11/parse-octopus-levels
                         example-energy-levels-2))))))

(deftest day11-2-test
  (testing "Day 11 Part 2"
    (is (= 320 (day11-2)))))