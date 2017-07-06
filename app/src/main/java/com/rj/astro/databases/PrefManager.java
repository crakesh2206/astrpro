/**
 * Managing Preferences.. ie., 
 *
 * First Time Launch and Daily Checkin
 * 
 * @author DroidOXY
 */

package com.rj.astro.databases;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yashDev on 05/05/16.
 */
public class PrefManager {
    private static final String TOKEN = "device_id";
    private static final String USER_ID = "userId";
    private static final String USE_MOBILE = "mobile";
    private static final String USE_GENDER = "gender";
    private static final String USE_DOB = "dob";
    private static final String USE_DOT = "dot";
    private static final String USE_BIRTHPLACE = "birthplace";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "astr";
    private static final String USERMAIL = "emailaddress";
    private static final String IS_USERTYPE_ADMIN = "isUserAdmin";
    private static final String USER_NAME = "username";
    private static final String IS_LOG_IN = "Islogin";
    private String dot;

    private boolean tokenSent =false;


    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setUserName(String user) {
        editor.putString(USER_NAME, user);
        editor.commit();
    }

    public String getUserName() {
        return pref.getString(USER_NAME,null);
    }
    public void setUserEmail(String user) {
        editor.putString(USERMAIL, user);
        editor.commit();
    }

    public String getUserEmail() {
        return pref.getString(USERMAIL,null);
    }
    public void setLogIn(boolean isCheckin) {
        editor.putBoolean(IS_LOG_IN, isCheckin);
        editor.commit();
    }

    public boolean isLogedIn() {
        return pref.getBoolean(IS_LOG_IN,false);
    }

    public void setUserIsAdmin(boolean isUserisAdmin) {
        editor.putBoolean(IS_USERTYPE_ADMIN, isUserisAdmin);
        editor.commit();
    }

    public boolean isUserisAdmin() {
        return pref.getBoolean(IS_USERTYPE_ADMIN,false);
    }


    public void setToken(String refreshedToken) {
        editor.putString(TOKEN, refreshedToken);
        editor.commit();
    }

    public String getToken() {
        return pref.getString(TOKEN,null);
    }


    public void setUserId(String id) {
        editor.putString(USER_ID, id);
        editor.commit();
    }
//
    public String getUserId() {
        return pref.getString(USER_ID,null);
    }

    public void setUserMobile(String userMobile) {
        editor.putString(USE_MOBILE,userMobile);
        editor.commit();
    }

    public void setGender(String gender) {
        editor.putString(USE_GENDER,gender);
        editor.commit();
    }

    public void setDOB(String DOB) {
        editor.putString(USE_DOB,DOB);
        editor.commit();
    }

    public void setDot(String dot) {
        editor.putString(USE_DOT,dot);
        editor.commit();
    }

    public void setBirthPlace(String birthPlace) {
        editor.putString(USE_BIRTHPLACE,birthPlace);
        editor.commit();
    }

    public void setTokenSent(boolean tokenSent) {
        this.tokenSent = tokenSent;
    }
    public boolean isTokenSent() {
        return tokenSent;
    }

    public  void clear() {
        editor.clear().commit();
    }
}
