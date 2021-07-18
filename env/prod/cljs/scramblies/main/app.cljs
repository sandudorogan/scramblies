(ns scramblies.main.app
  (:require [scramblies.main.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
