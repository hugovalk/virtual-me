(defproject virtual-me "0.1.0-SNAPSHOT"
  :description "AI personal assistant that can do boring tasks for you."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [im.chit/hara.io.scheduler "2.5.10"]
                 [org.clojure/tools.logging "0.4.0"]
                 [ch.qos.logback/logback-classic "1.1.3"]
                 [clj-http "3.7.0"]]
  :main ^:skip-aot virtual-me.core
  :target-path "target/%s"
  :bin {:name "test-app"
        :bin-path "."
        :bootclasspath false
        :jvm-opts ["-server" "-Dfile.encoding=utf-8" "$JVM_OPTS"]}
  :profiles {:uberjar {:aot :all}
             :dev {:plugins [[lein-binplus "0.6.2"]]}})
