package com.cbp.orderbook;

import com.cbp.main.OrderbookApplication;
import com.cbp.message.response.Snapshot;
import com.cbp.websocket.WebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.TextMessage;

import java.util.List;

import static com.cbp.orderbook.TestHelper.create_l2updates;
import static com.cbp.orderbook.TestHelper.create_snapshot;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderbookApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderBookSingleSidedTest {

    @Autowired
    OrderBookSingleSided askOrderBook;

    @Autowired
    OrderBookSingleSided bidOrderBook;

    @Autowired
    WebSocketHandler webSocketHandler;

    @Autowired
    ObjectMapper mapper;

    @Value("${orderbook.partition.size}")
    int partitionSize;

    @Value("${subscribe.productId}")
    String productId;

    Snapshot snapshot;

    final int NUMBER_OF_BIDS_IN_L2UPDATE = 22248;
    final int NUMBER_OF_ASKS_IN_L2UPDATE = 10970;

    @Test
    public void initialize_initializesAskOrderBook_succeeds() throws Exception {
        run_snapshot("snapshot");
        int snapshot_askSize = snapshot.getAsks().length;
        assertEquals(askOrderBook.getPartition1().getSize(), partitionSize);
        assertEquals(askOrderBook.getPartition2().getSize(), snapshot_askSize - partitionSize);
        int i = 0;
        for (OrderBookLevel level : askOrderBook.getPartition1().getOrderBook()) {
            if (i == partitionSize) break;
            String[] ask = snapshot.getAsks()[i++];
            assertEquals(ask[0], level.getPrice());
            assertEquals(ask[1], level.getSize());
        }
        for (OrderBookLevel level : askOrderBook.getPartition2().getOrderBook()) {
            String[] ask = snapshot.getAsks()[i++];
            assertEquals(ask[0], level.getPrice());
            assertEquals(ask[1], level.getSize());
        }
    }

    @Test
    public void initialize_initializesBidOrderBook_succeeds() throws Exception {
        run_snapshot("snapshot");
        int snapshot_askSize = snapshot.getBids().length;
        assertEquals(bidOrderBook.getPartition1().getSize(), partitionSize);
        assertEquals(bidOrderBook.getPartition2().getSize(), snapshot_askSize - partitionSize);
        int i = 0;
        for (OrderBookLevel level : bidOrderBook.getPartition1().getOrderBook()) {
            if (i == partitionSize) break;
            String[] bid = snapshot.getBids()[i++];
            assertEquals(bid[0], level.getPrice());
            assertEquals(bid[1], level.getSize());
        }
        for (OrderBookLevel level : bidOrderBook.getPartition2().getOrderBook()) {
            String[] bid = snapshot.getBids()[i++];
            assertEquals(bid[0], level.getPrice());
            assertEquals(bid[1], level.getSize());
        }
    }

    @Test
    public void l2update_askOrderBookIsAscending_succeeds() throws Exception {
        run_snapshot("snapshot");
        run_l2updates("l2updates");

        OrderBookPartition partition1 = askOrderBook.getPartition1();
        OrderBookPartition partition2 = askOrderBook.getPartition2();

        assertEquals(partition1.getSize(), partitionSize);
        assertEquals(partition2.getSize(), NUMBER_OF_ASKS_IN_L2UPDATE);
        assertEquals(partition1.getFirst(), new OrderBookLevel("58828.41", "0.12075801"));
        assertEquals(partition1.getLast(), new OrderBookLevel("58836.02", "0.05000000"));
        assertEquals(partition2.getFirst(), new OrderBookLevel("58836.32", "0.08498152"));
        assertEquals(partition2.getLast(), new OrderBookLevel("9999999999.00", "0.00010000"));

        OrderBookLevel prevLevel = partition1.getOrderBook().pollFirst();
        for (OrderBookLevel level : partition1.getOrderBook()) {
            assertTrue(prevLevel.getPriceInDouble() < level.getPriceInDouble());
            prevLevel = level;
        }
        for (OrderBookLevel level : partition2.getOrderBook()) {
            assertTrue(prevLevel.getPriceInDouble() < level.getPriceInDouble());
            prevLevel = level;
        }
    }

    @Test
    public void l2update_bidOrderBookIsAscending_succeeds() throws Exception {
        run_snapshot("snapshot");
        run_l2updates("l2updates");

        OrderBookPartition partition1 = bidOrderBook.getPartition1();
        OrderBookPartition partition2 = bidOrderBook.getPartition2();

        assertEquals(partition1.getSize(), partitionSize);
        assertEquals(partition2.getSize(), NUMBER_OF_BIDS_IN_L2UPDATE);
        assertEquals(partition1.getFirst(), new OrderBookLevel("58828.40", "0.01279000"));
        assertEquals(partition1.getLast(), new OrderBookLevel("58820.01", "0.24995472"));
        assertEquals(partition2.getFirst(), new OrderBookLevel("58820.00", "0.06500000"));
        assertEquals(partition2.getLast(), new OrderBookLevel("0.01", "1642862.03752186"));

        OrderBookLevel prevLevel = partition1.getOrderBook().pollFirst();
        for (OrderBookLevel level : partition1.getOrderBook()) {
            assertTrue(prevLevel.getPriceInDouble() > level.getPriceInDouble());
            prevLevel = level;
        }
        for (OrderBookLevel level : partition2.getOrderBook()) {
            assertTrue(prevLevel.getPriceInDouble() > level.getPriceInDouble());
            prevLevel = level;
        }
    }

    @Test(expected = IllegalStateException.class)
    public void l2update_updateTimeIsOutOfSync_throwsException() throws Exception {
        run_snapshot("snapshot");
        run_l2updates("l2updates_outOfSync");
    }

    private void run_snapshot(String fileName) throws Exception {
        String snapshotMessage = create_snapshot(fileName);
        snapshot = mapper.readValue(snapshotMessage, Snapshot.class);
        webSocketHandler.handleTextMessage(null, new TextMessage(snapshotMessage));
    }

    private void run_l2updates(String fileName) throws Exception {
        List<String> l2updates = create_l2updates(fileName);
        for (String l2update : l2updates) {
            webSocketHandler.handleTextMessage(null, new TextMessage(l2update));
        }
    }
}
