package com.example.xqtdn.myapplicationtest;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import Dao.MessageDao;
import Dao.SqlHelper;
import module.Message;


public class AddActivity extends AppCompatActivity {
    SqlHelper thisSqlHelper;
    Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        addButton =  findViewById(R.id.submit_add);
        thisSqlHelper = new SqlHelper(this, "contact.db", null, 1);
        addButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = thisSqlHelper.getWritableDatabase();
                MessageDao messageDao = new MessageDao(db);
                EditText textName =  findViewById(R.id.name);
                EditText textContext =  findViewById(R.id.showContent);
           //实例化模型且创建具体模型对象
                Message this_message = new Message();
                this_message.setName(textName.getText().toString());
                this_message.setContent(textContext.getText().toString());
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                this_message.setDate(sdf.format(date));
            //调用Dao层存入手机数据库
                messageDao.save(this_message);
             //提示并跳回前端
                Toast.makeText(AddActivity.this, "文章保存成功！", Toast.LENGTH_LONG).show();
                Intent intent = new Intent( AddActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
