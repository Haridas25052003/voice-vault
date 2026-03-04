package com.demo.Rapid_fire.dto;

public class SocketMessage {

    private String command;
    private Object payload;

    public SocketMessage(){}

    public SocketMessage(String command, Object payload){
        this.command=command;
        this.payload=payload;
    }

    public String getCommand(){
        return command;
    }

    public Object getPayload(){
        return payload;
    }

    public void setCommand(String command){
        this.command=command;
    }

    public void setPayload(String payload){
        this.payload=payload;
    }
}
