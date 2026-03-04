package com.demo.ObjecMapperPractice.Service;

import com.demo.ObjecMapperPractice.demo.Question;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class FileReaderService {

    public Map<Integer, Question> loadQuestions(String folderPath) throws IOException {

        Map<Integer, Question> questionMap = new HashMap<>();

        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        for (File file : files) {

            if (file.isFile()) {

                String fileName = file.getName();

                // Example: q1.wav → extract 1
                int questionId = Integer.parseInt(fileName.replaceAll("\\D", ""));

                byte[] audioBytes = Files.readAllBytes(file.toPath());

                String questionText = "Question " + questionId;

                Question question = new Question(questionId, questionText, audioBytes);

                questionMap.put(questionId, question);
            }
        }

        return questionMap;
    }
}