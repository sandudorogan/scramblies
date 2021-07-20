(ns scramblies.main.events.scramble
  (:require [re-frame.core :as rf]))

(rf/reg-event-db
  :scramble/assoc-input-value
  (fn [db [_ input-key input-value]]
    (-> db
        (assoc-in [:scramble input-key] input-value)
        (update :scramble dissoc :result)))) ;; Delete any info from the previous submit

(rf/reg-event-fx
  :scramble/do-scramble
  (fn [{:keys [db]} [_]]
    (let [first-string  (get-in db [:scramble :first-string])
          second-string (get-in db [:scramble :second-string])]
      {:dispatch [:make-request {:method      "POST"
                                 :url         "/scramble"
                                 :custom-opts {:body (js/JSON.stringify #js {:first-string  first-string
                                                                             :second-string second-string})}
                                 :loader      false
                                 :on-success  #(let [response (-> % :body js/JSON.parse js->clj (get "scramble"))]
                                                 (rf/dispatch [:scramble/assoc-result response]))}]})))

(rf/reg-event-db
  :scramble/assoc-result
  (fn [db [_ result]]
    (assoc-in db [:scramble :result] result)))
