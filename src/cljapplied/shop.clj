(ns cljapplied.shop
  (:require [cljapplied.value.money :refer [make-money +$ *S]])
  (:import (cljapplied.value.money Money)))

(defrecord CatalogItem [number dept desc ^Money price])
(defrecord Cart [number customer line-items settled?])
(defrecord LineItem [quantity catalog-item ^Money price])
(defrecord Customer [cname email membership-number])
