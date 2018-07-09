package Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import module.Message;


public class MessageDao implements MessageService{

    SQLiteDatabase db;
    public MessageDao(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public boolean save(Object objects) {
        try {
            Message message = (Message) objects;
            ContentValues values = new ContentValues();
            values.put("name", message.getName());
            values.put("content", message.getContent());
            values.put("date",String.valueOf(message.getDate()));
            long index = db.insert("article", null, values);
            if (index > 0)
                return true;
            else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Object objects) {
        try {
            Message message = (Message) objects;
            ContentValues values = new ContentValues();
            values.put("name", message.getName());
            values.put("content", message.getContent());
            values.put("date",String.valueOf(message.getDate()));
            long index = db.update("article", values, "id=?", new String[]{String.valueOf(message.getId())});
            if (index > 0)
                return true;
            else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            int index = db.delete("article", "id=?", new String[]{id});
            if (index > 0)
                return true;
            else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List showAll() {
        Cursor cursor=db.query(false,"article",new String[]{"id","date","name","content"},null,null,null,null,null,null);
        List<Message> list =new ArrayList<>();
        while(cursor.moveToNext()){
            Message message = new Message(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
            list.add(message);
        }
        return list;
    }

    @Override
    public List showById(String id_query) {
        Cursor cursor=db.query("article",new String[]{"id","date","name","content"},"id=?",new String[]{id_query},null,null,null);
        List<Message> list =new ArrayList<>();
        while(cursor.moveToNext()){
            Message message = new Message(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
            list.add(message);
        }
        return list;
    }
}
