package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    EditText Ed_time,Ed_title,Ed_tag,Ed_main;
    private String oldContent ="";
    private String oldTime ="";
    private String oldTitle="";
    private String oldTag="";
    private int openMode =0;
    private long id=0;
    private String tag="";
    public Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Ed_time =findViewById(R.id.Ed_time);
        Ed_tag=findViewById(R.id.Ed_tag);
        Ed_title=findViewById(R.id.Ed_title);
        Ed_main = findViewById(R.id.Ed_main);
        Intent getIntent = getIntent();
        int openMode = getIntent.getIntExtra("mode",0);
        if (openMode==3){
            //打开已经存在的项目
            id=getIntent.getLongExtra("id",0);
            oldContent=getIntent.getStringExtra("main");
            oldTime=getIntent.getStringExtra("time");
            oldTitle=getIntent.getStringExtra("title");
            oldTag=getIntent.getStringExtra("tag");
            Ed_time.setText(oldTime);
            Ed_title.setText(oldTitle);
            Ed_main.setText(oldContent);
            Ed_tag.setText(oldTag);
        }
    }
    //back键自动保存
    public boolean onKeyDown(int keyCode , KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_HOME){
            return true;
        }else if(keyCode == KeyEvent.KEYCODE_BACK){
            autoSetMassage();
            setResult(RESULT_OK,intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
    public void autoSetMassage(){
        if (openMode==4){
            //新建的record
            if (Ed_title.getText().toString().length()==0){
                intent.putExtra("mode",-1);//没有改变
            }else {
                intent.putExtra("mode",0);//新建笔记
                intent.putExtra("title",Ed_title.getText().toString());
                intent.putExtra("time",Ed_time.getText().toString());
                intent.putExtra("main",Ed_main.getText().toString());
                intent.putExtra("tag",Ed_tag.getText().toString());
            }
        }else {
            if (Ed_title.getText().toString().equals(oldTitle)
                    &&Ed_time.getText().toString().equals(oldTime)
                    &&Ed_main.getText().toString().equals(oldContent)
                    &&Ed_tag.getText().toString().equals(oldTag)){
                intent.putExtra("mode",-1);//没有修改
            }else {
                intent.putExtra("mode",1);
                intent.putExtra("title",Ed_title.getText().toString());
                intent.putExtra("time",Ed_time.getText().toString());
                intent.putExtra("main",Ed_main.getText().toString());
                intent.putExtra("tag",Ed_tag.getText().toString());
                intent.putExtra("id",id);
            }
        }
    }

}