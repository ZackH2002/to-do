package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.tv.TvRecordingClient;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private RecordDatabase dbHelper;
    private Context context = this;
    private RecordAdapter adapter;
    private List<Record> recordList = new ArrayList<>();

    TextView textView;
    private ListView lv;
    final String TGA = "tag";
    public FloatingActionButton btn_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_new = (FloatingActionButton) findViewById(R.id.Btn_new);
        lv = findViewById(R.id.lv);
        adapter = new RecordAdapter(getApplicationContext(), recordList);
        lv.setAdapter(adapter);
        refreshListView();
        lv.setOnItemClickListener(this);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("mode", 4);//新建record的mode值为4
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        int returnMode;
        long record_id;
        returnMode = data.getExtras().getInt("mode", -1);
        record_id = data.getExtras().getLong("id", 0);
        if (returnMode == 1) {
            //更新record
            String main = data.getStringExtra("main");
            String time = data.getStringExtra("time");
            String title = data.getStringExtra("title");
            String tag = data.getStringExtra("tag");
            Record newRecord = new Record(title, main, time, tag);
            newRecord.setId(record_id);
            CRUD crud = new CRUD(context);
            crud.open();
            crud.updateRecord(newRecord);
            crud.close();

        }else if (returnMode==0){
            //新建record
            String main = data.getStringExtra("main");
            String time = data.getStringExtra("time");
            String title = data.getStringExtra("title");
            String tag = data.getStringExtra("tag");
            Record record = new Record(title, main, time, tag);
            CRUD crud = new CRUD(context);
            crud.open();
            crud.addRecord(record);
            crud.close();
        }
        refreshListView();
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void refreshListView() {
        CRUD crud = new CRUD(context);
        crud.open();
        if (recordList.size() > 0) recordList.clear();
        recordList.addAll(crud.getAllRecord());
        crud.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Record record = (Record) parent.getItemAtPosition(position);
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra("title", record.getTitle());
        intent.putExtra("time", record.getTime());
        intent.putExtra("main", record.getContent());
        intent.putExtra("mode", 3);//点击的mode值
        intent.putExtra("tag", record.getTag());
        intent.putExtra("id", record.getId());
        startActivityForResult(intent, 1);
    }
}