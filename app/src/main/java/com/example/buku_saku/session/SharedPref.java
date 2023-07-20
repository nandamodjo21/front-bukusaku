package com.example.buku_saku.session;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private static SharedPref instance;
    private static Context mCtx;

    private  static final String SHARED_PREF_NAME = "buku-saku";
    private static final  String KEY_ID = "id_login";
    private static final String KEY_NIK = "nik";
    private static final  String KEY_USERNAME = "username";

    String channelnotif = "channelA" ;
    String channelid = "default" ;


    private SharedPref(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPref getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPref(context);
        }
        return instance;
    }




    public  boolean session(String id_login, String nik, String username){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID, id_login);
        editor.putString(KEY_NIK, nik);
        editor.putString(KEY_USERNAME, username);
        editor.apply();
        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_ID, "0") != "0"){
            return true;
        }
        return false;
    }

    public  boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getKeyId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ID, null);
    }
    public String getKeyUsername(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getKeyNik(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NIK,null);
    }
}