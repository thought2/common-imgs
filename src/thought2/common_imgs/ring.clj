(ns thought2.common-imgs.ring
  (:require  [clojure.data.json :refer [read-json]]
             [org.httpkit.client :as http]))

(defn pre-wrap [f]
  (fn [h & opts]
    (fn [x]
      (h (apply f x opts)))))

(defn post-wrap [f]
  (fn [h & opts]
    (fn [x]
      (apply f (h x) opts))))

(def http-handler (comp deref http/request))

(def wrap-default-req
  (pre-wrap
   (fn [req & [defaults]]
     #(merge-with merge defaults req))))

(def wrap-json-resp
  (post-wrap
   #(update % :body read-json)))


