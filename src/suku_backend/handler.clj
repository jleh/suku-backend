(ns suku-backend.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :as middleware]
            [ring.util.response :refer [response]]
            [suku-backend.world-events :as world-events]
            [suku-backend.places :as places]
            [ring.util.response :as response]
            [ring.adapter.jetty :refer [run-jetty]])
  (:gen-class))

(defn json-response [data]
  (-> (response/response data)
      (response/header "Content-Type" "application/json")))

(defroutes app-routes
           (GET "/" [] "Hello World")
           (GET "/hello-world" [] (response {:msg "hello-world"}))
           (GET "/worldEvents" [] (json-response (world-events/get-world-events)))
           (GET "/places" [] (json-response (places/get-places)))
           (route/not-found "Not Found"))

(def app (-> app-routes
           (middleware/wrap-json-body {:keywords? true})
           (middleware/wrap-json-response)
           (wrap-defaults site-defaults)))

(defn -main [& args]
  (run-jetty app {:port (Integer/valueOf (or (System/getenv "port") "3000"))}))
