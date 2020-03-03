package com.urufit.aitum.ui;

import android.content.Context;
import android.content.SharedPreferences;

public final class SharedPreferenc {

    public static final String SHARED_PREF_NAME = "shred_pref";

    public static boolean getBoolean(Context context, String key) {
        return context.getSharedPreferences(SHARED_PREF_NAME, 0).getBoolean(key, false);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return context.getSharedPreferences(SHARED_PREF_NAME, 0).getBoolean(key, defaultValue);
    }

    public static SharedPreferences.Editor putBoolean(Context context, String key, boolean value) {
        return context.getSharedPreferences(SHARED_PREF_NAME, 0).edit().putBoolean(key, value);
    }
}
