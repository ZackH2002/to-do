package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    EditText Ed_time,Ed_title,Ed_tag,Ed_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Ed_time =findViewById(R.id.Ed_time);
        Ed_tag=findViewById(R.id.Ed_tag);
        Ed_title=findViewById(R.id.Ed_title);
        Ed_main = findViewById(R.id.Ed_main);
    }
    //back键自动保存
    public boolean onKeyDown(int keyCode , KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_HOME){
            return true;
        }else if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            intent.putExtra("title",Ed_title.getText().toString());
            intent.putExtra("time",Ed_time.getText().toString());
            intent.putExtra("main",Ed_main.getText().toString());
            intent.putExtra("tag",Ed_tag.getText().toString());
            setResult(RESULT_OK,intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
}