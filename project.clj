(defproject loginform "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [compojure "1.4.0"]
                 [secretary "1.2.3"]

                 ;; Frontend dependencies
                 [org.clojure/clojurescript "1.7.170"]
                 [hiccup "1.0.5"]
                 [prismatic/dommy "1.1.0"]
                 [ring/ring-core "1.4.0" :exclusions [javax.servlet/servlet-api]]
                 [ring/ring-servlet "1.4.0" :exclusions [javax.servlet/servlet-api]]
                 [ring/ring-defaults "0.1.5" :exclusions [javax.servlet/servlet-api]]
                 [cc.qbits/jet "0.7.1"]
                 [bouncer "1.0.0"]
                 [reagent "0.6.0-alpha"]
                 ]
  :source-paths ["src/clj"]
  :main loginform.core
  :plugins [[lein-cljsbuild "1.1.2"]]
  :cljsbuild {:builds
              [{:id "app"
                :source-paths ["src/cljs"]
                :compiler {:output-to "resources/public/js/app.js"
                           :output-dir "resources/public/js/out"
                           :source-map true
                           :optimizations :none
                           :asset-path "/static/js/out"
                           :main "loginform.core"
                           :pretty-print true}}]})
