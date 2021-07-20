(ns scramblies.main.views.scramble
  (:require
   [re-frame.core :as rf]))

(defn scramble-result []
  (let [result @(rf/subscribe [:scramble/result])]
    (when (some? result)
      [:span
       (if result
         "Hooray! You can create the second string using the characters from the first one!"
         "Sadly, you can't create the second string using the characters from the first one. :(")])))

(defn scramble-input [input-key]
  (let [input-value (rf/subscribe [(->> input-key name (str "scramble/") keyword)])]
    [:div
     [:input
      {:value     @input-value
       :on-change #(rf/dispatch [:scramble/assoc-input-value input-key (.. % -target -value)])}]]))

(defn scramble []
  [:div.grid-center.grid-5-rows
   [:span "Insert the strings to scramble:"]
   [:div.scramble.material-icons
    (doall
      (for [input-key [:first-string :second-string]]
        ^{:key input-key}
        [scramble-input input-key]))
    [:div
     [:button
      {:type     "button"
       :on-click #(rf/dispatch [:scramble/do-scramble])}
      [:span "Submit"]]]]
   [scramble-result]])
