package com.demo.Rapid_fire.service;

import org.springframework.stereotype.Service;

@Service
public class LLMService {

    public String generateQuestion() {

        // later we will call ChatGPT API here

        return "Explain how HashMap works internally.";
    }

    public String evaluateAnswer(String question, String answer) {

        // later we will call ChatGPT API here

        return "Score: 7/10 - Good explanation but missing collision handling.";
    }
}