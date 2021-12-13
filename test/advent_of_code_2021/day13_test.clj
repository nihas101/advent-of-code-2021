(ns advent-of-code-2021.day13-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2021.day13 :refer :all]))

(def ^:private fold-instructions-example
 (str "6,10\n"
      "0,14\n"
      "9,10\n"
      "0,3\n"
      "10,4\n"
      "4,11\n"
      "6,0\n"
      "6,12\n"
      "4,1\n"
      "0,13\n"
      "10,12\n"
      "3,4\n"
      "3,0\n"
      "8,4\n"
      "1,10\n"
      "2,14\n"
      "8,10\n"
      "9,0\n"
      "\n"
      "fold along y=7\n"
      "fold along x=5"))

(deftest parse-map-test
  (testing "Day 13 Part 1 - parse-fold-instructions"
    (is (= {:dots #{[0 3]
                    [0 13]
                    [0 14]
                    [1 10]
                    [2 14]
                    [3 0]
                    [3 4]
                    [4 1]
                    [4 11]
                    [6 0]
                    [6 10]
                    [6 12]
                    [8 4]
                    [8 10]
                    [9 0]
                    [9 10]
                    [10 4]
                    [10 12]}
            :fold-instructions [["y" 7] ["x" 5]]}
           (#'advent-of-code-2021.day13/parse-origami
            fold-instructions-example)))))

(deftest fold-paper-test
  (testing "Day 13 Part 1 - fold-paper"
    (is (= #{[0 0] [1 0] [2 0] [3 0] [4 0]
             [0 1]                   [4 1]
             [0 2]                   [4 2]
             [0 3]                   [4 3]
             [0 4] [1 4] [2 4] [3 4] [4 4]}
           (#'advent-of-code-2021.day13/fold-paper
            (#'advent-of-code-2021.day13/parse-origami
             fold-instructions-example))))))

(deftest day13-1-example-test
  (testing "Day 13 Part 1 - example"
    (is (= 17
           (day13-1
            (#'advent-of-code-2021.day13/parse-origami
             fold-instructions-example))))))

(deftest day13-1-test
  (testing "Day 13 Part 1"
    (is (= 745 (day13-1)))))

(deftest day13-2-example-test
  (testing "Day 13 Part 2 - example"
    (is (= (str "P1\n"
                "5 5\n"
                "1 1 1 1 1\n"
                "1 0 0 0 1\n"
                "1 0 0 0 1\n"
                "1 0 0 0 1\n"
                "1 1 1 1 1")
           (day13-2
            (#'advent-of-code-2021.day13/parse-origami
             fold-instructions-example))))))

(deftest day13-2-test
  (testing "Day 13 Part 2"
    (is (= (str "P1\n"
                "39 6\n"
                "0 1 1 0 0 1 1 1 0 0 1 0 0 1 0 0 0 1 1 0 1 1 1 1 0 1 1 1 0 0 0 1 1 0 0 0 1 1 0\n"
                "1 0 0 1 0 1 0 0 1 0 1 0 1 0 0 0 0 0 1 0 1 0 0 0 0 1 0 0 1 0 1 0 0 1 0 1 0 0 1\n"
                "1 0 0 1 0 1 1 1 0 0 1 1 0 0 0 0 0 0 1 0 1 1 1 0 0 1 1 1 0 0 1 0 0 0 0 1 0 0 0\n"
                "1 1 1 1 0 1 0 0 1 0 1 0 1 0 0 0 0 0 1 0 1 0 0 0 0 1 0 0 1 0 1 0 1 1 0 1 0 0 0\n"
                "1 0 0 1 0 1 0 0 1 0 1 0 1 0 0 1 0 0 1 0 1 0 0 0 0 1 0 0 1 0 1 0 0 1 0 1 0 0 1\n"
                "1 0 0 1 0 1 1 1 0 0 1 0 0 1 0 0 1 1 0 0 1 0 0 0 0 1 1 1 0 0 0 1 1 1 0 0 1 1 0")
           (day13-2)))))