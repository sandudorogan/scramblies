(ns scramblies.main.routes.home
  (:require
   [scramblies.main.layout :as layout]
   [scramblies.main.scramble :as sc]
   [scramblies.main.utils :as utils]
   [clojure.java.io :as io]
   [scramblies.main.middleware :as middleware]
   [ring.util.response]
   [ring.util.http-response :as response]))

(defn home-page [request]
  (layout/render request "home.html"))

(defn home-routes []
  [""
   {:middleware [middleware/wrap-formats]}
   ["/"         {:get home-page}]
   ["/docs"     {:get (fn [_]
                        (-> (response/ok (-> "docs/docs.md" io/resource slurp))
                            (response/header "Content-Type" "text/plain; charset=utf-8")))}]
   ["/scramble" {:post (fn [request]
                         (-> request
                             :body-params
                             (select-keys [:first-string :second-string])
                             vals
                             (->> (apply sc/scramble?))
                             str
                             response/ok
                             (response/header "Content-Type" "text/plain; charset=utf-8")))}]])
