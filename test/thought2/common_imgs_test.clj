(ns thought2.common-imgs-test
  (:require [clojure.test :refer :all]
            [thought2.common-imgs :as api]
            [org.httpkit.client :as http]))

(defn get-img [img-spec]
  (let [width 1]
    (-> img-spec
        (api/img-spec->url width)
        http/get deref)))

(defn ok? [resp]
  (= 200 (resp :status)))

(defn gather-img-specs [n]
  (loop [xs []]
    (if (> (count xs) n)
      (take n xs)
      (recur (concat xs (api/random-img-specs 50))))))

(defn enough-specs? [n-reqs]
  (let [ok? #(-> % count (> 40))
        get-specs #(api/random-img-specs 50)]
    (->> (repeatedly n-reqs get-specs) 
         (every? ok?))))

(defn realize-img-specs? [n-reqs]
  (->> (gather-img-specs n-reqs)
       (map get-img)
       (filter ok?)
       count
       (< (* n-reqs 0.8))))

(deftest enough-specs-test
  (testing "Testing if random-img-specs delivers enough img-specs"
    (is (enough-specs? 10))))

(deftest realize-img-specs-test
  (testing "Testing if all img-specs can be used for http-requests"
    (is (realize-img-specs? 200))))
