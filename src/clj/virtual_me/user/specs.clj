(ns virtual-me.user.specs
  (:require [clojure.spec.alpha :as spec]))

(spec/def ::user-name-type (spec/and string? #(re-matches #"^[^\s]+$" %)))

(spec/def ::user-id uuid?)
(spec/def ::user-name ::user-name-type)
(spec/def ::user (spec/keys :req [::user-id
                                  ::user-name]))
