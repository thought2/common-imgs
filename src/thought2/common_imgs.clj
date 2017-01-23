(ns thought2.common-imgs
  (:require
   [clojure.spec :as s]
   [thought2.common-imgs.core :as core]
   [thought2.common-imgs.spec-utils :refer [wrap-conform]]))

(s/def ::pos-nr (s/and number? pos?))

(s/def ::result-count (s/and int? #(< 0 % (inc 50))))

(s/def ::url-template (s/tuple string? string?))

(s/def ::size (s/tuple ::pos-nr ::pos-nr))

(s/def ::img-spec (s/keys :req-un [::url-template ::size]))

(s/def ::random-img-specs (s/cat :n ::result-count))

(defn- width-ok? [[img-spec width]]
  (> (-> img-spec :size first)
     width))

(def
  ^{:doc   "Receive 1-50 img-specs by one HTTP request."
    :added "0.1.0"
    :arglists '([n])}
  random-img-specs
  (wrap-conform core/random-img-specs*
                (s/cat :n ::result-count)))

(def
  ^{:doc   "Transform img-spec into a valid URL when provided a width."
    :added "0.1.0"
    :arglists '([img-spec width])}
  img-spec->url 
  (wrap-conform core/img-spec->url*
                (s/and width-ok?
                       (s/cat :img-spec ::img-spec
                              :width ::pos-nr))))

(def
  ^{:doc "Returns an infinite sequence of img-specs of a minimum size."
    :added "0.2.0"
    :arglists '([min-size])}
  img-specs
  (wrap-conform core/img-specs
                (s/cat :min-size ::size)))

