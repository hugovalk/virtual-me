(ns virtual-me.user.core
  (:require [virtual-me.user.specs :as uspec])
  (:import (java.util UUID)))

(defprotocol UserStore
  (new-user [user-store user-name])
  (query-by-user-name [user-store user-name]))

(defn create-new-user [user-name]
  {::uspec/user-id (UUID/randomUUID)
   ::uspec/user-name user-name})

