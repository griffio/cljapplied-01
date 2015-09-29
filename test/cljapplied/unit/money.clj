(ns cljapplied.unit.money
  (:require [clojure.test :refer [deftest]]
            [midje.sweet :refer :all]
            [cljapplied.value.money :refer :all]))
(deftest two-money-facts-in-a-test
  (fact "For Money records, you get two constructor factory functions:- positional and map"
        (let [usd (->Money 100 (:usd market-currencies))]
          usd => (map->Money {:amount 100 :currency (:usd market-currencies)})
          usd =not=> (map->Money {:amount 100 :currency (:jyp market-currencies)}))))