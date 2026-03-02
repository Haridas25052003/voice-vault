package com.demo.service;

import com.ai.interviewer.model.InterviewQuestion;
import lombok.Data;
import com.demo.model.*;

import java.util.List;

@Data
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
}