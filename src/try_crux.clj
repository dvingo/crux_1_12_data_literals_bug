(ns try-crux
  (:require
    [crux.api :as crux]
    [tick.alpha.api]))

;; >= 1.12
;(def crux-node (crux/start-node {}))

;; < 1.12
(def crux-node (crux/start-node {:crux.node/topology '[crux.standalone/topology]}))

(comment
  (crux/status crux-node)
  (crux/await-tx
    crux-node
    (crux/submit-tx
      crux-node
      [[:crux.tx/put {:crux.db/id "hi" :my/duration #time/duration"PT10M"}]]))

  (crux/entity (crux/db crux-node) "hi"))
