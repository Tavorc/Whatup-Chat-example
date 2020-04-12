package com.test.user.robotemitest.model;

import io.realm.Realm;

/**
 * Singleton class for realm local DB
 */
public class RealmDB {
    private static Realm mRealm;

    private RealmDB(){}
    public static Realm getInstance(){
        if(mRealm==null){
            mRealm = Realm.getDefaultInstance();
        }
        return mRealm;
    }
}
