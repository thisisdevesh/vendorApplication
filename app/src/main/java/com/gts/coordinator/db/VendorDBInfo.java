package com.gts.coordinator.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gts.coordinator.dao.Vendor;

import java.util.ArrayList;

/**
 * Created by GTS Developer on 21-Feb-2017 @ 14:50
 */

public class VendorDBInfo {

  public static final String TABLE_NAME = "VENDOR_MASTER ";
  public static final String
          COLUMN_ID = "VNDM_ID",
          COLUMN_UID = "VNDM_UID", // THIS ID is primary key on server database for vendor
          COLUMN_NAME = "VNDM_NAME",
          COLUMN_ADDRESS = "VNDM_ADDRESS",
          COLUMN_PHNO = "VNDM_PHNO",
          COLUMN_IS_VERIFIED = "VNDM_IS_VERIFIED",
          COLUMN_VEN_BALANCE = "VNDM_BALANCE",
          COLUMN_STATUS = "VNDM_STATUS";


  private CoordinatorDbHelper vendorPathDB;

  static String createTableScript(){
    StringBuilder tableCreateSql = new StringBuilder()
            .append("CREATE TABLE ").append(TABLE_NAME)
            .append("(")
            .append(COLUMN_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
            .append(COLUMN_UID).append(" INTEGER ,")
            .append(COLUMN_NAME).append(" TEXT,")
            .append(COLUMN_PHNO).append(" TEXT,")
            .append(COLUMN_ADDRESS).append(" TEXT,")
            .append(COLUMN_IS_VERIFIED).append(" INTEGER ,")
            .append(COLUMN_VEN_BALANCE).append(" INTEGER ,")
            .append(COLUMN_STATUS).append(" INTEGER")
            .append(");");
    return tableCreateSql.toString();
  }

  static String getDropTableScript(){
    return String.format("DROP TABLE IF EXISTS %s ;", TABLE_NAME);
  }

  public VendorDBInfo(Context context) {
    this.vendorPathDB = new CoordinatorDbHelper(context);
  }

  public void close() {
//        db.close();
    vendorPathDB.close();
  }

  public boolean insertVendor(String venName, byte status, String phoneNo, String address, long venId, boolean isVerified,long venBalance) {
    SQLiteDatabase db = vendorPathDB.getWritableDatabase();
    boolean isInserted = insertVendor(db, venName, status, phoneNo, address, venId, isVerified, venBalance ) ;
    db.close();
//    Log.i("VendorDBInfo ", "= insertResult ******************=" + insertResult);
    return isInserted ;

  }

  // for batch entry
  public static boolean insertVendor(SQLiteDatabase db, String venName, byte status, String phoneNo, String address, long venId, boolean isVerified,long venBalance) {
    ContentValues pathElement = new ContentValues();
    pathElement.put(COLUMN_NAME, venName);
    pathElement.put(COLUMN_STATUS, status);
    pathElement.put(COLUMN_PHNO, phoneNo);
    pathElement.put(COLUMN_ADDRESS, address);
    pathElement.put(COLUMN_UID, venId);
    pathElement.put(COLUMN_VEN_BALANCE, venBalance);
    pathElement.put(COLUMN_IS_VERIFIED, isVerified);
    long insertResult = db.insertWithOnConflict(TABLE_NAME, null, pathElement, SQLiteDatabase.CONFLICT_REPLACE);
//    Log.i("VendorDBInfo ", "= insertResult ******************=" + insertResult);
    return insertResult != -1;

  }


  public boolean updateVendor( String phoneNumber,long vendorId) {
//    SQLiteDatabase db = driverPathDB.getWritableDatabase();
//
//    ContentValues pathElement = new ContentValues();

//    SQLiteDatabase db = driverPathDB.getWritableDatabase();
    SQLiteDatabase database = vendorPathDB.getWritableDatabase();
//    ContentValues contentValues = new ContentValues();
    ContentValues contentValues = new ContentValues();
    contentValues.put( COLUMN_PHNO, phoneNumber);
//    contentValues.put(COLUMN_CURRENT_LOCATION, currentLocation);
    String selection = String.format(" %s=? ", COLUMN_UID);
    String[] selectionArgs = {String.valueOf(vendorId)};
    long updateResult =  database.update(TABLE_NAME, contentValues, selection, selectionArgs);

//    database.update(TABLE_NAME, " WHERE,)
//    Log.i("DriverDBInfo","Update result is ----- = "+updateResult);
//    database.update(TABLE_NAME, contentValues,"WHERE", null);


    database.close();
    return updateResult != -1;

  }



// rss

  public ArrayList<Vendor> getVendors() {
    SQLiteDatabase sqLiteDatabase = vendorPathDB.getReadableDatabase();
    ArrayList<Vendor> vendorList = new ArrayList<Vendor>();
//    Log.i("DriverDBInfo", "You are inside");
//    String selection = String.format(" %s=? and %s=?", COLUMN_ID, COLUMN_IS_VERIFIED);
    String[] projection = {COLUMN_NAME,
            COLUMN_STATUS,
            COLUMN_PHNO,
            COLUMN_ADDRESS,
            COLUMN_UID,COLUMN_VEN_BALANCE,COLUMN_IS_VERIFIED};
    Cursor rs = sqLiteDatabase.query(TABLE_NAME, projection, null, null, null, null, COLUMN_ID + " ASC");
    rs.moveToFirst();
    while (!rs.isAfterLast()) {
      long vendorID = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_UID));
      long venBalance = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_VEN_BALANCE));
      String name = rs.getString(rs.getColumnIndexOrThrow(COLUMN_NAME));
      String address = rs.getString(rs.getColumnIndexOrThrow(COLUMN_ADDRESS));
      String phoneNum = rs.getString(rs.getColumnIndexOrThrow(COLUMN_PHNO));
      byte status = (byte) rs.getInt(rs.getColumnIndexOrThrow(COLUMN_STATUS));
      boolean isVerified = rs.getInt(rs.getColumnIndex(COLUMN_IS_VERIFIED))==1;
      vendorList.add(new Vendor(name, phoneNum,address,  vendorID,venBalance,isVerified,status));
      rs.moveToNext();
//      Log.i("DriverDBInfo = ", "List = " + driverList);
    }
    if (rs != null && !rs.isClosed())
      rs.close();

    sqLiteDatabase.close();


//        return latLngList;

    return vendorList;
  }

  public ArrayList<Vendor> getVendors(String query) {
    SQLiteDatabase sqLiteDatabase = vendorPathDB.getReadableDatabase();
    ArrayList<Vendor> vendorList = new ArrayList<Vendor>();
    boolean isSearch = query!=null && !query.isEmpty() ;
    Log.i("VendorDBInfo", String.format("getVendors():query=%s;\tisSearch=%s", query, isSearch ) ) ;
//    String selection = String.format(" %s=? and %s=?", COLUMN_ID, COLUMN_IS_VERIFIED);
    StringBuilder sqlBldr = new StringBuilder("SELECT DISTINCT ");
    sqlBldr.append(COLUMN_NAME).append(",")
            .append(COLUMN_STATUS).append(",")
            .append(COLUMN_PHNO).append(",")
            .append(COLUMN_ADDRESS).append(",")
            .append(COLUMN_UID).append(",")
            .append(COLUMN_VEN_BALANCE).append(",")
            .append(COLUMN_IS_VERIFIED).append(" FROM ")
            .append(TABLE_NAME).append(" LEFT JOIN ").append(DriverDBInfo.TABLE_NAME)
            .append(" ON ").append(COLUMN_UID).append("=").append(DriverDBInfo.COLUMN_VND_ID);
    if (isSearch) {
        sqlBldr.append(" where ")
              .append(COLUMN_NAME).append(" LIKE '%").append(query).append("%' OR ")
              .append(COLUMN_PHNO).append(" LIKE '%").append(query).append("%' OR ")
              .append(DriverDBInfo.COLUMN_PHNO).append(" LIKE '%").append(query).append("%' OR ")
              .append(DriverDBInfo.COLUMN_NAME).append(" LIKE '%").append(query).append("%' OR ")
              .append(DriverDBInfo.COLUMN_CAB_NUMBER).append(" LIKE '%").append(query).append("%'; ");
    }
    Log.i("VendorDBInfo", String.format("getVendors():query=%s", sqlBldr ) ) ;
    Cursor rs = sqLiteDatabase.rawQuery(sqlBldr.toString(), null);
    rs.moveToFirst();
    while (!rs.isAfterLast()) {
      long vendorID = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_UID));
      long venBalance = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_VEN_BALANCE));
      String name = rs.getString(rs.getColumnIndexOrThrow(COLUMN_NAME));
      String address = rs.getString(rs.getColumnIndexOrThrow(COLUMN_ADDRESS));
      String phoneNum = rs.getString(rs.getColumnIndexOrThrow(COLUMN_PHNO));
      byte status = (byte) rs.getInt(rs.getColumnIndexOrThrow(COLUMN_STATUS));
      boolean isVerified = rs.getInt(rs.getColumnIndex(COLUMN_IS_VERIFIED))==1;
      vendorList.add(new Vendor(name, phoneNum,address,  vendorID,venBalance,isVerified,status));
      rs.moveToNext();
//      Log.i("DriverDBInfo = ", "List = " + driverList);
    }
    if (rs != null && !rs.isClosed())
      rs.close();

    sqLiteDatabase.close();

    return vendorList;
  }



  public static ArrayList<Vendor> getAllVendors(Context context) {
    VendorDBInfo vendorDBInfo = new VendorDBInfo(context);

    ArrayList<Vendor> vendorList = vendorDBInfo.getVendors();
    vendorDBInfo.close();
    return vendorList;
  }


  public static long deleteAllVendors(SQLiteDatabase sqLiteDatabase) {
    long del = sqLiteDatabase.delete(TABLE_NAME, null, null);
    return del;
  }

}
