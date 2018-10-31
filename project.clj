(defproject suku-backend "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-jetty-adapter "1.7.0"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/java.jdbc "0.7.8"]
                 [org.postgresql/postgresql "42.2.5"]
                 [gedcom "0.1.1"]
                 [ragtime "0.7.2"]
                 [environ "1.1.0"]]
  :plugins [[lein-ring "0.12.4"]]
  :ring {:handler suku-backend.handler/app}
  :uberjar-name "suku-backend-standalone.jar"
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}
   :uberjar {:aot :all
             :main suku-backend.handler}}
  :aliases {"migrate" ["run" "-m" "user/migrate"]
            "rollback" ["run" "-m" "user/rollback"]})
