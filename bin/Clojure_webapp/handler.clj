(ns Clojure-webapp.handler
  (:use Clojure-webapp.controller
        compojure.core)
  (:require [noir.util.middleware :as noir-middleware]
            [compojure.route :as route]))


(defroutes app-routes
  (route/resources "/")
  (route/not-found "Resource Not Found"))

(def all-routes [bingo-routes app-routes])

(def app (-> all-routes
             noir-middleware/app-handler
             ))

(def war-handler (noir-middleware/war-handler app))