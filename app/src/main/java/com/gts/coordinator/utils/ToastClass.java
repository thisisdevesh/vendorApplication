package com.gts.coordinator.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by GTS Developer on 17-Feb-2017 @ 11:43
 */

public class ToastClass {
  public static void showToast(Context context, String message) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
  }
}
