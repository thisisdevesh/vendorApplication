package com.gts.coordinator.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by GTS Developer on 31-Dec-2016 @ 15:26
 */

public class PreferenceData {
  private static final String PREF_KEY = "GTSCoordinatorAppKey";
  public PreferenceData(Context context) {
  }

  public static void saveString(Context context, String key, String value)
  {
    SharedPreferences preferenceData = context.getSharedPreferences(PREF_KEY,0);
    SharedPreferences.Editor editor = preferenceData.edit();
    editor.putString(key,value);
    editor.commit();
  }
  public static void saveLong(Context context, String key, long value) {
    SharedPreferences prefs = context.getSharedPreferences(PREF_KEY, 0);
    SharedPreferences.Editor edit = prefs.edit();
    edit.putLong(key, value);
    edit.commit();
  }
  public static void saveInt(Context context, String key, int value) {
    SharedPreferences prefs = context.getSharedPreferences(PREF_KEY, 0);
    SharedPreferences.Editor edit = prefs.edit();
    edit.putInt(key, value);
    edit.commit();
  }

  public static void saveBoolean(Context context, String key, boolean value) {
    SharedPreferences prefs = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
    SharedPreferences.Editor edit = prefs.edit();
    edit.putBoolean(key, value);
    edit.commit();
  }

  public static String getString(Context context, String key) {
    SharedPreferences sharedpreferences = context.getSharedPreferences(PREF_KEY, 0);

    return sharedpreferences.getString(key, "");
  }


  public static Boolean getBoolean(Context context, String key) {
    SharedPreferences sharedpreferences = context.getSharedPreferences(PREF_KEY, 0);

    return sharedpreferences.getBoolean(key,false);
  }


  public static Long getLong(Context context, String key) {
    SharedPreferences sharedpreferences = context.getSharedPreferences(PREF_KEY, 0);

    return sharedpreferences.getLong(key, 0);
  }
  public static Integer getInt(Context context, String key) {
    SharedPreferences sharedpreferences = context.getSharedPreferences(PREF_KEY, 0);

    return sharedpreferences.getInt(key, 0);
  }
}
