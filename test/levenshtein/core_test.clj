(ns levenshtein.core-test
  (:require [clojure.test :refer :all]
            [levenshtein.core :refer :all]))

(deftest without-last-char-test
  (is (= (without-last-char "") ""))
  (is (= (without-last-char "a") ""))
  (is (= (without-last-char "ab") "a")))

(deftest last-equal?-test
  (is (true? (last-equal? "a" "a")))
  (is (true? (last-equal? "ab" "ab")))
  (is (false? (last-equal? "ba" "ab"))))

(deftest distance-between-blank-and-something
  (testing "blank and something"
    (is (= (levenshtein-distance "" "") 0))
    (is (= (levenshtein-distance "x" "") 1))
    (is (= (levenshtein-distance "" "x") 1)))
  (testing "the rest of the cases"
    (is (= (levenshtein-distance "abc" "ab") 1))
    (is (= (levenshtein-distance "ab" "abc") 1))
    (is (= (levenshtein-distance "ab" "bc") 2))
    (is (= (levenshtein-distance "water" "w1t1r") 2))))

(deftest distance-of-the-pair
  (is (= (levenshtein-distance ["" ""]) 0)))
