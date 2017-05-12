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





}