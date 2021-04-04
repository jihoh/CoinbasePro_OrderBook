package com.cbp.message;

import com.cbp.constants.Fields;
import com.cbp.message.request.Subscribe;
import com.cbp.message.request.Unsubscribe;
import com.cbp.message.response.Heartbeat;
import com.cbp.message.response.L2update;
import com.cbp.message.response.Snapshot;
import com.cbp.message.response.Subscriptions;
import com.cbp.orderbook.OrderBookDoubleSided;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

import static com.cbp.constants.Fields.RequestType;
import static com.cbp.constants.Fields.ResponseType;
import static com.cbp.constants.Fields.Type.type;

@Component
public class MessageHandler {

    private final ApplicationContext applicationContext;
    private final ObjectMapper objectMapper;
    private final ObjectReader objectReader;
    private final OrderBookDoubleSided orderBook;
    Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    public MessageHandler(ObjectMapper objectMapper,
                          OrderBookDoubleSided orderBook,
                          ApplicationContext applicationContext) {
        this.objectReader = objectMapper.readerFor(new TypeReference<Map<String, Object>>() {});
        this.objectMapper = objectMapper;
        this.orderBook = orderBook;
        this.applicationContext = applicationContext;
    }

    public void process(String message) throws Exception {
        Map<String, Object> map = objectReader.readValue(message);
        ResponseType responseType = Fields.ResponseType.valueOf((String) map.get(type.name()));
        switch (responseType) {
            case snapshot:
                Snapshot snapshot = objectMapper.readValue(message, Snapshot.class);
                orderBook.initialize(snapshot);
                break;
            case l2update:
                L2update l2update = objectMapper.readValue(message, L2update.class);
                orderBook.update(l2update);
                break;
            case subscriptions:
                Subscriptions subscriptions = objectMapper.readValue(message, Subscriptions.class);
                logger.info("Received subscriptions: " + subscriptions.toString());
                break;
            case heartbeat:
                Heartbeat heartbeat = objectMapper.readValue(message, Heartbeat.class);
                break;
            case error:
                Error error = objectMapper.readValue(message, Error.class);
                logger.error("Received error: " + error.toString());
                break;
            default:
                throw new IllegalArgumentException("Received invalid type: " + message);
        }
    }

    public String send(RequestType requestType, WebSocketSession session) throws Exception {
        String message;
        switch (requestType) {
            case subscribe:
                Subscribe subscribe = applicationContext.getBean(Subscribe.class);
                message = objectMapper.writeValueAsString(subscribe);
                break;
            case unsubscribe:
                Unsubscribe unsubscribe = applicationContext.getBean(Unsubscribe.class);
                message = objectMapper.writeValueAsString(unsubscribe);
                break;
            default:
                throw new Exception("Unsupported request type: " + requestType.name());
        }
        session.sendMessage(new TextMessage(message));
        return message;
    }
}
