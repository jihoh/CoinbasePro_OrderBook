package com.cbp.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;

@Configuration
public class WebSocketConfiguraton {

  @Bean
  WebSocketContainer webSocketContainerFactoryBean(@Value("${buffer.sizeInBytes}") int bufferSize) {
    WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
    webSocketContainer.setDefaultMaxTextMessageBufferSize(bufferSize);
    return webSocketContainer;
  }

  @Bean
  WebSocketClient webSocketClient(WebSocketContainer webSocketContainer) {
    return new StandardWebSocketClient(webSocketContainer);
  }

  @Bean
  @Profile("prod")
  WebSocketConnectionManager webSocketConnectionManager(
      WebSocketClient webSocketClient,
      WebSocketHandler webSocketHandler,
      @Value("${websocket.feed}") String URL) {
    WebSocketConnectionManager webSocketConnectionManager
            = new WebSocketConnectionManager(webSocketClient, webSocketHandler, URL);
    webSocketConnectionManager.start();
    return webSocketConnectionManager;
  }

  @Bean
  @Profile("test")
  WebSocketConnectionManager testWebSocketConnectionManager(
          WebSocketClient webSocketClient,
          WebSocketHandler webSocketHandler,
          @Value("${websocket.feed}") String URL) {
    return new WebSocketConnectionManager(webSocketClient, webSocketHandler, URL);
  }
}
