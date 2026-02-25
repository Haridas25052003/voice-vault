package com.example.demo.config;

import com.example.demo.handler.InterviewWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
            .addHandler(interviewHandler(), "/interview-ws")
            .setAllowedOrigins("*");
    }

    @Bean
    public InterviewWebSocketHandler interviewHandler() {
        return new InterviewWebSocketHandler();
    }

    // ✅ ADD THIS NEW BEAN — This increases the buffer size
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();

        // Set buffer to 5MB (5 * 1024 * 1024 = 5,242,880 bytes)
        container.setMaxBinaryMessageBufferSize(5 * 1024 * 1024);  // 5 MB
        container.setMaxTextMessageBufferSize(5 * 1024 * 1024);    // 5 MB

        // Optional: keep connection alive longer (in milliseconds)
        container.setMaxSessionIdleTimeout(300000L); // 5 minutes

        return container;
    }
}