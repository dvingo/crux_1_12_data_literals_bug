Repo with data readers bug happening since crux.core 1.11.
- change the crux version in deps and then eval the code in the source file in a REPL.

from the zulip chat:

https://juxt-oss.zulipchat.com/#narrow/stream/194466-crux/topic/data_readers.20support

```clojure

(crux/status crux-node)
=>
{:crux.version/version "20.07-1.10.0-beta",
 :crux.version/revision nil,
 :crux.kv/kv-store "crux.kv.rocksdb.RocksKv",
 :crux.kv/estimate-num-keys 23740,
 :crux.kv/size 75646713,
 :crux.index/index-version 12,
 :crux.doc-log/consumer-state nil,
 :crux.tx-log/consumer-state nil}

 (crux/await-tx
    crux-node
    (crux/submit-tx
      crux-node
      [[:crux.tx/put {:crux.db/id "hi" :my/duration #time/duration"PT10M"}]]))
=> #:crux.tx{:tx-id 470, :tx-time #inst"2020-11-03T01:52:02.703-00:00"}
(crux/entity (crux/db crux-node) "hi")
=> {:crux.db/id "hi", :my/duration #time/duration"PT10M"}

Using 1.12, I first saw this bug using a rocksdb + postgres setup
but I also verified it fails for an in-memory node as well:

(def crux-node (crux/start-node {}))

(crux/status crux-node)
=>
{:crux.version/version "20.09-1.12.1-beta",
 :crux.version/revision nil,
 :crux.kv/kv-store "crux.mem_kv.MemKv",
 :crux.kv/estimate-num-keys 14,
 :crux.kv/size 0,
 :crux.index/index-version 13,
 :crux.doc-log/consumer-state nil,
 :crux.tx-log/consumer-state nil}


(crux/await-tx
   crux-node
   (crux/submit-tx
     crux-node
     [[:crux.tx/put {:crux.db/id "hi" :my/duration #time/duration"PT10M"}]]))
=> #:crux.tx{:tx-id 0, :tx-time #inst"2020-11-03T02:01:09.036-00:00"}

(crux/entity (crux/db crux-node) "hi")
=> {:crux.db/id "hi", :my/duration (. java.time.Duration parse "PT10M")}
```
