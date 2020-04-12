package com.test.user.robotemitest.view.adapter;

public class ChatMessage {
    private String message;
    private  String sender;
    private  String timestamp;

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
