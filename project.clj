;; hack for using http nexus repos
(require 'cemerick.pomegranate.aether)
(cemerick.pomegranate.aether/register-wagon-factory!
  "http" #(org.apache.maven.wagon.providers.http.HttpWagon.))

(defproject chronojob "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "FreeAtLast"}
  :parent-project {:coords [com.flocktory/staff.root "4.0.5"]
                   :inherit [:managed-dependencies]}
  :dependencies [[org.clojure/clojure]

                 [com.flocktory/staff.logging]
                 [com.flocktory/staff.metrics]
                 [com.flocktory/staff.probs]
                 [com.flocktory/staff.sql]
                 [com.flocktory/staff.config]
                 [com.flocktory/staff.config]
                 ;; jaeger почему-то не работает в k8s
                 [com.flocktory/staff.service "2.4.6-no-jaeger"]

                 [org.clojure/tools.reader]
                 [http-kit "2.3.0"]
                 [prismatic/schema]
                 [prismatic/plumbing]
                 [ring/ring-core "1.7.1"]
                 [com.grammarly/omniconf "0.2.2"]
                 [defcomponent]
                 [honeysql "0.6.3"]
                 [org.postgresql/postgresql "9.4.1208.jre7"]
                 [com.zaxxer/HikariCP "2.4.5"]
                 [bidi "2.0.6"]
                 [ring/ring-json "0.4.0"]
                 [org.clojure/core.async "0.4.490"
                  :exclusions [org.clojure/tools.reader]]
                 [clj-time "0.11.0"]

                 [io.prometheus/simpleclient "0.0.14"]
                 [io.prometheus/simpleclient_common "0.0.14"]

                 ;[org.slf4j/slf4j-api "1.7.7"]
                 ;[ch.qos.logback/logback-classic "1.1.2"]
                 ;[org.slf4j/log4j-over-slf4j "1.7.7"]
                 ;[org.slf4j/jul-to-slf4j "1.7.7"]
                 ;[org.slf4j/jcl-over-slf4j "1.7.7"]
                 ;[net.kencochrane.raven/raven-logback "6.0.0"]

                 [javax.servlet/servlet-api "2.5"]
                 [javax.xml.bind/jaxb-api "2.3.0"]]

  :jvm-opts ["-Duser.timezone=GMT"]

  :plugins [[lein-parent "0.3.5"]
            [lein-eftest "0.5.4"]]

  :eftest {:multithread? false}
  :main chronojob.core

  :source-paths ["src" "spec/src" "dev"]
  :resource-paths ["resources"]

  :profiles {:repl {:source-paths ["src" "spec/src" "dev"]
                    :resource-paths ["test/resources" "resources"]
                    :repl-options {:init-ns user.my}
                    :dependencies [[org.clojure/tools.namespace "0.2.11"]]
                    :injections [(require 'clojure.tools.namespace.repl)
                                 (require 'user.my)]}}
  :mirrors {"central" {:name "Nexus"
                       :url "https://nexus.flocktory.com/nexus/content/groups/public/"
                       :repo-manager true}
            #"clojars" {:name "Nexus"
                        :url "https://nexus.flocktory.com/nexus/content/groups/public/"
                        :repo-manager true}}
  :repositories [["releases"
                  {:url "https://nexus.flocktory.com/nexus/content/repositories/releases/"
                   :sign-releases false}]
                 ["snapshots"
                  {:url "https://nexus.flocktory.com/nexus/content/repositories/snapshots/"
                   :sign-releases false}]])
