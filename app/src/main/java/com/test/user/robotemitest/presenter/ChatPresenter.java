package com.test.user.robotemitest.presenter;

import com.test.user.robotemitest.model.Contacts;
import com.test.user.robotemitest.model.Message;
import com.test.user.robotemitest.model.RealmControllers.ContactsRealmController;
import com.test.user.robotemitest.model.RealmControllers.MessageRealmControler;

import io.realm.RealmResults;


public class ChatPresenter {

    private ContactsRealmController mContactsRealmController;
    private MessageRealmControler mMessageRealmControler;

    public ChatPresenter() {
        mContactsRealmController=new ContactsRealmController();
        mMessageRealmControler = new MessageRealmControler();
    }

    public Contacts getContactById(int id){
     return mContactsRealmController.getContactById(id);
    }

    public RealmResults<Message>  getMessages(String id){
        return mMessageRealmControler.getMessagesById(id);
    }

}
