package com.example.todo;

import android.content.ContentValues;
import android.content.Context;
import android.content.ReceiverCallNotAllowedException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

//增删改查
public class CRUD {
    SQLiteOpenHelper dbHandler;
    SQLiteDatabase db;
    private static final String[] columns = {
            RecordDatabase.ID,
            RecordDatabase.TITLE,
            RecordDatabase.TIME,
            RecordDatabase.CONTENT,
            //RecordDatabase.CHECKED,
            RecordDatabase.MODE
    };

    public CRUD(Context context) {
        dbHandler = new RecordDatabase(context);
    }

    public void open() {
        db = dbHandler.getWritableDatabase();
    }

    public void close() {
        dbHandler.close();
    }

    //添加项目到recordDatabase
    public Record addRecord(Record record) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RecordDatabase.TITLE, record.getTitle());
        contentValues.put(RecordDatabase.CONTENT, record.getContent());
        contentValues.put(RecordDatabase.TIME, record.getTime());
        contentValues.put(RecordDatabase.MODE, record.getTag());
        //contentValues.put(RecordDatabase.CHECKED,record.getCheck());
        long insetId = db.insert(RecordDatabase.TABLE_NAME, null, contentValues);
        record.setId(insetId);
        return record;
    }

    //获取数据
    public Record getRecord(long id) {
        //cursor查询
        Cursor cursor = db.query(RecordDatabase.TABLE_NAME, columns, RecordDatabase.ID +
                "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        Record record = new Record(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        return record;
    }

    public List<Record> getAllRecord() {
        Cursor cursor = db.query(RecordDatabase.TABLE_NAME, columns, null, null, null, null, null);
        List<Record> records = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Record record = new Record();
                record.setId(cursor.getLong(cursor.getColumnIndex(RecordDatabase.ID)));
                record.setTitle(cursor.getString(cursor.getColumnIndex(RecordDatabase.TITLE)));
                record.setContent(cursor.getString(cursor.getColumnIndex(RecordDatabase.CONTENT)));
                record.setTime(cursor.getString(cursor.getColumnIndex(RecordDatabase.TIME)));
                record.setTag(cursor.getString(cursor.getColumnIndex(RecordDatabase.MODE)));
                //record.setCheck(cursor.getString(cursor.getColumnIndex(RecordDatabase.CHECKED)));
                records.add(record);


            }
        }
        return records;
    }

    //更新数据
    public int updateRecord(Record record) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RecordDatabase.TITLE, record.getTitle());
        contentValues.put(RecordDatabase.CONTENT, record.getContent());
        contentValues.put(RecordDatabase.TIME, record.getTime());
        contentValues.put(RecordDatabase.MODE, record.getTag());
       // contentValues.put(RecordDatabase.CHECKED,record.getCheck());
        return db.update(RecordDatabase.TABLE_NAME, contentValues, RecordDatabase.ID + "=?", new String[]{
                String.valueOf(record.getId())
        });
    }

    //删除
    public void removeRecord(Record record) {
        db.delete(RecordDatabase.TABLE_NAME, RecordDatabase.ID + "=" + record.getId(), null);
    }

}
