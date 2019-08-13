(ns virtual-me.user.repository.inmemory
  (:require [virtual-me.user.core :as user]
            [virtual-me.user.specs :as uspec]))

(defrecord InMemoryUserStore [store]
  user/UserStore
  (user/new-user [user-store user-name]
    (if (nil? (user/query-by-user-name user-store user-name))
      (let [user (user/create-new-user user-name)
            user-id (::uspec/user-id user)]
        (swap! (:store user-store)
               (fn [store] (assoc store user-id user)))
        user)
      (throw (IllegalArgumentException. (str "User name: " user-name " already exists.")))))
  (user/query-by-user-name [user-store user-name]
    (let [matches (filter #(= (::uspec/user-name (second %)) user-name) @(:store user-store))]
      (second (first matches)))))

(defn init-inmemory-user-store
  ([] (init-inmemory-user-store (atom {})))
  ([store] (InMemoryUserStore. store)))
