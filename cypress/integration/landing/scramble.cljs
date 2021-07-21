(ns landing.scramble
  (:require-macros [latte.core :refer [describe beforeEach it]])
  (:require
   [cljs.reader :as reader]
   [oops.core :refer [oget oset! ocall oapply ocall! oapply!
                      oget+ oset!+ ocall+ oapply+ ocall!+ oapply!+]] ))

(def cy js/cy)

(describe "Landing page"
          (beforeEach []
                      (.. cy (clearLocalStorage))
                      (.. cy (visit "/")))

          (it "renders the page as expected" []
              (.. cy
                  (get "#app > div > div > span")
                  (should "contain.text" "strings to scramble"))
              (.. cy (get "#app > div > div > div > div:nth-child(1) > input"))
              (.. cy (get "#app > div > div > div > div:nth-child(2) > input"))
              (.. cy
                  (get "#app > div > div > div > div:nth-child(3) > button > span")
                  (should "have.text" "Submit")))

          (it "processes user input" []
              (.. cy
                  (get "#app > div > div > div > div:nth-child(1) > input")
                  (type "test string"))
              (.. cy
                  (window)
                  (its "scramblies.main.app.app_db_as_edn")
                  (should (fn [app-db-as-edn]
                            (let [db (reader/read-string (app-db-as-edn))]
                              (assert (= (get-in db [:scramble :first-string]) "test string"))))))
              (.. cy
                  (get "#app > div > div > div > div:nth-child(2) > input")
                  (type "another test string"))
              (.. cy
                  (window)
                  (its "scramblies.main.app.app_db_as_edn")
                  (should (fn [app-db-as-edn]
                            (let [db (reader/read-string (app-db-as-edn))]
                              (assert (= (get-in db [:scramble :second-string]) "another test string")))))))

          (it "calls backend and clears last result" []
              (.. cy
                  (intercept "POST" "/scramble")
                  (as "scrambleCall"))
              (.. cy
                  (get "#app > div > div > div > div:nth-child(1) > input")
                  (type "test string"))
              (.. cy
                  (get "#app > div > div > div > div:nth-child(2) > input")
                  (type "another test string"))
              (.. cy
                  (get "#app > div > div > div > div:nth-child(3) > button")
                  (click))
              (.. cy
                  (wait "@scrambleCall"))
              (.. cy
                  (get "#app > div > div > div > div:nth-child(2) > input")
                  (type "more string"))
              (.. cy
                  (window)
                  (its "scramblies.main.app.app_db_as_edn")
                  (should (fn [app-db-as-edn]
                            (let [db (reader/read-string (app-db-as-edn))]
                              (assert (nil? (get-in db [:scramble :result]))))))))

          (it "calls backend and shows negative response" []
              (.. cy (intercept "POST" "/scramble"
                                (fn [^js request]
                                  (let [body (.-body request)]
                                    (assert (= (oget body "first-string") "test string"))
                                    (assert (= (oget body "second-string") "another test string"))
                                    (oset! request "!alias" "scrambleRequest")))))
              (.. cy
                  (get "#app > div > div > div > div:nth-child(1) > input")
                  (type "test string"))
              (.. cy
                  (get "#app > div > div > div > div:nth-child(2) > input")
                  (type "another test string"))
              (.. cy
                  (get "#app > div > div > div > div:nth-child(3) > button")
                  (click))
              (.. cy
                  (wait "@scrambleRequest")
                  (its "response.body")
                  (then (fn [body]
                          (-> body js/JSON.parse (oget "scramble") (= false) assert))))
              (.. cy (wait 1000))
              (.. cy
                  (get "#app > div > div > span:nth-child(3)")
                  (should "contain.text" "can't create")) )

          (it "calls backend and shows positive response" []
              (.. cy (intercept "POST" "/scramble"
                                (fn [^js request]
                                  (let [body (.-body request)]
                                    (assert (= (oget body "first-string") "test string"))
                                    (assert (= (oget body "second-string") "test string"))
                                    (oset! request "!alias" "scrambleRequest")))))
              (.. cy
                  (get "#app > div > div > div > div:nth-child(1) > input")
                  (type "test string"))
              (.. cy
                  (get "#app > div > div > div > div:nth-child(2) > input")
                  (type "test string"))
              (.. cy
                  (get "#app > div > div > div > div:nth-child(3) > button")
                  (click))
              (.. cy
                  (wait "@scrambleRequest")
                  (its "response.body")
                  (then (fn [body]
                          (-> body js/JSON.parse (oget "scramble") (= true) assert))))
              (.. cy (wait 1000))
              (.. cy
                  (get "#app > div > div > span:nth-child(3)")
                  (should "contain.text" "can create")) )
          ,)
