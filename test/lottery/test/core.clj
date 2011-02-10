(ns lottery.test.core
  (:use [lottery.core] :reload)
  (:use [clojure.test]))

(deftest the-lottery  
  (testing "buy a ticket"
    (let [lottery-register (:register @*lottery*)
	  ticket-number (buy-ticket "buyer")]
      (is (and (>= ticket-number 1)
	       (<= ticket-number 50)))
      (is (= (:register @*lottery*)
	     (+ lottery-register 10)))))  
  (testing "draw winners"
    (let [winners (draw-winners)]
      (is (= (count winners) 3))
      (is (= (get-in winners [1 :amount]) 78.75))
      (is (= (get-in winners [2 :amount]) 15.75))
      (is (= (get-in winners [3 :amount]) 10.5)))))
