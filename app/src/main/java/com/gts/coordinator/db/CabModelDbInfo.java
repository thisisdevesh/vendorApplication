package com.gts.coordinator.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gts.coordinator.dao.CabCategory;
import com.gts.coordinator.dao.CabModel;
import com.gts.coordinator.dao.Vendor;

import java.util.ArrayList;

import static com.gts.coordinator.db.DriverDBInfo.deleteAll;

/**
 * Created by GTS Developer on 10-Mar-2017 @ 12:17
 */

public class CabModelDbInfo {

  public static final String TABLE_NAME = "CAB_MODEL_MASTER";
  public static final String
          COLUMN_ID = "CBMM_ID",
          COLUMN_NAME ="CBMM_NAME",
          COLUMN_UID = "CBMM_UID",
          COLUMN_CATEGORY_UID = "CBMM_CATEGORY_UID";

  private CoordinatorDbHelper cabModelDB;

  static String createTableScript(){
    StringBuilder tableCreateSql = new StringBuilder()
            .append("CREATE TABLE ").append(TABLE_NAME)
            .append("(")
            .append(COLUMN_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
            .append(COLUMN_UID).append(" INTEGER ,")
            .append(COLUMN_NAME).append(" TEXT,")
            .append(COLUMN_CATEGORY_UID).append(" INTEGER")
            .append(");");
    return tableCreateSql.toString();
  }

  static String getDropTableScript(){
    return String.format("DROP TABLE IF EXISTS %s ;", TABLE_NAME);
  }

  public CabModelDbInfo(Context context) {
    cabModelDB = new CoordinatorDbHelper(context);
  }
  public void close() {
//        db.close();
    cabModelDB.close();
  }
  public boolean insertCabModel(String modelName, long modelId ,long cabCategoryId)
  {
    SQLiteDatabase db = cabModelDB.getWritableDatabase();
    ContentValues pathElement = new ContentValues();
    pathElement.put(COLUMN_NAME,modelName);
    pathElement.put(COLUMN_UID,modelId);
    pathElement.put(COLUMN_CATEGORY_UID,cabCategoryId);
    long insertResult = db.insert(TABLE_NAME, null, pathElement);
    db.close();
    return insertResult != -1;
  }

  public ArrayList<CabModel> getCabModel(long cabCatId) {
    SQLiteDatabase sqLiteDatabase = cabModelDB.getReadableDatabase();
    ArrayList<CabModel> cabModelList = new ArrayList<CabModel>();
//    String[] projection = {COLUMN_CATEGORY_UID,COLUMN_NAME}; // commented and changed by Ajay Kotiyal @ 20170410 1616
    String[] projection = {COLUMN_UID,COLUMN_NAME};
    String selection = null;
    String[] selectionArgs = null;
    if (cabCatId >= 0) {

      selection = String.format(" %s=? ", COLUMN_CATEGORY_UID);
      selectionArgs = new String[]{String.valueOf(cabCatId)};
    }
    String orderBy = null;

//     orderBy = String.format("%s ASC ", COLUMN_ID);
    Cursor rs = sqLiteDatabase.query(TABLE_NAME, projection, selection, selectionArgs, null, null, orderBy);
    rs.moveToFirst();
    while (!rs.isAfterLast()) {
//      long cabModelID = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_CATEGORY_UID)); // commented and changed by Ajay Kotiyal @ 20170410 1616
      long cabModelID = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_UID));
      String cabModelName = rs.getString(rs.getColumnIndexOrThrow(COLUMN_NAME));
      cabModelList.add(new CabModel(cabModelID,cabModelName));
      rs.moveToNext();
    }
    if (!rs.isClosed())
      rs.close();

    sqLiteDatabase.close();
    return  cabModelList;
  }

/*
  public long deleteCabModels() {
    SQLiteDatabase sqLiteDatabase = cabModelDB.getWritableDatabase();
    long del = sqLiteDatabase.delete(TABLE_NAME, null, null);
    sqLiteDatabase.close();
    return del;
  }
*/
  public static long deleteAllCabModels( SQLiteDatabase sqLiteDatabase ) {
    long del = sqLiteDatabase.delete(TABLE_NAME, null, null);
    deleteAll(sqLiteDatabase);
    return del;
  }
}
