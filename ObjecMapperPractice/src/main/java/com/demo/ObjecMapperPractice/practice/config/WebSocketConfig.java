package com.demo.ObjecMapperPractice.practice.config;

import com.demo.ObjecMapperPractice.practice.handler.InterviewWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final InterviewWebSocketHandler handler;

    public WebSocketConfig(InterviewWebSocketHandler handler) {
        this.handler = handler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(handler, "/interview")
                .setAllowedOrigins("*");
    }
}