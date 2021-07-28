package com.zh.testbottonnav;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SPUtil {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    public static void init(Context context){
        if(preferences == null){
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
            editor = preferences.edit();
        }
    }

    public static void setState(String key, boolean isLogin) {
        editor.putBoolean(key, isLogin).apply();
    }

    public static boolean getState(String key){
        boolean isLogin = preferences.getBoolean(key, false);
        return isLogin;
    }

    public static void saveUsername(String key,String username){
        editor.putString(key,username).apply();
    }
    public static String getUsername(String key){
        String username = preferences.getString(key, "");
        return username;
    }

    public static void saveAvatar(String key,String avatar){
        editor.putString(key,avatar).apply();
    }
    public static String getAvatar(String key){
        String avatar = preferences.getString(key, null);
        return avatar;
    }

    public static void saveUserId(String key,String userId){
        editor.putString(key,userId).apply();
    }
    public static String getUserId(String key){
        String userId = preferences.getString(key, "");
        return userId;
    }

    public static void saveId(String key,int id){
        editor.putInt(key,id).apply();
    }
    public static int getId(String key){
        int id = preferences.getInt(key, 0);
        return id;
    }

}
