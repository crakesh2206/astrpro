package com.rj.astro.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Codefingers-1 on 31-05-2017.
 */

public class dbHelper extends SQLiteOpenHelper {

//qid ques user_id tm usertype towho
    private static final int VERSION =1 ;
    private static final String DATABASENAME = "astr";
    //users
    private static final String TABLE_USERS = "users";
    private static final String KEY_ID = "_id";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USERNAME = "user_name";
    private static final String KEY_EMAIL   = "email";
    private static final String KEY_USERMOBILE = "mobile";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_USERTYPE = "usertype";
    private static final String KEY_DOB = "dob";
    private static final String KEY_DOT = "dot";
    private static final String KEY_BIRTHPLACE = "birthplace";


    // Todo table create statement
    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_USER_ID + " INTEGER,"
            + KEY_USERNAME + " TEXT,"
            + KEY_EMAIL + " TEXT,"
            + KEY_USERMOBILE + " TEXT,"
            + KEY_GENDER + " TEXT,"
            + KEY_USERTYPE + " TEXT,"
            + KEY_DOB + " TEXT,"
            + KEY_DOT + " TEXT,"
            + KEY_BIRTHPLACE + " TEXT" + ")";
//
//    // Tag table create statement
//    private static final String CREATE_TABLE_TAG = "CREATE TABLE " + TABLE_TAG
//            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TAG_NAME + " TEXT,"
//            + KEY_CREATED_AT + " DATETIME" + ")";
//
//    // todo_tag table create statement
//    private static final String CREATE_TABLE_TODO_TAG = "CREATE TABLE "
//            + TABLE_TODO_TAG + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
//            + KEY_TODO_ID + " INTEGER," + KEY_TAG_ID + " INTEGER,"
//            + KEY_CREATED_AT + " DATETIME" + ")";


    public dbHelper(Context context) {
        super(context,DATABASENAME, null,VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
