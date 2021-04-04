package com.cbp.orderbook;

import com.cbp.message.response.L2update;
import com.cbp.message.response.Snapshot;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static com.cbp.orderbook.TestHelper.create_l2updates;
import static com.cbp.orderbook.TestHelper.create_snapshot;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class OrderBookDoubleSidedTest {

    @Mock
    private OrderBookSingleSided bidOrderBook;

    @Mock
    private OrderBookSingleSided askOrderBook;

    @InjectMocks
    OrderBookDoubleSided orderBook;

    ObjectMapper mapper = new ObjectMapper();

    final int NUMBER_OF_BIDS_IN_SNAPSHOT = 248;
    final int NUMBER_OF_ASKS_IN_SNAPSHOT = 407;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void initialize_initializesOrderBooks_succeeds() throws IOException {
        String message = create_snapshot("snapshot");
        Snapshot snapshot = mapper.readValue(message, Snapshot.class);
        doNothing().when(bidOrderBook).initialize(snapshot.getBids());
        doNothing().when(askOrderBook).initialize(snapshot.getAsks());
        orderBook.initialize(snapshot);
        verify(bidOrderBook, times(1)).initialize(snapshot.getBids());
        verify(askOrderBook, times(1)).initialize(snapshot.getAsks());
    }

    @Test
    public void update_l2updatesOrderBooks_succeeds() throws IOException {
        List<String> l2updates = create_l2updates("l2updates");
        doNothing().when(bidOrderBook).update(any());
        doNothing().when(askOrderBook).update(any());
        for (String l2update : l2updates) {
            orderBook.update(mapper.readValue(l2update, L2update.class));
        }
        verify(bidOrderBook, times(NUMBER_OF_BIDS_IN_SNAPSHOT)).update(any());
        verify(askOrderBook, times(NUMBER_OF_ASKS_IN_SNAPSHOT)).update(any());
    }
}
