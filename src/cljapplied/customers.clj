(ns cljapplied.customers
  (:require [cljapplied.shop :refer (->Customer)]))

(def cust1 (->Customer "Rod" "rod@example.com" "A123456"))
(def cust2 (->Customer "Jane" "jane@example.com" "B123456"))
(def cust3 (->Customer "Freddy" "freddy@example.com" "C123456"))
