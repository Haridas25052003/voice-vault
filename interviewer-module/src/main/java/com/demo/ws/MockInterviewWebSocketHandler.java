package com.demo.ws;

import com.ai.interviewer.enums.InterviewCommand;
import com.ai.interviewer.service.InterviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class MockInterviewWebSocketHandler extends BinaryWebSocketHandler {

    private final InterviewService interviewService;

    // Store active browser sessions
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    /**
     * When WebSocket connection is established
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String sessionId = session.getId();
        sessions.put(sessionId, session);

        log.info("WebSocket connected: {}", sessionId);

        // Initialize interview session
        interviewService.initializeSession(sessionId);

        // Send READY message to browser
        sendTextMessage(sessionId, "READY: Interview initialized");
    }

    /**
     * Handle audio chunks (binary messages)
     */
    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {

        String sessionId = session.getId();

        byte[] audioData = message.getPayload().array();

        log.info("Received audio chunk from session {}", sessionId);

        interviewService.processAudioChunk(sessionId, audioData);
    }

    /**
     * Handle text commands from browser
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {

        String sessionId = session.getId();
        String payload = message.getPayload();

        log.info("Received text message: {} from {}", payload, sessionId);

        switch (payload) {

            case "START_INTERVIEW":
                String firstQuestion = interviewService.startInterview(sessionId);
                sendTextMessage(sessionId, firstQuestion);
                break;

            case "ANSWER_COMPLETE":
                String nextQuestion = interviewService.moveToNextQuestion(sessionId);
                sendTextMessage(sessionId, nextQuestion);
                break;

            default:
                sendTextMessage(sessionId, "ERROR: Unknown command");
        }
    }

    /**
     * When WebSocket connection closes
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {

        String sessionId = session.getId();
        sessions.remove(sessionId);

        interviewService.removeSession(sessionId);

        log.info("WebSocket closed: {}", sessionId);
    }

    /**
     * Helper method to send text message
     */
    private void sendTextMessage(String sessionId, String message) {

        WebSocketSession session = sessions.get(sessionId);

        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                log.error("Error sending message to {}", sessionId, e);
            }
        }
    }
}