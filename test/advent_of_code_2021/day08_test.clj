(ns advent-of-code-2021.day08-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2021.day08 :refer :all]))

(def ^:private example-signal-pattern-1
  (str "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab "
       "| cdfeb fcadb cdfeb cdbaf"))

(deftest parse-signal-pattern-test
  (testing "Day 8 Part 1 - parse-signal-pattern"
    (is (= {:output ["bcdef" "abcdf" "bcdef" "abcdf"]
            :signals ["abcdefg"
                      "bcdef"
                      "acdfg"
                      "abcdf"
                      "abd"
                      "abcdef"
                      "bcdefg"
                      "abef"
                      "abcdeg"
                      "ab"]}
           (#'advent-of-code-2021.day08/parse-signal-pattern
            example-signal-pattern-1)))))

(def ^:private example-signal-pattern-2
  (#'advent-of-code-2021.day08/parse-signal-patterns
   (str "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe\n"
        "edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc\n"
        "fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg\n"
        "fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb\n"
        "aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea\n"
        "fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb\n"
        "dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe\n"
        "bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef\n"
        "egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb\n"
        "gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce")))

(deftest day8-1-example-test
  (testing "Day 8 Part 1 - parse-signal-pattern"
    (is (= 26
           (day8-1 example-signal-pattern-2)))))

(deftest day8-1-test
  (testing "Day 8 Part 1"
    (is (= 514 (day8-1)))))

(deftest day8-2-example-1-test
  (testing "Day 8 Part 2 - parse-signal-pattern"
    (is (= 5353
           (day8-2
            [(#'advent-of-code-2021.day08/parse-signal-pattern
              example-signal-pattern-1)])))))

(deftest day8-2-example-2-test
  (testing "Day 8 Part 2 - parse-signal-pattern"
    (is (= 61229 (day8-2 example-signal-pattern-2)))))

(deftest day8-2-test
  (testing "Day 8 Part 2"
    (is (= 1012272 (day8-2)))))