(ns thought2.common-imgs.utils
  (:require [clojure.core.matrix :refer [lt]]))

(defn apply-concat [xs]
  (lazy-seq
   (when-let [s (seq xs)]
     (concat (first s) (apply-concat (rest s))))))

(defn lt-both? [v1 v2]
  (= [1 1] (lt v1 v2)))
