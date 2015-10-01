(ns cljapplied.unit.shop
  (:require [clojure.test :refer [deftest]]
            [midje.sweet :refer :all]
            [cljapplied.value.money :refer :all]
            [cljapplied.shop :refer :all]
            [cljapplied.inventory :refer :all]))
(deftest create-line-items
  (fact "constructor for making line items with different quantities"
        (make-line-item book1 1) => (->LineItem 1 book1 (:price book1))
        (make-line-item book2 2) => (->LineItem 2 book2 (*$ (:price book2) 2))))