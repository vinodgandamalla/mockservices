(ns loginform.core
  (:require [qbits.jet.server :refer [run-jetty]]
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
           ))

;; Simple function that works as controller
;; It should return a proper response. In our
;; case it returns a content of static index.html.

(def in-db {:users [{:user "user1" :password "techno" :email-id "user1@gmail.com"}
                   {:user "user2" :password "identity" :email-id "user2@gmail.com"}
                   {:user "user3" :password "ti" :email-id "user3@gmail.com"}
                   {:user "user4" :password "tid" :email-id "user4@gmail.com"}]})


(defn find-in [in-db email-id]
  (println  email-id)
  (first
   (filter (fn[user](= (:email-id user) email-id))
           (:users in-db))))


(defn home
  [req]
  (render (io/resource "index.html") req))

;; Routes definition
(defroutes app-routes
  (GET "/" [] home)
  (POST "/authenticate" {body :body}
        (let [usr (find-in in-db (:email-id body))]
          (println (:email-id body))
          (rr/content-type
           (cond (or (nil? usr)
                     (not= (:password usr)(:password body))) (rr/status {:message "invalid credentials"} 404)
                 :else (rr/response (dissoc usr :password))) "application/json")))
 
  (POST "/changepassword" {body :body}
    (let [usr (find-in in-db (:email-id body))]
      (println (:email-id body))
      (rr/content-type
       (cond (or (nil? usr)
                 (not= (:password usr)(:password body))) (rr/status {:message "invalid user"} 404)
             :else (rr/response (dissoc usr :password))) "application/json")))
  

  (POST "/forgotpassword" {body :body}
    (let [usr (find-in in-db (:email-id body))]
      (println (:email-id body))
      (rr/content-type
       (cond (or (nil? usr)
                 (not= (:password usr)(:password body))) (rr/status {:message "invalid user"} 404)
             :else (rr/response (dissoc usr :password :user))) "application/json")))
  
  (route/resources "/static")
  (route/not-found "<h1>Page not found</h1>"))

(def app
  (-> app-routes
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))
      (ring-json/wrap-json-body {:keywords? true})
      (ring-json/wrap-json-response)))


;; Application entry point
(defn -main
  [& args]
  (run-jetty {:ring-handler app :port 8081}))

