package com.example.baitaplab2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDB extends SQLiteOpenHelper {

    public static final String Tablename = "ContactTable";
    public static final String Id = "Id";
    public static final String Name = "Name";
    public static final String Phone = "Phone";
    public static final String Email = "Email";
    public static final String Image = "Image";
    public static final String Status = "Status";
    public MyDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "Create table if not exists " + Tablename +"("
                + Id + " Integer Primary Key, "
                + Name + " Text, "
                + Phone + " Text, "
                + Email + " Text, "
                + Image + " Text, "
                + Status + " Integer)";
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //xoá bảng TableContact đã có
        db.execSQL("Drop table if exists " + Tablename);
        //tạo lại
        onCreate(db);
    }
    public void addContact(Contact c){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put(Id,c.getId());
        value.put(Name,c.getName());
        value.put(Phone,c.getPhone());
        value.put(Email,c.getEmail());
        value.put(Image,c.getImage());
        value.put(Status,c.getStatus()==true?1:0);
        db.insert(Tablename,null,value);
        db.close();
    }
    public ArrayList<Contact> getAllContact(){
        ArrayList<Contact> list = new ArrayList<>();
        //câu truy vấn
        String sql = "Select * from " + Tablename;
        //Lấy đối tượng csdl sqlite
        SQLiteDatabase db = this.getReadableDatabase();
        //Chạy câu truy vấn trả về dạng Cursor
        Cursor cursor = db.rawQuery(sql,null);
        //tạo ArrayList<Contact> để trả về
        if(cursor!=null){
            while(cursor.moveToNext()){
                Contact contact = new Contact(cursor.getInt(0),
                        cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),cursor.getInt(5)==1?true:false);
                list.add(contact);
            }

        }
        return list;
    }
    public void updateContact(int id, Contact contact){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Id, contact.getId());
        value.put(Name, contact.getName());
        value.put(Phone, contact.getPhone());
        value.put(Email, contact.getEmail());
        value.put(Image, contact.getImage());
        value.put(Status,contact.getStatus()==true?1:0);
        db.update(Tablename,value,Id + "=?",
                new String[]{String.valueOf(id)});
        db.close();
    }
    public void deleteContact(int id){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Delete From " + Tablename + " Where ID = " + id;
        db.execSQL(sql);
        db.close();
    }
}
