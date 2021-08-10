package com.gts.coordinator.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.gts.coordinator.dao.Driver;
import com.gts.coordinator.utils.AppConstants;

import java.util.ArrayList;

import static android.icu.text.MessagePattern.ArgType.SELECT;

/**
 * Created by GTS Developer on 08-Feb-2017 @ 13:49
 */

public class DriverDBInfo {
    private final String TAG = getClass().getSimpleName();
    private final double NORTH_WEST_LATITUDE_INDIA = 38.0, NORTH_WEST_LONGITUDE_INDIA = 69.0, SOUTH_EAST_LATITUDE_INDIA = 5.0, SOUTH_EAST_LONGITUDE_INDIA = 97.0;
    public static final String TABLE_NAME = "DRIVER_MASTER ";
    public static final String
            COLUMN_ID = "DRVM_ID",
            COLUMN_NAME = "DRVM_NAME",
            COLUMN_PHNO = "DRVM_PHNO",
            COLUMN_STATUS = "DRVM_STATUS",
            COLUMN_LATITUDE = "DRVM_LAT",
            COLUMN_LONGITUDE = "DRVM_LNG",
            COLUMN_UID = "DRVM_UID",
            COLUMN_VND_ID = "DRVM_VEN_ID",
            COLUMN_CATEGORY = "DRVM_CATEGORY",
            COLUMN_CAB_NAME = "DRVM_CAB_NAME",
            COLUMN_CAB_CAT_ID = "DRVM_CAB_CAT_ID",
            COLUMN_IS_VERIFIED = "DRVM_IS_VERIFIED",
            COLUMN_CAB_NUMBER = "DRVM_CAB_NUMBER",
            COLUMN_DRIVER_UNDER_MAINTENANCE = "DRVM_UNDER_MAINTENANCE",
            COLUMN_DRIVING_LICENCE_NUMBER = "DRVM_DRIVING_LICENCE_NUMBER",
            COLUMN_DRIVING_LICENCE_EXPIRY_DATE = "DRVM_LICENCE_EXPIRY_DATE";


    private CoordinatorDbHelper driverPathDB;

    static String createTableScript() {
        StringBuilder tableCreateSql = new StringBuilder()
                .append("CREATE TABLE ").append(TABLE_NAME)
                .append("(")
                .append(COLUMN_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
                .append(COLUMN_NAME).append(" TEXT,")
                .append(COLUMN_PHNO).append(" TEXT,")
                .append(COLUMN_CATEGORY).append(" TEXT,")
                .append(COLUMN_STATUS).append(" INTEGER,")
                .append(COLUMN_LATITUDE).append(" REAL,")
                .append(COLUMN_LONGITUDE).append(" REAL,")
                .append(COLUMN_UID).append(" INTEGER,")
                .append(COLUMN_VND_ID).append(" INTEGER,")
                .append(COLUMN_CAB_CAT_ID).append(" INTEGER,")
                .append(COLUMN_CAB_NUMBER).append(" TEXT,")
                .append(COLUMN_IS_VERIFIED).append(" INTEGER,")
                .append(COLUMN_CAB_NAME).append(" TEXT,")
                .append(COLUMN_DRIVING_LICENCE_NUMBER).append(" TEXT,")
                .append(COLUMN_DRIVING_LICENCE_EXPIRY_DATE).append(" TEXT,")
                .append(COLUMN_DRIVER_UNDER_MAINTENANCE).append(" INTEGER")
                .append(");");
        return tableCreateSql.toString();
    }

    static String getDropTableScript() {
        return String.format("DROP TABLE IF EXISTS %s ;", TABLE_NAME);
    }

    public DriverDBInfo(Context context) {
        driverPathDB = new CoordinatorDbHelper(context);
    }

    public void close() {
//        db.close();
        driverPathDB.close();
    }

    public long attachDriverWithCab(String driverPhNo, String driverName, double latitude,
                                    double longitude, byte status, long driverID, boolean underMaintenence,
                                    long vendorId, String cabNo, String category, String cabName, long cabCatId,
                                    boolean isVerifiedDriver, String dlNumber, String dlExpire) {

        SQLiteDatabase db = driverPathDB.getWritableDatabase();

        ContentValues driverContents = new ContentValues();
        driverContents.put(COLUMN_NAME, driverName);
        driverContents.put(COLUMN_PHNO, driverPhNo);
        driverContents.put(COLUMN_STATUS, status);
        driverContents.put(COLUMN_LATITUDE, latitude);
        driverContents.put(COLUMN_LONGITUDE, longitude);
        driverContents.put(COLUMN_UID, driverID);
        driverContents.put(COLUMN_VND_ID, vendorId);
        driverContents.put(COLUMN_CAB_NUMBER, cabNo);
        driverContents.put(COLUMN_CATEGORY, category);
        driverContents.put(COLUMN_CAB_NAME, cabName);
        driverContents.put(COLUMN_CAB_CAT_ID, cabCatId);
        driverContents.put(COLUMN_IS_VERIFIED, isVerifiedDriver);
        driverContents.put(COLUMN_DRIVING_LICENCE_NUMBER, dlNumber);
        driverContents.put(COLUMN_DRIVING_LICENCE_EXPIRY_DATE, dlExpire);
//    pathElement.put(COLUMN_CURRENT_LOCATION, currentLocation);
        driverContents.put(COLUMN_DRIVER_UNDER_MAINTENANCE, underMaintenence);
        String whereClause = String.format("%s=?", COLUMN_CAB_NUMBER);
        String[] whereClauseArgs = {cabNo};
        long updateCab = db.update(TABLE_NAME, driverContents, whereClause, whereClauseArgs);
        db.close();
        //        Log.i("UpdateLocationService","DB closed.");
        //        isInserted = insertResult>0 ;
        //        Log.i("DriverDBInfo","Location saved.");
        //        return isInserted ;
        //        return insertResult > 0 ; // if atleast 1 row is inserted, then it should be successful. (in this case, one value will be inserted in a single call)
//    Log.i("DriverDBInfo ", "= insertResult ******************=" + insertResult);
        return updateCab;

    }

    public boolean insertCab(String driverPhNo, String driverName, double latitude,
                             double longitude, byte status, long driverID, boolean underMaintenance,
                             long vendorId, String cabNo, String category, String cabName, long cabCatId,
                             boolean isVerifiedDriver) {

        SQLiteDatabase db = driverPathDB.getWritableDatabase();
        boolean isInserted = insertCab(db, driverPhNo, driverName, latitude, longitude, status, driverID, underMaintenance, vendorId, cabNo, category, cabName, cabCatId, isVerifiedDriver);
        db.close();
        return isInserted;

    }

    // for batch entry// for batch entry
    public static boolean insertCab(SQLiteDatabase db, String driverPhNo, String driverName, double latitude,
                                    double longitude, byte status, long driverID, boolean underMaintenance,
                                    long vendorId, String cabNo, String category, String cabName, long cabCatId,
                                    boolean isVerifiedDriver) {

        ContentValues pathElement = new ContentValues();
        pathElement.put(COLUMN_NAME, driverName);
        pathElement.put(COLUMN_PHNO, driverPhNo);
        pathElement.put(COLUMN_STATUS, status);
        pathElement.put(COLUMN_LATITUDE, latitude);
        pathElement.put(COLUMN_LONGITUDE, longitude);
        pathElement.put(COLUMN_UID, driverID);
        pathElement.put(COLUMN_VND_ID, vendorId);
        pathElement.put(COLUMN_CAB_NUMBER, cabNo);
        pathElement.put(COLUMN_CATEGORY, category);
        pathElement.put(COLUMN_CAB_NAME, cabName);
        pathElement.put(COLUMN_CAB_CAT_ID, cabCatId);
        pathElement.put(COLUMN_IS_VERIFIED, isVerifiedDriver);
//    pathElement.put(COLUMN_CURRENT_LOCATION, currentLocation);
        pathElement.put(COLUMN_DRIVER_UNDER_MAINTENANCE, underMaintenance);

        long insertResult = db.insertWithOnConflict(TABLE_NAME, null, pathElement, SQLiteDatabase.CONFLICT_REPLACE);
//    Log.i("DriverDBInfo ", "= insertResult ******************=" + insertResult);
        return insertResult != -1;

    }



    public ArrayList<Driver> getAllDriverData() {
        SQLiteDatabase sqLiteDatabase = driverPathDB.getReadableDatabase();
        ArrayList<Driver> driverList = new ArrayList<>();
        String[] projection = {COLUMN_NAME, COLUMN_PHNO, COLUMN_STATUS, COLUMN_LATITUDE,
                COLUMN_LONGITUDE, COLUMN_UID, COLUMN_DRIVER_UNDER_MAINTENANCE, COLUMN_VND_ID, COLUMN_CAB_NUMBER, COLUMN_CATEGORY, COLUMN_CAB_NAME, COLUMN_CAB_CAT_ID, COLUMN_IS_VERIFIED};
        ArrayList<String> argsList = new ArrayList<>();
        StringBuilder selection = new StringBuilder();
        String[] selectionArgs = new String[argsList.size()];
        selectionArgs = argsList.toArray(selectionArgs); // convert array to array list
        String orderBy = String.format("%s ASC ", COLUMN_ID);
        Cursor rs = sqLiteDatabase.query(TABLE_NAME, projection, selection.toString(), selectionArgs, null, null, orderBy);
        rs.moveToFirst();
        while (!rs.isAfterLast()) {
            long driverID = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_UID));
            long vendorID = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_VND_ID));
            long cabCatID = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_CAB_CAT_ID));
            String name = rs.getString(rs.getColumnIndexOrThrow(COLUMN_NAME));
            String category = rs.getString(rs.getColumnIndexOrThrow(COLUMN_CATEGORY));
            String phoneNum = rs.getString(rs.getColumnIndexOrThrow(COLUMN_PHNO));
            String cabNo = rs.getString(rs.getColumnIndexOrThrow(COLUMN_CAB_NUMBER));
            String cabName = rs.getString(rs.getColumnIndexOrThrow(COLUMN_CAB_NAME));
//      String currentLocation= rs.getString(rs.getColumnIndexOrThrow(COLUMN_CURRENT_LOCATION));
            byte cabStatus = (byte) rs.getInt(rs.getColumnIndexOrThrow(COLUMN_STATUS));
            double latitude = rs.getDouble(rs.getColumnIndexOrThrow(COLUMN_LATITUDE));
            double longitude = rs.getDouble(rs.getColumnIndexOrThrow(COLUMN_LONGITUDE));
            boolean underMaintenance = rs.getShort(rs.getColumnIndexOrThrow(COLUMN_DRIVER_UNDER_MAINTENANCE)) == 1;
            boolean isVerified = rs.getInt(rs.getColumnIndexOrThrow(COLUMN_IS_VERIFIED)) == 1;
            driverList.add(new Driver(driverID, latitude, longitude, name, phoneNum, cabStatus, underMaintenance, vendorID, cabNo, category, cabName, cabCatID, isVerified));
            rs.moveToNext();
//      Log.i("DriverDBInfo = ", "List = " + driverList);
        }
        if (rs != null && !rs.isClosed())
            rs.close();

        sqLiteDatabase.close();
        return driverList;
    }

    /**
     * Filter driver list according to vendor id and some status (like Free, Busy etc)
     *
     * @param vendorId
     * @param status
     * @return
     */
    // get all driver
    public ArrayList<Driver> getDrivers(long vendorId, byte status) {
        SQLiteDatabase sqLiteDatabase = driverPathDB.getReadableDatabase();

        ArrayList<Driver> driverList = new ArrayList<>();
        Log.i(TAG, String.format("getDrivers():vendorId=%s;\tstatus=%s", vendorId, status));
        String[] projection = {COLUMN_NAME, COLUMN_PHNO, COLUMN_STATUS, COLUMN_LATITUDE, COLUMN_LONGITUDE, COLUMN_UID, COLUMN_DRIVER_UNDER_MAINTENANCE, COLUMN_VND_ID,
                COLUMN_CAB_NUMBER, COLUMN_CATEGORY, COLUMN_CAB_NAME, COLUMN_CAB_CAT_ID, COLUMN_IS_VERIFIED};
//     String selection = COLUMN_VND_ID+"=? and "+COLUMN_STATUS+"=? ";
//    String selection =  String.format(" %s=? and %s=?",COLUMN_VND_ID,COLUMN_STATUS);
        StringBuilder selection = new StringBuilder(); //String.format("( %s >= ? or %s <= ? ) and ( %s >= ? or %s = ? ) ", COLUMN_LATITUDE, COLUMN_LATITUDE, COLUMN_LONGITUDE, COLUMN_LONGITUDE); ;
        ArrayList<String> argsList = new ArrayList<>();
        /******* code for india bound ***********/
        selection.append("( ").append(COLUMN_LATITUDE).append(" >= ? and ").append(COLUMN_LATITUDE).append(" <= ? )")
                .append(" and ( ").append(COLUMN_LONGITUDE).append(" >= ? and ").append(COLUMN_LONGITUDE).append("<= ? ) ");
//    selection.append(COLUMN_VND_ID).append("=? and ").append(COLUMN_STATUS).append("=? ");

        argsList.add(String.valueOf(SOUTH_EAST_LATITUDE_INDIA));
        argsList.add(String.valueOf(NORTH_WEST_LATITUDE_INDIA));
        argsList.add(String.valueOf(NORTH_WEST_LONGITUDE_INDIA));
        argsList.add(String.valueOf(SOUTH_EAST_LONGITUDE_INDIA));
        /******* code for india bound ***********/

//    String[] selectionArgs = null ;
//    selectionArgs = new String[]{String.valueOf(NORTH_WEST_LATITUDE_INDIA), String.valueOf(SOUTH_EAST_LATITUDE_INDIA), String.valueOf(NORTH_WEST_LONGITUDE_INDIA), String.valueOf(SOUTH_EAST_LONGITUDE_INDIA)};

        if (vendorId >= 0 && status >= 0) {
//      selection = String.format(" %s=? and %s=?", COLUMN_VND_ID, COLUMN_STATUS);
//      selectionArgs = new String[]{String.valueOf(vendorId), String.valueOf(status)};
            selection.append(" and "); // india bound
            selection.append(COLUMN_VND_ID).append("=? and ").append(COLUMN_STATUS).append("=?");
            argsList.add(String.valueOf(vendorId));
            argsList.add(String.valueOf(status));
        } else if (vendorId >= 0) {
//      selectionArgs =new String[] {String.valueOf(vendorId), String.valueOf(status)};
//      selection = String.format(" %s=? ", COLUMN_VND_ID);
//      selectionArgs = new String[]{String.valueOf(vendorId)};
            selection.append(" and "); // India Bound
            selection.append(COLUMN_VND_ID).append("=? ");
            argsList.add(String.valueOf(vendorId));
        } else if (status >= 0) {
            selection.append(" and "); // India Bound
            selection.append(COLUMN_STATUS).append("=? ");
//      selection = String.format(" %s=?", COLUMN_STATUS);
//      selectionArgs = new String[]{String.valueOf(status)};
            argsList.add(String.valueOf(status));
        }
        String[] selectionArgs = new String[argsList.size()];
        selectionArgs = argsList.toArray(selectionArgs); // convert array to array list
        String orderBy = String.format("%s ASC ", COLUMN_ID);
        Log.i(TAG, "selection=" + selection);
        Log.i(TAG, "argsList=" + argsList);
        for (int i = 0; i < selectionArgs.length; ++i) {
            Log.i(TAG, "Loop:selectionArgs[i]=" + selectionArgs[i]);
        }
//    Cursor rs = sqLiteDatabase.query(TABLE_NAME, projection, null, null, null, null, COLUMN_ID + " ASC");
        Cursor rs = sqLiteDatabase.query(TABLE_NAME, projection, selection.toString(), selectionArgs, null, null, orderBy);
        rs.moveToFirst();
        while (!rs.isAfterLast()) {
            long driverID = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_UID));
            long vendorID = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_VND_ID));
            long cabCatID = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_CAB_CAT_ID));
            String name = rs.getString(rs.getColumnIndexOrThrow(COLUMN_NAME));
            String category = rs.getString(rs.getColumnIndexOrThrow(COLUMN_CATEGORY));
            String phoneNum = rs.getString(rs.getColumnIndexOrThrow(COLUMN_PHNO));
            String cabNo = rs.getString(rs.getColumnIndexOrThrow(COLUMN_CAB_NUMBER));
            String cabName = rs.getString(rs.getColumnIndexOrThrow(COLUMN_CAB_NAME));
//      String currentLocation= rs.getString(rs.getColumnIndexOrThrow(COLUMN_CURRENT_LOCATION));
            byte cabStatus = (byte) rs.getInt(rs.getColumnIndexOrThrow(COLUMN_STATUS));
            double latitude = rs.getDouble(rs.getColumnIndexOrThrow(COLUMN_LATITUDE));
            double longitude = rs.getDouble(rs.getColumnIndexOrThrow(COLUMN_LONGITUDE));
            boolean underMaintenance = rs.getShort(rs.getColumnIndexOrThrow(COLUMN_DRIVER_UNDER_MAINTENANCE)) == 1;
            boolean isVerified = rs.getInt(rs.getColumnIndexOrThrow(COLUMN_IS_VERIFIED)) == 1;
            driverList.add(new Driver(driverID, latitude, longitude, name, phoneNum, cabStatus, underMaintenance, vendorID, cabNo, category, cabName, cabCatID, isVerified));
            rs.moveToNext();
//      Log.i("DriverDBInfo = ", "List = " + driverList);
        }
        if (rs != null && !rs.isClosed())
            rs.close();
        sqLiteDatabase.close();
        return driverList;
    }


    // get all driver
// get all driver
    public ArrayList<Driver> getDrivers2(String query,byte status) {
        SQLiteDatabase sqLiteDatabase = driverPathDB.getReadableDatabase();
        ArrayList<Driver> driverList = new ArrayList<>();
       // Log.i(TAG, String.format("getDrivers():vendorId=%s;\tstatus=%s", vendorId, status));
        String[] projection = {COLUMN_NAME, COLUMN_PHNO, COLUMN_STATUS, COLUMN_LATITUDE,
                COLUMN_LONGITUDE, COLUMN_UID, COLUMN_DRIVER_UNDER_MAINTENANCE, COLUMN_VND_ID, COLUMN_CAB_NUMBER, COLUMN_CATEGORY, COLUMN_CAB_NAME, COLUMN_CAB_CAT_ID, COLUMN_IS_VERIFIED};
//     String selection = COLUMN_VND_ID+"=? and "+COLUMN_STATUS+"=? ";
//    String selection =  String.format(" %s=? and %s=?",COLUMN_VND_ID,COLUMN_STATUS);
        StringBuilder selection = new StringBuilder(); //String.format("( %s >= ? or %s <= ? ) and ( %s >= ? or %s = ? ) ", COLUMN_LATITUDE, COLUMN_LATITUDE, COLUMN_LONGITUDE, COLUMN_LONGITUDE); ;
        ArrayList<String> argsList = new ArrayList<>();
        /******* code for india bound ***********/
        selection.append("( ").append(COLUMN_LATITUDE).append(" >= ? and ").append(COLUMN_LATITUDE).append(" <= ? )")
                .append(" and ( ").append(COLUMN_LONGITUDE).append(" >= ? and ").append(COLUMN_LONGITUDE).append("<= ? ) ");
//    selection.append(COLUMN_VND_ID).append("=? and ").append(COLUMN_STATUS).append("=? ");

        argsList.add(String.valueOf(SOUTH_EAST_LATITUDE_INDIA));
        argsList.add(String.valueOf(NORTH_WEST_LATITUDE_INDIA));
        argsList.add(String.valueOf(NORTH_WEST_LONGITUDE_INDIA));
        argsList.add(String.valueOf(SOUTH_EAST_LONGITUDE_INDIA));
        /******* code for india bound ***********/

//    String[] selectionArgs = null ;
//    selectionArgs = new String[]{String.valueOf(NORTH_WEST_LATITUDE_INDIA), String.valueOf(SOUTH_EAST_LATITUDE_INDIA), String.valueOf(NORTH_WEST_LONGITUDE_INDIA), String.valueOf(SOUTH_EAST_LONGITUDE_INDIA)};

        if (!query.equals("")&& status>=0) {
//      selection = String.format(" %s=? and %s=?", COLUMN_VND_ID, COLUMN_STATUS);
//      selectionArgs = new String[]{String.valueOf(vendorId), String.valueOf(status)};
            selection.append(" and "); // india bound
            selection.append(COLUMN_CAB_NUMBER).append("=? and ").append(COLUMN_STATUS).append("=?");
            argsList.add(query);
            argsList.add(String.valueOf(status));
        }
        else if (!query.equals("")) {
//      selectionArgs =new String[] {String.valueOf(vendorId), String.valueOf(status)};
//      selection = String.format(" %s=? ", COLUMN_VND_ID);
//      selectionArgs = new String[]{String.valueOf(vendorId)};
            selection.append(" and "); // India Bound
            selection.append(COLUMN_CAB_NUMBER).append("=? ");
            argsList.add(String.valueOf(query));
        }
        else if (status >= 0) {
            selection.append(" and "); // India Bound
            selection.append(COLUMN_STATUS).append("=? ");
//      selection = String.format(" %s=?", COLUMN_STATUS);
//      selectionArgs = new String[]{String.valueOf(status)};
            argsList.add(String.valueOf(status));
        }
        String[] selectionArgs = new String[argsList.size()];
        selectionArgs = argsList.toArray(selectionArgs); // convert array to array list
        String orderBy = String.format("%s ASC ", COLUMN_ID);
        Log.i(TAG, "selection=" + selection);
        Log.i(TAG, "argsList=" + argsList);
        for (int i = 0; i < selectionArgs.length; ++i) {
            Log.i(TAG, "Loop:selectionArgs[i]=" + selectionArgs[i]);
        }
//    Cursor rs = sqLiteDatabase.query(TABLE_NAME, projection, null, null, null, null, COLUMN_ID + " ASC");
        Cursor rs = sqLiteDatabase.query(TABLE_NAME, projection, selection.toString(), selectionArgs, null, null, orderBy);
        rs.moveToFirst();
        while (!rs.isAfterLast()) {
            long driverID = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_UID));
            long vendorID = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_VND_ID));
            long cabCatID = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_CAB_CAT_ID));
            String name = rs.getString(rs.getColumnIndexOrThrow(COLUMN_NAME));
            String category = rs.getString(rs.getColumnIndexOrThrow(COLUMN_CATEGORY));
            String phoneNum = rs.getString(rs.getColumnIndexOrThrow(COLUMN_PHNO));
            String cabNo = rs.getString(rs.getColumnIndexOrThrow(COLUMN_CAB_NUMBER));
            String cabName = rs.getString(rs.getColumnIndexOrThrow(COLUMN_CAB_NAME));
//      String currentLocation= rs.getString(rs.getColumnIndexOrThrow(COLUMN_CURRENT_LOCATION));
            byte cabStatus = (byte) rs.getInt(rs.getColumnIndexOrThrow(COLUMN_STATUS));
            double latitude = rs.getDouble(rs.getColumnIndexOrThrow(COLUMN_LATITUDE));
            double longitude = rs.getDouble(rs.getColumnIndexOrThrow(COLUMN_LONGITUDE));
            boolean underMaintenance = rs.getShort(rs.getColumnIndexOrThrow(COLUMN_DRIVER_UNDER_MAINTENANCE)) == 1;
            boolean isVerified = rs.getInt(rs.getColumnIndexOrThrow(COLUMN_IS_VERIFIED)) == 1;
            driverList.add(new Driver(driverID, latitude, longitude, name, phoneNum, cabStatus, underMaintenance, vendorID, cabNo, category, cabName, cabCatID, isVerified));
            rs.moveToNext();
//      Log.i("DriverDBInfo = ", "List = " + driverList);
        }
        if (rs != null && !rs.isClosed())
            rs.close();
        sqLiteDatabase.close();
        return driverList;
    }
    /**
     * Filter Driver list according to only vendor id
     *
     * @param vendorId
     * @return
     */
    public ArrayList<Driver> getDrivers(long vendorId) {
        return getDrivers(vendorId, (byte) -1);
    }

    /**
     * Filter Driver list according to only status
     *
     * @param status
     * @return
     */
    public ArrayList<Driver> getDrivers(byte status) {
        return getDrivers(-1, status);
    }

    /**
     * Get All Drivers
     *
     * @return
     */
    public ArrayList<Driver> getDrivers() {
        return getDrivers(-1, (byte) -1);
    }

    /**
     * get All drivers by vendorId and status
     *
     * @param context
     * @param vendorId
     * @param status
     * @return
     */
    public static ArrayList<Driver> getDrivers(Context context, long vendorId, byte status) {
        DriverDBInfo driverDBInfo = new DriverDBInfo(context);
        ArrayList<Driver> driverList = driverDBInfo.getDrivers(vendorId, status);
        driverDBInfo.close();
        return driverList;
    }
    public static ArrayList<Driver> getDrivers12(Context context, String cabNo, byte status) {
        DriverDBInfo driverDBInfo = new DriverDBInfo(context);
        ArrayList<Driver> driverList = driverDBInfo.getDrivers2(cabNo, status);
        driverDBInfo.close();
        return driverList;
    }


    //  public static ArrayList<Driver> getDriverLoc(Context context,String number){
//    DriverDBInfo driverDBInfo = new DriverDBInfo(context);
//   // ArrayList<Driver> driverList = driverDBInfo.getDrivers(number);
//    driverDBInfo.close();
//    return driverList;
//  }
    //rss

    /**
     * get all drivers base on  vendorId
     *
     * @param context
     * @param vendorId
     * @return
     */
    public static ArrayList<Driver> getDrivers(Context context, long vendorId) {

        return getDrivers(context, vendorId, (byte) -1);
    }

    /**
     * get all drivers based on status
     *
     * @param context
     * @param status
     * @return
     */
    public static ArrayList<Driver> getDrivers(Context context, byte status) {

        return getDrivers(context, -1, status);
    }

    public static ArrayList<Driver> getDrivers(Context context) {

        return getDrivers(context, -1, (byte) -1);
    }


    public boolean updateDriver(double latitude,
                                double longitude, byte status, long driverID) {
//    SQLiteDatabase db = driverPathDB.getWritableDatabase();
//
//    ContentValues pathElement = new ContentValues();

//    SQLiteDatabase db = driverPathDB.getWritableDatabase();
        SQLiteDatabase database = driverPathDB.getWritableDatabase();
//    ContentValues contentValues = new ContentValues();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, status);
        contentValues.put(COLUMN_LATITUDE, latitude);
        contentValues.put(COLUMN_LONGITUDE, longitude);
        contentValues.put(COLUMN_UID, driverID);
//    contentValues.put(COLUMN_CURRENT_LOCATION, currentLocation);
        String selection = String.format(" %s=? ", COLUMN_UID);
        String[] selectionArgs = {String.valueOf(driverID)};
        long updateResult = database.update(TABLE_NAME, contentValues, selection, selectionArgs);
        database.close();
        return updateResult != -1;

    }

    public boolean updateCab(long driverID, long vendorId) {
        SQLiteDatabase database = driverPathDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_VND_ID, vendorId);
        String selection = String.format(" %s=? ", COLUMN_UID);
        String[] selectionArgs = {String.valueOf(driverID)};
        long updateResult = database.update(TABLE_NAME, contentValues, selection, selectionArgs);
        database.close();
        return updateResult != -1;

    }

    public boolean updateDriverPhone(long driverID, String phoneNum) {
        SQLiteDatabase database = driverPathDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PHNO, phoneNum);
        String selection = String.format(" %s=? ", COLUMN_UID);
        String[] selectionArgs = {String.valueOf(driverID)};
        long updateResult = database.update(TABLE_NAME, contentValues, selection, selectionArgs);
        database.close();
        return updateResult != -1;

    }


    public static long deleteAllDrivers(SQLiteDatabase sqLiteDatabase) {
        long del = sqLiteDatabase.delete(TABLE_NAME, null, null);
        deleteAll(sqLiteDatabase);
        return del;
    }

    public boolean deleteEarlierInfo(long earlierVenID, long driverID) {
        SQLiteDatabase sqLiteDatabase = driverPathDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_UID, driverID);
        contentValues.put(COLUMN_VND_ID, earlierVenID);
        String selection = String.format(" %s=? ", COLUMN_UID);
        String[] selectionArgs = {String.valueOf(driverID)};
        long del = sqLiteDatabase.delete(TABLE_NAME, selection, selectionArgs);//   doubt
        sqLiteDatabase.close();
//   Log.i("DriverDBInfo","del value is ------------:"+del);
        return del != -1;
    }

    public int getCabCount(long vendorId, byte status) {

        SQLiteDatabase sqLiteDatabase = driverPathDB.getReadableDatabase();
//    ArrayList<Driver> driverList = new ArrayList<Driver>();
        int count = 0;
//    Log.i("DriverDBInfo", "You are inside");
//    String[] projection = {COLUMN_ID};
//     String selection = COLUMN_VND_ID+"=? and "+COLUMN_STATUS+"=? ";
//    String selection =  String.format(" %s=? and %s=?",COLUMN_VND_ID,COLUMN_STATUS);
        String selection = "";
//    selection.append(COLUMN_VND_ID).append("=? and ").append(COLUMN_STATUS).append("=? ");
//    String[] selectionArgs = null;


        if (vendorId >= 0 && status >= 0) {
            selection = String.format(" WHERE %s='%s' and %s='%s'", COLUMN_VND_ID, vendorId, COLUMN_STATUS, status);
//      selectionArgs = new String[]{String.valueOf(vendorId), String.valueOf(status)};
        } else if (vendorId >= 0) {
//      selection.append(COLUMN_VND_ID).append("=? ");
//      selectionArgs =new String[] {String.valueOf(vendorId), String.valueOf(status)};
            selection = String.format("WHERE %s='%s' ", COLUMN_VND_ID, vendorId);
//      selectionArgs = new String[]{String.valueOf(vendorId)};
        } else if (status >= 0) {
//      selection.append(COLUMN_STATUS).append("=? ");
            selection = String.format("WHERE %s='%s'", COLUMN_STATUS, status);
//      selectionArgs = new String[]{String.valueOf(status)};
        }


        Cursor rs = sqLiteDatabase.rawQuery("SELECT COUNT(*) AS CAB_COUNT FROM DRIVER_MASTER  " + selection, null);
        rs.moveToFirst();
        count = rs.getInt(rs.getColumnIndex("CAB_COUNT"));
//    Log.i("DriverDBInfo", " getCabCount() : count =" + count);
 /*   while (!rs.isAfterLast()) {
      long driverID = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_UID));
      long vendorID = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_VND_ID));
      String name = rs.getString(rs.getColumnIndexOrThrow(COLUMN_CITY_NAME));
      String phoneNum = rs.getString(rs.getColumnIndexOrThrow(COLUMN_PHNO));
      String cabNo = rs.getString(rs.getColumnIndexOrThrow(COLUMN_CAB_NUMBER));
//      String currentLocation= rs.getString(rs.getColumnIndexOrThrow(COLUMN_CURRENT_LOCATION));
      byte cabStatus = (byte) rs.getInt(rs.getColumnIndexOrThrow(COLUMN_STATUS));
      double latitude = rs.getDouble(rs.getColumnIndexOrThrow(COLUMN_LATITUDE));
      double longitude = rs.getDouble(rs.getColumnIndexOrThrow(COLUMN_LONGITUDE));
      boolean undrMaintenence = rs.getShort(rs.getColumnIndexOrThrow(COLUMN_DRIVER_UNDER_MAINTENANCE)) == 1;
      driverList.add(new Driver(driverID, latitude, longitude, name, phoneNum, cabStatus, undrMaintenence, vendorID, cabNo));
      rs.moveToNext();
//      Log.i("DriverDBInfo = ", "List = " + driverList);
    }*/
        if (rs != null && !rs.isClosed())
            rs.close();

        sqLiteDatabase.close();


//        return latLngList;

        return count;

    }


    //  public int[] getCabsStatus(long vendorId, byte status)
    public int[] getCabsStatus(long vendorId) {

        SQLiteDatabase sqLiteDatabase = driverPathDB.getReadableDatabase();
//    ArrayList<Driver> driverList = new ArrayList<Driver>();
        int[] count = new int[AppConstants.CAB_STATUS_COUNT];
//    Log.i("DriverDBInfo", "You are inside");
//    String[] projection = {COLUMN_ID};
//     String selection = COLUMN_VND_ID+"=? and "+COLUMN_STATUS+"=? ";
//    String selection =  String.format(" %s=? and %s=?",COLUMN_VND_ID,COLUMN_STATUS);
        String whereClause = "";
//    selection.append(COLUMN_VND_ID).append("=? and ").append(COLUMN_STATUS).append("=? ");
//    String[] selectionArgs = null;


        if (vendorId >= 0) {
//      selection.append(COLUMN_VND_ID).append("=? ");
//      selectionArgs =new String[] {String.valueOf(vendorId), String.valueOf(status)};
            whereClause = String.format("WHERE %s='%s' ", COLUMN_VND_ID, vendorId);
//      selectionArgs = new String[]{String.valueOf(vendorId)};
        }

//    String orderBy = String.format("%s ASC ", COLUMN_ID);
        String groupBy = String.format(" group by %s ", COLUMN_STATUS);
        String sql = String.format("SELECT COUNT(*) AS CAB_COUNT,%s FROM %s %s %s", COLUMN_STATUS, TABLE_NAME, whereClause, groupBy);
//    Cursor rs = sqLiteDatabase.query(TABLE_NAME, projection, null, null, null, null, COLUMN_ID + " ASC");
//    Cursor rs = sqLiteDatabase.query(TABLE_NAME, projection, selection, selectionArgs, null, null, orderBy);
//    Cursor rs = sqLiteDatabase.rawQuery("SELECT COUNT(*) AS CAB_COUNT FROM DRIVER_MASTER  " + whereClause, null);
        Cursor rs = sqLiteDatabase.rawQuery(sql, null);
        rs.moveToFirst();
//    Log.i("DriverDBInfo", " getCabCount() : count =" + count);
        while (!rs.isAfterLast()) {
            byte cabStatus = (byte) rs.getInt(rs.getColumnIndexOrThrow(COLUMN_STATUS));
//      count[cabStatus-1] = rs.getInt(rs.getColumnIndex("CAB_COUNT"));
            switch (cabStatus) {
                case AppConstants.CAB_STATUS_FREE:
                    count[0] = rs.getInt(rs.getColumnIndex("CAB_COUNT"));
                    break;
                case AppConstants.CAB_STATUS_BUSY:
                    count[1] = rs.getInt(rs.getColumnIndex("CAB_COUNT"));
                    break;
                case AppConstants.CAB_STATUS_LOGOUT:
                    count[2] = rs.getInt(rs.getColumnIndex("CAB_COUNT"));
                    break;
                case AppConstants.CAB_STATUS_MAINTENANCE:
                    count[3] = rs.getInt(rs.getColumnIndex("CAB_COUNT"));
                    break;

            }
            rs.moveToNext();
//      Log.i("DriverDBInfo = ", "List = " + driverList);
        }
        if (rs != null && !rs.isClosed())
            rs.close();

        sqLiteDatabase.close();


//        return latLngList;

        return count;

    }

    public static int[] getCabsStatus(Context context, long vendorId) {
        DriverDBInfo driverDBInfo = new DriverDBInfo(context);

        int[] cabCount = driverDBInfo.getCabsStatus(vendorId);
        driverDBInfo.close();
        return cabCount;
    }

    public static int[] getCabsStatus(Context context) {
        return getCabsStatus(context, -1);
    }

    public static int getCabCount(Context context, long vendorId, byte status) {
        DriverDBInfo driverDBInfo = new DriverDBInfo(context);

        int cabCount = driverDBInfo.getCabCount(vendorId, status);
        driverDBInfo.close();
        return cabCount;
    }

    public ArrayList<Driver> getCabNum(long vendorId, byte status) {
        SQLiteDatabase sqLiteDatabase = driverPathDB.getReadableDatabase();
        ArrayList<Driver> driverList = new ArrayList<Driver>();
//    Log.i("DriverDBInfo", "You are inside");
        String[] projection = {COLUMN_VND_ID, COLUMN_STATUS, COLUMN_CAB_NUMBER};
//     String selection = COLUMN_VND_ID+"=? and "+COLUMN_STATUS+"=? ";
//    String selection =  String.format(" %s=? and %s=?",COLUMN_VND_ID,COLUMN_STATUS);
        String selection = null;
//    selection.append(COLUMN_VND_ID).append("=? and ").append(COLUMN_STATUS).append("=? ");
        String[] selectionArgs = null;


        if (vendorId >= 0 && status >= 0) {
            selection = String.format(" %s=? and %s=?", COLUMN_VND_ID, COLUMN_STATUS);
            selectionArgs = new String[]{String.valueOf(vendorId), String.valueOf(status)};
        } else if (vendorId >= 0) {
//      selection.append(COLUMN_VND_ID).append("=? ");
//      selectionArgs =new String[] {String.valueOf(vendorId), String.valueOf(status)};
            selection = String.format(" %s=? ", COLUMN_VND_ID);
            selectionArgs = new String[]{String.valueOf(vendorId)};
        } else if (status >= 0) {
//      selection.append(COLUMN_STATUS).append("=? ");
            selection = String.format(" %s=?", COLUMN_STATUS);
            selectionArgs = new String[]{String.valueOf(status)};
        }

        String orderBy = String.format("%s ASC ", COLUMN_ID);
//    Cursor rs = sqLiteDatabase.query(TABLE_NAME, projection, null, null, null, null, COLUMN_ID + " ASC");
        Cursor rs = sqLiteDatabase.query(TABLE_NAME, projection, selection, selectionArgs, null, null, orderBy);
        rs.moveToFirst();
        while (!rs.isAfterLast()) {
            long driverID = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_UID));
            long vendorID = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_VND_ID));
            long cabCatId = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_CAB_CAT_ID));
            String name = rs.getString(rs.getColumnIndexOrThrow(COLUMN_NAME));
            String category = rs.getString(rs.getColumnIndexOrThrow(COLUMN_CATEGORY));
            String phoneNum = rs.getString(rs.getColumnIndexOrThrow(COLUMN_PHNO));
            String cabNo = rs.getString(rs.getColumnIndexOrThrow(COLUMN_CAB_NUMBER));
            String cabName = rs.getString(rs.getColumnIndexOrThrow(COLUMN_CAB_NAME));
//      String currentLocation= rs.getString(rs.getColumnIndexOrThrow(COLUMN_CURRENT_LOCATION));
            byte cabStatus = (byte) rs.getInt(rs.getColumnIndexOrThrow(COLUMN_STATUS));
            double latitude = rs.getDouble(rs.getColumnIndexOrThrow(COLUMN_LATITUDE));
            double longitude = rs.getDouble(rs.getColumnIndexOrThrow(COLUMN_LONGITUDE));
            boolean underMaintenance = rs.getShort(rs.getColumnIndexOrThrow(COLUMN_DRIVER_UNDER_MAINTENANCE)) == 1;
            boolean isverified = rs.getShort(rs.getColumnIndexOrThrow(COLUMN_IS_VERIFIED)) == 1;
            driverList.add(new Driver(driverID, latitude, longitude, name, phoneNum, cabStatus, underMaintenance, vendorID, cabNo, category, cabName, cabCatId, isverified));
            rs.moveToNext();
//      Log.i("DriverDBInfo = ", "List = " + driverList);
        }
        if (rs != null && !rs.isClosed())
            rs.close();

        sqLiteDatabase.close();


//        return latLngList;

        return driverList;
    }


    public ArrayList<Driver> getCabNum(long vendorId) {
        return getDrivers(vendorId, (byte) -1);
    }

    public static ArrayList<Driver> getCabNum(Context context, long vendorId, byte status) {
        DriverDBInfo driverDBInfo = new DriverDBInfo(context);

        ArrayList<Driver> driverList = driverDBInfo.getDrivers(vendorId, status);
        driverDBInfo.close();
        return driverList;
    }


    public ArrayList<String> getCabNumbers(long vendorId, long cabCatId) {
        SQLiteDatabase sqLiteDatabase = driverPathDB.getReadableDatabase();
        ArrayList<String> cabNumberList = new ArrayList<String>();
//    Log.i("DriverDBInfo", "You are inside");
        String[] projection = {COLUMN_CAB_NUMBER};
        String selection = String.format(" %s=? and %s=?", COLUMN_VND_ID, COLUMN_CAB_CAT_ID);
        String[] selectionArgs = new String[]{String.valueOf(vendorId), String.valueOf(cabCatId)};
        Cursor rs = sqLiteDatabase.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        rs.moveToFirst();
        while (!rs.isAfterLast()) {
            String cabNo = rs.getString(rs.getColumnIndexOrThrow(COLUMN_CAB_NUMBER));
//      String currentLocation= rs.getString(rs.getColumnIndexOrThrow(COLUMN_CURRENT_LOCATION));
            cabNumberList.add(cabNo);
            rs.moveToNext();
//      Log.i("DriverDBInfo = ", "List = " + driverList);
        }
        if (rs != null && !rs.isClosed())
            rs.close();
        sqLiteDatabase.close();


//        return latLngList;

        return cabNumberList;
    }


    public ArrayList<Driver> getDriverByNum(String cabNum) {
        SQLiteDatabase sqLiteDatabase = driverPathDB.getReadableDatabase();
        ArrayList<Driver> driverList = new ArrayList<Driver>();
        Log.i("DriverDBInfo", "You are inside");
        String[] projection = {COLUMN_NAME, COLUMN_PHNO, COLUMN_STATUS, COLUMN_LATITUDE,
                COLUMN_LONGITUDE, COLUMN_UID, COLUMN_DRIVER_UNDER_MAINTENANCE, COLUMN_VND_ID, COLUMN_CAB_NUMBER, COLUMN_CATEGORY, COLUMN_CAB_NAME, COLUMN_CAB_CAT_ID, COLUMN_IS_VERIFIED};
        String selection = String.format(" %s=? ", COLUMN_CAB_NUMBER);
        String[] selectionArgs = new String[]{String.valueOf(cabNum)};
        Cursor rs = sqLiteDatabase.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        rs.moveToFirst();

//    Log.i("DriverDBInfo = ", "List ----------= " + driverList);

        while (!rs.isAfterLast()) {
            Log.i("DriverDBInfo = ", "List ------44----= " + driverList);

            long driverID = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_UID));
            long vendorID = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_VND_ID));
            long cabCatID = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_CAB_CAT_ID));
            String name = rs.getString(rs.getColumnIndexOrThrow(COLUMN_NAME));
            String category = rs.getString(rs.getColumnIndexOrThrow(COLUMN_CATEGORY));
            String phoneNum = rs.getString(rs.getColumnIndexOrThrow(COLUMN_PHNO));
            String cabNo = rs.getString(rs.getColumnIndexOrThrow(COLUMN_CAB_NUMBER));
            String cabName = rs.getString(rs.getColumnIndexOrThrow(COLUMN_CAB_NAME));
//      String currentLocation= rs.getString(rs.getColumnIndexOrThrow(COLUMN_CURRENT_LOCATION));
            byte cabStatus = (byte) rs.getInt(rs.getColumnIndexOrThrow(COLUMN_STATUS));
            double latitude = rs.getDouble(rs.getColumnIndexOrThrow(COLUMN_LATITUDE));
            double longitude = rs.getDouble(rs.getColumnIndexOrThrow(COLUMN_LONGITUDE));
            boolean underMaintenance = rs.getShort(rs.getColumnIndexOrThrow(COLUMN_DRIVER_UNDER_MAINTENANCE)) == 1;
            boolean isVerified = rs.getShort(rs.getColumnIndexOrThrow(COLUMN_IS_VERIFIED)) == 1;
            driverList.add(new Driver(driverID, latitude, longitude, name, phoneNum, cabStatus, underMaintenance, vendorID, cabNo, category, cabName, cabCatID, isVerified));
            rs.moveToNext();
            Log.i("DriverDBInfo = ", "List ----------= " + driverList);
        }
        if (rs != null && !rs.isClosed())

            rs.close();

        sqLiteDatabase.close();
        return driverList;

    }


    public long getDriverID(String cabNumber) {
        SQLiteDatabase sqLiteDatabase = driverPathDB.getReadableDatabase();
        String[] projection = {COLUMN_UID};
        String selection = String.format(" %s=? ", COLUMN_CAB_NUMBER);
        String[] selectionArgs = new String[]{String.valueOf(cabNumber)};
        Cursor rs = sqLiteDatabase.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        rs.moveToFirst();
        long driverIdVal = 0;
        while (!rs.isAfterLast()) {
            long driverID = rs.getLong(rs.getColumnIndexOrThrow(COLUMN_UID));
            driverIdVal = driverID;
            rs.moveToNext();
//      Log.i("DriverDBInfo = ", "List ----------= " + driverList);
        }
        if (rs != null && !rs.isClosed())
            rs.close();

        sqLiteDatabase.close();
        return driverIdVal;


    }


    public static boolean deleteAll(SQLiteDatabase db) {
        long deleteResult = db.delete(TABLE_NAME, null, null);
        db.execSQL(String.format("DELETE FROM sqlite_sequence WHERE name = '%s' ", TABLE_NAME)); //reset auto increment (will start from 1 next time)
//        Log.i("GeneralProperties", "static deleteAll():deleteResult=" + deleteResult);
        return deleteResult > 0;
    }

}




