package com.andxpar.biblioparcial.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        sqLiteDatabase.execSQL("create table articulos(codigo int primary key, descripcion text, precio real)");
        sqLiteDatabase.execSQL("create table bookDocente(codigo integer, nombre text, autor text)");
        sqLiteDatabase.execSQL("create table bookEstudiante(codigo integer, nombre text, autor text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists User");
        sqLiteDatabase.execSQL("drop table if exists articulos");
        sqLiteDatabase.execSQL("drop table if exists bookDocente");
        sqLiteDatabase.execSQL("drop table if exists bookEstudiante");
        sqLiteDatabase.execSQL("create table User(idUser integer,dni integer ,name text," +
                "lastName text, faculty text, program text, role text, user String," +
                "password text)");
        sqLiteDatabase.execSQL("create table articulos(codigo int primary key, descripcion text, precio real)");
        sqLiteDatabase.execSQL("create table bookDocente(codigo integer, nombre text, autor text)");
        sqLiteDatabase.execSQL("create table bookEstudiante(codigo integer, nombre text, autor text)");
    }
}
