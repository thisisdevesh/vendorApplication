package com.gts.coordinator.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class CoordinatorDbHelper extends SQLiteOpenHelper {

  public static final int DATABASE_VERSION = 6;
  public static final String DATABASE_NAME = "coordinator.db";

  public CoordinatorDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {

    db.execSQL(DriverDBInfo.createTableScript());
    db.execSQL(VendorDBInfo.createTableScript());
    db.execSQL(CabCategoryDBInfo.createTableScript());
    db.execSQL(CabModelDbInfo.createTableScript());
    db.execSQL(CityListDBInfo.createTableScript());
   // db.execSQL(NotificationDBInfo.createTableScript());
//    db.execSQL(WallteDBInfo.createTableScript());
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    db.execSQL(DriverDBInfo.getDropTableScript());
    db.execSQL(VendorDBInfo.getDropTableScript());
    db.execSQL(CabCategoryDBInfo.getDropTableScript());
    db.execSQL(CabModelDbInfo.getDropTableScript());
    db.execSQL(CityListDBInfo.getDropTableScript());
  //  db.execSQL(NotificationDBInfo.getDropTableScript());
 //   db.execSQL(WallteDBInfo.createTableScript());
    onCreate(db);
  }

}
