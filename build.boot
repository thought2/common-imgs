(def project 'thought2/common-imgs)
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"resources" "src"}
          :source-paths   #{"test"}
          :dependencies   '[[http-kit              "2.1.18"]
                            [org.clojure/data.json "0.2.6"]
                            [adzerk/boot-test      "RELEASE" :scope "test"]])

(task-options!
 pom {:project     project
      :version     version
      :description "Clojure library to retrieve random images from the Wikimedia Commons API."
      :scm         {:url "https://github.com/thought2/common-imgs"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}})

(deftask build-install
  "Build and install the project locally."
  []
  (comp (pom) (jar) (install)))

(require '[adzerk.boot-test :refer [test]])

