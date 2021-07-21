(ns scramblies.main.routes.scramble
  (:require
   [scramblies.main.utils.scramble :as sc]
   [scramblies.main.middleware :as middleware]
   [clojure.data.json :as json]
   [ring.util.http-response :as response]))

(defn scramble-routes []
  [""
   {:middleware [middleware/wrap-formats]}
   ["/scramble" {:post (fn [request]
                         (or (some-> request
                                     :params
                                     (select-keys [:first-string :second-string])
                                     vals
                                     (->> (apply sc/scramble?) (assoc {} :scramble))
                                     json/write-str
                                     response/ok
                                     (response/header "Content-Type" "text/json; charset=utf-8"))
                             (response/bad-request)))}]])
