package com.cbp.orderbook;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class OrderBookPartitionTest {

    OrderBookPartition orderBookPartition;

    @Before
    public void setup() {
        orderBookPartition = new OrderBookPartition(Comparator.naturalOrder());
    }

    @Test
    public void partition_allFunctions_success() {
        assertEquals(orderBookPartition.getSize(), 0);
        OrderBookLevel level1 = new OrderBookLevel("58673.76", "0.03567375");
        OrderBookLevel level2 = new OrderBookLevel("58844.90", "0.40460000");
        OrderBookLevel level3 = new OrderBookLevel("58714.14", "0.00851583");
        OrderBookLevel level4 = new OrderBookLevel("58570.64", "0.01000000");
        OrderBookLevel level5 = new OrderBookLevel("58831.40", "0.15763862");
        orderBookPartition.add(level1);
        orderBookPartition.add(level2);
        orderBookPartition.add(level3);
        orderBookPartition.add(level4);
        orderBookPartition.add(level5);
        assertEquals(orderBookPartition.getSize(), 5);
        assertTrue(orderBookPartition.contains(level3.getPrice()));
        orderBookPartition.remove(level3);
        assertEquals(orderBookPartition.getSize(), 4);
        assertFalse(orderBookPartition.contains(level3.toString()));
        assertEquals(orderBookPartition.getFirst(), level4);
        assertEquals(orderBookPartition.pollFirst(), level4);
        assertEquals(orderBookPartition.getSize(), 3);
        assertEquals(orderBookPartition.getLast(), level2);
        assertEquals(orderBookPartition.pollLast(), level2);
        assertEquals(orderBookPartition.getSize(), 2);
        assertEquals(orderBookPartition.getComparator(), Comparator.naturalOrder());
    }
}
