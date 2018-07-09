package com.example.xqtdn.myapplicationtest;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Dao.MessageDao;
import Dao.SqlHelper;
import module.Message;

public class MainActivity extends AppCompatActivity {
     Button AddMsg;
     SqlHelper thisSqlHelper;
     ListView listView;
     //简单适配器？
    static SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AddMsg = findViewById(R.id.submit_new);
        listView = findViewById(R.id.list);
        //主页的点击跳转
        AddMsg.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });
        //页面数据读取
        thisSqlHelper=new SqlHelper(this,"contact.db",null,1);
        SQLiteDatabase db = thisSqlHelper.getWritableDatabase();
        MessageDao messageDao = new MessageDao(db);
        //调用Dao层方法
        List<Message> list= messageDao.showAll();
        int size = list.size();
        Message message;
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < size; i++) {
            message = list.get(i);
            Map<String, Object> map = new HashMap<String, Object>();
            //虚空
            map.put("id",message.getId());
            map.put("name", message.getName());
            map.put("content", message.getContent());
            map.put("date", message.getDate());
            data.add(map);
        }
        //渲染前端页面。
        adapter = new SimpleAdapter(MainActivity.this, data, R.layout.message_li, new String[]{"id","name","content","date"}, new int[]{R.id.id, R.id.name,R.id.content,R.id.date});
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView idView = view.findViewById(R.id.id);
                String this_id=idView.getText().toString();
                Intent intent=new Intent(MainActivity.this,MessageActivity.class);
                intent.putExtra("id",this_id);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                TextView this_id0 = view.findViewById(R.id.id);
                String this_id =this_id0.getText().toString();
                SQLiteDatabase db = thisSqlHelper.getWritableDatabase();
                MessageDao messageDao= new MessageDao(db);
                boolean bool = messageDao.delete(this_id);
                if(bool){
                    Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });


    }
}
