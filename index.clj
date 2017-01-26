(set-env! :dependencies '[[hiccup "1.0.5"]])

(use 'hiccup.core 'hiccup.page)
(require '[clojure.java.io :as io])

(def xs (->> (io/file "api")
             .listFiles
             (map str)
             (remove (partial re-find #"RELEASE$"))))

(defn -main [& args]
  (spit "index.html"
        (html5
         [:body
          (for [f xs]
            [:div [:a {:href f } f]])])))
