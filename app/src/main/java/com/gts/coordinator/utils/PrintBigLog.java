package com.gts.coordinator.utils;

import android.util.Log;

/**
 * Created by GTS on 4/22/2016.
 */
public class PrintBigLog {



  public static void printString(String tag, String veryLongString) {
    int maxLogSize = 1000;
    for (int i = 0; i <= veryLongString.length() / maxLogSize; i++) {
      int start = i * maxLogSize;
      int end = (i + 1) * maxLogSize;
      end = end > veryLongString.length() ? veryLongString.length() : end;
      Log.i(tag, veryLongString.substring(start, end));
    }
  }

}
