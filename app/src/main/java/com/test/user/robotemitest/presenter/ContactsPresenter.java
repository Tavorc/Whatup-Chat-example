package com.test.user.robotemitest.presenter;


import com.test.user.robotemitest.model.Contacts;
import com.test.user.robotemitest.model.RealmControllers.ContactsRealmController;
import com.test.user.robotemitest.networking.ContactsConstant;
import com.test.user.robotemitest.networking.ContactsControllerApi;
import com.test.user.robotemitest.networking.request.ContactsRequest;
import java.util.List;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

public class ContactsPresenter {
    private ContactsControllerApi mContactsControllerApi;

    private ContactsView mView;
    protected CompositeSubscription mCompositeSubscription=new CompositeSubscription();
    private ContactsRealmController mContactsRealmController;


    public ContactsPresenter(ContactsView movieView) {
        this.mView = movieView;
        mContactsControllerApi=new ContactsControllerApi();
        mContactsRealmController = new ContactsRealmController();
    }


    public void getContactsData(){

        //Build request for server call
        ContactsRequest contactsRequest=new ContactsRequest();
        contactsRequest.setKey(ContactsConstant.KEY);
        contactsRequest.setType(ContactsConstant.TYPE);

        mContactsControllerApi.getContactsList(contactsRequest).subscribe(new Subscriber<List<Contacts>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.onError(e.getMessage());
            }

            @Override
            public void onNext(List<Contacts> contacts) {
                mView.setData(contacts);
            }
        });
    }

    public Contacts getContactById(int id){
        return mContactsRealmController.getContactById(id);
    }

    public void unbind(){
        mCompositeSubscription.clear();
    }


    public interface ContactsView{
        void setData(List<Contacts> contacts);
        void onError(String message);
    }
}
