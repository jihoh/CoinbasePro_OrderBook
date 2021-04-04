# Run instructions

Run the scipt `orderbook.sh` with instrument to subscribe as argument

*Example: BTC-USD orderbook*
```
./orderbook.sh BTC-USD
```
Ctrl+C to terminate application

# Data structure design

`OrderBookDoubleSided` - bid/ask orderdook
* `OrderBookSingleSided` - bid orderbook 
    *  `OrderBookPartition` - partition1
    *  `OrderBookPartition` - partition2
*  `OrderBookSingleSided` - ask orderbook
    *  `OrderBookPartition` - partition1
    *  `OrderBookPartition` - partition2

Each partition has a TreeMap and HashMap. TreeMap stores all the orders in sorted order. The time complexity is O(log(n)) for add, remove, and search. Hashmap is also used to store all the orders, but is able to search in O(1). Hence, overall complexity becomes O(log(n)) for add and remove, and O(1) for search. 

Each bid and ask orderbooks has two partitions. Partition1 is of size K that is equal to the number of levels closest to mid (in our case 10). Partition2 holds the remaining levels. The orderbook is printed when partition1 is changed, rather than for every update. From my analysis with K=10, when partition1 was hit 10,000 times, partition2 was hit 10,564 times. Hence, partitioning with K=10 reduces printing the orderbook by about half.

# Validations 
For every l2update, a validation is done to:
  1. validate l2update time is greater than previous update
  2. validate the state of the partition and orderbook
  3. validate missing heartbeats - *not implemented*

These validation failures indicate that the orderbook is in bad state. Currently, the failures stop the orderbook from processing further updates. Ideally, the process can be automatically restarted via a process manager, or the process can clear the orderbook and then unsubscribe and resubscribe to the snapshot, l2updates, and heartbeat. 