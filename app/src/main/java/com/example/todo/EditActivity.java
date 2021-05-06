package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity  {
    EditText Ed_time, Ed_title, Ed_tag, Ed_main;
    public Button btu_time,btu_delete;
    TextView textView;
    private String oldContent = "";
    private String oldTime = "";
    private String oldTitle = "";
    private String oldTag = "";
    private String Time="";
    private int openMode = 0;
    private long id = 0;
    private String tag = "";
    public Intent intent = new Intent();
    private TimePickerView pvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Ed_time =findViewById(R.id.Ed_time);
        btu_time = findViewById(R.id.btu_time);
        textView=findViewById(R.id.tv_time);
        btu_delete=findViewById(R.id.btu_delete);
        btu_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTimePicker();
            }
        });
        btu_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (openMode == 4) {
                    intent.putExtra("mode", -1);
                    setResult(RESULT_OK, intent);
                } else {
                    //删除
                    intent.putExtra("mode", 2);
                    intent.putExtra("id", id);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        Ed_tag = findViewById(R.id.Ed_tag);
        Ed_title = findViewById(R.id.Ed_title);
        Ed_main = findViewById(R.id.Ed_main);
        Intent getIntent = getIntent();
        int openMode = getIntent.getIntExtra("mode", 0);
        if (openMode == 3) {
            //打开已经存在的项目
            id = getIntent.getLongExtra("id", 0);
            oldContent = getIntent.getStringExtra("main");
            oldTime = getIntent.getStringExtra("time");
            oldTitle = getIntent.getStringExtra("title");
            oldTag = getIntent.getStringExtra("tag");
            btu_time.setText(oldTime);
            Ed_time.setText(oldTime);
            Ed_title.setText(oldTitle);
            Ed_main.setText(oldContent);
            Ed_tag.setText(oldTag);
        }
    }

    //back键自动保存
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            autoSetMassage();
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void autoSetMassage() {
        if (openMode == 4) {
            //新建的record
            if (Ed_title.getText().toString().length() == 0) {
               intent.putExtra("mode", -1);//没有改变
            } else {
            intent.putExtra("mode", 0);//新建笔记
            intent.putExtra("title", Ed_title.getText().toString());
            intent.putExtra("time", Ed_time.getText().toString());
            intent.putExtra("main", Ed_main.getText().toString());
            intent.putExtra("tag", Ed_tag.getText().toString());
           }
        } else {
            if (Ed_title.getText().toString().equals(oldTitle)
                    && Ed_time.getText().toString().equals(oldTime)
                    && Ed_main.getText().toString().equals(oldContent)
                    && Ed_tag.getText().toString().equals(oldTag)) {
                intent.putExtra("mode", -1);//没有修改
            } else {
                intent.putExtra("mode", 1);
                intent.putExtra("title", Ed_title.getText().toString());
                intent.putExtra("time", Ed_time.getText().toString());
                intent.putExtra("main", Ed_main.getText().toString());
                intent.putExtra("tag", Ed_tag.getText().toString());
                intent.putExtra("id", id);
            }
        }
    }

    private void initTimePicker() {
        new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Time = getTime(date);
                Ed_time.setText(getTime(date));
                Toast.makeText(EditActivity.this, "" + date.toString(), Toast.LENGTH_SHORT).show();
            }
        })
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setType(new boolean[]{true, true, true, true, true, false})
                .build().show();
    }

    private String getTime(Date date) {//截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
}