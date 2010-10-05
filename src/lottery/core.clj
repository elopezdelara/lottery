(ns lottery.core
  (:use [clojure.set]))

(defrecord ticket [number buyer])

(defn jackpot
  "Return the amount of the jackpot based on the amount in the bank."
  [bank]
  (* bank 0.5))

(defn create-tickets
  "Create a list of tickets starting from 1 to the number specified."
  [size]
  (vec (map #(ticket. % nil) (range 1 (inc size)))))

(defn available-tickets
  "Return a list of the available tickets (not yet purchased)"
  [tickets]
  (vec (filter #(nil? (:buyer %)) @tickets)))

(defn buy-ticket
  "Buy a ticket from the ticket list and update the bank amount"
  [bank tickets number buyer price]
  (let [idx (dec number)]
    (dosync (alter tickets assoc idx (assoc (tickets idx) :buyer buyer))
	    (alter bank + price))
    (@tickets idx)))

(defn buy-random-ticket
  "Buy a random ticket from the ticket list and update the bank amount"
  [bank tickets buyer price]
  (dosync
   (let [ticket-number (:number (first (shuffle (available-tickets tickets))))]
     (if (nil? ticket-number)
       nil       
       (buy-ticket bank tickets ticket-number buyer price)))))

(defn draw-tickets
  "Randomly draw the specified number of tickets from the ticket list (also
   removes them from the list)"
  [tickets size]
  (dosync
   (let [drawn-tickets (take size (shuffle @tickets))]
     (ref-set tickets (vec (difference (set @tickets) (set drawn-tickets))))
     (vec drawn-tickets))))

(defn get-winners
  "Draw tickets and assign the corresponding prizes"
  [tickets jackpot prizes]
  (let [drawn-tickets (draw-tickets tickets (count prizes))]
    (into [] (map (fn [t p] (hash-map :ticket t :amount (p jackpot)))
		  drawn-tickets prizes))))
