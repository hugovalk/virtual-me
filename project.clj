;(require 'cemerick.pomegranate.aether)
;(cemerick.pomegranate.aether/register-wagon-factory!
;  "http" #(org.apache.maven.wagon.providers.http.HttpWagon.))
(defproject virtual-me "0.1.0-SNAPSHOT"
  :description "AI personal assistant that can do boring tasks for you."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.520"]
                 [im.chit/hara.io.scheduler "2.5.10"]
                 [clojure-opennlp "0.5.0"]
                 [org.deeplearning4j/deeplearning4j-ui_2.11 "1.0.0-beta3"
                  :exclusions [org.eclipse.collections/eclipse-collections-forkjoin
                               org.eclipse.collections/eclipse-collections-api
                               org.eclipse.collections/eclipse-collections
                               com.google.guava/guava]]
                 [org.deeplearning4j/deeplearning4j-core "1.0.0-beta3"]
                 [org.deeplearning4j/deeplearning4j-nlp "1.0.0-beta3"]
                 [org.nd4j/nd4j-native-platform "1.0.0-beta3"]
                 [org.clojure/tools.logging "0.5.0"]
                 [ch.qos.logback/logback-classic "1.2.3"]
                 [compojure "1.6.1"]
                 [org.flatland/ordered "1.5.7"]
                 [metosin/compojure-api "1.1.12"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-core "1.7.1"]
                 [ring/ring-devel "1.7.1"]
                 [http-kit "2.3.0"]
                 [com.taoensso/sente "1.13.1"]
                 [com.google.guava/guava "27.0-jre"]
                 [reagent "0.8.1"]
                 [hiccup "1.0.5"]
                 [clj-http "3.10.0"]
                 [cljs-http "0.1.46"]
                 [cheshire "5.9.0"]
                 [garden "1.3.9"]
                 [figwheel "0.5.19"]]
  :main virtual-me.httpkit
  :source-paths ["src/clj", "src/scripts", "src/garden"]
  :test-paths ["test/clj"]
  :resource-paths ["models/nl"
                   "models/en"
                   "resources"]
  :target-path "target/%s"
  :clean-targets ^{:protect false} ["target"
                                    "resources/public/js"
                                    "resources/public/css"]
  :bin {:name "test-app"
        :bin-path "."
        :resource-paths ["resources"]
        :bootclasspath false
        :jvm-opts ["-server" "-Dfile.encoding=utf-8" "$JVM_OPTS"]}
  :profiles {:download-nlp-models {:main virtual-me-scripts.nlp.models-downloader}
             :train-w2v-models {:main virtual-me-scripts.nlp.w2v-trainer}
             :uberjar {:prep-tasks ["compile"
                                    ["garden" "once"]
                                    ["cljsbuild" "once"]]
                       :aot :all
                       :main virtual-me.httpkit}
             :dev {:plugins [[lein-binplus "0.6.5"]
                             [lein-midje "3.2.1"]
                             [lein-cljsbuild "1.1.7"]
                             [lein-garden "0.3.0"]
                             [lein-figwheel "0.5.19"]
                             [lein-ring "0.12.5"]
                             [lein-ancient "0.6.15"]]
                   :dependencies [[midje "1.9.9"]
                                  [ring/ring-mock "0.4.0"]
                                  [javax.servlet/servlet-api "2.5"]
                                  [org.clojure/test.check "0.9.0"]
                                  [cider/cider-nrepl "0.21.1"]
                                  [figwheel-sidecar "0.5.19"]]}}
  :aliases {"test" ^:pass-through-help ["midje"]
            "download-nlp-models" ["with-profile" "download-nlp-models" "run"]
            "train-w2v-models" ["with-profile" "train-w2v-models" "run"]}
  :ring {:handler virtual-me.httpkit/app
         :port 8080
         :auto-reload? true
         :auto-refresh? true}
  :cljsbuild {:builds {
                       :prod
                       {:compiler {:optimizations :advanced
                                   :pretty-print false
                                   :output-to "resources/public/js/main.js"}
                        :source-paths ["src/cljs"]
                        :jar true}
                       :dev
                       {:compiler {:asset-path "js/out"
                                   :main "virtual-me.dev"
                                   :optimizations :none
                                   :pretty-print true
                                   :source-map true
                                   :source-map-timestamp true
                                   :cache-analysis true
                                   :output-to "resources/public/js/main.js"
                                   :output-dir "resources/public/js/out"}
                        :figwheel {:websocket-url "ws://[[client-hostname]]:[[server-port]]/figwheel-ws"
                                   :on-jsload "virtual-me.dev/fig-reload"}
                        :source-paths ["src/cljs" "src/cljs-dev"]}}}
  :garden {:builds [{:source-paths ["src/garden"]
                     :id "dev"
                     :stylesheet virtual-me.css/screen
                     :compiler {:output-to "resources/public/css/screen.css"
                                :pretty-print? false}}]}
  :figwheel {:css-dirs ["resources/public/css"]
             :server-logfile "logs/figwheel_server.log"
             :ring-handler virtual-me.httpkit/app-figwheel
             :server-port 8080
             :nrepl-port 7888})
