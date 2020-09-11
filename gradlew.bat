package com.freedom.playpuzzlequizgame.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.freedom.playpuzzlequizgame.utils.Utils;

import java.util.UUID;

/**
 * Created by Lincoln on 05/05/16.
 */
public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    public static final String PREF_DB_NAME = "Intelligence-prefs";

    private static final String PREF_EMAIL = "pref_email";
    private static final String PREF_KEY_PASS = "pref_key_pass";
    public static final String PREF_NAME = "pref_name";
    private static final String PREF_USERNAME = "pref_username";
    private static final String PREF_AGE = "pref_age";
    private static final String PREF_PHONE = "pref_phone";
    private static final String PREF_PHOTO = "pref_photo";
    private static final String PREF_CREDITS = "pref_credits";
    private static final String PREF_FAILED = "pref_failed";
    private static final String PREF_SUCCESS = "pref_success";
    private static final String PREF_TOTAL = "pref_total";
    private static final String PREF_SHARE_CODE = "pref_share_code";
    private static final String PREF_LAST_UPDATED = "pref_last_updated";
    private static final String IS_LOGIN_LAUNCH = "pref_accountSet";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_DB_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setLoginLaunch(boolean isLogin) {
        editor.putBoolean(IS_LOGIN_LAUNCH, isLogin);
        editor.commit();
    }

    public boolean isLoginLaunch() {
        return pref.getBoolean(IS_LOGIN_LAUNCH, false);
    }

    public void putString(String key, String defValue) {
        editor.putString(key, defValue);
        editor.