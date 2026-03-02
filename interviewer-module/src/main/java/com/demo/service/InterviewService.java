package com.demo.service;

import com.ai.interviewer.model.InterviewQuestion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class InterviewService {

    private final Map<String, InterviewSession> sessions = new ConcurrentHashMap<>();

    public void initializeSession(String sessionId) {
        sessions.put(sessionId,
                new InterviewSession(sessionId, loadQuestions()));
        log.info("Session initialized: {}", sessionId);
    }

    public String startInterview(String sessionId) {
        InterviewSession session = sessions.get(sessionId);
        if (session == null) return "ERROR";

        return session.getCurrentQuestion().getQuestionText();
    }

    public String nextQuestion(String sessionId) {
        InterviewSession session = sessions.get(sessionId);
        if (session == null) return "ERROR";

        boolean hasMore = session.moveNext();
        if (!hasMore) {
            return "INTERVIEW_COMPLETED";
        }
        return session.getCurrentQuestion().getQuestionText();
    }

    public void processAudio(String sessionId, byte[] audio) {
        log.info("Received {} bytes from {}", audio.length, sessionId);
        // Here we will later integrate STT + LLM
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

    private List<InterviewQuestion> loadQuestions() {
        return List.of(
                new InterviewQuestion(1, "Tell me about yourself."),
                new InterviewQuestion(2, "Explain OOP principles."),
                new InterviewQuestion(3, "What is multithreading?"),
                new InterviewQuestion(4, "What is REST API?")
        );
    }
}