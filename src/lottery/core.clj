(ns lottery.core)

(def *lottery* (atom {:tickets      (zipmap (range 1 51) (repeat nil))
		      :prizes       [0.375 0.075 0.05] ;; 1st = 0.5*0.75, 2nd = 0.5*0.15, 3rd = 0.5*0.1
		      :register     200
		      :ticket-price 10}))

(defn shuffle-tickets
  "Shuffle the tickets"
  []
  (shuffle (vec (:tickets @*lottery*))))

(defn pick-ticket
  "Pick a random ticket not yet purchased"
  []
  (first (filter (fn [[_ buyer]] (nil? buyer)) (shuffle-tickets))))  

(defn buy-ticket
  "Buy a ticket for the specified buyer name"
  [buyer]
  (when-let [[ticket-number _] (pick-ticket)]
    (dosync 
     (swap! *lottery* update-in [:register] + (:ticket-price @*lottery*))
     (swap! *lottery* update-in [:tickets] assoc ticket-number buyer))
    ticket-number))

(defn draw-winners
  "Draw the winners and calculate the prizes"
  []
  (apply hash-map
	 (mapcat (fn [number ticket prize]
		   (list number {:ticket ticket
				 :amount (* (:register @*lottery*) prize)}))
		 (iterate inc 1) (shuffle-tickets) (:prizes @*lottery*))))

