(def project 'thought2/common-imgs)
(def version "0.2.0-SNAPSHOT")
(def description "Clojure library to retrieve random images from the Wikimedia Commons API.")

(set-env! :resource-paths #{"resources" "src"}
          :source-paths   #{"test"}
          :dependencies   '[[boot-codox             "0.10.2" :scope "test"]
                            [http-kit               "2.1.18"]
                            [net.mikera/core.matrix "0.54.0"]
                            [org.clojure/data.json  "0.2.6"]
                            [adzerk/boot-test       "RELEASE" :scope "test"]])

(require '[adzerk.boot-test :refer [test]]
         '[codox.boot :refer [codox]])

(task-options!
 pom {:project     project
      :version     version
      :description description
      :scm         {:url "https://github.com/thought2/common-imgs"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}}
 codox {:name "common-imgs"
        :version version
        :description description
        :source-paths #{"src"}
        :filter-namespaces ['thought2.common-imgs]})

(deftask build-install
  "Build and install the project locally."
  []
  (comp (pom) (jar) (install)))


