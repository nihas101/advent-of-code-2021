(ns advent-of-code-2021.day05-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2021.day05 :refer :all]))

(def ^:private lines-input
  (str "0,9 -> 5,9\n"
       "8,0 -> 0,8\n"
       "9,4 -> 3,4\n"
       "2,2 -> 2,1\n"
       "7,0 -> 7,4\n"
       "6,4 -> 2,0\n"
       "0,9 -> 2,9\n"
       "3,4 -> 1,4\n"
       "0,0 -> 8,8\n"
       "5,5 -> 8,2\n"))

(def ^:private parsed-lines
  (#'advent-of-code-2021.day05/parse-lines lines-input))

(deftest parse-lines-test
  (testing "Day 5 - parse-lines"
    (is (= [[[0 9] [5 9]]
            [[8 0] [0 8]]
            [[9 4] [3 4]]
            [[2 2] [2 1]]
            [[7 0] [7 4]]
            [[6 4] [2 0]]
            [[0 9] [2 9]]
            [[3 4] [1 4]]
            [[0 0] [8 8]]
            [[5 5] [8 2]]]
           (#'advent-of-code-2021.day05/parse-lines lines-input)))))

(deftest extrapolate-lines-test
  (testing "Day 5 Part 1 - extrapolate-lines"
    (is (= {[0 9] 2 [1 4] 1 [1 9] 2 [2 1] 1 [2 2] 1 [2 4] 1
            [2 9] 2 [3 4] 2 [3 9] 1 [4 4] 1 [4 9] 1 [5 4] 1
            [5 9] 1 [6 4] 1 [7 0] 1 [7 1] 1 [7 2] 1 [7 3] 1
            [7 4] 2 [8 4] 1 [9 4] 1}
           (frequencies
            (mapcat #'advent-of-code-2021.day05/extrapolate-line
                    (filter (fn [[[x1 y1] [x2 y2]]] (or (= x1 x2) (= y1 y2)))
                            parsed-lines)))))))

(deftest day-5-1-example-test
  (testing "Day 5 Part 1 - example"
    (is (= 5 (day5-1 parsed-lines)))))

(deftest day-5-1-test
  (testing "Day 5 Part 1"
    (is (= 5373 (day5-1)))))

(deftest day-5-2-example-test
  (testing "Day 5 Part 2 - example"
    (is (= 12 (day5-2 parsed-lines)))))

(deftest day-5-2-test
  (testing "Day 5 Part 2"
    (is (= 21514 (day5-2)))))