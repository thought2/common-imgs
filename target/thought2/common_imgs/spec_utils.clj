(ns thought2.common-imgs.spec-utils
  (:require [clojure.spec :as s]))

(defn wrap-conform [f spec & [msg]]
  (fn [& input]
    (let [parsed (s/conform spec input)]
      (if (= parsed ::s/invalid)
        (throw (ex-info msg (s/explain-data spec input)))
        (f parsed)))))
