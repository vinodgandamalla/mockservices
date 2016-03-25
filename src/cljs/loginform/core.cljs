(ns loginform.core
  (:require-macros [secretary.core :refer [defroute]])
  (:require [goog.events :as events]
            [goog.dom :as dom]
            [goog.history.EventType :as EventType]
            [bouncer.core :as b]
            [bouncer.validators :as v]
            [secretary.core :as secretary]
            [reagent.core :as reagent :refer [atom render]])
  (:import goog.History))


(defn mycomponent [label id text val]
  [:div.row 
   [:div.col-md-5
    [:label label]]
   [:div.col-md-7 
    [:input.form-control{:type "text"
                         :id id
                         :value @text
                         :on-change (fn [event]
                                      (reset! text (-> event .-target .-value)))
                         :on-key-press (fn [event]
                                         (when (= 13 (.-charCode event))
                                           (reset! val true)))}]]])


(defn home []
  (let [val (reagent/atom false)
        text (reagent/atom "vinod")]
    (fn []
      [:div.container
       [:div.form-group
        [:div [:h1 "Form"]]
        (if (= @val true)
          [:div.row {:on-click (fn [event] (reset! val false))}
           [:div.col-md-5
            [:label "First Name"]]
           [:div.col-md-7
            [:span @text]]]
          [mycomponent "First Name" 1 text val])]])))

;;{:on-click (fn [event]
;;(reset! val false)}

(reagent/render-component [home]
                          (.getElementById js/document "app"))
