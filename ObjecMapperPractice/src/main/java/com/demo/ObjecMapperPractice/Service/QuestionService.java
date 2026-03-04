package com.demo.ObjecMapperPractice.Service;

import com.demo.ObjecMapperPractice.demo.Question;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Map;

@Service
public class QuestionService {

    private final FileReaderService filereader;

    public QuestionService(FileReaderService filereader) {
        this.filereader = filereader;
    }

    public Map<Integer, Question> getQuestions() throws IOException {
        return filereader.loadQuestions("questions-folder");
    }
}