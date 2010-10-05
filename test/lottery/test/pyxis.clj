(ns lottery.test.pyxis
  (:use [lottery.pyxis] :reload)
  (:use [clojure.test]))

(deftest pyxis-lottery
  (testing "global state"
    (is (= @*bank* 200))
    (is (= (count @*tickets*) 50))
    (is (= *ticket-price* 10))
    (is (empty? @*winners*))
    (is (= (count *prizes*) 3))
    (is (= ((*prizes* 0) 100) 75))
    (is (= ((*prizes* 1) 100) 15))
    (is (= ((*prizes* 2) 100) 10)))

  (testing "no winners before draw"
    (is (= :draw-required (winners))))

  (testing "buying tickets"
    (is (= :invalid-name (buy 10)))
    (is (= :invalid-name (buy nil)))
    (is (= :invalid-name (buy "")))
    (let [ticket (buy "valid name")]
      (is (= (:buyer ticket) "valid name"))))

  (testing "draw"
    (is (= :draw-performed (draw))))

  (testing "no tickets after a draw"
    (is (= :lottery-closed (buy "valid name"))))

  (testing "no draws after a draw"
    (is (= :lottery-closed (draw))))

  (testing "3 winners after draw"
    (let [winners (winners)]
      (is (= (count winners) 3)))))
