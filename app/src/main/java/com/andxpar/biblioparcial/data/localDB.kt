package com.andxpar.biblioparcial.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase

fun getDB(context: Context): SQLiteDatabase {
    val admin = AdminSqlOpenHelper(context, "loggedUser", null, 1)
    return admin.writableDatabase
}