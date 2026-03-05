package com.demo.ObjecMapperPractice.practice.handler;

import com.demo.ObjecMapperPractice.practice.service.InterviewService;
import com.demo.ObjecMapperPractice.practice.service.QuestionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class InterviewWebSocketHandler extends TextWebSocketHandler {

    private final InterviewService interviewService;

    private final ObjectMapper mapper = new ObjectMapper();

    public InterviewWebSocketHandler(InterviewService interviewService) {
        this.interviewService = interviewService;
    }


    @Override
    protected void handleTextMessage(WebSocketSession session,
                                     TextMessage message) throws Exception {

        FrontendRequest request =
                mapper.readValue(message.getPayload(),FrontendRequest.class);

        processRequest(session,request);
    }


    private void processRequest(WebSocketSession session,
                                FrontendRequest request) throws Exception{

        int index = request.getIndex();

        QuestionResponse response =
                interviewService.getQuestionByIndex(index);

        sendQuestionText(session,response);

        sendAudio(session,response);
    }


    private void sendQuestionText(WebSocketSession session,
                                  QuestionResponse response) throws Exception{

        session.sendMessage(
                new TextMessage(response.getQuestionText())
        );
    }


    private void sendAudio(WebSocketSession session,
                           QuestionResponse response) throws Exception{

        session.sendMessage(
                new BinaryMessage(response.getAudioBytes())
        );
    }
}