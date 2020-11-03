(ns try-crux
  (:require
    [crux.api :as crux]
    [tick.alpha.api]))

(def crux-node (crux/start-node {}))

(comment
  (crux/await-tx
    crux-node
    (crux/submit-tx
      crux-node
      [[:crux.tx/put {:crux.db/id "hi" :my/duration #time/duration"PT10M"}]]))

  (crux/entity (crux/db crux-node) "hi"))
