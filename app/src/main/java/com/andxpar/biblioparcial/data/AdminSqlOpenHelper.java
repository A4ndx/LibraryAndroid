package com.andxpar.biblioparcial.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AdminSqlOpenHelper extends SQLiteOpenHelper {

    public AdminSqlOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory
            factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table User(idUser integer,dni integer, name text," +
                "lastName text, faculty text, program text, role text, user String," +
                "password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists User");
        sqLiteDatabase.execSQL("create table User(idUser integer,dni integer ,name text," +
                "lastName text, faculty text, program text, role text, user String," +
                "password text)");
    }
}
