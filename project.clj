(defproject virtual-me "0.1.0-SNAPSHOT"
  :description "AI personal assistant that can do boring tasks for you."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.946"]
                 [im.chit/hara.io.scheduler "2.5.10"]
                 [clojure-opennlp "0.4.0"]
                 [org.clojure/tools.logging "0.4.0"]
                 [ch.qos.logback/logback-classic "1.1.3"]
                 [compojure "1.6.0"]
                 [metosin/compojure-api "1.1.11"]
                 [ring/ring-defaults "0.3.1"]
                 [reagent "0.7.0"]
                 [hiccup "1.0.5"]
                 [clj-http "3.7.0"]
                 [cljs-http "0.1.44"]
                 [cheshire "5.8.0"]
                 [garden "1.3.3"]
                 [figwheel "0.5.14"]]
  :main ^:skip-aot virtual-me.core
  :source-paths ["src/clj"]
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
  :profiles {:uberjar {:aot :all}
             :dev {:plugins [[lein-binplus "0.6.2"]
                             [lein-midje "3.2.1"]
                             [lein-cljsbuild "1.1.7"]
                             [lein-garden "0.3.0"]
                             [lein-figwheel "0.5.14"]
                             [lein-ring "0.12.3"]]
                   :dependencies [[midje "1.9.1"]
                                  [ring/ring-mock "0.3.2"]
                                  [javax.servlet/servlet-api "2.5"]]}}
  :aliases {"test" ^:pass-through-help ["midje"]}
  :ring {:handler virtual-me.web.routes/app}
  :cljsbuild {:builds {
                       :prod
                       {:compiler {:optimizations :advanced
                                   :pretty-print false
                                   :output-to "resources/public/js/main.js"}
                        :source-paths ["src/cljs"]}
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
                        :source-paths ["src/cljs" "src/cljs-dev"]}}}
  :garden {:builds [{:source-paths ["src/garden"]
                     :stylesheet virtual-me.css/screen
                     :compiler {:output-to "resources/public/css/screen.css"
                                :pretty-print? false}}]}
  :figwheel {:css-dirs ["resources/public/css"]
             :server-logfile "logs/figwheel_server.log"
             :ring-handler virtual-me.web.routes/app
             :server-port 3000})
