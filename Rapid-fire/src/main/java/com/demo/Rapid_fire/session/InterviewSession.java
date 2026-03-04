package com.demo.Rapid_fire.session;

import java.util.ArrayList;
import java.util.List;

public class InterviewSession {

    private String sessionId;

    private String currentQuestion;

    private StringBuilder transcriptBuffer = new StringBuilder();

    private int questionIndex = 0;

    private List<Integer> scores = new ArrayList<>();

    public InterviewSession(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(String currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public StringBuilder getTranscriptBuffer() {
        return transcriptBuffer;
    }

    public void clearTranscript() {
        transcriptBuffer.setLength(0);
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public void nextQuestion() {
        questionIndex++;
    }

    public List<Integer> getScores() {
        return scores;
    }

}