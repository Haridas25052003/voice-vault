package com.demo.ObjecMapperPractice.demo;

public class Question {

    private int questionId;
    private String questionText;
    private byte[] audioBytes;

    public Question(int questionId, String questionText, byte[] audioBytes) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.audioBytes = audioBytes;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public byte[] getAudioBytes() {
        return audioBytes;
    }
}