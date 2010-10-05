(ns lottery.pyxis
  (:require [lottery.core :as core])
  (:use [clojure.contrib.math]))

;; Initialize the global state variables
(def *bank* (ref 200))
(def *tickets* (ref (core/create-tickets 50)))
(def *ticket-price* 10)
(def *winners* (ref []))
(def *prizes* [(fn [jackpot] (round (* jackpot 0.75)))   ;; 1st prize
	       (fn [jackpot] (round (* jackpot 0.15)))   ;; 2nd prize
	       (fn [jackpot] (round (* jackpot 0.10)))]) ;; 3rd prize

(defn buy
  "Buy a ticket for the specified buyer"
  [buyer]
  (cond
   (not (empty? @*winners*)) :lottery-closed
   (and (string? buyer)
	(not (empty? buyer))) (core/buy-random-ticket *bank* *tickets* buyer *ticket-price*)
   :default :invalid-name))

(defn draw
  "Draw the winner tickets"
  []
  (if (not (empty? @*winners*))
    :lottery-closed
    (dosync
     (alter *winners* into (core/get-winners *tickets* (core/jackpot @*bank*) *prizes*))
     :draw-performed)))

(defn winners
  "Return the winners from the last draw"
  []
  (if (empty? @*winners*) :draw-required @*winners*))

