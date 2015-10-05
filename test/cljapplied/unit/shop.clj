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
  (fact "calculate revenue by department for the provided customer shopping carts"
        (let [cart1 (make-cart cust1 (add-line-item new-line-items (make-line-item book1 1)) true)
              cart2 (make-cart cust1 (add-line-item new-line-items (make-line-item book1 1)) false)
              cart3 (make-cart cust1 (add-line-item new-line-items (make-line-item toys1 2)) true)
              cart4 (make-cart cust1 (add-line-item new-line-items (make-line-item toys1 1)) true)]
          (revenue-by-department [cart1 cart2]) => {(:dept book1) (:price book1)}
          (revenue-by-department [cart1 cart2 cart3 cart4]) => {(:dept book1) (:price book1) (:dept toys1) (*$ (:price toys1) 3)}))
  (fact "use shopping-items add 2 line items into a cart"
        (let [line1 (make-line-item book1 1) line2 (make-line-item book2 1) shopping (shopping-items [line1 line2])]
              (make-cart cust2 shopping false) => (contains (:line-items {line1 line2})))))