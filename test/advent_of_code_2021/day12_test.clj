(ns advent-of-code-2021.day12-test
  (:require
   [clojure.test :refer :all]
   [advent-of-code-2021.day12 :refer :all]))

(defonce ^:private example-map-1 (str "start-A\n"
                                      "start-b\n"
                                      "A-c\n"
                                      "A-b\n"
                                      "b-d\n"
                                      "A-end\n"
                                      "b-end\n"))

(defonce ^:private example-map-2 (str "dc-end\n"
                                      "HN-start\n"
                                      "start-kj\n"
                                      "dc-start\n"
                                      "dc-HN\n"
                                      "LN-dc\n"
                                      "HN-end\n"
                                      "kj-sa\n"
                                      "kj-HN\n"
                                      "kj-dc\n"))

(defonce ^:private example-map-3 (str "fs-end\n"
                                      "he-DX\n"
                                      "fs-he\n"
                                      "start-DX\n"
                                      "pj-DX\n"
                                      "end-zg\n"
                                      "zg-sl\n"
                                      "zg-pj\n"
                                      "pj-he\n"
                                      "RW-he\n"
                                      "fs-DX\n"
                                      "pj-RW\n"
                                      "zg-RW\n"
                                      "start-pj\n"
                                      "he-WI\n"
                                      "zg-he\n"
                                      "pj-fs\n"
                                      "start-RW\n"))

(deftest parse-map-test
  (testing "Day 12 Part 1 - parse-map"
    (is (= {"A" #{"b" "c" "end" "start"}
            "b" #{"A" "d" "end" "start"}
            "c" #{"A"}
            "d" #{"b"}
            "end" #{"A" "b"}
            "start" #{"A" "b"}}
           (#'advent-of-code-2021.day12/parse-map example-map-1)))))

(deftest distinct-path-1-test
  (testing "Day 12 Part 1 - distinct-paths 1"
    (is (= #{["start" "A" "b" "A" "c" "A" "end"]
             ["start" "A" "b" "A" "end"]
             ["start" "A" "b" "end"]
             ["start" "A" "c" "A" "b" "A" "end"]
             ["start" "A" "c" "A" "b" "end"]
             ["start" "A" "c" "A" "end"]
             ["start" "A" "end"]
             ["start" "b" "A" "c" "A" "end"]
             ["start" "b" "A" "end"]
             ["start" "b" "end"]}
           (set
            (mapv :path
                  (#'advent-of-code-2021.day12/distinct-paths
                   (#'advent-of-code-2021.day12/parse-map example-map-1)
                   #'advent-of-code-2021.day12/select-caves-1
                   #'advent-of-code-2021.day12/visit-small-cave-1)))))))

(deftest distinct-path-2-test
  (testing "Day 12 Part 1 - distinct-paths 2"
    (is (= #{["start" "HN" "dc" "HN" "end"]
             ["start" "HN" "dc" "HN" "kj" "HN" "end"]
             ["start" "HN" "dc" "end"]
             ["start" "HN" "dc" "kj" "HN" "end"]
             ["start" "HN" "end"]
             ["start" "HN" "kj" "HN" "dc" "HN" "end"]
             ["start" "HN" "kj" "HN" "dc" "end"]
             ["start" "HN" "kj" "HN" "end"]
             ["start" "HN" "kj" "dc" "HN" "end"]
             ["start" "HN" "kj" "dc" "end"]
             ["start" "dc" "HN" "end"]
             ["start" "dc" "HN" "kj" "HN" "end"]
             ["start" "dc" "end"]
             ["start" "dc" "kj" "HN" "end"]
             ["start" "kj" "HN" "dc" "HN" "end"]
             ["start" "kj" "HN" "dc" "end"]
             ["start" "kj" "HN" "end"]
             ["start" "kj" "dc" "HN" "end"]
             ["start" "kj" "dc" "end"]}
           (set
            (mapv :path
                  (#'advent-of-code-2021.day12/distinct-paths
                   (#'advent-of-code-2021.day12/parse-map example-map-2)
                   #'advent-of-code-2021.day12/select-caves-1
                   #'advent-of-code-2021.day12/visit-small-cave-1)))))))

(deftest distinct-path-3-test
  (testing "Day 12 Part 1 - distinct-paths 3"
    (is (= 226
           (count
            (#'advent-of-code-2021.day12/distinct-paths
             (#'advent-of-code-2021.day12/parse-map example-map-3)
             #'advent-of-code-2021.day12/select-caves-1
             #'advent-of-code-2021.day12/visit-small-cave-1))))))

(deftest day12-1-example-test
  (testing "Day 12 Part 1 - example"
    (is (= 10
           (day12-1
            (#'advent-of-code-2021.day12/parse-map example-map-1))))))

(deftest day12-1-test
  (testing "Day 12 Part 1"
    (is (= 3369 (day12-1)))))

(deftest distinct-path-1-2-test
  (testing "Day 12 Part 2 - distinct-paths 1"
    (is (= #{["start" "A" "b" "A" "b" "A" "c" "A" "end"]
             ["start" "A" "b" "A" "b" "A" "end"]
             ["start" "A" "b" "A" "b" "end"]
             ["start" "A" "b" "A" "c" "A" "b" "A" "end"]
             ["start" "A" "b" "A" "c" "A" "b" "end"]
             ["start" "A" "b" "A" "c" "A" "c" "A" "end"]
             ["start" "A" "b" "A" "c" "A" "end"]
             ["start" "A" "b" "A" "end"]
             ["start" "A" "b" "d" "b" "A" "c" "A" "end"]
             ["start" "A" "b" "d" "b" "A" "end"]
             ["start" "A" "b" "d" "b" "end"]
             ["start" "A" "b" "end"]
             ["start" "A" "c" "A" "b" "A" "b" "A" "end"]
             ["start" "A" "c" "A" "b" "A" "b" "end"]
             ["start" "A" "c" "A" "b" "A" "c" "A" "end"]
             ["start" "A" "c" "A" "b" "A" "end"]
             ["start" "A" "c" "A" "b" "d" "b" "A" "end"]
             ["start" "A" "c" "A" "b" "d" "b" "end"]
             ["start" "A" "c" "A" "b" "end"]
             ["start" "A" "c" "A" "c" "A" "b" "A" "end"]
             ["start" "A" "c" "A" "c" "A" "b" "end"]
             ["start" "A" "c" "A" "c" "A" "end"]
             ["start" "A" "c" "A" "end"]
             ["start" "A" "end"]
             ["start" "b" "A" "b" "A" "c" "A" "end"]
             ["start" "b" "A" "b" "A" "end"]
             ["start" "b" "A" "b" "end"]
             ["start" "b" "A" "c" "A" "b" "A" "end"]
             ["start" "b" "A" "c" "A" "b" "end"]
             ["start" "b" "A" "c" "A" "c" "A" "end"]
             ["start" "b" "A" "c" "A" "end"]
             ["start" "b" "A" "end"]
             ["start" "b" "d" "b" "A" "c" "A" "end"]
             ["start" "b" "d" "b" "A" "end"]
             ["start" "b" "d" "b" "end"]
             ["start" "b" "end"]}
           (set
            (mapv :path
                  (#'advent-of-code-2021.day12/distinct-paths
                   (#'advent-of-code-2021.day12/parse-map example-map-1)
                   #'advent-of-code-2021.day12/select-caves-2
                   #'advent-of-code-2021.day12/visit-small-cave-2)))))))

(deftest distinct-path-2-2-test
  (testing "Day 12 Part 2 - distinct-paths 2"
    (is (= 103
           (count
            (#'advent-of-code-2021.day12/distinct-paths
             (#'advent-of-code-2021.day12/parse-map example-map-2)
             #'advent-of-code-2021.day12/select-caves-2
             #'advent-of-code-2021.day12/visit-small-cave-2))))))

(deftest distinct-path-3-2-test
  (testing "Day 12 Part 2 - distinct-paths 3"
    (is (= 3509
           (count
            (#'advent-of-code-2021.day12/distinct-paths
             (#'advent-of-code-2021.day12/parse-map example-map-3)
             #'advent-of-code-2021.day12/select-caves-2
             #'advent-of-code-2021.day12/visit-small-cave-2))))))

(deftest day12-2-test
  (testing "Day 12 Part 2"
    (is (= 85883 (day12-2)))))