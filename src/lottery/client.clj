(ns lottery.client
  (:require [lottery.pyxis :as lottery])
  (:require [clojure.string :as string]))

(defn buy
  "Buy a ticket for the specified buyer"
  [buyer]
  (let [result (lottery/buy buyer)]
    (println 
     (condp = result
	 :invalid-name "Please provide a valid name."
	 :lottery-closed "Tickets have already been drawn. The lottery is closed."
	 (str (:buyer result) ", thank you for buying a ticket. Your ticket number is "
	      (:number result) ".")))))

(defn draw
  "Draw the winner tickets"
  []
  (let [result (lottery/draw)]
    (println
     (condp = result
	 :lottery-closed "Tickets have already been drawn. The lottery is closed."
	 :draw-performed "Draw performed."))))

(defn- prepare-table
  "Take the winner results and put them into a matrix"
  [winners]
  (vector
   (vec (map #(let [number (get-in % [:ticket :number])]
		(str "Ticket #" number))
	     winners))
   (vec (map #(let [buyer (get-in % [:ticket :buyer])
		    amount (get-in % [:amount])]
		(str (if (empty? buyer) "Not purchased" buyer) " : $" amount))
	     winners))))

(defn- show-table
  "Take a matrix and display it as a table with ascii characters"
  [table]
  (let [format (fn [row] (str \| (string/join \| row) \| \newline))
	fill (fn [string width filler]
	       (apply str (conj (repeat (- width (.length (str string))) filler)
				string)))
	col-count (count (first table))
	col-width (map-indexed #(list %1 (apply max %2))
			       (for [i (range col-count)]
				 (map #(.length (str (nth % i))) table)))	
	sep-row (map (fn [[idx width]]
		       (fill "" width  \-)) col-width)
	data-row (for [t table]
		   (map (fn [[idx width]]
			  (fill (t idx) width  \space)) col-width))]
    (apply str
	   (if-not (empty? table)
	     (format sep-row))
	   (apply str 
		  (for [data-rows (interpose sep-row data-row)]
		    (format data-rows)))
	   (if-not (empty? table)
	     (format sep-row)))))

(defn winners
  "Return the winners from the last draw"
  []
  (let [result (lottery/winners)]
    (println
     (condp = result
	 :draw-required "You need to perform a draw in order to see the winners."
	 (show-table (prepare-table result))))))

