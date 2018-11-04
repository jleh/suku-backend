(ns suku-backend.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :as middleware]
            [ring.util.response :refer [response status]]
            [suku-backend.db :as db]
            [suku-backend.world-events :as world-events]
            [suku-backend.places :as places]
            [ring.util.response :as response]
            [ring.adapter.jetty :refer [run-jetty]]
            [environ.core :refer [env]]
            [ring.middleware.cors :refer [wrap-cors]]
            [clojure.tools.logging :as log])
  (:gen-class))

(def allowed-origins [#"http://localhost:8080"
                      #"http://karttalehtinen.fi"])

(defn json-response [data]
  (-> (response/response data)
      (response/header "Content-Type" "application/json")))

(defn authenticate [request operation]
  (let [auth (get (:headers request) "authorization")]
    (if (= auth (str "Basic " (env :authorization)))
      (operation request)
      (do
        (log/info "Unauthorized request" (:compojure/route request))
        (status (response "Unauthorized") 401)))))

(defroutes app-routes
           (GET "/" [] "Hello World")
           (GET "/hello-world" [] (response {:msg "hello-world"}))
           (GET "/worldEvents" [] (json-response (world-events/get-world-events)))
           (GET "/places" [] (json-response (places/get-places)))
           (PUT "/places/:id" requrest (authenticate requrest places/update-place-request))
           (route/not-found "Not Found"))

(def app
  (-> app-routes
      (middleware/wrap-json-body {:keywords? true})
      (middleware/wrap-json-response)
      (wrap-cors :access-control-allow-origin allowed-origins
                 :access-control-allow-methods [:get :put :post :delete])
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))))

(defn -main [& args]
  (db/migrate)
  (run-jetty app {:port (Integer/valueOf (or (System/getenv "PORT") "3000"))
                  :join? false}))
