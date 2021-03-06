(defproject scramblies.main "0.1.0-SNAPSHOT"

  :description "FIXME: write description"
  :url "http://example.com/FIXME"

  :dependencies [[ch.qos.logback/logback-classic "1.2.3"]
                 [org.clojure/data.json "2.4.0"]
                 [clojure.java-time "0.3.2"]
                 [com.cognitect/transit-clj "1.0.324"]
                 [com.cognitect/transit-cljs "0.8.269"]
                 [com.h2database/h2 "1.4.200"]
                 [conman "0.9.1"]
                 [cprop "0.1.17"]
                 [expound "0.8.9"]
                 [funcool/struct "1.4.0"]
                 [json-html "0.4.7"]
                 [luminus-http-kit "0.1.9"]
                 [luminus-migrations "0.7.1"]
                 [luminus-transit "0.1.2"]
                 [luminus/ring-ttl-session "0.3.3"]
                 [markdown-clj "1.10.5"]
                 [metosin/muuntaja "0.6.8"]
                 [metosin/reitit "0.5.13"]
                 [metosin/ring-http-response "0.9.2"]
                 [mount "0.1.16"]
                 [nrepl "0.8.3"]
                 [org.clojure/clojure "1.10.3"]
                 [org.clojure/clojurescript "1.10.866" :scope "provided"]
                 [org.clojure/core.async "1.3.618"]
                 [org.clojure/tools.cli "1.0.206"]
                 [org.clojure/tools.logging "1.1.0"]
                 [org.webjars.npm/bulma "0.9.2"]
                 [org.webjars.npm/material-icons "0.7.0"]
                 [org.webjars/webjars-locator "0.41"]
                 [ring-webjars "0.2.0"]
                 [ring/ring-core "1.9.3"]
                 [ring/ring-defaults "0.3.2"]
                 [selmer "1.12.40"]

                 [reagent "1.1.0"]
                 [re-frame/re-frame "1.2.0"]
                 [cljs-http/cljs-http "0.1.46"]
                 [binaryage/devtools "1.0.3"]
                 [thheller/shadow-cljs "2.14.3" :scope "provided"]]

  :min-lein-version "2.0.0"
  
  :source-paths ["src/clj" "src/cljs" "src/cljc"]
  :test-paths ["test/clj"]
  :resource-paths ["resources" "target/cljsbuild"]
  :target-path "target/%s/"
  :main ^:skip-aot scramblies.main.core

  :plugins [[lein-shell "0.5.0"]] 
  :clean-targets ^{:protect false}
  [:target-path "target/cljsbuild"]
  

  :profiles
  {:uberjar {:omit-source true
             
             :prep-tasks     ["compile" ["shell" "shadow-cljs" "release" "app"]]
             :aot            :all
             :uberjar-name   "scramblies.main.jar"
             :source-paths   ["env/prod/clj"  "env/prod/cljs" ]
             :resource-paths ["env/prod/resources"]}

   :dev  [:project/dev :profiles/dev]
   :test [:project/dev :project/test :profiles/test]

   :project/dev {:jvm-opts     ["-Dconf=dev-config.edn" ]
                 :dependencies [[binaryage/devtools "1.0.3"]
                                [cider/piggieback "0.5.2"]
                                [pjstadig/humane-test-output "0.11.0"]
                                [prone "2021-04-23"]
                                [ring/ring-devel "1.9.3"]
                                [refactor-nrepl "2.5.1"]
                                [cider/cider-nrepl "0.26.0"]
                                [ring/ring-mock "0.4.0"]]
                 :plugins      [[com.jakemccrary/lein-test-refresh "0.24.1"]
                                [jonase/eastwood "0.3.5"]]

                 :source-paths   ["env/dev/clj"  "env/dev/cljs" "test/cljs" ]
                 :resource-paths ["env/dev/resources"]
                 :repl-options   {:init-ns user
                                  :timeout 120000}
                 :injections     [(require 'pjstadig.humane-test-output)
                                  (pjstadig.humane-test-output/activate!)]}
   :project/test {:jvm-opts       ["-Dconf=test-config.edn" ]
                  :resource-paths ["env/test/resources"] 
                  
                  
                  }
   :profiles/dev  {}
   :profiles/test {}})
