(ns virtual-me.user.core-test
  (:use midje.sweet
        virtual-me.user.core)
  (:require [virtual-me.user.specs :as uspec]
            [clojure.spec.alpha :as spec]))

(fact-group "create-new-user"
       (fact "Can create a new valid user from a user name"
             (let [user (create-new-user "ausername")]
               (spec/valid? ::uspec/user user) => true)))
