package com.cbp.websocket;

import com.cbp.message.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import static com.cbp.constants.Fields.RequestType.subscribe;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    private MessageHandler messageHandler;

    public WebSocketHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        messageHandler.process(message.getPayload());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Connection Established");
        messageHandler.send(subscribe, session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        logger.error("Transport Error", exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        logger.info("Connection Closed [" + status.getReason() + "]");
    }
}
