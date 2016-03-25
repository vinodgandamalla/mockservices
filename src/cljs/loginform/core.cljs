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


;; (defn mycomponent [label id text val]
;;   [:div.row 
;;    [:div.col-md-5
;;     [:label label]]
;;    [:div.col-md-7 
;;     [:input.form-control{:type "text"
;;                          :id id
;;                          :value @text
;;                          :on-change (fn [event]
;;                                       (reset! text (-> event .-target .-value)))
;;                          :on-key-press (fn [event]
;;                                          (when (= 13 (.-charCode event))
;;                                            (reset! val true)))}]]])
;; (defn home []
;;   (let [val (reagent/atom false)
;;         text (reagent/atom "vinod")]
;;     (fn []
;;       [:div.container
;;        [:div.form-group
;;         [:div [:h1 "My Profile Page"]]
;;         (if (= @val true)
;;           [:div.row {:on-click (fn [event] (reset! val false))}
;;            [:div.col-md-5
;;             [:label "First Name"]]
;;            [:div.col-md-7
;;             [:span @text]]]
;;           [mycomponent "First Name" 1 text val])]])))


(defn mytextbox [type id]
  [:input.form-control {:type type :id id}])

(defn labelandtextbox [label type id]
  [:div.row
   [:div.col-sm-5 [:label label]]
   [:div.col-sm-7 [mytextbox type id]]
   ])


(defn myradiobutton [name value]
  [:input {:type "radio" :name name :value value} value])



(defn personaldetails []
  [:div.form-group [:label [:h2 "Personal Details"]]
   [:div.row 
    [:div.col-md-5
     [labelandtextbox "First Name*" "text" "fn"]]
    [:div.col-md-5
     [labelandtextbox "Father's Name/Spouse Name*" "text" "ln"]]]
   [:div.row
    [:div.col-md-5
     [labelandtextbox "Last Name*"]]
    [:div.col-md-5
     [:div.row
      [:div.col-sm-5 [:label "Gender"]]
      [:div.col-sm-7
       [:div.row
        [:div.col-sm-6 [myradiobutton "Gender" "Male"]]
        [:div.col-sm-6 [myradiobutton "Gender" "Female"]]]]]]]
   [:div.row
    [:div.col-md-5
     [labelandtextbox "Date of Birth*" "date" "dob"]]
    [:div.col-md-5
     [:div.row
      [:div.col-sm-5 [:label "Marital Status"]]
      [:div.col-sm-7
       [:div.row
        [:div.col-sm-6 [myradiobutton "maritalstatus" "Single"]]
        [:div.col-sm-6 [myradiobutton "maritalstatus" "Married"]]]]]]]
   [:div.row
    [:div.col-md-5]
    [:div.col-md-5
     [labelandtextbox "Nationality" "text" "Nationality"]]]
   [:div.row
    [:div.col-md-5
     [:div.row
      [:div.col-sm-5 [:label "Status"]]
      [:div.col-sm-7 [myradiobutton "status" "Resident Individual"]]]]
    [:div.col-md-7
     [:div.row
      [:div.col-sm-6 [myradiobutton "status" "Non Resident Individual"]]
      [:div.col-sm-6 [myradiobutton "status" "Foreign National(Passport copy Mandatory)"]]]]]])



(defn addressdetails []
  [:div.form-group [:label [:h2 "Address Details"]]
   [:div.row
    [:div.col-md-5
     [labelandtextbox "House/Flat No" "text" "h.no"]]
    [:div.col-md-5
     [labelandtextbox "Land Mark" "text" "landmark"]]]
   [:div.row
    [:div.col-md-5
     [labelandtextbox "Street/Road no" "text" "street"]]
    [:div.col-md-5
     [labelandtextbox "Mobile No" "text" "mobileno"]]]
   [:div.row
    [:div.col-md-5
     [labelandtextbox "City/Town/Village*" "text" "city"]]
    [:div.col-md-5
     [labelandtextbox "Land line" "text" "landline"]]]
   [:div.row
    [:div.col-md-5
     [labelandtextbox "House/Flat No." "text" "h.no"]]
    [:div.col-md-5
     [labelandtextbox "Email-id" "email" "email"]]]
     ])

<div class="dropdown">
<button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Dropdown Example
<span class="caret"></span></button>
<ul class="dropdown-menu">
<li><a href="#">HTML</a></li>
<li><a href="#">CSS</a></li>
<li><a href="#">JavaScript</a></li>
</ul>
</div>
</div>


[:div {:class "dropdown"}
 [:button {:class "btn btn-primary dropdown-toggle" :type "button" :data-toggle "dropdown"} "Select Any District"]]



(defn home []
  [:div.container
   [personaldetails]
   [:hr]
   [addressdetails]])

(reagent/render-component [home]
                          (.getElementById js/document "app"))
