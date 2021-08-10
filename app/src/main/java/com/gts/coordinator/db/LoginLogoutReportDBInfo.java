package com.gts.coordinator.db;


import android.content.Context;

/**
 * Created by GTS Developer on 16-May-2017 @ 15:41
 */

public class LoginLogoutReportDBInfo {
  public static final String TABLE_NAME = "LOGIN_LOGOUT_REPORT_MASTER ";
  public static final String COLUMN_ID = "LGLORM_ID",
          COULMN_LOGIN_TIME = "LGLORM_LOGIN_TIME",
          COULMN_LOGOUT_TIME = "LGLORM_LOGOUT_TIME",
          COULMN_TOTAL_HOUR = "LGLORM_TOTAL_HOUR";

  private CoordinatorDbHelper loginLogoutReportDB;

  static String createTableScript() {
    StringBuilder tableCreateSql = new StringBuilder()
            .append("CREATE TABLE").append(TABLE_NAME).append("(")
            .append(COLUMN_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
            .append(COULMN_LOGIN_TIME).append(" INTEGER,")
            .append(COULMN_LOGOUT_TIME).append(" INTEGER,")
            .append(COULMN_TOTAL_HOUR).append(" INTEGER")
            .append(");");
    return tableCreateSql.toString();


  }

  static String getDropTableScript() {
    return String.format("DROP TABLE IF EXISTS %S ;", TABLE_NAME);
  }

  public LoginLogoutReportDBInfo(Context context) {
    loginLogoutReportDB = new CoordinatorDbHelper(context);
  }

  public void close() {
    loginLogoutReportDB.close();
  }


}
