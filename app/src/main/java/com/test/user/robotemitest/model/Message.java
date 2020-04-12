package com.test.user.robotemitest.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Message extends RealmObject {

    @PrimaryKey
    private String idMessage;
    private String idUserSender;
    private String message;
    private String time;


    public String getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(String idMessage) {
        this.idMessage = idMessage;
    }

    public String getIdUserSender() {
        return idUserSender;
    }

    public void setIdUserSender(String idUserSender) {
        this.idUserSender = idUserSender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
