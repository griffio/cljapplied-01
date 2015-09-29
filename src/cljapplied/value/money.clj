(ns cljapplied.value.money)

(declare validate-same-currency)                            ;; forward declaration

(defrecord Currency [divisor sym desc])

(defrecord Money [amount ^Currency currency]
  Comparable
  (compareTo [m1 m2]
    (validate-same-currency m1 m2)
    (compare (:amount m1) (:amount m2))))

(def market-currencies {:usd (->Currency 100 "USD" "US Dollar")
                        :eur (->Currency 100 "EUR" "Euro")
                        :gbp (->Currency 100 "GBP" "Pound Sterling")
                        :jyp (->Currency 100 "JPY" "Japanese Yen")
                        :kwd (->Currency 100 "KWD" "Kuwaiti Dinar)")})

(defn- validate-same-currency
  [m1 m2]
  (or (= (:currency m1) (:currency m2))
      (throw
        ex-info "Currencies are not equivalent."
        {:m1 m1 :m2 m2})))