(ns scramblies.main.events.network
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [re-frame.core :as rf]
            [cljs.core.async :as async :refer [<!]]
            [cljs-http.client :as http]))

(rf/reg-event-fx
  :make-request
  (fn [_ [_ {:keys [custom-opts
                   method
                   on-success
                   on-error
                   url]}]]
    (let [cancel     (async/chan)
          progress   (async/chan)
          action     (case method
                       "GET"  http/get
                       "POST" http/post
                       "PUT"  http/put)
          base-opts  {:headers  (merge
                                  {"Content-Type" "application/json"
                                   "Accept"       "application/json"})
                      :method   method
                      :progress progress
                      :cancel   cancel}
          opts       (merge-with merge base-opts custom-opts)
          opts       (cond-> opts
                       (= (get-in opts [:headers "Content-Type"]) "multipart/form-data")
                       (update :headers dissoc "Content-Type"))
          on-error   (or on-error #())
          on-success (or on-success #())]
      (go (when-let [response (<! (action url opts))]
            (if (= 200 (:status response))
              (on-success response)
              (on-error response))))
      {})))
