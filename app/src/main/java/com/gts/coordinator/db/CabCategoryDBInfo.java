package com.gts.coordinator.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gts.coordinator.dao.CabCategory;
import com.gts.coordinator.dao.Driver;

import java.util.ArrayList;

import static com.gts.coordinator.db.DriverDBInfo.deleteAll;

/**
 * Created by GTS Developer on 10-Mar-2017 @ 11:25
 */

public class CabCategoryDBInfo {
  public static final String TABLE_NAME = "CAB_CATEGORY_MASTER";
  public static final String
          COLUMN_ID = "CBCM_ID",
          COLUMN_NAME = "CBCM_NAME",
          COLUMN_CODE = "CBCM_CODE",
          COLUMN_UID =  "CBCM_UID";

  private CoordinatorDbHelper cabCategoryDB;


  static String createTableScript(){
    StringBuilder tableCreateSql = new StringBuilder()
            .append("CREATE TABLE ").append(TABLE_NAME)
            .append("(")
            .append(COLUMN_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
            .append(COLUMN_UID).append(" INTEGER ,")
            .append(COLUMN_NAME).append(" TEXT,")
            .append(COLUMN_CODE).append(" TEXT UNIQUE")
            .append(");");
    return tableCreateSql.toString();
  }

  static String getDropTableScript(){
    return String.format("DROP TABLE IF EXISTS %s ;", TABLE_NAME);
  }

  public CabCategoryDBInfo(Context context) {
    cabCategoryDB = new CoordinatorDbHelper(context);
  }
  public void close() {
//        db.close();
    cabCategoryDB.close();
  }
  public boolean insertCabCategory(String categoryName, String categoryCode ,long cabCategoryId)
  {
    SQLiteDatabase db = cabCategoryDB.getWritableDatabase();

    ContentValues pathElement = new ContentValues();
    pathElement.put(COLUMN_NAME,categoryName);
    pathElement.put(COLUMN_CODE,categoryCode);
    pathElement.put(COLUMN_UID,cabCategoryId);
    long insertResult = db.insert(TABLE_NAME, null, pathElement);
    db.close();
    return insertResult != -1;  }


  public ArrayList<CabCategory> getCabCategory(long cabCatId) {
    SQLiteDatabase sqLiteDatabase = cabCategoryDB.getReadableDatabase();
    ArrayList<CabCategory> cabCategoriesList = new ArrayList<CabCategory>();
    String[] projection = {COLUMN_UID,COLUMN_CODE,COLUMN_NAME,};
    String selection = null;
    String[] selectionArgs = null;
    if (cabCatId >= 0) {

      selection = String.format(" %s=? ", COLUMN_UID);
      selectionArgs = new String[]{String.valueOf(cabCatId)};
    }

    String orderBy = String.format("%s ASC ", COLUMN_ID);
//    Cursor rs = sqLiteDatabase.query(TABLE_NAME, projection, null, null, null, null, COLUMN_ID + " ASC");
    Cursor rs = sqLiteDatabase.query(TABLE_NAME, projection, selection, selectionArgs, null, null, orderBy);
    rs.moveToFirst();
    while (!rs.isAfterLast()) {
      long cabCatID = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_UID));
      String categoryCode = rs.getString(rs.getColumnIndexOrThrow(COLUMN_CODE));
      String categoryName = rs.getString(rs.getColumnIndexOrThrow(COLUMN_NAME));
       cabCategoriesList.add(new CabCategory(categoryCode,cabCatID,categoryName));
      rs.moveToNext();
    }
    if (rs != null && !rs.isClosed())
      rs.close();

    sqLiteDatabase.close();

    return  cabCategoriesList;
  }
/*public  long getCategoryId()
{
  SQLiteDatabase sqLiteDatabase = cabCategoryDB.getReadableDatabase();
  long id = sqLiteDatabase.s

}*/

/*
  public long deleteCabCategory() {
    SQLiteDatabase sqLiteDatabase = cabCategoryDB.getWritableDatabase();
    long del = sqLiteDatabase.delete(TABLE_NAME, null, null);
    sqLiteDatabase.close();
    return del;
  }
*/

  public static long deleteAllCabCategory( SQLiteDatabase sqLiteDatabase ) {
    long del = sqLiteDatabase.delete(TABLE_NAME, null, null);
    deleteAll(sqLiteDatabase);
    return del;
  }


  public ArrayList<CabCategory> getCabCategory() {
    return getCabCategory(-1);
  }
}
