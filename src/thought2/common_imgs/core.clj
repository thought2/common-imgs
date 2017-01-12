(ns thought2.common-imgs.core
  (:require
   [clojure.spec :as spec]
   [thought2.common-imgs.ring :refer [wrap-json-resp http-handler]]
   [clojure.string :as s]))

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

;; api

(defn random-img-specs* [{:keys [n]}]
  (-> (fetch n)
      parse))

(defn img-spec->url* [{:keys [img-spec width]}]
  (resolve-url-template (img-spec :url-template) width))

