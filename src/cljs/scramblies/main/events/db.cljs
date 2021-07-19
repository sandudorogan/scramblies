(ns scramblies.main.events.db
  (:require [re-frame.core :as rf]))

(rf/reg-event-db
  :initialize-db
  (fn [_ []]
    {:scramble {:first-string  ""
                :second-string ""}}))
