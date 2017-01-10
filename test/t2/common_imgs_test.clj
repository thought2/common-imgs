(ns t2.common-imgs-test
  (:require [clojure.test :refer :all]
            [t2.common-imgs :refer :all]))

(deftest a
  (testing "FIXME, I fail."
    (is (let [{:keys [size thumb-url url-template]} (random-spec)]
          (and (string? thumb-url))))))


