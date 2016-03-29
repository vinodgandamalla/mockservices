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

(defn mytextbox1 [id finbool fintext]
  [:input.form-control {:type "text"
                        :id id
                        :value @fintext
                        :on-change (fn [event] (reset! fintext (-> event .-target .-value)))
                        :on-key-press (fn [event]
                                        (when (= 13 (.-charCode event))
                                          (reset! finbool false)))
                        :on-dbl-click (fn [event]
                                        (reset! finbool false))
            
                        }])



(defn myradiobutton [name value genderbool gendertext]
  [:input {:type "radio" :name name :value value :on-click (fn [event]
                                                              (reset! genderbool false))} value])

(defn myradiobutton1 [name value label]
  [:input {:type "radio" :name name :value value } [:label label]])

(defn myselect []
  [:select.form-control
    [:option {:value "Select"} "Select"]
    [:option {:value "B"} "B"]
    [:option {:value "C"} "C"]
    [:option {:value "D"} "D"]
    [:option {:value "E"} "E"]
    [:option {:value "F"} "F"]
    [:option {:value "G"} "G"]])



(defn personaldetails []
  (let [finbool (reagent/atom true)
        fintext (reagent/atom "abc")
        fanbool (reagent/atom true)
        fantext (reagent/atom "gvk")
        lnbool (reagent/atom true)
        lntext (reagent/atom "def")
        nationbool (reagent/atom true)
        nationtext (reagent/atom "India")
        genderbool (reagent/atom true)
        gendertext (reagent/atom "")
       ]
    (fn []
      [:div.form-group [:label [:h2 "Personal Details"]]
       [:hr]
       [:hr]
       [:div.row
        [:div.col-md-5
         [:div.row {:on-mouse-enter (fn [event] (reset! finbool true))
                    :on-mouse-leave (fn [event]
                                      (reset! finbool false))}
          [:div.col-sm-5 [:label "First Name*"]]
          [:div.col-sm-7
           (if (= @finbool true)
             [mytextbox1 "fn" finbool fintext]
             [:span  @fintext])]]]
        [:div.col-md-5
         [:div.row {:on-mouse-enter (fn [event] (reset! fanbool true))
                    :on-mouse-leave (fn [event]
                                      (reset! fanbool false))}
          [:div.col-sm-5 [:label "Father's Name/Spouse Name*"]]
          [:div.col-sm-7
           (if (= @fanbool true)
             [mytextbox1 "fan" fanbool fantext]
             [:span @fantext])]]]]
       [:div.row
        [:div.col-md-5
         [:div.row {:on-mouse-enter (fn [event] (reset! lnbool true))
                    :on-mouse-leave (fn [event]
                                      (reset! lnbool false))}
          [:div.col-sm-5 [:label "Last Name*"]]
          [:div.col-sm-7
           (if (= @lnbool true)
             [mytextbox1 "fn" lnbool lntext]
             [:span  @lntext])]]]
        [:div.col-md-5
         [:div.row
          [:div.col-sm-5 [:label "Gender"]]
          [:div.col-sm-7
           (if (= @genderbool true)
             [:div.row
              [:div.col-sm-6 [myradiobutton "Gender" "Male"]]
              [:div.col-sm-6 [myradiobutton "Gender" "Female"]]]
             [:span @gendertext])]]]]
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
        [:div.col-md-5
         [:div.row {:on-mouse-enter (fn [event] (reset! nationbool true))
                    :on-mouse-leave (fn [event]
                                      (reset! nationbool false))}
          [:div.col-sm-5 [:label "Nationality"]]
          [:div.col-sm-7
           (if (= @nationbool true)
             [mytextbox1 "fn" nationbool nationtext]
             [:span  @nationtext])]]]]
       [:div.row
        [:div.col-md-5
         [:div.row
          [:div.col-sm-5 [:label "Status"]]
                   [:div.col-sm-7 [myradiobutton "status" "Resident Individual"]]]]
        [:div.col-md-5
         [:div.row
          [:div.col-sm-5[myradiobutton "status" "Non Resident Individual"]]
          [:div.col-sm-7 [myradiobutton "status" "Foreign National(Passport copy Mandatory)"]]]]]
      ])))


(defn addressdetails []
  (let [hnbool (reagent/atom true)
        hntext (reagent/atom "1-97")
        ldmbool (reagent/atom true)
        ldmtext (reagent/atom "avenue")
        streetbool (reagent/atom true)
        streettext (reagent/atom "7th lane")
        mobilebool (reagent/atom true)
        mobiletext (reagent/atom "9247358308")
        citybool (reagent/atom true)
        citytext (reagent/atom "hyderabad")
        landlinebool (reagent/atom true)
        landlinetext (reagent/atom "040-82099")
        emailbool (reagent/atom true)
        emailtext (reagent/atom "gvk@gmail.com")
        telbool (reagent/atom true)
        teltext (reagent/atom "123456")
        pinbool (reagent/atom true)
        pintext (reagent/atom 500085)
        faxbool (reagent/atom true)
        faxtext (reagent/atom "012010")]
    (fn []
      [:div.form-group [:label [:h2 "Address Details"]]
       [:hr]
       [:hr]
       [:div.row
        [:div.col-md-5
         [:div.row {:on-mouse-enter (fn [event] (reset! hnbool true))
                    :on-mouse-leave (fn [event]
                                      (reset! hnbool false))}
          [:div.col-sm-5 [:label "House/Flat No"]]
          [:div.col-sm-7
           (if (= @hnbool true)
             [mytextbox1 "hno" hnbool hntext]
             [:span  @hntext])]]]
        [:div.col-md-5
         [:div.row {:on-mouse-enter (fn [event] (reset! ldmbool true))
                    :on-mouse-leave (fn [event]
                                      (reset! ldmbool false))}
          [:div.col-sm-5 [:label "Landmark"]]
          [:div.col-sm-7
           (if (= @ldmbool true)
             [mytextbox1 "ldm" ldmbool ldmtext]
             [:span @ldmtext])]]]]
       [:div.row
        [:div.col-md-5
         [:div.row {:on-mouse-enter (fn [event] (reset! streetbool true))
                    :on-mouse-leave (fn [event]
                                      (reset! streetbool false))}
          [:div.col-sm-5 [:label "Street/Road Name"]]
          [:div.col-sm-7
           (if (= @streetbool true)
             [mytextbox1 "streetno" streetbool streettext]
             [:span  @streettext])]]]
        [:div.col-md-5
         [:div.row {:on-mouse-enter (fn [event] (reset! mobilebool true))
                    :on-mouse-leave (fn [event]
                                      (reset! mobilebool false))}
          [:div.col-sm-5 [:label "Mobile No"]]
          [:div.col-sm-7
           (if (= @mobilebool true)
             [mytextbox1 "mobileno" mobilebool mobiletext]
             [:span @mobiletext])]]]]
       [:div.row
        [:div.col-md-5
         [:div.row {:on-mouse-enter (fn [event] (reset! citybool true))
                    :on-mouse-leave (fn [event]
                                      (reset! citybool false))}
          [:div.col-sm-5 [:label "City/Town/Village*"]]
          [:div.col-sm-7
           (if (= @citybool true)
             [mytextbox1 "city" citybool citytext]
             [:span  @citytext])]]]
        [:div.col-md-5
         [:div.row {:on-mouse-enter (fn [event] (reset! landlinebool true))
                    :on-mouse-leave (fn [event]
                                      (reset! landlinebool false))}
          [:div.col-sm-5 [:label "Landline"]]
          [:div.col-sm-7
           (if (= @landlinebool true)
             [mytextbox1 "landline" landlinebool landlinetext]
             [:span @landlinetext])]]]]
       [:div.row
        [:div.col-md-5
         [:div.row
          [:div.col-sm-5 [:label "District*"]]
          [:div.col-sm-7 [myselect]]]]
        [:div.col-md-5
         [:div.row {:on-mouse-enter (fn [event] (reset! emailbool true))
                    :on-mouse-leave (fn [event]
                                      (reset! emailbool false))}
          [:div.col-sm-5 [:label "Email id"]]
          [:div.col-sm-7
           (if (= @emailbool true)
             [mytextbox1 "email" emailbool emailtext]
             [:span  @emailtext])]]]]
       [:div.row
        [:div.col-md-5
         [:div.row
          [:div.col-sm-5 [:label "State*"]]
          [:div.col-sm-7 [myselect]]]]
        [:div.col-md-5
         [:div.row {:on-mouse-enter (fn [event] (reset! telbool true))
                    :on-mouse-leave (fn [event]
                                      (reset! telbool false))}
          [:div.col-sm-5 [:label "Tel.(Res)"]]
          [:div.col-sm-7
           (if (= @telbool true)
             [mytextbox1 "tel" telbool teltext]
             [:span  @teltext])]]]]
       
       [:div.row
        [:div.col-md-5
         [:div.row {:on-mouse-enter (fn [event] (reset! pinbool true))
                    :on-mouse-leave (fn [event]
                                      (reset! pinbool false))}
          [:div.col-sm-5 [:label "Pincode*"]]
          [:div.col-sm-7
           (if (= @pinbool true)
             [mytextbox1 "pin" pinbool pintext]
             [:span  @pintext])]]]
        [:div.col-md-5
         [:div.row {:on-mouse-enter (fn [event] (reset! faxbool true))
                    :on-mouse-leave (fn [event]
                                      (reset! faxbool false))}
          [:div.col-sm-5 [:label "Fax"]]
          [:div.col-sm-7
           (if (= @faxbool true)
             [mytextbox1 "fax" faxbool faxtext]
             [:span @faxtext])]]]]
       ])))


(defn primarydetails []
  [:div.form-group [:label [:h2 "Primary Details"]]
   [:hr]
   [:hr]
   [:div.row
    [:div.col-md-5
     [:div.row
     [:div.col-sm-5 [myradiobutton1 "primarydetails" "aadhar" "Aadhaar No"]]
     [:div.col-sm-7 [myradiobutton1 "primarydetails" "license" "Driving License"]]]]
    [:div.col-md-5
     [:div [myradiobutton1 "primarydetails" "pancardno" "Pan Card No"]]]]
   [:div.row
    [:div.col-md-5
     [:div.row
     [:div.col-sm-5 [myradiobutton1 "primarydetails" "passport" "Passport"]]
     [:div.col-sm-7  [myradiobutton1 "primarydetails" "telephonebill" "Latest Telephone Bill"]]]]
    [:div.col-md-5
     [:div [myradiobutton1 "primarydetails" "bankacc" "LatestBankA/cStatement(last 6 months)"]]]]
   [:div.row
    [:div.col-md-5 [labelandtextbox "*Not more than 3 months old.validity/expiry date of proof to be submitted" "date" "expirydate"]]
    [:div.col-md-5 [:div.row
                    [:div.col-sm-5
                     [:label "Upload a Document"]]
                    [:div.col-sm-7
                     [:input {:type "file" :name "upload document" :value "Upload Document"}]]]]]])


(defn loanspeciality []
  [:div.form-group [:label [:h2 "Loan Speciality"]]
   [:hr]
   [:hr]
   [:div.row
    [:div.col-md-5
     [:div.row
      [:div.col-sm-5 [:input {:type "checkbox" :name "Loans" :value "technology"} [:label"Technology Loans"]]]
      [:div.col-sm-7 [:input {:type "checkbox" :name "Loans" :value "startup"} [:label "Start-up Loans"]]]]]
    [:div.col-md-5 [:input {:type "checkbox" :name "Loans" :value "international"} [:label"International Business Expansion Loans"]]]]])

(defn finish []
  [:div.row
   [:div.col-md-4]
   [:div.col-md-4 [:input {:type "button" :name "finish" :value "Finish"}]]])



(defn home []
  [:div.container
   [personaldetails]
   [addressdetails]
   [primarydetails]
   [loanspeciality]
   [finish]])



(defn homeimg []
  ;; [:img {:src "http://localhost:8081/vinod.jpeg" :width 20 :height 30} ]
  [:div
  [:img.img-rounded
   {:src "http://localhost:8081/vinod.jpg" :alt "vinod pic"
    :width "100" :height "150"}]])

(reagent/render-component [home]
                          (.getElementById js/document "app"))
