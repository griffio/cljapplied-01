(ns cljapplied.shop
  (:require [cljapplied.value.money :refer [make-money +$ *$]])
  (:import (cljapplied.value.money Money)
           (java.util UUID)))

(defrecord CatalogItem [number dept desc ^Money price])
(defrecord Cart [number customer line-items settled?])
(defrecord LineItem [quantity catalog-item ^Money price])
(defrecord Customer [cname email membership-number])

(defn make-line-item
  "Given a catalog-item and quantity make a line item record"
  [catalog-item quantity]
  (->LineItem quantity catalog-item (*$ (:price catalog-item) quantity)))

(defn make-catalog-item
  "catalog product"
  [dept desc ^Money price] (->CatalogItem (UUID/randomUUID) dept desc price))

(def departments [:books :clothing :electronics :food :toys])

(defn- dept-total
  "map selects the :total from each element, then sums with +$ to produce department totals"
  [m k v]
  (assoc m k (->> (map :total v)
                  (reduce +$))))

(defn- line-summary
  "Given a LineItem with a CatalogItem, returns a map containing the CatalogItem's :dept as :dept and LineItem's :price
  as :total"
  [line-item]
  {:dept  (get-in line-item [:catalog-item :dept])
   :total (:price line-item)})

(defn revenue-by-department [all-carts]
  (->> (filter :settled? all-carts)
       (mapcat :line-items)
       (map line-summary)
       (group-by :dept)
       (reduce-kv 0 dept-total)))