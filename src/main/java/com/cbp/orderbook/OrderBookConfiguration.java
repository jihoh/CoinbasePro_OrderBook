package com.cbp.orderbook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.cbp.constants.Fields.Side;

import java.util.Comparator;

@Configuration
public class OrderBookConfiguration {

    @Bean
    OrderBookSingleSided askOrderBook(@Value("${orderbook.partition.size}") int k) {
        return new OrderBookSingleSided(
                Side.ask,
                new OrderBookPartition(Comparator.naturalOrder()),
                new OrderBookPartition(Comparator.naturalOrder()),
                k);
    }

    @Bean
    OrderBookSingleSided bidOrderBook(@Value("${orderbook.partition.size}") int k) {
        return new OrderBookSingleSided(
                Side.bid,
                new OrderBookPartition(Comparator.reverseOrder()),
                new OrderBookPartition(Comparator.reverseOrder()),
                k);
    }
}
