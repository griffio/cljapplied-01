(ns cljapplied.unit.money
  (:require [clojure.test :refer [deftest]]
            [midje.sweet :refer :all]
            [cljapplied.value.money :refer :all])
  (:import (clojure.lang ExceptionInfo)))
(deftest two-money-facts-in-a-test
  (fact "For Clojure records, you get two constructor factory functions:- positional and map; make-money is provided too"
        (let [usd100 (->Money 100 (:usd market-currencies))]
          usd100 => (map->Money {:amount 100 :currency (:usd market-currencies)})
          usd100 =not=> (map->Money {:amount 100 :currency (:jyp market-currencies)})
          usd100 => (make-money 100 (:usd market-currencies))))
  (fact "equal, addition and multiply operations"
        (let [gbp10 (->Money 10 (:gbp market-currencies)) kwd10 (->Money 10 (:kwd market-currencies))]
          (=$ gbp10) => true
          (=$ gbp10 gbp10) => true
          (=$ gbp10 gbp10 (->Money 99 (:gbp market-currencies))) => false
          (=$ gbp10 (->Money 10 (:gbp market-currencies))) => true
          (=$ gbp10 kwd10) => (throws ExceptionInfo)
          (+$ gbp10) => gbp10
          (+$ gbp10 gbp10) => (->Money 20 (:gbp market-currencies))
          (+$ gbp10 gbp10 gbp10) => (->Money 30 (:gbp market-currencies))
          (+$ gbp10 kwd10) => (throws ExceptionInfo)
          (*$ gbp10 10) => (->Money 100 (:gbp market-currencies)))))