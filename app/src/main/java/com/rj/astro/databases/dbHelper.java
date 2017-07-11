package com.rj.astro.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Codefingers-1 on 31-05-2017.
 */

public class DbHelper extends SQLiteOpenHelper {

//qid ques user_id tm usertype towho
    private static final int VERSION =2 ;
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
//questaiontab

    private static final String TABLE_QUESTIONS = "questions";
    private static final String TABLE_QUESTIONS_FOR_ADMIN = "questionsadmin";
    private static final String KEY_QID = "qid";
    private static final String KEY_CATAGORY = "catagory";
    private static final String KEY_SUB_CATAGORY = "sub_catagory";
    private static final String KEY_TIME = "time";
    private static final String KEY_QUESTION = "ques";
    private static final String KEY_TOWHO = "towho";

    private static final String TABLE_FEEDBACK = "feedbacks";
    private static final String KEY_FEED_ID = "feed_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_FEEDBACK = "feedback";

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



    private static final String CREATE_TABLE_QUESTIONS = "CREATE TABLE "
            + TABLE_QUESTIONS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_QID + " INTEGER,"
            + KEY_CATAGORY + " TEXT,"
            + KEY_SUB_CATAGORY + " TEXT,"
            + KEY_QUESTION + " TEXT,"
            + KEY_USER_ID + " TEXT,"
            + KEY_TIME + " TEXT,"
            + KEY_USERTYPE + " TEXT,"
            + KEY_TOWHO + " TEXT" + ")";
    private static final String CREATE_TABLE_QUESTIONS_ADMIN = "CREATE TABLE "
            + TABLE_QUESTIONS_FOR_ADMIN + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_QID + " INTEGER,"
            + KEY_CATAGORY + " TEXT,"
            + KEY_SUB_CATAGORY + " TEXT,"
            + KEY_QUESTION + " TEXT,"
            + KEY_USER_ID + " TEXT,"
            + KEY_TIME + " TEXT,"
            + KEY_USERTYPE + " TEXT,"
            + KEY_TOWHO + " TEXT" + ")";


    private static final String CREATE_TABLE_FEEDBACKS = "CREATE TABLE "
            + TABLE_FEEDBACK + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_FEED_ID + " INTEGER,"
            + KEY_NAME + " TEXT,"
            + KEY_EMAIL + " TEXT,"
            + KEY_USERMOBILE + " TEXT,"
            + KEY_FEEDBACK + " TEXT,"
            + KEY_USERTYPE + " TEXT,"
            + KEY_USER_ID + " TEXT" + ")";
// /
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


    public DbHelper(Context context) {
        super(context,DATABASENAME, null,VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_QUESTIONS);
        db.execSQL(CREATE_TABLE_QUESTIONS_ADMIN);
        db.execSQL(CREATE_TABLE_FEEDBACKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS_FOR_ADMIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDBACK);

        // create new tables
        onCreate(db);
    }


    /*
 * Creating a todo
 */
    public void createUSER(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, user.KEY_USER_ID);
        values.put(KEY_USERNAME,user.KEY_USERNAME );
        values.put(KEY_EMAIL, user.KEY_EMAIL);
        values.put(KEY_USERMOBILE, user.KEY_USERMOBILE);
        values.put(KEY_USERTYPE, user.KEY_USERTYPE);
        values.put(KEY_GENDER, user.KEY_GENDER);


        // updating row
        int count =  db.update(TABLE_USERS, values, KEY_USER_ID + " = ?",
                new String[] { String.valueOf(user.KEY_USER_ID) });
        if(count==0){
            // insert row
            db.insert(TABLE_USERS, null, values);
        }


     }
    public void createQUESTION(Questions ques) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_QID, ques.KEY_QID);
        values.put(KEY_CATAGORY,ques.KEY_CATAGORY );
        values.put(KEY_SUB_CATAGORY, ques.KEY_SUB_CATAGORY);
        values.put(KEY_QUESTION, ques.KEY_QUESTION);
        values.put(KEY_USER_ID, ques.KEY_USER_ID);
        values.put(KEY_USERTYPE, ques.KEY_USERTYPE);
        values.put(KEY_TIME, ques.KEY_TIME);
        values.put(KEY_TOWHO, ques.KEY_TOWHO);


        // updating row
        int count =  db.update(TABLE_QUESTIONS, values, KEY_QID + " = ?",
                new String[] { String.valueOf(ques.KEY_QID) });
        if(count==0){
            // insert row
            db.insert(TABLE_QUESTIONS, null, values);
        }

    }
    public void createQUESTION_ADMIN(Questions ques) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_QID, ques.KEY_QID);
        values.put(KEY_CATAGORY,ques.KEY_CATAGORY );
        values.put(KEY_SUB_CATAGORY, ques.KEY_SUB_CATAGORY);
        values.put(KEY_QUESTION, ques.KEY_QUESTION);
        values.put(KEY_USER_ID, ques.KEY_USER_ID);
        values.put(KEY_USERTYPE, ques.KEY_USERTYPE);
        values.put(KEY_TIME, ques.KEY_TIME);
        values.put(KEY_TOWHO, ques.KEY_TOWHO);


        // updating row
        int count =  db.update(TABLE_QUESTIONS_FOR_ADMIN, values, KEY_QID + " = ?",
                new String[] { String.valueOf(ques.KEY_QID) });
        if(count==0){
            // insert row
            db.insert(TABLE_QUESTIONS_FOR_ADMIN, null, values);
        }

    }

    public List<Questions> getAllQuestions(int userid) {
        List<Questions> quess = new ArrayList<Questions>();
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTIONS+ " where "+KEY_USER_ID+" = "+userid+" order by "+KEY_TIME+" ASC";


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Questions qs = new Questions();
                qs.KEY_ID = String.valueOf(c.getInt((c.getColumnIndex(KEY_ID))));
                qs.KEY_QID = String.valueOf(c.getInt((c.getColumnIndex(KEY_QID))));
                qs.KEY_CATAGORY = String.valueOf(c.getString((c.getColumnIndex(KEY_CATAGORY))));
                qs.KEY_SUB_CATAGORY = String.valueOf(c.getString((c.getColumnIndex(KEY_SUB_CATAGORY))));
                qs.KEY_QUESTION = String.valueOf(c.getString((c.getColumnIndex(KEY_QUESTION))));
                qs.KEY_USER_ID = String.valueOf(c.getString((c.getColumnIndex(KEY_USER_ID))));
                qs.KEY_TIME = String.valueOf(c.getString((c.getColumnIndex(KEY_TIME))));
                qs.KEY_TOWHO = String.valueOf(c.getString((c.getColumnIndex(KEY_TOWHO))));
                qs.KEY_USERTYPE = String.valueOf(c.getString((c.getColumnIndex(KEY_USERTYPE))));


                // adding to todo list
                quess.add(qs);
            } while (c.moveToNext());
        }

        return quess;


    }
    public List<Questions> getAllQuestionsForAdmin(int userid) {
        List<Questions> quess = new ArrayList<Questions>();
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTIONS_FOR_ADMIN+ " where "+KEY_USER_ID+" = "+userid+" order by "+KEY_TIME+" ASC";


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Questions qs = new Questions();
                qs.KEY_ID = String.valueOf(c.getInt((c.getColumnIndex(KEY_ID))));
                qs.KEY_QID = String.valueOf(c.getInt((c.getColumnIndex(KEY_QID))));
                qs.KEY_CATAGORY = String.valueOf(c.getString((c.getColumnIndex(KEY_CATAGORY))));
                qs.KEY_SUB_CATAGORY = String.valueOf(c.getString((c.getColumnIndex(KEY_SUB_CATAGORY))));
                qs.KEY_QUESTION = String.valueOf(c.getString((c.getColumnIndex(KEY_QUESTION))));
                qs.KEY_USER_ID = String.valueOf(c.getString((c.getColumnIndex(KEY_USER_ID))));
                qs.KEY_TIME = String.valueOf(c.getString((c.getColumnIndex(KEY_TIME))));
                qs.KEY_TOWHO = String.valueOf(c.getString((c.getColumnIndex(KEY_TOWHO))));
                qs.KEY_USERTYPE = String.valueOf(c.getString((c.getColumnIndex(KEY_USERTYPE))));


                // adding to todo list
                quess.add(qs);
            } while (c.moveToNext());
        }

        return quess;


    }

}
