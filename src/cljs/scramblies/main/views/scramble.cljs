(ns scramblies.main.views.scramble
  (:require
   [re-frame.core :as rf]
   [reagent.core :as r]))

(defn scramble-result []
  (let [result @(rf/subscribe [:scramble/result])]
    (when (some? result)
      [:span
       (if result
         "Hooray! You can create the second string using the characters from the first one!"
         "Sadly, you can't create the second string using the characters from the first one. :(")])))

(defn scramble-input [_]
  (let [input-value (r/atom nil)]
    (fn [input-key]
      [:div
       [:input
        {:value     @input-value
         :on-change (fn [e]
                      (let [value (.. e -target -value)]
                        (reset! input-value value)
                        (rf/dispatch [:scramble/assoc-input-value input-key value])))}]])))

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
