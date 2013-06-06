(ns Clojure-webapp.model
  (:require [noir.session :as session]))


  ;helper functions
(defn split [n xs]
  (reduce #(if (< (count (first %1)) n)
         (list (concat (first %1) [%2]) '())
         (list (first %1) (concat (last %1) [%2])))
      '(() ()) xs))(defn remove-at [k xs]
  (let [s (split (inc k) xs)]
    (list
     (concat (butlast (first s)) (last s))
     (last (first s))))
  )
(defn random-select [n xs]
  (let [r (remove-at (rand-int (count xs)) xs)]
    (if (= 1 n)
      (list (last r))
      (cons (last r) (random-select (dec n) (first r))))))
(defn remove-at [k xs]
  (let [s (split (inc k) xs)]
    (list
     (concat (butlast (first s)) (last s))
     (last (first s))))
  )
(defn full-board? []
  (< 99 (:beurt (session/get :game-state)))) 
  

(defn randboard []
   (vec (clojure.core/sort (random-select 25 (range 1 100)))))

(defn empty-board []
  (loop [result (transient []), length-left 5, interim-val (randboard)]
    (if (= length-left 0)
      (persistent! result)
      (recur 
        (conj! result (vec (take 5 interim-val)))
      (- length-left 1)
      (drop 5 interim-val)))))


(def init-state {:board (empty-board) :beurt 0 :getrokken (random-select 99 (range 1 100)) })

(defn reset-game! []
  (session/put! :game-state init-state))

(defn get-board []
  (:board (session/get :game-state)))

(defn volgende-beurt []
  (let [beurtnr (inc (:beurt (session/get :game-state)))]
    (session/swap! (fn [session-map]
                     (assoc session-map :game-state ({:beurt beurtnr} :game-state))))))
                            
      ;;:game-state conj :beurt beurtnr))) 


(defn get-beurtgetal []
  (nth (:getrokken (session/get :game-state)) (:beurt (session/get :game-state))))

(defn getrokken-getallen []
  (take (:beurt (session/get :game-state)) (:getrokken (session/get :game-state))))

(defn get-board-cell 
  ([row col] (get-board-cell (get-board) row col))
  ([board row col] (get-in board [row col])))

(defn winner-in-rows-cols-ordiags?
  [board]
  (let [diag-coords [[[0 0] [1 1] [2 2] [3 3] [4 4]]
                     [[0 4] [3 1] [2 2] [1 3] [0 4]]
                     
                     [[0 0] [1 0] [2 0] [3 0] [4 0]]
                     [[0 1] [1 1] [2 1] [3 1] [4 1]]
                     [[0 2] [1 2] [2 2] [3 2] [4 2]]
                     [[0 3] [1 3] [2 3] [3 3] [4 3]]
                     [[0 4] [1 4] [2 4] [3 4] [4 4]]
                     
                     [[0 0] [0 1] [0 2] [0 3] [0 4]]
                     [[1 0] [1 1] [1 2] [1 3] [1 4]]
                     [[2 0] [2 1] [2 2] [2 3] [2 4]]
                     [[3 0] [3 1] [3 2] [3 3] [3 4]]
                     [[4 0] [4 1] [4 2] [4 3] [4 4]]
                     ]]
    (boolean (some (fn [coords] 
                     (every? (fn [coord] 
                               (contains? (get-board-cell board coord) (getrokken-getallen))) 
                             coords))
                   diag-coords))))

(defn winner? 
  ([] (winner? (get-board)))
  ([board] (winner-in-rows-cols-ordiags? board)))

(defn new-state [row col oldstate]
  (if (not winner?)
    {:board (assoc-in (:board oldstate) [row col] (str "p" (get-board-cell row col)))}))
    
(defn play! [row col]
  (session/swap! (fn [session-map]
                   (assoc session-map :game-state 
                          (new-state row col (:game-state session-map))))))

 
(defn showcache []
  (session/get :game-state))