(ns thought2.common-imgs-test
  (:require [clojure.test :refer :all]
            [thought2.common-imgs :as api]
            [org.httpkit.client :as http]
            [thought2.common-imgs.utils :as utils]))

(defn get-img [img-spec]
  (let [width 1
        url (api/img-spec->url img-spec width)
        _ (println "fetching" url)
        res (-> url http/get deref)
        _ (println "done.")]
    res))

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

(defn sizes-ok? [img-specs min-size]
  (every? #(utils/lt-both? min-size (:size %)) img-specs))

(defn responses-ok? [img-specs fact]
  (let [n (count img-specs)]
    (->> (map get-img img-specs)
         (filter ok?)
         count
         (< (* n fact)))))

(defn img-specs-test [test-size n] 
  (let [fact 0.8
        img-specs (->> (api/img-specs test-size)
                       (take n))]
    (is (sizes-ok? img-specs test-size))
    (is (responses-ok? img-specs fact))))


#_(deftest long-test
    (is (enough-specs? 10))
    (is (realize-img-specs? 100))
    (is (img-specs-test [1000 1000] 100)))

(deftest quick-test
  (is (enough-specs? 1))
  (is (realize-img-specs? 5))
  (is (img-specs-test [100 100] 5)))
