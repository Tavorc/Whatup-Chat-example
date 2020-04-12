package com.test.user.robotemitest.model.RealmControllers;

import com.test.user.robotemitest.model.Message;
import com.test.user.robotemitest.model.RealmDB;

import java.util.UUID;

import io.realm.RealmResults;

public class MessageRealmControler {

    private static  final  String ID_USER="idUserSender";

    /**
     * Insert message to realm db
     * @param idUserSender
     * @param message
     * @param time
     */
    public void addMessage(String idUserSender,String message,String time){
        RealmDB.getInstance().beginTransaction();
        Message messageRealm = RealmDB.getInstance().createObject(Message.class, UUID.randomUUID().toString());
        messageRealm.setIdUserSender(idUserSender);
        messageRealm.setMessage(message);
        messageRealm.setTime(time);
        RealmDB.getInstance().commitTransaction();
    }

    public RealmResults<Message> getMessagesById(String id){
        RealmResults<Message> messages = RealmDB.getInstance().where(Message.class).equalTo(ID_USER, id).findAll();
        return messages;
    }

}
