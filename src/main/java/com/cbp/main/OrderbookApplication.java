package com.cbp.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.cbp.websocket", "com.cbp.orderbook", "com.cbp.message"})
@ComponentScan
public class OrderbookApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderbookApplication.class, args);
    }
}
