package com.tmaprojects.smarthome;

import android.app.Application;

/**
 * Created by TarekMA on 2/1/17.
 * facebook/tarekkma1
 */

public class MyApp extends Application {
    private static MyApp instance;

    DatabaseManager DBManager;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        DBManager = new DatabaseManager(this);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DBManager.closeDatabases();
    }

    public static DatabaseManager getDBManager() {
        return getInstance().DBManager;
    }

    public static MyApp getInstance() {
        return instance;
    }
}
