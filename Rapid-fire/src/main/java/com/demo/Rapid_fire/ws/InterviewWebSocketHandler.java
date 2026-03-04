package com.demo.Rapid_fire.ws;

import com.ai.interviewer.dto.SocketMessage;
import com.ai.interviewer.session.SessionManager;
import com.ai.interviewer.session.InterviewSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class InterviewWebSocketHandler extends BinaryWebSocketHandler {

    private final SessionManager sessionManager;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Map<String, WebSocketSession> socketSessions = new ConcurrentHashMap<>();


    /**
     * Called when WebSocket connection is established
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String sessionId = session.getId();

        socketSessions.put(sessionId, session);

        sessionManager.createSession(sessionId);

        sendMessage(session, new SocketMessage("READY", "Connection established"));

        System.out.println("WebSocket connected: " + sessionId);
    }


    /**
     * Handle text messages (commands)
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        SocketMessage socketMessage =
                objectMapper.readValue(message.getPayload(), SocketMessage.class);

        String command = socketMessage.getCommand();

        String sessionId = session.getId();

        InterviewSession interviewSession = sessionManager.getSession(sessionId);

        if (interviewSession == null) {
            return;
        }

        switch (command) {

            case "START_INTERVIEW":

                sendMessage(session,
                        new SocketMessage("INFO", "Interview started"));

                break;

            case "LONG_PAUSE":

                sendMessage(session,
                        new SocketMessage("INFO", "User stopped speaking"));

                break;

            default:

                sendMessage(session,
                        new SocketMessage("ERROR", "Unknown command"));

        }
    }


    /**
     * Handle binary messages (audio chunks)
     */
    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {

        byte[] audioChunk = message.getPayload().array();

        String sessionId = session.getId();

        System.out.println("Received audio chunk from session: " + sessionId
                + " size: " + audioChunk.length);

    }


    /**
     * Called when WebSocket closes
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {

        String sessionId = session.getId();

        sessionManager.removeSession(sessionId);

        socketSessions.remove(sessionId);

        System.out.println("WebSocket closed: " + sessionId);
    }


    /**
     * Helper method to send message
     */
    private void sendMessage(WebSocketSession session, SocketMessage message) throws IOException {

        String json = objectMapper.writeValueAsString(message);

        session.sendMessage(new TextMessage(json));
    }

}