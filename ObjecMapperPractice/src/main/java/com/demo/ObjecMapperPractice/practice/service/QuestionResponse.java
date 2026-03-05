package com.demo.ObjecMapperPractice.practice.service;

public class QuestionResponse {

    private String questionText;
    private byte[] audioBytes;


    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public byte[] getAudioBytes() {
        return audioBytes;
    }

    public void setAudioBytes(byte[] audioBytes) {
        this.audioBytes = audioBytes;
    }
}