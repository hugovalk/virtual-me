(ns virtual-me.user.specs-test
  (:use midje.sweet
        virtual-me.user.specs)
  (:require [virtual-me.user.specs :as uspec]
            [clojure.spec.alpha :as spec])
  (:import (java.util UUID)))

(facts "User specs guarantee validness of the user object"
       (fact "A user id is an UUID"
             (spec/valid? ::uspec/user-id (UUID/randomUUID)))
       (fact "A user name is a string"
             (spec/valid? ::uspec/user-name "ausername") => true)
       (fact "A user name cannot contain spaces"
             (spec/valid? ::uspec/user-name "a username with whitespace") => false)
       (fact "Every user must have a user-id and user-name"
             (spec/valid? ::uspec/user {::uspec/user-id (UUID/randomUUID)
                                        ::uspec/user-name "ausername"}) => true))
