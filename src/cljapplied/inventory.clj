(ns cljapplied.inventory
  (:require [cljapplied.value.money :refer [make-money]]
            [cljapplied.shop :refer [make-catalog-item]]))

(def book1 (make-catalog-item :books "Cheap Book" (make-money 1.99)))
(def book2 (make-catalog-item :books "Expensive Book" (make-money 29.99)))
(def clothing1 (make-catalog-item :clothing "Jacket" (make-money 44.99)))
(def clothing2 (make-catalog-item :clothing "Shirt" (make-money 12.99)))
(def clothing3 (make-catalog-item :clothing "Tie" (make-money 3.99)))
(def electronics1 (make-catalog-item :electronics "Shaver" (make-money 33.99)))
(def electronics2 (make-catalog-item :electronics "Television" (make-money 199.99)))
(def electronics3 (make-catalog-item :electronics "Blu-Ray Player" (make-money 69.99)))
(def food1 (make-catalog-item :food "Apple" (make-money 0.25)))
(def food2 (make-catalog-item :food "Orange" (make-money 0.30)))
(def food3 (make-catalog-item :food "Rhubarb" (make-money 0.57)))
(def food4 (make-catalog-item :food "Salad" (make-money 1.15)))
(def food5 (make-catalog-item :food "Sausages" (make-money 2.49)))
(def toys1 (make-catalog-item :toys "Furbies" (make-money 9.99)))
(def toys2 (make-catalog-item :toys "Minions" (make-money 15.99)))