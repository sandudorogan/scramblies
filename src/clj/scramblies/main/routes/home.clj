(ns scramblies.main.routes.home
  (:require
   [scramblies.main.layout :as layout]
   [scramblies.main.middleware :as middleware]
   [ring.util.response]))

(defn home-page [request]
  (layout/render request "home.html"))

(defn home-routes []
  [""
   {:middleware [middleware/wrap-formats]}
   ["/"         {:get home-page}]])
