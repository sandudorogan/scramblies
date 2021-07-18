(ns scramblies.main.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [scramblies.main.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[scramblies.main started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[scramblies.main has shut down successfully]=-"))
   :middleware wrap-dev})
