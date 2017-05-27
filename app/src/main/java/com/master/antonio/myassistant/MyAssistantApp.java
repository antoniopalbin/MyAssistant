package com.master.antonio.myassistant;

import android.app.Application;

import com.siimkinks.sqlitemagic.SqliteMagic;

/**
 * Created by Antonio on 27/05/2017.
 */

public final class MyAssistantApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SqliteMagic.init(this);
    }
}
