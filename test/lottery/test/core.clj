(ns lottery.test.core
  (:use [lottery.core] :reload)
  (:use [clojure.contrib.math])
  (:use [clojure.test]))

(deftest tickets
  (let [bank (ref 200)
	tickets (ref nil)]
    
    (testing "create"
      (dosync (ref-set tickets (create-tickets 6)))      
      (is (= @tickets
	     [(lottery.core.ticket. 1 nil)
	      (lottery.core.ticket. 2 nil)
	      (lottery.core.ticket. 3 nil)
	      (lottery.core.ticket. 4 nil)
	      (lottery.core.ticket. 5 nil)
	      (lottery.core.ticket. 6 nil)]) "Creating 6 tickets should produce 6 ticket records"))
  
    (testing "buy specific"
      (buy-ticket bank tickets 2 "buyer name" 10)
      (is (and (= (@tickets 1) ;; Ticket #2 is item 1 in the list (zero based indexing)
		  (lottery.core.ticket. 2 "buyer name"))
	       (= @bank 210)) "The name of the buyer should be written in the ticket list and the bank amount should be updated"))

    (testing "available"
      (is (= (available-tickets tickets)
	     [(lottery.core.ticket. 1 nil)
	      (lottery.core.ticket. 3 nil)
	      (lottery.core.ticket. 4 nil)
	      (lottery.core.ticket. 5 nil)
	      (lottery.core.ticket. 6 nil)]) "Should return the tickets not yet purchased"))

    (testing "buy random"
      (buy-random-ticket bank tickets "buyer 1" 10)
      (buy-random-ticket bank tickets "buyer 2" 10)
      (buy-random-ticket bank tickets "buyer 3" 10)
      (buy-random-ticket bank tickets "buyer 4" 10)
      (buy-random-ticket bank tickets "buyer 5" 10)
      (is (= (available-tickets tickets)
	     []) "Should return an empty list after all the tickets have been purchased"))
    
    (testing "draw"
      (let [d1 (draw-tickets tickets 3)
	    d2 (draw-tickets tickets 3)]
	(is (and (not (= d1 d2))
		 (= (count @tickets) 0)) "Two different draws should not return the same tickets, and the ticket list should be empty after two draws")))))

(deftest lottery
  (let [bank (ref 200)
	tickets (ref (create-tickets 6))
	prizes [(fn [jackpot] (round (* jackpot 0.75)))
		(fn [jackpot] (round (* jackpot 0.15)))
		(fn [jackpot] (round (* jackpot 0.10)))]]
  
    (testing "jackpot"
      (is (= (jackpot @bank) 100) "The jackpot should be 50% the amount in the bank"))

    (testing "winners"
      (let [winners (get-winners tickets (jackpot @bank) prizes)]
	(is (= (count winners) 3) "There should be only 3 winner tickets")
	(is (= (:amount (winners 0))
	       ((prizes 0) (jackpot @bank))) "The first prize should have the correct amount")
	(is (= (:amount (winners 1))
	       ((prizes 1) (jackpot @bank))) "The second prize should have the correct amount")	
	(is (= (:amount (winners 2))
	       ((prizes 2) (jackpot @bank))) "The third prize should have the correct amount")))))
