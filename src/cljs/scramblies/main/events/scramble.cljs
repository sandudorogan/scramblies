(ns scramblies.main.events.scramble
  (:require [re-frame.core :as rf]))


(rf/reg-event-db
  :scramble/assoc-input-value
  (fn [db [_ input-key input-value]]
    (assoc-in db [:scramble input-key] input-value)))
