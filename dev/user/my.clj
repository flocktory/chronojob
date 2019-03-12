(ns user.my
  (:require [clojure.tools.namespace.repl :refer [refresh set-refresh-dirs]]
            [com.stuartsierra.component :as component]
            [defcomponent :refer [defcomponent]])
  (:import [io.prometheus.client CollectorRegistry]))

(set-refresh-dirs "src/" "test/" "dev/" "spec/src/")

(def system nil)

(defn init []
  (alter-var-root #'system (constantly (defcomponent/system
                                         [;; api и компоненты передаем сюда
                                          ;; is/api is/http-server
                                          ;; es/api es/http-server
                                          ;; push-s3/push-s3
                                          ;; as/api as/http-server
                                          ;; a/auditory
                                          ;; party.estimator/estimator
                                          ;; migration/migration
                                          ]
                                         {:file-config "config/production.clj"}))))

(defn start []
  (alter-var-root #'system component/start)
  :started)

(defn stop []
  (alter-var-root #'system
                  (fn [s] (when s (component/stop s) nil))))

(defn go []
  (init)
  (start))

(defn reset* []
  (stop)
  (refresh :after 'user.my/go)
  :ok)

(defn reset
  []
  (stop)
  (.clear (CollectorRegistry/defaultRegistry))
  (refresh :after 'user.my/go))