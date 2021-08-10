package com.gts.coordinator.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gts.coordinator.dao.CityList;

import java.util.ArrayList;

/**
 * Created by GTS Developer on 25-Mar-2017 @ 13:18
 */

public class CityListDBInfo {
  public static final String TABLE_NAME ="CITY_LIST_MASTER";
  public static final String
          COLUMN_ID = "CLM_ID",
          COLUMN_CITY_NAME ="CLM_NAME",
          COLUMN_CITY_UID = "CLM_CITY_UID";

  private CoordinatorDbHelper cityListDb;

  static String createTableScript(){
    StringBuilder tableCreateSql = new StringBuilder()
            .append("CREATE TABLE ").append(TABLE_NAME)
            .append("(")
            .append(COLUMN_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
            .append(COLUMN_CITY_UID).append(" INTEGER ,")
            .append(COLUMN_CITY_NAME).append(" TEXT")
            .append(");");
    return tableCreateSql.toString();
  }

  static String getDropTableScript(){
    return String.format("DROP TABLE IF EXISTS %s ;", TABLE_NAME);
  }

  public CityListDBInfo(Context context) {
    cityListDb = new CoordinatorDbHelper(context);
  }
  public void close() {
//        db.close();
    cityListDb.close();
  }


  public boolean insertCityName(String cityName, long cityId)

  {

//    Log.i("insertCityName","entering cityName------------------------");
    SQLiteDatabase db = cityListDb.getWritableDatabase();
    ContentValues pathElement = new ContentValues();
    pathElement.put(COLUMN_CITY_NAME,cityName);
    pathElement.put(COLUMN_CITY_UID,cityId);
//    Log.i("CityListDBInfo--------","*** cityName************"+cityName);
//    Log.i("CityListDBInfo--------","*****cityID**********"+cityId);
    long insertResult = db.insert(TABLE_NAME, null, pathElement);

//    Log.i("CityListDBInfo--------","****** insertResult =  "+insertResult);

    db.close();
    return insertResult != -1;
  }


  public ArrayList<CityList> getCityList()
  {
    SQLiteDatabase sqLiteDatabase = cityListDb.getReadableDatabase();
    ArrayList<CityList> cabModelList = new ArrayList<>();
    String[] projection = {COLUMN_CITY_UID, COLUMN_CITY_NAME};
    String selection = null;
    String[] selectionArgs = null;


//      selection = String.format(" %s=? ", COLUMN_CATEGORY_UID);
//      selectionArgs = new String[]{String.valueOf(cabCatId)};
    
    String orderBy = null;

//     orderBy = String.format("%s ASC ", COLUMN_ID);
    Cursor rs = sqLiteDatabase.query(TABLE_NAME, projection, null,null, null, null, orderBy);
    rs.moveToFirst();
    while (!rs.isAfterLast()) {
      long cityId = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_CITY_UID));
      String cityName = rs.getString(rs.getColumnIndexOrThrow(COLUMN_CITY_NAME));
      cabModelList.add(new CityList(cityId,cityName));
      rs.moveToNext();
    }
    if (!rs.isClosed())
      rs.close();

    sqLiteDatabase.close();
    return  cabModelList;
  }

  public long deleteCityList()
  {
    SQLiteDatabase sqLiteDatabase = cityListDb.getWritableDatabase();
    long del = sqLiteDatabase.delete(TABLE_NAME, null, null);
    sqLiteDatabase.close();
    return del;
  }


}
