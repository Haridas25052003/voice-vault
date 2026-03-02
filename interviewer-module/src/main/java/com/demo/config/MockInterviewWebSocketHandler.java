package com.demo.config;

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
    private final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String sessionId = session.getId();
        sessionMap.put(sessionId, session);

        interviewService.initializeSession(sessionId);

        sendMessage(sessionId, "READY");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String sessionId = session.getId();
        String command = message.getPayload();

        switch (command) {
            case "START_INTERVIEW":
                sendMessage(sessionId,
                        interviewService.startInterview(sessionId));
                break;

            case "ANSWER_COMPLETE":
                String next = interviewService.nextQuestion(sessionId);
                sendMessage(sessionId, next);
                break;

            default:
                sendMessage(sessionId, "UNKNOWN_COMMAND");
        }
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        interviewService.processAudio(session.getId(),
                message.getPayload().array());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        interviewService.removeSession(session.getId());
        sessionMap.remove(session.getId());
    }

    private void sendMessage(String sessionId, String msg) {
        try {
            WebSocketSession session = sessionMap.get(sessionId);
            if (session != null && session.isOpen()) {
                session.sendMessage(new TextMessage(msg));
            }
        } catch (IOException e) {
            log.error("Error sending message", e);
        }
    }
}