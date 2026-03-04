package com.demo.ObjecMapperPractice.controller;

import com.demo.ObjecMapperPractice.Service.QuestionService;
import com.demo.ObjecMapperPractice.demo.Question;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.Map;

@RestController
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/questions")
    public Map<Integer, Question> getQuestions() throws IOException {
        return questionService.getQuestions();
    }
}