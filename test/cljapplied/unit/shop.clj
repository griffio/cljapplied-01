(ns cljapplied.unit.shop
  (:require [clojure.test :refer [deftest]]
            [midje.sweet :refer :all]
            [cljapplied.value.money :refer :all]
            [cljapplied.customers :refer :all]
            [cljapplied.shop :refer :all]
            [cljapplied.inventory :refer :all]))
(deftest create-shop
  (fact "make-line-item constructor for a line item with variable quantity"
        (make-line-item book1 1) => (->LineItem 1 book1 (:price book1))
        (make-line-item book2 2) => (->LineItem 2 book2 (*$ (:price book2) 2)))
  (fact "make-cart constructor"
        (make-cart cust1 (add-line-item new-line-items book1) true) => (contains {:customer cust1}))
  (fact "calculate cart revenue for the price of one book"
        (let [cart1 (make-cart cust1 (add-line-item new-line-items (make-line-item book1 1)) true)
              cart2 (make-cart cust1 (add-line-item new-line-items (make-line-item book1 1)) false)]
          (revenue-by-department [cart1 cart2]) => {(:dept book1) (:price book1)})))