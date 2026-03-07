package com.demo.ObjecMapperPractice.practice.service;

import org.springframework.stereotype.Service;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Service
public class InterviewService {

    private static final String AUDIO_FOLDER = "C:/audio/";

    private final Map<Integer,String> questionMap = new HashMap<>();
    private final Map<Integer,String> audioMap = new HashMap<>();


    public InterviewService(){

        initializeQuestions();
    }


    private void initializeQuestions(){

        questionMap.put(0,"Tell me about yourself");
        questionMap.put(1,"Explain Java OOP concepts");
        questionMap.put(2,"What is multithreading?");
        questionMap.put(3,"Explain deadlock");
        questionMap.put(4,"What is Spring Boot");

        audioMap.put(0,"q0.wav");
        audioMap.put(1,"q1.wav");
        audioMap.put(2,"q2.wav");
        audioMap.put(3,"q3.wav");
        audioMap.put(4,"q4.wav");
    }


    public QuestionResponse getQuestionByIndex(int index) throws Exception{

        String text = getQuestionText(index);

        byte[] audio = loadAudioFile(index);

        return buildResponse(text,audio);
    }


    private String getQuestionText(int index){

        return questionMap.get(index);
    }


    private byte[] loadAudioFile(int index) throws Exception{

        String fileName = audioMap.get(index);

        Path path = Path.of(AUDIO_FOLDER + fileName);

        return Files.readAllBytes(path);
    }


    private QuestionResponse buildResponse(String text,byte[] audio){

        QuestionResponse response = new QuestionResponse();

        response.setQuestionText(text);
        response.setAudioBytes(audio);

        return response;
    }
}