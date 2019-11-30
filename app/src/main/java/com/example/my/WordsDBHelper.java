package com.example.my;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class WordsDBHelper extends SQLiteOpenHelper {
private final static String DATABASE_NAME = "wordsdb";
private  final  static int DATABASE_VERSION = 1;

private final static String SQL_CREATE_DATABASE = "create table "
        + Words.Word.TABLE_NAME + "(" +
        Words.Word._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
        Words.Word.COLUMN_NAME_WORD + " TEXT" + "," +
        Words.Word.COLUMN_NAME_MEANING + " TEXT" + ","
        + Words.Word.COLUMN_NAME_SAMPLE + " TEXT" + ")";

private  final static  String SQL_DELETE_DATABASE = "DROP TABLE IF EXISTS " + Words.Word.TABLE_NAME;

    public WordsDBHelper(Context context, String s, Object o, int i) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_DATABASE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_DATABASE);
        onCreate(db);
    }
}
