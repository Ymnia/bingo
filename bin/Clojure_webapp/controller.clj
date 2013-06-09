(ns Clojure-webapp.controller
  (:use compojure.core)
  (:require [compojure.core :as compojure]
            [Clojure-webapp.view :as view]
            [Clojure-webapp.model :as model]))


(defn start-page []
  (model/reset-game!)
  (view/play-screen))

(defn turn-page [button-pressed]
  (if (= (first (keys button-pressed)) :VOLGENDE )
    (if (model/full-board?)
      (view/draw-screen)
      (do (model/volgende-beurt!) (view/play-screen)))
    (let [button-id (name (first (keys button-pressed)))
          rownr (Integer/parseInt (str (second button-id)))
          colnr (Integer/parseInt (str (nth button-id 2)))]
      (if (model/cell-is-beurtgetal? rownr colnr)
        (do
          (model/play! rownr colnr)
          (if (model/winner?)
            (view/winner-screen)
            (view/play-screen)))
        (view/play-screen))))
  
  )
        ;(if-let [winner (model/winner?)]
        ; (view/winner-screen winner)

(defroutes bingo-routes
  (GET "/" [] (start-page))
  (POST "/" {button-pressed :params} (turn-page button-pressed)))