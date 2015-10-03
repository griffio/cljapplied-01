(ns cljapplied.shop
  (:require [cljapplied.value.money :refer [make-money +$ *$]])
  (:import (cljapplied.value.money Money)
           (java.util UUID)
           (clojure.lang PersistentQueue)))

(defrecord Customer [cname email membership-number])
(defrecord CatalogItem [uuid dept desc ^Money price])
(defrecord LineItem [quantity catalog-item ^Money price])
(defrecord Cart [id-seq ^Customer customer line-items settled?])

(defonce cart-id-seq (atom 0))

(def new-line-items PersistentQueue/EMPTY)

(defn add-line-item [line-items line-item]
  (conj line-items line-item))

(defn make-cart
  [customer line-items settled?]
  (let [cart-id (swap! cart-id-seq inc)]
    (->Cart cart-id customer line-items settled?)))

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
       (reduce-kv dept-total {})))