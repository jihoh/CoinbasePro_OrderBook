package com.cbp.orderbook;

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

import static com.cbp.constants.Fields.Side;

public class OrderBookPartition {

    private final TreeSet<OrderBookLevel> orderBook;

    private final HashMap<String, OrderBookLevel> map;

    private final Comparator<OrderBookLevel> comparator;

    public OrderBookPartition(Comparator<OrderBookLevel> comparator) {
        orderBook = new TreeSet<>(comparator);
        map = new HashMap<>();
        this.comparator = comparator;
    }

    public TreeSet<OrderBookLevel> getOrderBook() {
        return orderBook;
    }

    public Comparator<OrderBookLevel> getComparator() {
        return comparator;
    }

    public void add(OrderBookLevel orderBookLevel) {
        orderBook.add(orderBookLevel);
        map.put(orderBookLevel.getPrice(), orderBookLevel);
    }

    public void remove(OrderBookLevel orderBookLevel) {
        orderBook.remove(orderBookLevel);
        map.remove(orderBookLevel.getPrice());
    }

    public int getSize() {
        return orderBook.size();
    }

    public boolean contains(String price) {
        return map.containsKey(price);
    }

    public OrderBookLevel getFirst() {
        return orderBook.first();
    }

    public OrderBookLevel getLast() {
        return orderBook.last();
    }

    public OrderBookLevel getOrderBookLevel(String price) {
        return map.get(price);
    }

    public OrderBookLevel pollFirst() {
        if(orderBook.size() != 0) {
            OrderBookLevel orderBookLevel = orderBook.pollFirst();
            map.remove(orderBookLevel.getPrice());
            return orderBookLevel;
        } else {
            return null;
        }
    }

    public OrderBookLevel pollLast() {
        if(orderBook.size() != 0) {
            OrderBookLevel orderBookLevel = orderBook.pollLast();
            map.remove(orderBookLevel.getPrice());
            return orderBookLevel;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return orderBook.toString();
    }

    public void print(Side side) {
        System.out.printf("%s : %s %n", side.name(), this.toString());
    }
}
