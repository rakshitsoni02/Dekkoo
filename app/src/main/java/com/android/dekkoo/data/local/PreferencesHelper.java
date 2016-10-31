package com.android.dekkoo.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.dekkoo.injection.ApplicationContext;

import javax.inject.Inject;

public class PreferencesHelper {
    public static final String PREF_FILE_NAME = "android_boilerplate_pref_file";
    public static final String TITTLE_VALUE = "tittle_selection";
    private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

    public void setTittle(String value) {
        mPref.edit().putString(TITTLE_VALUE, value).apply();
    }

    public String getTittle() {
        return mPref.getString(TITTLE_VALUE, "");
    }
}
