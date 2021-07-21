(ns scramblies.main.events.network
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [re-frame.core :as rf]
            [cljs.core.async :as async :refer [<!]]
            [cljs-http.client :as http]))

(rf/reg-event-fx
  :make-request
  (fn [{:keys [db]} [_ {:keys [custom-opts
                              loader
                              method
                              on-success
                              on-error
                              progress-callback
                              url]
                       :as   params}]]
    (let [cancel     (async/chan)
          progress   (async/chan)
          loader     (if (false? loader)
                       #()
                       #(rf/dispatch [:ui/loading (assoc params :cancel cancel)]))
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
      (loader)
      (if progress-callback
        (go-loop []
          (when-let [response (<! progress)]
            (progress-callback response)
            (if (= (:total response) (:loaded response))
              (async/close! progress)
              (recur))))
        (async/close! progress))
      (go (when-let [response (<! (action url opts))]
            (loader)
            (if (= 200 (:status response))
              (on-success response)
              (on-error response))))
      {})))
