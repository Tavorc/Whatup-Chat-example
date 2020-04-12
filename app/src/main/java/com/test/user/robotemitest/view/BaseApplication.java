package com.test.user.robotemitest.view;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.test.user.robotemitest.model.Contacts;
import com.test.user.robotemitest.model.RealmControllers.ContactsRealmController;
import com.test.user.robotemitest.model.RealmControllers.MessageRealmControler;
import com.test.user.robotemitest.networking.pubnub.PubNubBuilder;
import com.test.user.robotemitest.networking.pubnub.PubSubPnCallback;



import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;

public class BaseApplication extends Application {
    private ContactsRealmController mContactsRealmController;
    private List<String> mChannels=new ArrayList<>();
    private PubSubPnCallback mSubscribeCallback;
    private BroadcastReceiver mBroadcastReceiver;
    private MessageRealmControler mMessageRealmControler;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        mMessageRealmControler=new MessageRealmControler();
        mContactsRealmController = new ContactsRealmController();

        /**
         * Initialize the callback of PubNub
         */
        if(mSubscribeCallback==null){
            mSubscribeCallback = new PubSubPnCallback(getBaseContext());
            PubNubBuilder.getInstance().addListener(mSubscribeCallback);
        }

        /**
         * If not the first time use in app initialize the channels list and subscribe
         */
        if(mContactsRealmController.getContactsList().size()>0){
            for (Contacts item : mContactsRealmController.getContactsList()) {
                mChannels.add(String.valueOf(item.getId()));
            }
            PubNubBuilder.getInstance().subscribe().channels(mChannels).execute();
        }

        /**
         * Receive the data when get message and update the local DB
         */
        mBroadcastReceiver= new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if(intent.getAction().equals(PubSubPnCallback.MESSAGE_DATA)){
                        String mes=intent.getStringExtra(PubSubPnCallback.MESSAGE);
                        String idUser=intent.getStringExtra(PubSubPnCallback.ID_USER);
                        String time=intent.getStringExtra(PubSubPnCallback.TIME);
                        long timetoken =intent.getLongExtra(PubSubPnCallback.TIME_TOKEN,0);
                        mMessageRealmControler.addMessage(idUser,mes,time);
                        mContactsRealmController.updateLastAction(idUser,timetoken,time,mes);
                    }
                }
            };

        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, new IntentFilter(PubSubPnCallback.MESSAGE_DATA));
    }
}
