package com.demo.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InterviewQuestion {

    private int id;
    private String questionText;
}