(ns virtual-me.web.routes
  (:require [compojure.api.sweet :refer :all]
            [compojure.route :as route]
            [taoensso.sente :as sente]
            [taoensso.sente.server-adapters.http-kit :refer (get-sch-adapter)]
            [virtual-me.bot.core :as bot]
            [ring.util.http-response :refer :all]
            [ring.middleware.reload :as reload]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults api-defaults]]
            [virtual-me.web.views :refer :all]
            [clojure.core.async :as async  :refer [<! <!! >! >!! put! chan go go-loop]]))


(let [{:keys [ch-recv send-fn connected-uids
              ajax-post-fn ajax-get-or-ws-handshake-fn]}
      (sente/make-channel-socket! (get-sch-adapter) {})]

  (def ring-ajax-post ajax-post-fn)
  (def ring-ajax-get-or-ws-handshake ajax-get-or-ws-handshake-fn)
  (def ch-chsk ch-recv)                                     ; ChannelSocket's receive channel
  (def chsk-send! send-fn)                                  ; ChannelSocket's send API fn
  (def connected-uids connected-uids))                      ; Watchable, read-only atom

(defroutes main-routes
  (GET "/" [] (index-page))
  (route/resources "/")
  (route/not-found "Page not found"))

(defapi api-routes
  (swagger-routes {:ui   "/api-docs"
                   :spec "/swagger.json"
                   :data {:info {:version     "G.A"
                                 :title       "Virtual Me API"
                                 :description "API to use the Virtual Me Chatbot services."
                                 :contact     {:name "Hugo Valk"
                                               :url  "https://github.com/hugovalk/virtual-me"}}
                          :tags [{:name "maintenance" :description "Endpoints used for system maintenance."}
                                 {:name "messages" :description "Endpoints for exchanging messages with the chatbot."}
                                 {:name "sessions" :description "Endpoints for managing chat sessions."}]}})
  (GET "/chsk" req (ring-ajax-get-or-ws-handshake req))
  (POST "/chsk" req (ring-ajax-post req))
  (context "/api" []
    (GET "/ping/:echo" [echo]
      :tags ["maintenance"]
      (ok {:echo echo}))
    (context "/bot" []
      (GET "/name" []
        (ok {:name bot/bot-name}))
      (POST "/messages" [ms]
        :tags ["messages"]
        (ok {:messages (bot/respond ms)})))))

(defn app
  ([] (app false))
  ([is-dev?]
   (let [handler (routes
                   (wrap-defaults api-routes site-defaults)
                   (wrap-defaults main-routes site-defaults))]
     (if is-dev?
       (reload/wrap-reload handler)
       handler))))

(def app-figwheel (app true))

(defn start-example-broadcaster!
  "As an example of server>user async pushes, setup a loop to broadcast an
  event to all connected users every 10 seconds"
  []
  (let [broadcast!
        (fn [i]
          (let [uids (:any @connected-uids)]
            (println "Broadcasting server>user: %s uids" (count uids))
            (doseq [uid uids]
              (chsk-send! uid
                          [:some/broadcast
                           {:what-is-this "An async broadcast pushed from server"
                            :how-often "Every 10 seconds"
                            :to-whom uid
                            :i i}]))))]

    (go-loop [i 0]
             (<! (async/timeout 10000))
             (broadcast! i)
             (recur (inc i)))))

;(start-example-broadcaster!)