(ns scramblies.main.subscribers.scramble
  (:require [re-frame.core :as rf]))

(rf/reg-sub
  :scramble
  (fn [db _]
    (:scramble db)))

(rf/reg-sub
  :scramble/first-string
  :<- [:scramble]
  (fn [scramble _]
    (:first-string scramble)))

(rf/reg-sub
  :scramble/second-string
  :<- [:scramble]
  (fn [scramble _]
    (:second-string scramble)))

(rf/reg-sub
  :scramble/result
  :<- [:scramble]
  (fn [scramble _]
    (:result scramble)))
