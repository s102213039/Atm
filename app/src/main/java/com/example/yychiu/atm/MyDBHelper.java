package com.example.yychiu.atm;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yychiu on 2017/10/19.
 */

class MyDBHelper extends SQLiteOpenHelper{

    private static MyDBHelper instance ;
    static MyDBHelper getInstance(Context ctx){
        if(instance==null){
            instance = new MyDBHelper(ctx,"expense.db",null,1);
        }
        return instance;
    }
    private MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE main.exp "+
                "(_id INTEGER PRIMARY KEY NOT NULL , "+
                "cdate DATETIME NOT NULL , "+
                "info VARCHAR, "+
                "amount INTEGER)");
        db.execSQL("CREATE TABLE infos "+
                "(_id INTEGER PRIMARY KEY NOT NULL , "+
                "info VARCHAR)");
        String[] infos ={"Breakfast","Lunch","Bread","Bill","Parking"};
        for(String info:infos){
            ContentValues values = new ContentValues();
            values.put("info",info);
            db.insert("infos",null,values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
