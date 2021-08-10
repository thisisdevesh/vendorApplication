package com.gts.coordinator.ui.login;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.gts.coordinator.Activity.SplashActivity;
import com.gts.coordinator.BroadcastReceiver.SMSReceiver;
import com.gts.coordinator.Model.ContractorData.Login.LoginModel;
import com.gts.coordinator.Model.ContractorData.Login.PostLogin;
import com.gts.coordinator.R;
import com.gts.coordinator.ui.otp.OtpFragment;
import com.gts.coordinator.utils.PreferenceData;
import com.gts.coordinator.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClientWcm;
import com.sdsmdg.tastytoast.TastyToast;
import com.splunk.mint.Mint;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "MyTag";
    private TextInputLayout etUserName, etPassword;
    private MaterialButton btnLogin, forgot_password;
    private String deviceId, respMsg, password, userName;
    private ProgressBar processBar;
    private boolean loginStatus;
    private FrameLayout rootView;
    private boolean isFCMReceiverRegistered;
    private Task<InstanceIdResult> fcmRegId;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    long coordinatorId;
    private RetrofitApiInterface apiInterface;
    private TextView dumy_text;
    private LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        Mint.initAndStartSession(this.getApplication(), "4b1e42e1");
        genrateFcmId();
        layoutReference();
        rootViewRefernce();
        if (loginStatus) {
            Intent in = new Intent(getApplicationContext(), SplashActivity.class);
            startActivity(in);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        } else {
            //fcmRegId = getRegistrFcmId(this);
            deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            if (PreferenceData.getString(LoginActivity.this, "user_name") != null) {
                etUserName.getEditText().setText(PreferenceData.getString(LoginActivity.this, "user_name"));
            }
            btnLogin.setOnClickListener(view -> {
                forgot_password.setEnabled(false);

                userName = etUserName.getEditText().getText().toString().trim();
                password = etPassword.getEditText().getText().toString().trim();
                if (userName.equals("")) {
                    etUserName.setEnabled(true);
                    etUserName.setError("Please Enter E-Mail");
                } else if (!userName.contains(".") || !userName.contains("@")) {
                    etUserName.setEnabled(true);
                    etUserName.setError("Please Enter Valid Email");
                    //  Utils.showOkAlert(LoginActivity.this, getString(R.string.error), "Please Enter Valid Email", true);
                    //  ToastClass.showToast(LoginActivity.this, "Please Enter Valid Email");
                } else if (password.equals("")) {
                    etPassword.requestFocus();
                    etPassword.setEnabled(true);
                    etPassword.setError("Please Enter Password");
                } else if (Utils.getConnectivityStatus(LoginActivity.this) == 0) {
                    Utils.showOkAlert(LoginActivity.this, getString(R.string.info), "Please check your internet connection", false);
                } else {
                    userName = String.valueOf(etUserName.getEditText().getText());
                    password = String.valueOf(etPassword.getEditText().getText());
                    PreferenceData.saveString(LoginActivity.this, "user_name", userName);
                    etUserName.setEnabled(false);
                    etPassword.setEnabled(false);
                    allPermission();
                }
            });
        }


    }

    private void rootViewRefernce() {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            rootView.getWindowVisibleDisplayFrame(r);
            int screenHeight = rootView.getRootView().getHeight();
            int keypadHeight = screenHeight - r.bottom;
            if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                //rootView.setBackground(getResources().getDrawable(R.drawable.background_hide));
                rootView.setBackground(getResources().getDrawable(R.drawable.background_hide));
                if (dumy_text.getVisibility() == View.VISIBLE) {
                    dumy_text.setVisibility(View.GONE);
                }
            } else {
                rootView.setBackground(getResources().getDrawable(R.drawable.logo_gts2));
                if (dumy_text.getVisibility() == View.GONE) {
                    dumy_text.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void layoutReference() {
        processBar = findViewById(R.id.process_bar);
        rootView = findViewById(R.id.root_view);
        loginStatus = PreferenceData.getBoolean(this, "login_status");
        etUserName = findViewById(R.id.et_user_name);
        etPassword = findViewById(R.id.et_password);
        forgot_password = findViewById(R.id.forget_password);
        dumy_text = findViewById(R.id.dumy_text);
        btnLogin = findViewById(R.id.btn_login);
       /* callForgotPassword();*/
    }

    private void callForgotPassword() {
        forgot_password.setOnClickListener(view -> {
            btnLogin.setEnabled(false);
            forgot_password.setEnabled(false);

            if (Utils.getConnectivityStatus(LoginActivity.this) == 0) {
                Utils.showOkAlert(LoginActivity.this, getString(R.string.info), "Please check your internet connection", false);
            } else {
                String userName = etUserName.getEditText().getText().toString();
                if (!userName.equals("")) {
                    forgot_password.setEnabled(true);
                    btnLogin.setEnabled(true);
                    OtpFragment otpFragment = new OtpFragment();
                    otpFragment = OtpFragment.newInstance(userName);
                    otpFragment.setCancelable(false);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
                    ft = getSupportFragmentManager().beginTransaction();
                    prev = getSupportFragmentManager().findFragmentByTag("dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);
                    otpFragment.show(ft, "dialog");
                }
                else {
                    etUserName.setError("Please Enter User Name");
                    etUserName.requestFocus();
                }
            }
        });
    }

    private void genrateFcmId() {
        // Get token
        if (isGooglePlayServicesAvailable(LoginActivity.this)) {
            FirebaseApp.initializeApp(this);
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        fcmRegId = FirebaseInstanceId.getInstance().getInstanceId();
                        PreferenceData.saveString(this, "fcmToken", fcmRegId.toString());
                        Log.e("token firebase", "" + fcmRegId);
                        Log.e("Token", "" + fcmRegId);
                    });
        } else {
            Toast.makeText(this, "Please  Update Google Play Service", Toast.LENGTH_SHORT).show();

        }


    }

    // check google services
    public boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        registerReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isFCMReceiverRegistered = false;
    }

    private void allPermission() {
        if (checkAndRequestPermissions() == true) {
            if (fcmRegId == null || "".equals(fcmRegId) || "null".equals(fcmRegId)) {
                DialogInterface.OnCancelListener cancelListener = new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        processBar.setVisibility(View.GONE);
                    }
                };
            } else {
                etUserName.setEnabled(false);
                etPassword.setEnabled(false);
                login();
            }
        }
    }

    //used for permissions checking
    private boolean checkAndRequestPermissions() {
        int permissionCAMERA = ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CAMERA);
        int storagePermission = ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int readContactPermission = ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_CONTACTS);
        int locationPermission = ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int callPermission = ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CALL_PHONE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (readContactPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (callPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);

        }
        List<String> listGrantedPermission = new ArrayList<>();
        if (storagePermission == PackageManager.PERMISSION_GRANTED) {
            listGrantedPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionCAMERA == PackageManager.PERMISSION_GRANTED) {
            listGrantedPermission.add(Manifest.permission.CAMERA);
        }
        if (readContactPermission == PackageManager.PERMISSION_GRANTED) {
            listGrantedPermission.add(Manifest.permission.READ_CONTACTS);
        }
        if (locationPermission == PackageManager.PERMISSION_GRANTED) {
            listGrantedPermission.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (callPermission == PackageManager.PERMISSION_GRANTED) {
            listGrantedPermission.add(Manifest.permission.CALL_PHONE);
        }
        Log.i("LoginActibiyt", "************" + listPermissionsNeeded.size());
        Log.i("LoginActibiyt", "*****GrantedList*******  " + listGrantedPermission.size());
        if (listGrantedPermission.size() < 4) {
            ActivityCompat.requestPermissions(LoginActivity.this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_CAMERA);
            return false;
        } else {
            return true;
        }
    }

    private void startProcessing() {
        processBar.setVisibility(View.VISIBLE);
    }

    private void stopProcessing() {
        processBar.setVisibility(View.GONE);
    }

    /**
     * This is login for login where url will hit through Volley
     */
    private void login() {
        closeKeyboadrd();
        btnLogin.setVisibility(View.GONE);
        startProcessing();
        loginViewModel.getApiStatus(
                userName, password, Utils.getAppVersionName(LoginActivity.this), Utils.getOSVersionName(), Utils.getDeviceName(), "Android",
                fcmRegId.toString(), Utils.getAppVersionCode(LoginActivity.this), Build.VERSION.SDK_INT, deviceId
        ).observe(LoginActivity.this, integer -> {
            if (integer == 0) {
                stopProcessing();
            } else if (integer == 1) {
                forgot_password.setVisibility(View.VISIBLE);
                forgot_password.setEnabled(true);
                btnLogin.setEnabled(true);
                showLayout();
            } else if (integer == 2) {
                stopProcessing();
                startActivity(new Intent(getApplicationContext(), SplashActivity.class));
                finish();
            } else {
                forgot_password.setVisibility(View.VISIBLE);
                forgot_password.setEnabled(true);
                btnLogin.setEnabled(true);
                showLayout();
            }
        });
        loginViewModel.getErrorMessage().observe(LoginActivity.this, s -> {
            if (s != null) {
                TastyToast.makeText(LoginActivity.this, s, TastyToast.LENGTH_SHORT, TastyToast.INFO).show();
            }
        });
    }

    private void showLayout() {
        btnLogin.setVisibility(View.VISIBLE);
        etUserName.setEnabled(true);
        etPassword.setEnabled(true);
        stopProcessing();
    }

    // todo not usable yet
/*    private String getRegistrFcmId(Context context) {
        String registrationId = PreferenceData.getString(context, getString(R.string.arg_fcm_token));
        if ("false".equalsIgnoreCase(registrationId)) {
            return "";
        }
        return registrationId;
    }*/

    private void registerReceiver() {
        if (!isFCMReceiverRegistered) {
            isFCMReceiverRegistered = true;
        }
    }

    // todo close Keyboard
    private void closeKeyboadrd() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults.length > 0 && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        && grantResults.length > 0 && grantResults[3] == PackageManager.PERMISSION_GRANTED) {
                    if (fcmRegId == null || "".equals(fcmRegId) || "null".equals(fcmRegId)) {
                        DialogInterface.OnCancelListener cancelListener = dialog -> {
                            showLayout();
                           // Utils.showOkAlert(LoginActivity.this, getString(R.string.error), "Device Error Please Restart Your Phone ", false);
                        };
                    } else {
                        login(); // login(fcmRegId);
                    }
                } else {
                    login();
                }
                break;
        }
    }
}