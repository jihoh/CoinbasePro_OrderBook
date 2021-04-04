package com.cbp.orderbook;

import com.cbp.message.response.L2update;
import com.cbp.message.response.Snapshot;
import org.springframework.stereotype.Component;

import static com.cbp.constants.Fields.Side.sell;
import static com.cbp.constants.Fields.Side.buy;
import static com.cbp.constants.Fields.Side;

@Component
public class OrderBookDoubleSided {

    private OrderBookSingleSided bidOrderBook;

    private OrderBookSingleSided askOrderBook;

    public OrderBookDoubleSided(OrderBookSingleSided bidOrderBook,
                                OrderBookSingleSided askOrderBook) {
        this.bidOrderBook = bidOrderBook;
        this.askOrderBook = askOrderBook;
    }

    public void initialize(Snapshot snapshot) {
        bidOrderBook.initialize(snapshot.getBids());
        askOrderBook.initialize(snapshot.getAsks());
    }

    public void update(L2update l2update) {
        String[][] changes = l2update.getChanges();
        for (String[] change : changes) {
            String side = change[0];
            if (Side.valueOf(side) == sell) {
                askOrderBook.update(l2update);
            } else if(Side.valueOf(side) == buy){
                bidOrderBook.update(l2update);
            } else {
                throw new IllegalArgumentException("Invalid Side L2update");
            }
        }
    }
}
