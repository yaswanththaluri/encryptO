package com.yaswanththaluri.encrypto;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmBase extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        Realm.init(this);

        RealmConfiguration configuration = new RealmConfiguration.Builder().name("DatabaseRealm.realm").build();

        Realm.setDefaultConfiguration(configuration);

    }
}
