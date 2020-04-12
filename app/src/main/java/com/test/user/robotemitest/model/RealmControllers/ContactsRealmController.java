package com.test.user.robotemitest.model.RealmControllers;

import com.test.user.robotemitest.model.Contacts;
import com.test.user.robotemitest.model.RealmDB;
import com.test.user.robotemitest.networking.response.ContactsResponse;
import java.util.List;
import io.realm.RealmResults;

public class ContactsRealmController {
    private static  final  String ID="id";

    /**
     * insert one contact to realm db
     * @param contactResponse
     */
    public void addContact(ContactsResponse contactResponse){
        RealmDB.getInstance().beginTransaction();
        Contacts contact = RealmDB.getInstance().createObject(Contacts.class,contactResponse.getId());
        contact.setAddress(contactResponse.getAddress());
        contact.setAvatar(contactResponse.getAvatar());
        contact.setEmail(contactResponse.getEmail());
        contact.setFirstName(contactResponse.getFirstName());
        contact.setLastName(contactResponse.getLastName());
        contact.setGender(contactResponse.getGender());
        contact.setPhone(contactResponse.getPhone());
        RealmDB.getInstance().commitTransaction();
    }

    /**
     *  Update the action of the contact when get a message
     * @param idContact
     * @param time
     * @param timeFormatted
     * @param message
     */
    public void updateLastAction(String idContact,long time,String timeFormatted,String message){
        RealmDB.getInstance().beginTransaction();
        Contacts contactTemp = RealmDB.getInstance().where(Contacts.class).equalTo(ID,Integer.parseInt(idContact)).findFirst();
        contactTemp.setLastTime(time);
        contactTemp.setLastTimeFormatted(timeFormatted);
        contactTemp.setLastMessage(message);
        RealmDB.getInstance().commitTransaction();
    }

    /**
     *
     * @return List<Contacts>
     */
    public List<Contacts> getContactsList(){
        RealmResults<Contacts> contactList = RealmDB.getInstance().where(Contacts.class).findAll();
        return contactList;
    }

    /**
     *
     * @param id
     * @return Contacts
     */
    public Contacts getContactById(int id){
        Contacts contact = RealmDB.getInstance().where(Contacts.class).equalTo(ID, id).findFirst();
        return contact;
    }
}
