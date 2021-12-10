(ns advent-of-code-2021.day10-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2021.day10 :refer :all]))

(def example-program
  (str "[({(<(())[]>[[{[]{<()<>>\n"
       "[(()[<>])]({[<{<<[]>>(\n"
       "{([(<{}[<>[]}>{[]{[(<()>\n"
       "(((({<>}<{<{<>}{[]{[]{}\n"
       "[[<[([]))<([[{}[[()]]]\n"
       "[{[{({}]{}}([{[{{{}}([]\n"
       "{<[[]]>}<{[{[{[]{()[[[]\n"
       "[<(<(<(<{}))><([]([]()\n"
       "<{([([[(<>()){}]>(<<{{\n"
       "<{([{{}}[<[[[<>{}]]]>[]]\n"))

(deftest syntax-state-test
  (testing "Day 10 Part 1 - syntax-state"
    (are [x y] (= x y)
      [:complete] (#'advent-of-code-2021.day10/syntax-state "<>")
      [:complete] (#'advent-of-code-2021.day10/syntax-state "()")
      [:complete] (#'advent-of-code-2021.day10/syntax-state "[]")
      [:complete] (#'advent-of-code-2021.day10/syntax-state "{}")

      [:incomplete [\>]] (#'advent-of-code-2021.day10/syntax-state "<")
      [:incomplete [\)]] (#'advent-of-code-2021.day10/syntax-state "(")
      [:incomplete [\]]] (#'advent-of-code-2021.day10/syntax-state "[")
      [:incomplete [\}]] (#'advent-of-code-2021.day10/syntax-state "{")

      [:corrupted \)] (#'advent-of-code-2021.day10/syntax-state "<)")
      [:corrupted \}] (#'advent-of-code-2021.day10/syntax-state "(}")
      [:corrupted \>] (#'advent-of-code-2021.day10/syntax-state "[>")
      [:corrupted \]] (#'advent-of-code-2021.day10/syntax-state "{]"))))

(deftest parse-parse-program
  (testing "Day 10 Part 1 - parse-program"
    (is (= [{:program "[({(<(())[]>[[{[]{<()<>>"
             :state [:incomplete [\} \} \] \] \) \} \) \]]]}
            {:program "[(()[<>])]({[<{<<[]>>("
             :state [:incomplete [\) \} \> \] \} \)]]}
            {:program "{([(<{}[<>[]}>{[]{[(<()>", :state [:corrupted \}]}
            {:program "(((({<>}<{<{<>}{[]{[]{}"
             :state [:incomplete [\} \} \> \} \> \) \) \) \)]]}
            {:program "[[<[([]))<([[{}[[()]]]", :state [:corrupted \)]}
            {:program "[{[{({}]{}}([{[{{{}}([]", :state [:corrupted \]]}
            {:program "{<[[]]>}<{[{[{[]{()[[[]"
             :state [:incomplete [\] \] \} \} \] \} \] \} \>]]}
            {:program "[<(<(<(<{}))><([]([]()", :state [:corrupted \)]}
            {:program "<{([([[(<>()){}]>(<<{{", :state [:corrupted \>]}
            {:program "<{([{{}}[<[[[<>{}]]]>[]]"
             :state [:incomplete [\] \) \} \>]]}]
           (#'advent-of-code-2021.day10/parse-program example-program)))))

(deftest day10-1-example-test
  (testing "Day 10 Part 1 - example"
    (is (= 26397
           (day10-1
            (#'advent-of-code-2021.day10/parse-program example-program))))))

(deftest day10-1-test
  (testing "Day 10 Part 1"
    (is (= 367059 (day10-1)))))

(deftest day10-2-example-test
  (testing "Day 10 Part 2 - example"
    (is (= 288957
           (day10-2
            (#'advent-of-code-2021.day10/parse-program example-program))))))

(deftest day10-2-test
  (testing "Day 10 Part 2"
    (is (= 1952146692 (day10-2)))))