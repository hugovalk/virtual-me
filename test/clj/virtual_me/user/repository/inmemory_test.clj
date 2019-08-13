(ns virtual-me.user.repository.inmemory-test
  (:use midje.sweet
        virtual-me.user.repository.inmemory)
  (:require [virtual-me.user.core :as user]
            [virtual-me.user.specs :as uspec]))

(facts "The InMemoryUserstore"
       (fact "Can create and store a new user"
             (let [store (atom {})
                   repo (init-inmemory-user-store store)
                   user (user/new-user repo "myusername")
                   user-id (::uspec/user-id user)]
               (get @store user-id) => {::uspec/user-id user-id
                                        ::uspec/user-name "myusername"}))
       (fact "Can retrieve a user by user name"
             (let [user-name "myusername"
                   repo (init-inmemory-user-store)
                   user-id (::uspec/user-id (user/new-user repo user-name))]
               (user/query-by-user-name repo user-name) => {::uspec/user-id user-id
                                                       ::uspec/user-name user-name}))
       (fact "Query by non-existing username returns nil"
             (let [repo (init-inmemory-user-store)]
               (user/query-by-user-name repo "nonexisting") => nil))
       (fact "Cannot create two users with the same user name"
             (let [user-name "myusername"
                   repo (init-inmemory-user-store)
                   user1 (user/new-user repo user-name)]
               (user/new-user repo user-name) => (throws IllegalArgumentException "User name: myusername already exists."))))
