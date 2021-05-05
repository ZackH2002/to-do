package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecordDatabase dbHelper;
    private Context context = this;
    private RecordAdapter adapter;
    private List<Record> recordList= new ArrayList<>();

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
        adapter = new RecordAdapter(getApplicationContext(),recordList);
        lv.setAdapter(adapter);
        refreshListView();
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        String main = data.getStringExtra("main");
        String time = data.getStringExtra("time");
        String title = data.getStringExtra("title");
        String tag = data.getStringExtra("tag");
        Record record = new Record(title,main,time,tag);
        CRUD crud = new CRUD(context);
        crud.open();
        crud.addRecord(record);
        crud.close();
        refreshListView();
    }
    public void refreshListView(){
        CRUD crud = new CRUD(context);
        crud.open();
        if(recordList.size()>0)recordList.clear();
        recordList.addAll(crud.getAllRecord());
        crud.close();
        adapter.notifyDataSetChanged();
    }
}