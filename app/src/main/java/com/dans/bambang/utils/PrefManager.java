package com.dans.bambang.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;


import java.lang.reflect.Type;

public class PrefManager {

    private static SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "PromoPick";
    private static final String IS_LOGIN = "IsLogin";
    private static final String LOGIN_WITH = "LoginWith";

    private static final String TAG_UPLOAD_PHOTO = "UrlPhoto";
    private static final String IS_URL_OR_BITMAP = "IsUrlBitmap";
//    private static final String IS_BACK_TO_LIST_DEPENDENT = "IsBackToListDependent";

    private static final String TAG_USERNAME = "username";
    private static final String TAG_EMAIL = "email";


    public PrefManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setTagUsername(String username) {
        editor.putString(TAG_USERNAME, username);
        editor.commit();
    }

    public String getTagUsername() {
        return pref.getString(TAG_USERNAME, "");
    }

    public void setIsLogin(boolean isFirstTime) {
        editor.putBoolean(IS_LOGIN, isFirstTime);
        editor.commit();
    }

    public boolean getIsLogin() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void setLoginWith(String loginWith) {
        editor.putString(LOGIN_WITH, loginWith);
        editor.commit();
    }

    public String getLoginWith() {
        return pref.getString(LOGIN_WITH, "");
    }

    public String getTagUploadPhoto() {
        return pref.getString(TAG_UPLOAD_PHOTO, "");
    }

    public void setTagUploadPhoto(String photo) {
        editor.putString(TAG_UPLOAD_PHOTO, photo);
        editor.commit();
    }

    public String getIsUrlOrBitmap() {
        return pref.getString(IS_URL_OR_BITMAP, "");
    }

    public void setIsUrlOrBitmap(String photo) {
        editor.putString(IS_URL_OR_BITMAP, photo);
        editor.commit();
    }


    public void setClearPrefrencessLogout() {

        editor.remove(IS_LOGIN);
        editor.remove(LOGIN_WITH);
        editor.remove(TAG_UPLOAD_PHOTO);
        editor.remove(IS_URL_OR_BITMAP);
        editor.remove(TAG_USERNAME);
        editor.remove(TAG_EMAIL);
        editor.commit();

    }

}
