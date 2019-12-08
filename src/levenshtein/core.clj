(ns levenshtein.core
  (:gen-class)
  (:require
    [clojure.math.combinatorics :as combo]
    [clojure.string :as str]))

; // len_s and len_t are the number of characters in string s and t respectively
; int LevenshteinDistance(const char *s, int len_s, const char *t, int len_t)
; { 
;   int cost;

;   /* test if last characters of the strings match */
;   if (s[len_s-1] == t[len_t-1])
;       cost = 0;
;   else
;       cost = 1;

;   /* return minimum of delete char from s, delete char from t, and delete char from both */
;   return minimum(LevenshteinDistance(s, len_s - 1, t, len_t    ) + 1,
;                  LevenshteinDistance(s, len_s    , t, len_t - 1) + 1,
;                  LevenshteinDistance(s, len_s - 1, t, len_t - 1) + cost);
; }

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
  [a b]
  (cond
    (str/blank? a) (count b)
    (str/blank? b) (count a)
    :else (min (inc (f (without-last-char a) b))
               (inc (f a (without-last-char b)))
               (+ (f (without-last-char a) (without-last-char b))
                  (if (last-equal? a b) 0 1)))))

(def levenshtein-distance f)


(comment

  (def -words-url
    "https://raw.githubusercontent.com/dolph/dictionary/master/unix-words")

  (def words
    (-> (slurp -words-url)
         (str/split #"\n")))

  (time (-> (take 20 words)
            #_(combo/selections 2))))

