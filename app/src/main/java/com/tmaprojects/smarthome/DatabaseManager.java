package com.tmaprojects.smarthome;

import android.content.Context;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

/**
 * Created by TarekMA on 2/1/17.
 * facebook/tarekkma1
 */

public class DatabaseManager {
    private Context context;
    private DB mainDB;


    public DatabaseManager(Context context) {
        this.context = context;
        openDatabases();
    }



    private void openDatabases(){
        try {
            mainDB = DBFactory.open(context,"favorites");
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public void closeDatabases(){
        try {
            mainDB.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }

    public DB getMainDB() {
        return mainDB;
    }
}
