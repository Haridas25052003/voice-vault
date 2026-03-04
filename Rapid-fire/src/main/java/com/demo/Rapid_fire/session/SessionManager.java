package com.demo.Rapid_fire.session;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private final Map<String, InterviewSession> sessions = new ConcurrentHashMap<>();

    public InterviewSession createSession(String sessionId) {

        InterviewSession session = new InterviewSession(sessionId);

        sessions.put(sessionId, session);

        return session;
    }

    public InterviewSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

}