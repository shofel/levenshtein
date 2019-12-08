(ns levenshtein.core
  (:gen-class)
  (:require
    [clojure.math.combinatorics :as combo]
    [clojure.string :as str]))

(defn without-last-char
  "Returns the given word without the last char"
  [x]
  (cond
    (str/blank? x) x
    :else (subs x 0 (dec (count x)))))

(defn last-equal?
  "Are the last chars of two words are equal?"
  [a b]
  (= (take-last 1 a) (take-last 1 b)))

(defn f
  "Levenshtein distance between two words"
  ([[a b]] (f a b))
  ([a b]
   (cond
     (str/blank? a) (count b)
     (str/blank? b) (count a)
     :else (min (inc (f (without-last-char a) b))
                (inc (f a (without-last-char b)))
                (+ (f (without-last-char a) (without-last-char b))
                   (if (last-equal? a b) 0 1))))))

(def levenshtein-distance f)


(comment

  (def -words-url
    "https://raw.githubusercontent.com/dolph/dictionary/master/unix-words")

  (def words
    (-> (slurp -words-url)
        (str/split #"\n")))

  (let [pairs (-> (take 10 words)
                  (combo/selections 2))]
    (->
      (map levenshtein-distance pairs)
      (doall)
      (time))))

