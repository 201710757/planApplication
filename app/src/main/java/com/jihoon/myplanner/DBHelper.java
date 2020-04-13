package com.jihoon.myplanner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
    static final String DATABASE_NAME = "date.db";
    public DBHelper(Context context, int version)
    {
        super(context, DATABASE_NAME, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE dateTodo (_id INTEGER PRIMARY KEY AUTOINCREMENT, year TEXT, month TEXT, day TEXT, title TEXT, todo TEXT);");

        db.execSQL("CREATE TABLE totalTodo (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, todo TEXT, color INTEGER, done INTEGER)");

        //db.execSQL("CREATE TABLE checkBox (name TEXT, phonenum TEXT, email TEXT, memo TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS dateTodo;");
        db.execSQL("DROP TABLE IF EXISTS totalTodo");
        onCreate(db);
    }
}