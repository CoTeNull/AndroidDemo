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
import java.util.List;

import Dao.MessageDao;
import Dao.SqlHelper;
import module.Message;


public class MessageActivity extends AppCompatActivity {
    SqlHelper sqlHelper;
    Button button,update;
    int this_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        //获取传过来的值
        Intent intent =getIntent();
        String id = intent.getStringExtra("id");
        this_id=Integer.parseInt(id);//为之后的修改保留一个全局变量
        //读取信息
        sqlHelper = new SqlHelper(this,"contact.db",null,1);
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        MessageDao messageDao= new MessageDao(db);
        List<Message> list = messageDao.showById(id);

        EditText name=findViewById(R.id.showName2);
        //虚空
        name.setText(list.get(0).getName());
        EditText content=findViewById(R.id.showContent2);
        content.setText(list.get(0).getContent());

        button=findViewById(R.id.submit_return);
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessageActivity.this,MainActivity.class);
                startActivity(intent);
            }
        } );
       update=findViewById(R.id.submit_update);
       update.setOnClickListener(new Button.OnClickListener(){

           @Override
           public void onClick(View view) {
               //声明
               EditText name=findViewById(R.id.showName2);
               EditText content=findViewById(R.id.showContent2);
               //引用模型修改
               Message this_message = new Message();
               this_message.setName(name.getText().toString());
               this_message.setContent(content.getText().toString());
               this_message.setId(this_id);
               Date date = new Date();
               SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
               this_message.setDate(sdf.format(date));
               //调用Dao层存入手机数据库
               SQLiteDatabase db = sqlHelper.getWritableDatabase();
               MessageDao messageDao = new MessageDao(db);
               messageDao.update(this_message);
               //提示并跳回前端
               Toast.makeText(MessageActivity.this, "文章修改成功！", Toast.LENGTH_LONG).show();
               Intent intent = new Intent( MessageActivity.this,MainActivity.class);
               startActivity(intent);
           }
       });
    }
    //返回按钮

}