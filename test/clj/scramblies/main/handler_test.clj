(ns scramblies.main.handler-test
  (:require
   [clojure.test :as t]
   [ring.mock.request :as req]
   [scramblies.main.handler :as scr]
   [scramblies.main.middleware.formats :as formats]
   [muuntaja.core :as m]
   [mount.core :as mount]))

(defn parse-json [body]
  (m/decode formats/instance "application/json" body))

(t/use-fixtures
  :once
  (fn [f]
    (mount/start #'scramblies.main.config/env
                 #'scramblies.main.handler/app-routes)
    (f)))

(t/deftest test-app
  (t/testing "main route"
    (let [response ((scr/app) (req/request :get "/"))]
      (t/is (= 200 (:status response)))))

  (t/testing "scramble route"
    (let [response ((scr/app) (req/request :post "/scramble" {:first-string "test" :second-string "testu"}))]
      (t/is (and (= 200 (:status response)) (-> response :body parse-json :scramble (= false)))))
    (let [response ((scr/app) (req/request :post "/scramble" {:first-string "test" :second-string "test"}))]
      (t/is (and (= 200 (:status response)) (-> response :body parse-json :scramble (= true)))))
    (let [response ((scr/app) (req/request :post "/scramble" {:first-string "testu" :second-string "test"}))]
      (t/is (and (= 200 (:status response)) (-> response :body parse-json :scramble (= true)))))
    (let [response ((scr/app) (req/request :post "/scramble" {:-string "test" :third-string "testu"}))]
      (t/is (= 400 (:status response)))))

  (t/testing "not-found route"
    (let [response ((scr/app) (req/request :get "/invalid"))]
      (t/is (= 404 (:status response))))))
