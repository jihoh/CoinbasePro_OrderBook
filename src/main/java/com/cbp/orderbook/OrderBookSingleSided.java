package com.cbp.orderbook;

import com.cbp.message.response.L2update;

import java.util.Comparator;
import java.util.Date;

import static com.cbp.constants.Fields.Side;

public class OrderBookSingleSided {

    private final OrderBookPartition partition1;
    private final OrderBookPartition partition2;
    private final Side side;
    private final Comparator<OrderBookLevel> comparator;
    private final int partitionSize;
    private Date prevUpdateTime = new Date(0);
    private int orderbook_size = 0;

    public OrderBookSingleSided(Side side,
                                OrderBookPartition partition1,
                                OrderBookPartition partition2,
                                int partitionSize) {
        this.side = side;
        this.partition1 = partition1;
        this.partition2 = partition2;
        this.partitionSize = partitionSize;
        this.comparator = partition2.getComparator();
    }

    public OrderBookPartition getPartition1() {
        return partition1;
    }

    public OrderBookPartition getPartition2() {
        return partition2;
    }

    public void initialize(String[][] limits) {
        int i = 0;
        for (String[] limit : limits) {
            OrderBookLevel orderBookLevel = new OrderBookLevel(limit[0], limit[1]);
            if (i++ < partitionSize) {
                partition1.add(orderBookLevel);
            } else {
                partition2.add(orderBookLevel);
            }
            orderbook_size++;
        }
    }

    public void update(L2update l2update) {
        OrderBookValidator.validateL2updateTimeSequence(l2update, prevUpdateTime);
        OrderBookValidator.validateOrderBookState(partition1, partition2, comparator, orderbook_size, partitionSize);

        String[][] changes = l2update.getChanges();

        for (String[] change : changes) {
            String price = change[1];
            String size = change[2];
            if (Double.parseDouble(size) == 0) {
                removeOrderLevel(price);
            } else if (partition1.contains(price) || partition2.contains(price)) {
                updateOrderLevel(price, size);
            } else {
                addOrderLevel(price, size);
            }
        }
        prevUpdateTime = l2update.getTime();
    }

    private void removeOrderLevel(String price) {
        if (partition1.contains(price)) {
            OrderBookLevel orderBookLevel = partition1.getOrderBookLevel(price);
            partition1.remove(orderBookLevel);
            partition1.add(partition2.pollFirst());
            partition1.print(side);
        } else {
            OrderBookLevel orderBookLevel = partition2.getOrderBookLevel(price);
            partition2.remove(orderBookLevel);
        }
        orderbook_size--;
    }

    private void updateOrderLevel(String price, String size) {
        if (partition1.contains(price)) {
            OrderBookLevel orderBookLevel = partition1.getOrderBookLevel(price);
            orderBookLevel.setSize(size);
            partition1.print(side);
        } else {
            OrderBookLevel orderBookLevel = partition2.getOrderBookLevel(price);
            orderBookLevel.setSize(size);
        }
    }

    private void addOrderLevel(String price, String size) {
        OrderBookLevel orderBookLevel = new OrderBookLevel(price, size);
        OrderBookLevel last = partition1.getLast();
        if (comparator.compare(last, orderBookLevel) >= 0) {
            partition2.add(partition1.pollLast());
            partition1.add(orderBookLevel);
            partition1.print(side);
        } else {
            partition2.add(orderBookLevel);
        }
        orderbook_size++;
    }
}
