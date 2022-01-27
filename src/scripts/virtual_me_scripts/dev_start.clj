(ns virtual-me-scripts.dev-start
  (:require [virtual-me.httpkit :refer [-main]]
            [nrepl.server :refer [start-server]]
            [cider.nrepl :refer [cider-nrepl-handler]]))

(defonce server (start-server :port 5555 :handler cider-nrepl-handler))
(-main)
