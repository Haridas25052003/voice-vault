package com.demo.service;

import com.ai.interviewer.model.InterviewQuestion;
import com.demo.model.InterviewQuestion;

import java.util.List;

public class InterviewSession {

    private String sessionId;
    private List<InterviewQuestion> questions;
    private int currentIndex = 0;

    public InterviewSession(String sessionId, List<InterviewQuestion> questions) {
        this.sessionId = sessionId;
        this.questions = questions;
    }

    public InterviewQuestion getCurrentQuestion() {
        if (currentIndex < questions.size()) {
            return questions.get(currentIndex);
        }
        return null;
    }

    public boolean moveNext() {
        currentIndex++;
        return currentIndex < questions.size();
    }

    public String getSessionId() {
        return sessionId;
    }
}