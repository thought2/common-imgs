(ns thought2.common-imgs.core
  (:require
   [clojure.spec :as spec]
   [thought2.common-imgs.utils :refer [apply-concat lt-both?]]
   [thought2.common-imgs.ring :refer [wrap-json-resp http-handler]]
   [clojure.string :as s]))

(def fetch-limit 50)

(defn req [n]
  {:method :get
   :url "https://commons.wikimedia.org/w/api.php"
   :query-params {"action" "query"
                  "format" "json"
                  "prop" "imageinfo"
                  "iiprop" "url|size|sha1"
                  "generator" "random"
                  "iiurlwidth" 100
                  "grnnamespace" 6
                  "grnlimit" n}})

(def http-req
  (-> http-handler
      wrap-json-resp))

(defn- mk-url-template [thumb-url]
  (s/split thumb-url #"(?<=[/-])(\d*)(?=px)"))

(defn- resolve-url-template [[templ1 templ2] width]
  (str templ1 width templ2))

(defn parse-img* [{:keys [width height thumburl] :as all}]
  (-> {:size [width height]
       :url-template (mk-url-template thumburl)}
      (merge
       (select-keys all [:sha1 :thumburl]))))

(defn parse-img [all] 
  (-> all :imageinfo first parse-img*))

(defn parse [resp]
  (->> resp :body :query :pages vals
       (map parse-img)
       (filter #(spec/valid? :thought2.common-imgs/img-spec %))))

(defn fetch [n]
  (-> n req http-req))

(defn infinite-img-specs []
  (-> #(-> (fetch fetch-limit) parse)
      repeatedly
      apply-concat))

;; api

(defn random-img-specs* [{:keys [n]}]
  {:added "0.1.0"}
  (-> (fetch n)
      parse))

(defn img-spec->url* [{:keys [img-spec width]}]
  {:added "0.1.0"}
  (resolve-url-template (img-spec :url-template) width))

(defn img-specs [{:keys [min-size]}]
  {:added "0.2.0"}
  (->> (infinite-img-specs)
       (filter #(lt-both? min-size (:size %)))))
