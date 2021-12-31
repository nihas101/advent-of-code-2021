(ns advent-of-code-2021.day18-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2021.utils :as u]
   [advent-of-code-2021.day18 :refer :all]))

(def example-snailfish-numbers
  (str "[1,2]\n"
       "[[1,2],3]\n"
       "[9,[8,7]]\n"
       "[[1,9],[8,5]]\n"
       "[[[[1,2],[3,4]],[[5,6],[7,8]]],9]\n"
       "[[[9,[3,8]],[[0,9],6]],[[[3,7],[4,9]],3]]\n"
       "[[[[1,3],[5,3]],[[1,3],[8,7]]],[[[4,9],[6,9]],[[8,2],[7,3]]]]\n"))

(deftest parse-snailfish-numbers-test
  (testing "Day 17 1 - parse-snailfish-numbers"
    (is (= [[1 2]
            [[1 2] 3]
            [9 [8 7]]
            [[1 9] [8 5]]
            [[[[1 2] [3 4]] [[5 6] [7 8]]] 9]
            [[[9 [3 8]] [[0 9] 6]] [[[3 7] [4 9]] 3]]
            [[[[1 3] [5 3]] [[1 3] [8 7]]] [[[4 9] [6 9]] [[8 2] [7 3]]]]]
           (#'advent-of-code-2021.day18/parse-snailfish-numbers example-snailfish-numbers)))))

(deftest tree-test
  (testing "Day 18 1 - tree"
    (is (= {:left {:left {:left {:left {:left {:value 9}
                                        :right {:value 8}}
                                 :right {:value 1}}
                          :right {:value 2}}
                   :right {:value 3}}
            :right {:value 4}}
           (#'advent-of-code-2021.day18/tree [[[[[9 8] 1] 2] 3] 4])))))

(deftest explode-tree-0-test
  (testing "Day 18 1 - explode-tree 0"
    (is (= [nil {:value 4}]
           (#'advent-of-code-2021.day18/explode-tree
            (#'advent-of-code-2021.day18/tree 4))))))

(deftest explode-tree-1-test
  (testing "Day 18 1 - explode-tree 1"
    (is (= [{:lvalue 9}
            [[[[0 9] 2] 3] 4]]
           (-> [[[[[9 8] 1] 2] 3] 4]
               (#'advent-of-code-2021.day18/tree)
               (#'advent-of-code-2021.day18/explode-tree)
               (update ,,, 1 u/tree->vec))))))

(deftest explode-tree-2-test
  (testing "Day 18 1 - explode-tree 2"
    (is (= [{:rvalue 2}
            [7 [6 [5 [7 0]]]]]
           (-> [7 [6 [5 [4 [3 2]]]]]
               (#'advent-of-code-2021.day18/tree)
               (#'advent-of-code-2021.day18/explode-tree)
               (update ,,, 1 u/tree->vec))))))

(deftest explode-tree-3-test
  (testing "Day 18 1 - explode-tree 3"
    (is (= [{}
            [[6 [5 [7 0]]] 3]]
           (-> [[6 [5 [4 [3 2]]]] 1]
               (#'advent-of-code-2021.day18/tree)
               (#'advent-of-code-2021.day18/explode-tree)
               (update ,,, 1 u/tree->vec))))))

(deftest explode-tree-4-test
  (testing "Day 18 1 - explode-tree 4"
    (is (= [{}
            [[3 [2 [8 0]]] [9 [5 [4 [3 2]]]]]]
           (-> [[3 [2 [1 [7 3]]]]
                [6 [5 [4 [3 2]]]]]
               (#'advent-of-code-2021.day18/tree)
               (#'advent-of-code-2021.day18/explode-tree)
               (update ,,, 1 u/tree->vec))))))

(deftest explode-tree-5-test
  (testing "Day 18 1 - explode-tree 5"
    (is (= [{:rvalue 2}
            [[3 [2 [8 0]]] [9 [5 [7 0]]]]]
           (-> [[3 [2 [1 [7 3]]]]
                [6 [5 [4 [3 2]]]]]
               (#'advent-of-code-2021.day18/tree)
               (#'advent-of-code-2021.day18/explode-tree)
               (second)
               (#'advent-of-code-2021.day18/explode-tree)
               (update ,,, 1 u/tree->vec))))))

(deftest split-tree-10-test
  (testing "Day 18 1 - split-tree 10"
    (is (= [true [5 5]]
           (-> 10
               (#'advent-of-code-2021.day18/tree)
               (#'advent-of-code-2021.day18/split-tree)
               (update ,,, 1 u/tree->vec))))))

(deftest split-tree-11-test
  (testing "Day 18 1 - split-tree 11"
    (is (= [true (u/internal-node (u/leaf-node 5)
                                  (u/leaf-node 6))]
           (#'advent-of-code-2021.day18/split-tree
            (#'advent-of-code-2021.day18/tree 11))))))

(deftest split-tree-12-test
  (testing "Day 18 1 - split-tree 12"
    (is (= [true (u/internal-node (u/leaf-node 6)
                                  (u/leaf-node 6))]
           (#'advent-of-code-2021.day18/split-tree
            (#'advent-of-code-2021.day18/tree 12))))))

(deftest reduce-tree-1-test
  (testing "Day 18 1 - reduce-tree 1"
    (is (= (u/internal-node (u/internal-node
                             (u/internal-node
                              (u/internal-node (u/leaf-node 0)
                                               (u/leaf-node 7))
                              (u/leaf-node 4))
                             (u/internal-node
                              (u/internal-node (u/leaf-node 7)
                                               (u/leaf-node 8))
                              (u/internal-node (u/leaf-node 6)
                                               (u/leaf-node 0))))
                            (u/internal-node (u/leaf-node 8)
                                             (u/leaf-node 1)))
           (-> [[[[[4 3] 4] 4] [7 [[8 4] 9]]]
                [1 1]]
               (#'advent-of-code-2021.day18/tree)
               (#'advent-of-code-2021.day18/reduce-tree))))))

(deftest reduce-trees-1-test
  (testing "reduce-trees - example 1"
    (is (= [[[[1 1] [2 2]] [3 3]] [4 4]]
           (-> [[1 1]
                [2 2]
                [3 3]
                [4 4]]
               (#'advent-of-code-2021.day18/reduce-trees)
               u/tree->vec)))))

(deftest reduce-trees-2-test
  (testing "reduce-trees - example 2"
    (is (= [[[[3 0] [5 3]] [4 4]] [5 5]]
           (-> [[1 1]
                [2 2]
                [3 3]
                [4 4]
                [5 5]]
               (#'advent-of-code-2021.day18/reduce-trees)
               u/tree->vec)))))

(deftest reduce-trees-3-test
  (testing "reduce-trees - example 3"
    (is (= [[[[5 0] [7 4]] [5 5]] [6 6]]
           (-> [[1 1]
                [2 2]
                [3 3]
                [4 4]
                [5 5]
                [6 6]]
               (#'advent-of-code-2021.day18/reduce-trees)
               u/tree->vec)))))

(deftest reduce-trees-4-test
  (testing "reduce-trees - example 4"
    (is (= [[[[8,7],[7,7]],[[8,6],[7,7]]]
            [[[0,7],[6,6]],[8,7]]]

           (-> [[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
                [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
                [[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
                [[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
                [7,[5,[[3,8],[1,4]]]]
                [[2,[2,2]],[8,[8,1]]]
                [2,9]
                [1,[[[9,3],9],[[9,0],[0,7]]]]
                [[[5,[7,4]],7],1]
                [[[[4,2],2],6],[8,7]]]
               (#'advent-of-code-2021.day18/reduce-trees)
               u/tree->vec)))))

(deftest day18-1-1-test
  (testing "Day 18 1 - example 1"
    (is (= 143
           (-> [[1,2],[[3,4],5]]
               day18-1)))))

(deftest day18-1-2-test
  (testing "Day 18 1 - example 2"
    (is (= 1384
           (-> [[[[0,7],4],[[7,8],[6,0]]],[8,1]]
               day18-1)))))

(deftest day18-1-3-test
  (testing "Day 18 1 - example 3"
    (is (= 445
           (-> [[[[1,1],[2,2]],[3,3]],[4,4]]
               day18-1)))))

(deftest day18-1-4-test
  (testing "Day 18 1 - example 4"
    (is (= 791
           (-> [[[[3,0],[5,3]],[4,4]],[5,5]]
               day18-1)))))

(deftest day18-1-5-test
  (testing "Day 18 1 - example 5"
    (is (= 1137
           (-> [[[[5,0],[7,4]],[5,5]],[6,6]]
               day18-1)))))

(deftest day18-1-6-test
  (testing "Day 18 1 - example 6"
    (is (= 3488
           (-> [[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]
               day18-1)))))

(deftest day18-1-7-test
  (testing "Day 18 1 - example 7"
    (is (= 4140
           (day18-1 [[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
                     [[[5,[2,8]],4],[5,[[9,9],0]]]
                     [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
                     [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
                     [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
                     [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
                     [[[[5,4],[7,7]],8],[[8,3],8]]
                     [[9,3],[[9,9],[6,[4,9]]]]
                     [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
                     [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]])))))

(deftest day18-1-test
  (testing "Day 18 1"
    (is (= 3869 (day18-1)))))

(deftest day18-2-1-test
  (testing "Day 18 2 - example 1"
    (is (= 3993
           (day18-2 [[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
                     [[[5,[2,8]],4],[5,[[9,9],0]]]
                     [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
                     [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
                     [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
                     [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
                     [[[[5,4],[7,7]],8],[[8,3],8]]
                     [[9,3],[[9,9],[6,[4,9]]]]
                     [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
                     [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]])))))

(deftest day18-2-test
  (testing "Day 18 2"
    (is (= 4671 (day18-2)))))