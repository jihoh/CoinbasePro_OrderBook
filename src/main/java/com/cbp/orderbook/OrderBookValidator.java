package com.cbp.orderbook;

import com.cbp.message.response.L2update;

import java.util.Comparator;
import java.util.Date;

import static com.google.common.base.Preconditions.checkState;

public class OrderBookValidator {

    public static void validateL2updateTimeSequence(L2update updateTime, Date prevUpdateTime) {
        checkState(updateTime.getTime().getTime() - prevUpdateTime.getTime() >= 0,
                "Order update is out of sync");
    }

    public static void validateOrderBookState(OrderBookPartition partition1,
                                              OrderBookPartition partition2,
                                              Comparator<OrderBookLevel> comparator,
                                              int orderBook_size,
                                              int partitionSize) {
        checkState(partition1.getSize() == partitionSize,
                "partition1 is in bad state - not equal to defined partition size");
        checkState(orderBook_size == partition2.getSize() + partition1.getSize(),
                "orderbook is in bad state");
        checkState(comparator.compare(partition1.getLast(), partition2.getFirst()) < 0,
                "orderbook is not sorted properly");
    }
}
