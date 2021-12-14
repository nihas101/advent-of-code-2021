(ns advent-of-code-2021.day14-test
  (:require
   [clojure.test :refer :all]
   [clojure.string :as string]
   [advent-of-code-2021.day14 :refer :all]))

(defonce ^:private polymer-example
  (str "NNCB\n"
       "\n"
       "CH -> B\n"
       "HH -> N\n"
       "CB -> H\n"
       "NH -> C\n"
       "HB -> C\n"
       "HC -> B\n"
       "HN -> C\n"
       "NN -> C\n"
       "BH -> H\n"
       "NC -> B\n"
       "NB -> B\n"
       "BN -> B\n"
       "BB -> N\n"
       "BC -> B\n"
       "CC -> N\n"
       "CN -> C\n"))

(deftest parse-polymer-input-test
  (testing "Day 14 Part 1 - parse-polymer-input"
    (is (= {:insertion-rules {"BB" "N"
                              "BC" "B"
                              "BH" "H"
                              "BN" "B"
                              "CB" "H"
                              "CC" "N"
                              "CH" "B"
                              "CN" "C"
                              "HB" "C"
                              "HC" "B"
                              "HH" "N"
                              "HN" "C"
                              "NB" "B"
                              "NC" "B"
                              "NH" "C"
                              "NN" "C"}
            :polymer {"CB" 1, "NC" 1, "NN" 1}
            :input "NNCB"}
           (#'advent-of-code-2021.day14/parse-polymer-input
            polymer-example)))))

(defn- example->freq [example]
  (frequencies
   (mapv string/join
         (partition 2 1 example))))

(deftest day14-1-example-1-test
  (testing "Day 14 Part 1 - example 1"
    (is (= (example->freq "NCNBCHB")
           (#'advent-of-code-2021.day14/create-polymer
            (#'advent-of-code-2021.day14/parse-polymer-input
             polymer-example) 1)))))

(deftest day14-1-example-2-test
  (testing "Day 14 Part 1 - example 2"
    (is (= (example->freq "NBCCNBBBCBHCB")
           (#'advent-of-code-2021.day14/create-polymer
            (#'advent-of-code-2021.day14/parse-polymer-input
             polymer-example) 2)))))

(deftest day14-1-example-3-test
  (testing "Day 14 Part 1 - example 3"
    (is (= (example->freq "NBBBCNCCNBBNBNBBCHBHHBCHB")
           (#'advent-of-code-2021.day14/create-polymer
            (#'advent-of-code-2021.day14/parse-polymer-input
             polymer-example) 3)))))

(deftest day14-1-example-4-test
  (testing "Day 14 Part 1 - example 4"
    (is (= (example->freq "NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB")
           (#'advent-of-code-2021.day14/create-polymer
            (#'advent-of-code-2021.day14/parse-polymer-input
             polymer-example) 4)))))

(deftest day14-1-example-10-test
  (testing "Day 14 Part 1 - example 10"
    (is (= 1588
           (day14-1
            (#'advent-of-code-2021.day14/parse-polymer-input
             polymer-example) 10)))))

(deftest day14-1-test
  (testing "Day 14 Part 1"
    (is (= 2068 (day14-1)))))

(deftest day14-2-test
  (testing "Day 14 Part 2"
    (is (= 2158894777814 (day14-2)))))