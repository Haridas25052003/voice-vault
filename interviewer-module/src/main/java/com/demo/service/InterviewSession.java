package com.demo.service;

import com.demo.model.*;
import com.ai.interviewer.model.InterviewQuestion;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InterviewSession {

    private String sessionId;
    private List<InterviewQuestion> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private boolean interviewCompleted = false;

    public InterviewSession(String sessionId, List<InterviewQuestion> questions) {
        this.sessionId = sessionId;
        this.questions = questions;
    }

    public InterviewQuestion getCurrentQuestion() {
        if (currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex);
        }
        return null;
    }

    public void moveToNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex >= questions.size()) {
            interviewCompleted = true;
        }
    }
}