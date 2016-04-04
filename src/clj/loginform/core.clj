(ns loginform.core
  (:require
                                        ;[qbits.jet.server :refer [run-jetty]]
   [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
   [compojure.core :refer :all]
   [compojure.route :as route]
   [compojure.response :refer [render]]
   [clojure.java.io :as io]
   [bouncer.core :as b]
   [bouncer.validators :as v]
   [ring.middleware.json :as ring-json]
   [compojure.route :as route]
   [ring.util.response	:as rr]
   [ring.middleware.cors :refer [wrap-cors]]
   [ring.adapter.jetty :as jetty]
   [clojure.string :as s]))

;; Simple function that works as controller
;; It should return a proper response. In our
;; case it returns a content of static index.html.

(def in-db (atom {:users [{:user "user1" :password "techno" :email-id "user1@gmail.com"
                           :services ["SalesAgent"]}
                          {:user "user2" :password "identity" :email-id "user2@gmail.com"
                           :services ["SuperAdmin"]}
                          {:user "user3" :password "ti" :email-id "user3@gmail.com"}
                          {:user "user4" :password "tid" :email-id "user4@gmail.com"}]}))

(def users-to-register (atom {:email "rajesh.ponnala@gmail.com"}))

(def registered-users (atom {:email "inturi.krishnarao@gmail.com"}))


(def myconnections (atom [{:name "Krishna Rao" :types "Individual" :profile-complete "25%"}
                          {:name "Shiva" :types "Individual" :profile-complete "85%"}
                          {:name "Bharath" :types "Individual" :profile-complete "5%"}
                          {:name "suresh" :types "Individual" :profile-complete "20%"}
                          ]))
(def myconnectionsempty (atom []))



(def myconnectionspending (atom [{:name "Rajesh" :types "Individual" :profile-complete "75%"}
                                 {:name "Srinu" :types "Individual" :profile-complete "75%"}
                                 {:name "Nivas" :types "Organizational" :profile-complete "45%"}]))

(def videolist (atom
                [{ :id 1 :description "How Know the Basics of Bussiness circle" :videoName "Tutorial 1"
                  :videoUrl "https://www.youtube.com/embed/VSdnJDO-xdg" :videoPic "http://easyhtml5video.com/assets/images/icons/features-any-videos.png" :videoCategory "generic"}
                 { :id 2 :description "How Know the Basics of Bussiness circle" :videoName "Tutorial 2"
                 :videoUrl "https://www.youtube.com/embed/uW7pZLyYjDE" :videoPic "http://easyhtml5video.com/assets/images/icons/features-any-videos.png" :videoCategory "generic"}
                 { :id 3 :description "How Know the Basics of Bussiness circle" :videoName "Tutorial 3"
                  :videoUrl "https://www.youtube.com/embed/wASCH_gPnDw" :videoPic "http://easyhtml5video.com/assets/images/icons/features-any-videos.png"  :videoCategory "generic"}
                 { :id 4 :description "How Know the Basics of Bussiness circle" :videoName "Tutorial 4"
                  :videoUrl "https://www.youtube.com/embed/VVd4ow-ZcX0" :videoPic "http://easyhtml5video.com/assets/images/icons/features-any-videos.png"  :videoCategory "generic"}
                 { :id 5 :description "How Know the Basics of Bussiness circle" :videoName "Tutorial 5"
                  :videoUrl "https://www.youtube.com/embed/FjUKmTIHyXo" :videoPic "http://easyhtml5video.com/assets/images/icons/features-any-videos.png"  :videoCategory "generic"}
                 { :id 6 :description "How Know the Basics of Bussiness circle" :videoName "Tutorial 6"
                  :videoUrl "https://www.youtube.com/embed/VSdnJDO-xdg" :videoPic "http://easyhtml5video.com/assets/images/icons/features-any-videos.png"  :videoCategory "generic"}
                 { :id 7 :description "How Know the Basics of Bussiness circle" :videoName "Tutorial 7"
                  :videoUrl "https://www.youtube.com/embed/l_fxk3QvOrQ" :videoPic "http://easyhtml5video.com/assets/images/icons/features-any-videos.png"  :videoCategory "generic"}
                 { :id 8 :description "How Know the Basics of Bussiness circle" :videoName "Tutorial 8"
                  :videoUrl "https://www.youtube.com/embed/VSdnJDO-xdg" :videoPic "http://easyhtml5video.com/assets/images/icons/features-any-videos.png"  :videoCategory "generic"}
                 { :id 9 :description "How Know the Basics of Bussiness circle" :videoName "Tutorial 9"
                  :videoUrl "https://www.youtube.com/embed/l_fxk3QvOrQ" :videoPic "http://easyhtml5video.com/assets/images/icons/features-any-videos.png"  :videoCategory "specialized"}
                 { :id 10 :description "How Know the Basics of Bussiness circle" :videoName "Tutorial 10"
                  :videoUrl "https://www.youtube.com/embed/VSdnJDO-xdg" :videoPic "http://easyhtml5video.com/assets/images/icons/features-any-videos.png"  :videoCategory "specialized"}
                 { :id 11 :description "How Know the Basics of Bussiness circle" :videoName "Tutorial 11"
                  :videoUrl "https://www.youtube.com/embed/uW7pZLyYjDE" :videoPic "http://easyhtml5video.com/assets/images/icons/features-any-videos.png"  :videoCategory "specialized"}
                 { :id 12 :description "How Know the Basics of Bussiness circle" :videoName "Tutorial 12"
                  :videoUrl "https://www.youtube.com/embed/wASCH_gPnDw" :videoPic "http://easyhtml5video.com/assets/images/icons/features-any-videos.png"  :videoCategory "specialized"}
                 { :id 13 :description "How Know the Basics of Bussiness circle" :videoName "Tutorial 13"
                  :videoUrl "https://www.youtube.com/embed/VVd4ow-ZcX0" :videoPic "http://easyhtml5video.com/assets/images/icons/features-any-videos.png"  :videoCategory "specialized"}
                 { :id 14 :description "How Know the Basics of Bussiness circle" :videoName "Tutorial 14"
                  :videoUrl "https://www.youtube.com/embed/FjUKmTIHyXo" :videoPic "http://easyhtml5video.com/assets/images/icons/features-any-videos.png"  :videoCategory "specialized"}
                 { :id 15 :description "How Know the Basics of Bussiness circle" :videoName "Tutorial 15"
                  :videoUrl "https://www.youtube.com/embed/l_fxk3QvOrQ" :videoPic "http://easyhtml5video.com/assets/images/icons/features-any-videos.png"  :videoCategory "specialized"}
                 { :id 16 :description "How Know the Basics of Bussiness circle" :videoName "Tutorial 16"
                  :videoUrl "https://www.youtube.com/embed/VSdnJDO-xdg" :videoPic "http://easyhtml5video.com/assets/images/icons/features-any-videos.png"  :videoCategory "specialized"}
                 { :id 17 :description "How Know the Basics of Bussiness circle" :videoName "Tutorial 17"
                  :videoUrl "https://www.youtube.com/embed/l_fxk3QvOrQ" :videoPic "http://easyhtml5video.com/assets/images/icons/features-any-videos.png"  :videoCategory "specialized"}
                 { :id 18 :description "How Know the Basics of Bussiness circle" :videoName "Tutorial 18"
                  :videoUrl "https://www.youtube.com/embed/VSdnJDO-xdg" :videoPic "http://easyhtml5video.com/assets/images/icons/features-any-videos.png"  :videoCategory "specialized"}]))


(defn find-in [in-db email-id]
  (first
   (filter (fn[user](= (:email-id user) email-id))
           (:users @in-db))))
(defn home
  [req]
  (render (io/resource "index.html") req))

;; Routes definition
(defroutes app-routes
  (GET "/" [] home)

  (POST "/signup" {body :body}
        (rr/content-type
         (cond (= (:email @registered-users) (:email body)) (rr/status {:message "already existing"} 409)
               (not= (:email @users-to-register) (:email body)) (rr/status {:message "invalid user"} 404)
               :else (rr/response {:message "sucessfull"})) "application/json"))


  (POST "/authenticate" {body :body}
        (let [usr (find-in in-db (:identifier body))]
          (rr/content-type
           (cond (or (nil? usr)
                     (not= (:password usr)(:password body)))
                 (rr/status {:message "invalid credentials"} 401)
                 :else (rr/response (assoc (dissoc usr :password)
                                           :token "gfjhjgjgh"))) "application/json")))

  (POST "/changepassword" {body :body}
        (let [usr (find-in in-db (:email body))]
          (rr/content-type
           (cond (not= (:password usr)(:password body)) (rr/status {:body {:message "current password does not match"}} 401)
                 (not=(:newpassword body)(:confirmnewpassword body))(rr/status {:body {:message "new password and confirmnewpassword does not match"}} 401)
                 :else ( do
                         (reset! in-db (assoc @in-db :users
                                              (map (fn [m]
                                                     (if (=(:email-id m) (:email body))(assoc m :password (:newpassword body))
                                                         m)) (:users @in-db))))
                         (rr/response {:message "changed successfully"})))
           "application/json")))

  (POST "/forgotpassword" {body :body}
        (let [usr (find-in in-db (:email body))]
          (rr/content-type
           (cond (nil? usr) (rr/status {:body {:message "invalid user"}} 401) 
                 :else (rr/response {:message "check  your mail for reset link"})) "application/json")))

  (GET "/salesagent/connections" []
       (rr/content-type
        (rr/response @myconnections)
        "application/json"))

  (GET "/salesagent/connections/search/:name" [name]
       (rr/content-type
        (rr/response (filter #(.contains (:name %) name) @myconnections))
        "application/json"))

  (GET "/salesagent/connections/pending" []
       (rr/content-type
        (rr/response @myconnectionspending)
        "application/json"))

  (GET "/salesagent/trainings" []
    (rr/content-type
     (rr/response @videolist)
     "application/json"))

  (route/resources "/static")
  (route/not-found "<h1>Page not found</h1>"))

(def app
  (-> app-routes
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))
      (wrap-cors :access-control-allow-origin [#".*"]
                 :access-control-allow-methods [:get :put :post :delete])
      (ring-json/wrap-json-body {:keywords? true})
      (ring-json/wrap-json-response)))


;; Application entry point
(defn -main
  [& args]
  (jetty/run-jetty app {:port 8081}))

