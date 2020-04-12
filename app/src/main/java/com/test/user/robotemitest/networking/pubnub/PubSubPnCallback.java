package com.test.user.robotemitest.networking.pubnub;


import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.JsonElement;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import com.test.user.robotemitest.R;
import com.test.user.robotemitest.utils.NotificationBuilder;


import java.util.Date;

public class PubSubPnCallback extends SubscribeCallback {
    private static  final String TEXT="text";
    public static  final String MESSAGE="message";
    public static  final String  MESSAGE_DATA="message_data";
    public static  final String ID_USER="idUser";
    public static  final String TIME="time";
    public static  final String TIME_TOKEN="timeToken";

    private Context mContext;
    public PubSubPnCallback(Context context) {
        mContext=context;
    }

    @Override
    public void status(PubNub pubnub, PNStatus status) {

    }
    @Override
    public void message(PubNub pubnub, PNMessageResult message) {
        try {
            JsonElement messageJson = message.getMessage();
            String mes = messageJson.getAsJsonObject().get(TEXT).getAsString();
            String sender = message.getChannel();
            Date now = new Date();

            long timeToken = message.getTimetoken();

            //Push notification when get a message
            new NotificationBuilder(mContext)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setText(mes)
                    .build(Integer.parseInt(sender))
                    .show();

            Intent intent=new Intent(MESSAGE_DATA);
            intent.putExtra(ID_USER,sender);
            intent.putExtra(MESSAGE,mes);
            intent.putExtra(TIME,now.toString());
            intent.putExtra(TIME_TOKEN,timeToken);

            //Send data to main thread
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void presence(PubNub pubnub, PNPresenceEventResult presence) {
    }
}