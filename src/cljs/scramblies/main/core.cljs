(ns scramblies.main.core
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as rf]
   [goog.dom :as gdom]
   [scramblies.main.events.db]
   [scramblies.main.events.scramble]
   [scramblies.main.events.network]
   [scramblies.main.subscribers.scramble]
   [scramblies.main.views.scramble :as view]))

(defn root []
  [:div.grid-center
   [view/scramble]])

(defn render []
  (rdom/render [root] (gdom/getElement "app")))

(defn ^:export init!
  "Executed only once, when the app starts."
  []
  (println "(init)")
  (rf/dispatch-sync [:initialize-db])
  (render))

(defn ^:dev/before-load stop
  "Shadow-CLJS hook, called before a hot-reload."
  []
  (println "(stop)")
  (rf/clear-subscription-cache!))

(defn ^:dev/after-load start
  "Shadow-CLJS hook, called after a hot-reload."
  []
  (println "(start)")
  (rdom/render [root] (gdom/getElement "app")))
