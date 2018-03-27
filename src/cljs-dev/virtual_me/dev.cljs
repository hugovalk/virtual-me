(ns virtual-me.dev
  (:require [virtual-me.js]
            [figwheel.client :as fw]))

(fw/start {:websocket-url "ws://localhost:3000/figwheel-ws"})
