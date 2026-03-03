package com.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;


public class InterviewQuestion {
    private int id;
    private String questionText;

    public InterviewQuestion(int id, String questionText) {
        this.id = id;
        this.questionText = questionText;
    }

    public int getId() {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }
}