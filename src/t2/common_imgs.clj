(ns t2.common-imgs
  (:require
   [clojure.string :as s]
   [org.httpkit.client :as http]
   [net.cgrand.enlive-html :refer [select html-snippet nth-child text-node]]))

(defn- go-down [& xs]
  (->> (partition 2 xs)
       (map (fn [[t n]] [t (nth-child n)]))
       (interpose :>)))

(def select-first (comp first select))

(defn- mk-url-template [thumb-url]
  (s/split thumb-url #"(?<=/)(\d*)(?=px)"))

(defn- resolve-url-template [[templ1 templ2] width]
  (str templ1 width templ2))

(defn- save-2int [s]
  (try (Integer. s)
       (catch Exception e)))

(defn- scrape-page [page-node]
  (let [sel (concat [:.wikitable :>]
                    (go-down :tr 2 :td 3 :a 1 :img 1))
        tag (select-first page-node sel) 
        {thumb-url :src
         width :data-file-width
         height :data-file-height} (:attrs tag)] 
    {:size (mapv (comp dec save-2int) [width height])
     :thumb-url thumb-url
     :url-template (mk-url-template thumb-url)}))

(defn- get-page []
  (let [url "https://commons.wikimedia.org/wiki/Special:Random/File"
        opts {:url url :method :get}]
    (-> (http/request opts))))


;; public api

(defn random-spec []
  (-> @(get-page)
      :body
      html-snippet
      scrape-page))

(defn spec->url [{:keys [size url-template]} width] 
  (if (<= width (size 0))
    (resolve-url-template url-template width)
    (throw (Exception. "Width is too big for this spec."))))
