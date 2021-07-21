(ns ^:dev/once scramblies.main.app
  (:require
   [clojure.walk :as walk]
   [cljs.spec.alpha :as s]
   [scramblies.main.core :as core]
   [re-frame.db :as rf-db]
   [expound.alpha :as expound]
   [devtools.core :as devtools]))

(goog-define ^boolean CYPRESS? false) ;;

;; Do we set monitoring points for Cypress?
(def cypress? (or CYPRESS?
                  #_(:cypress? config))) ;; TODO: figure out how to pass config data

(when cypress?
  (defn ^:export app-db-as-edn
    "Provides the app-db's value as EDN, in a string."
    []
    (->> @rf-db/app-db
         (walk/prewalk (fn [x]
                         (if (or (coll? x)
                                 (keyword? x)
                                 (symbol? x)
                                 (string? x)
                                 (number? x)
                                 (boolean? x)
                                 (nil? x)
                                 (inst? x))
                           x
                           :not-serializable)))
         str)))

(extend-protocol IPrintWithWriter
  js/Symbol
  (-pr-writer [sym writer _]
    (-write writer (str "\"" (.toString sym) "\""))))

(set! s/*explain-out* expound/printer)

(enable-console-print!)

(devtools/install!)

(core/init!)
