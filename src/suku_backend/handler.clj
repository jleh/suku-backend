(ns suku-backend.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :as middleware]
            [ring.util.response :refer [response]]
            [suku-backend.world-events :as world-events]
            [suku-backend.places :as places]
            [ring.util.response :as response]))

(defroutes app-routes
           (GET "/" [] "Hello World")
           (GET "/hello-world" [] (response {:msg "hello-world"}))
           (GET "/worldEvents" []
             (-> (response/response (world-events/get-world-events))
                 (response/header "Content-Type" "application/json")))
           (GET "/places" []
             (-> (response/response (places/get-places))
                 (response/header "Content-Type" "application/json")))
           (route/not-found "Not Found"))

(def app (-> app-routes
           (middleware/wrap-json-body {:keywords? true})
           (middleware/wrap-json-response)
           (wrap-defaults site-defaults)))
