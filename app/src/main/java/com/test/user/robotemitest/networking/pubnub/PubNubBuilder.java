package com.test.user.robotemitest.networking.pubnub;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;

/**
 * Singleton class for PubNub object
 */
public class PubNubBuilder {
    private static PubNub mPubnub ;

    private PubNubBuilder(){ }

    public static PubNub getInstance(){
        if(mPubnub ==null){
            PNConfiguration pnConfiguration = new PNConfiguration();
            pnConfiguration.setSubscribeKey(PubNubConstant.PUNNUB_SUBSCRIBE_KEY);
            pnConfiguration.setPublishKey(PubNubConstant.PUNNUB_PUBLISH_KEY);
            pnConfiguration.setSecure(false);
            mPubnub = new PubNub(pnConfiguration);
        }
        return mPubnub ;
    }
}
