(ns thought2.common-imgs-test
  (:require [clojure.test :refer :all]
            [thought2.common-imgs.utils :as utils]))


(deftest lt-both?-test
  (is (utils/lt-both? [20 20] [30 30]))
  (is (not (utils/lt-both? [40 20] [30 30])))  
  (is (not (utils/lt-both? [40 40] [30 30]))))
