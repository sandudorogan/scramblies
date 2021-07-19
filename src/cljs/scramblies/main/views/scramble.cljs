(ns scramblies.main.views.scramble
  (:require
   [re-frame.core :as rf]))

(defn scramble-input [input-key]
  (let [input-value (rf/subscribe [(->> input-key name (str "scramble/") keyword)])]
    [:div
     [:input
      {:value     @input-value
       :on-change #(rf/dispatch [:scramble/assoc-input-value input-key (.. % -target -value)])}]]))

(defn scramble []
  [:div
   [:span "Please insert the strings to perform the scramble upon:"]
   [:div.scramble.material-icons
    (doall
      (for [input-key [:first-string :second-string]]
        ^{:key input-key}
        [scramble-input input-key]))
    [:div
     [:button {:type "button"}
      [:span "Submit"]]]]])
