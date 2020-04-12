package com.test.user.robotemitest.networking;

import com.test.user.robotemitest.model.Contacts;
import com.test.user.robotemitest.model.RealmControllers.ContactsRealmController;
import com.test.user.robotemitest.networking.pubnub.PubNubBuilder;
import com.test.user.robotemitest.networking.request.ContactsRequest;
import com.test.user.robotemitest.networking.response.ContactsResponse;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ContactsControllerApi {
    private RetrofitBuilder mRetrofitBuilder;
    private ContactsRealmController mContactsRealmController;
    private List<String> mChannels=new ArrayList<>();


    public ContactsControllerApi() {
        this.mRetrofitBuilder =new RetrofitBuilder(ContactsConstant.BASE_URL);
        mContactsRealmController= new ContactsRealmController();
    }

    /**
     * get contacts list from server and insert the data to local DB
     * @param contactsRequest
     * @return
     */
    public Observable<List<Contacts>> getContactsList(ContactsRequest contactsRequest){
        return  mRetrofitBuilder.getContactsList()
                .getContactList(contactsRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> {
                    if(mContactsRealmController.getContactsList().size()==0){

                        for (ContactsResponse item : response) {
                            mChannels.add(String.valueOf(item.getId()));
                        }
                        //In the first time - subscribe to channels
                        PubNubBuilder.getInstance().subscribe().channels(mChannels).execute();

                        //Insert data to local DB
                        for (ContactsResponse item : response) {
                            mContactsRealmController.addContact(item);
                        }
                    }
                    return mContactsRealmController.getContactsList();
                });
    }
}
