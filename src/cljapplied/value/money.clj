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
        (ex-info "Currencies are not equivalent."
                 {:m1 m1 :m2 m2}))))

(defn =$
  "test equivalence currency amount required on all money arguments, otherwise exception"
  ([_] true)
  ([m1 m2] (zero? (.compareTo m1 m2)))
  ([m1 m2 & mn]
   (every? zero? (map #(.compareTo m1 %) (conj mn m2)))))

(defn +$
  "sums all equivalent money arguments of same currency, otherwise exception"
  ([m1] m1)
  ([m1 m2]
   (validate-same-currency m1 m2)
   (->Money (+ (:amount m1) (:amount m2)) (:currency m1)))
  ([m1 m2 & mn]
   (reduce +$ m1 (conj mn m2))))

(defn *$
  "multiply money by amount"
  [money amount] (->Money (* amount (:amount money)) (:currency money)))

(def zero-dollar (->Money 0 (:usd market-currencies)))
(def zero-pound (->Money 0 (:gbp market-currencies)))
(def zero-euro (->Money 0 (:eur market-currencies)))
(def zero-yen (->Money 0 (:jyp market-currencies)))
(def zero-dinar (->Money 0 (:kwd market-currencies)))

(defn make-money
  "constructor for amount and currency, defaults to 0 usd"
  ([] zero-dollar)
  ([amount] (make-money amount (:usd market-currencies)))
  ([amount currency] (->Money amount currency)))