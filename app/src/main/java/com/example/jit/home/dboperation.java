package com.example.jit.home;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


public class dboperation extends Activity {
    String comment;

    public static ArrayList<Message> messages;
    private static SQLiteOpenHelper eventsData;
    public static SQLiteDatabase db;
    ProgramDetail f = new ProgramDetail();
    ArrayList<String> all;

    public dboperation(Context context) {

        eventsData = new dbhelper(context);

    }

    public List<Message> insertcomment(String comment) {

        messages = new ArrayList<Message>();
        dbhelper helper = new dbhelper(getBaseContext());
        db = helper.getWritableDatabase();


        String str = "select * from '" + comment + "'";
        Cursor cursor = db.rawQuery(str, null);
        //System.out.println(cursor.getString(1));

        while (cursor.moveToNext()) {
            Message message = new Message();

            //////message.setName(cursor.getString(0));
            //message.setLatitude(cursor.getString(1));
            //message.setLongitude(cursor.getString(2));


            messages.add(message);

            System.out.println(cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2)
                    + " " + cursor.getString(3));
        }
        return messages;

    }


    public void insert(String from1) {

        db = eventsData.getReadableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put("text", from1);

        db.insert("comment", null, initialValues);



    }

    public ArrayList<String> select() {

        all = new ArrayList<String>();
        db = eventsData.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT text FROM comment", null);
        if (c != null) {
            while (c.moveToNext()) {
                comment = c.getString(0);
                all.add(comment);
            }
        }
        return all;
    }
}







	
	
	
	
	


