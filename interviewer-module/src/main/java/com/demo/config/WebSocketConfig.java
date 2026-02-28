package com.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final com.ai.interviewer.ws.MockInterviewWebSocketHandler mockInterviewWebSocketHandler;

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
        registry.addHandler(mockInterviewWebSocketHandler,"/ws/interview")
                .setAllowedOrigins("*");
    }
}
