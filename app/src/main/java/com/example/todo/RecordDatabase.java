package com.example.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecordDatabase extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "record";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String TIME = "time";
    public static final String MODE = "mode";
    //public static final String CHECK = "check";

    public RecordDatabase(Context context) {
        super(context, TABLE_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME
                + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TITLE + " TEXT NOT NULL,"
                + CONTENT + " TEXT NOT NULL,"
                + TIME + " TEXT NOT NULL,"
                + MODE + " INTEGER DEFAULT 1)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        for (int i = oldVersion;i<newVersion;i++){
//            switch (i){
//                case 1:
//                    break;
//                case 2:
//                    updateMode(db);
//                default:
//                    break;
//            }
//        }
    }
}
