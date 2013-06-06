(ns Clojure-webapp.view
  (:use hiccup.form
        [hiccup.def :only [defhtml]]
        [hiccup.element :only [link-to]]
        [hiccup.page :only [html5 include-css]])
  (:require [Clojure-webapp.model :as model]))

(defhtml layout [& content]
  (html5
    [:head
     [:title "Ruwan en Niels Bingo"]
     (include-css "/css/stijlloos.css")]
    [:body [:div#wrapper content]]))

(defn cell-html [rownr colnr cell with-submit?]
  [:td
   [:input {:name (str "b" rownr colnr)
            :value (str cell)
            :type (if with-submit?
                    "submit"
                    "button")}]])

(defn row-html [rownr row with-submit?]
  [:tr (map-indexed (fn [colnr cell]
                      (cell-html rownr colnr cell with-submit?))
                    row)])

(defn board-html [board with-submit?]
  (form-to [:post "/"]
           [:table
            (map-indexed (fn [rownr row]
                           (row-html rownr row with-submit?))
                         board)]))

(defn getrokken-nummer-html []
  [:div
   (form-to [:post "/"]
             [:input {:name "VOLGENDE"
                      :value (str "Getrokken is nu: "(model/get-beurtgetal))
                      :type "submit"}])
            [:p#hidden
             (model/showcache)]])

(defn play-screen []
  (layout
    [:div
     [:p "Player (only one supported at this moment)" "it's your turn"]
     (getrokken-nummer-html)
     (board-html (model/get-board) true)]))

(defn winner-screen [winner]
  (layout
    [:div
     [:p "Player, you have WON" winner]
     (board-html (model/get-board) false)
     (link-to "/" "Reset")]))

(defn draw-screen []
  (layout
    [:div
     [:p "It's a draw!"]
     (board-html (model/get-board) false)
     (link-to "/" "Reset")]))