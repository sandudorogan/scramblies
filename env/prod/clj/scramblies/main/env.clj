(ns scramblies.main.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[scramblies.main started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[scramblies.main has shut down successfully]=-"))
   :middleware identity})
