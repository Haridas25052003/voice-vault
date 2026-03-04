package com.demo.Rapid_fire.orchestrator;

import com.demo.Rapid_fire.session.InterviewSession;
import com.demo.Rapid_fire.session.SessionManager;
import com.demo.Rapid_fire.service.LLMService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InterviewOrchestrator {

    private final SessionManager sessionManager;
    private final LLMService llmService;

    /**
     * Start interview
     */
    public String startInterview(String sessionId) {

        InterviewSession session = sessionManager.getSession(sessionId);

        if (session == null) {
            return "Session not found";
        }

        String question = llmService.generateQuestion();

        session.setCurrentQuestion(question);

        return question;
    }


    /**
     * Store transcript from user audio
     */
    public void appendTranscript(String sessionId, String text) {

        InterviewSession session = sessionManager.getSession(sessionId);

        if (session == null) return;

        session.getTranscriptBuffer().append(text).append(" ");
    }


    /**
     * Evaluate user answer
     */
    public String evaluateAnswer(String sessionId) {

        InterviewSession session = sessionManager.getSession(sessionId);

        if (session == null) return "Session not found";

        String transcript = session.getTranscriptBuffer().toString();

        String question = session.getCurrentQuestion();

        String evaluation = llmService.evaluateAnswer(question, transcript);

        session.clearTranscript();

        session.nextQuestion();

        return evaluation;
    }


    /**
     * Ask next question
     */
    public String nextQuestion(String sessionId) {

        InterviewSession session = sessionManager.getSession(sessionId);

        if (session == null) return "Session not found";

        String question = llmService.generateQuestion();

        session.setCurrentQuestion(question);

        return question;
    }
}