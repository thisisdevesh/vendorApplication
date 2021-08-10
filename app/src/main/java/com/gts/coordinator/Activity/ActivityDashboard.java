package com.gts.coordinator.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

import com.gts.coordinator.Activity.From.AddDriverForm;
import com.gts.coordinator.BroadcastReceiver.MyApplication;
import com.gts.coordinator.Model.ContractorData.AppInfo.VersionCode;
import com.gts.coordinator.Model.ContractorData.UnreadNotificationCount.NotificationCountResponce;
import com.gts.coordinator.Model.ContractorData.UnreadNotificationCount.PostData;
import com.gts.coordinator.Retrofit.RetrofitClient;
import com.gts.coordinator.db.CabCategoryDBInfo;
import com.gts.coordinator.db.CabModelDbInfo;
import com.gts.coordinator.fragments.MapViewFragment;
import com.gts.coordinator.fragments.ProfileEditFragment;
import com.gts.coordinator.R;
import com.gts.coordinator.callback.TaskListener;
import com.gts.coordinator.db.CoordinatorDbHelper;
import com.gts.coordinator.db.DriverDBInfo;
import com.gts.coordinator.db.VendorDBInfo;
import com.gts.coordinator.service.DriverDataService;
import com.gts.coordinator.service.MyBookingService;
import com.gts.coordinator.ui.login.LoginActivity;
import com.gts.coordinator.ui.notification.ActivityNotification;
import com.gts.coordinator.ui.referralDriver.ReferDriverActivity;
import com.gts.coordinator.ui.vendorIncome.IncomActivity;
import com.gts.coordinator.ui.wallte.ActivityWallet;
import com.gts.coordinator.utils.PreferenceData;
import com.gts.coordinator.utils.Utils;
import com.gts.coordinator.service.ServiceProcessor;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.sdsmdg.tastytoast.TastyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnFocusChangeListener, View.OnClickListener,
        ProfileEditFragment.updateNumber {
    //private DriverDetailViewModel driverDetailViewModel;
    public static final int Update_Available = 100;
    private final String TAG = "MyTag";
    protected MapViewFragment.OnMapUpdateListener mapUpdateListener;
    //   private TabFragment homeFragment;
    private MapViewFragment mapViewFragment;
    private long contractorID;
    private ImageView profEdit, ivRefresh, button_search_loc = null, button_search_close;
    //  private MaterialButton ivSearch;
    private TextView butHome, butRegistation, butWallte, butNotification, butReport, butLogout, bookingList, ivSearch, butReferDriver, incom, offers;
    private boolean loginStatus;
    private DrawerLayout drawer;
    private String location;
    private CardView search_loc, search_cab;
    private Toolbar toolbar;
    private ProgressBar progressDialog;
    private int ste;
    private AlertDialog dialog;
    private RetrofitApiInterface apiInterface;
    private String phoneNo;
    private TextView userName, userPhoneNo, userEmail, versionName, textCounter;
    private EditText searchView;
    private LinearLayout bott_vendor, bott_cab, bott_driver, bott_wallate, id_bottom_seet_layout;

    // = MyDatabase.getInstance(ActivityDashboard.this);
    // private VendorListFragment vendorListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //driverDetailViewModel = new ViewModelProvider(this).get(DriverDetailViewModel.class);
        // Mint.initAndStartSession(this.getApplication(), "4b1e42e1");
        //  vendorListFragment = new VendorListFragment();
        //   checkUpdate();
        setTitle(getString(R.string.app_name));
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intLayoutReference();
        ivRefresh.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        searchView.setOnFocusChangeListener(this);
        contractorID = PreferenceData.getLong(ActivityDashboard.this, "cont_id");
        phoneNo = PreferenceData.getString(ActivityDashboard.this, "cont_phone1");
        mapViewFragment = new MapViewFragment();
        //vendorListFragment = new VendorListFragment();
        intDrawerLayout();
        callApiGetNotificationList();
        eventSearchView();
        Intent mIntent = new Intent(ActivityDashboard.this, DriverDataService.class);
        DriverDataService.enqueueWork(this, mIntent);

    }

    private void eventSearchView() {
        searchView.setOnClickListener(v -> drawer.closeDrawers());
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 1) {
                    button_search_loc.setVisibility(View.VISIBLE);
                    button_search_close.setVisibility(View.GONE);
                    drawer.closeDrawers();
                } else if (s.length() < 1) {
                    button_search_loc.setVisibility(View.GONE);
                    button_search_close.setVisibility(View.VISIBLE);
                    // closeSearchViewClickBackPressed();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        // search through soft keyboard
        searchView.setOnEditorActionListener((v, keyAction, keyEvent) -> {
            if (keyAction == EditorInfo.IME_ACTION_SEARCH || (keyEvent != null && KeyEvent.KEYCODE_ENTER == keyEvent.getKeyCode()
                    && keyEvent.getAction() == KeyEvent.ACTION_DOWN)) {
                if (ste == 1) {
                    closeKeyboard();

                    if (location.equals("")) {
                        Utils.showOkAlert(ActivityDashboard.this, getString(R.string.info), getResources().getString(R.string.enter_text), true);
                    } else {
                        //homeFragment.searchQuery(location, 1);
                    }
                } else if (ste == 0) {
                    closeKeyboard();
                    location = searchView.getText().toString();
                    if (location.equals("")) {
                        Utils.showOkAlert(ActivityDashboard.this, getString(R.string.info), getResources().getString(R.string.enter_text), true);
                    } else {

                        searchView.setText("");
                        closeSearchViewClickBackPressed();
                    }
                }

                return true;
            }
            return false;
        });
        // event for search close button
        //closeSearchView();
    }

    private void closeSearchView() {
        // Toast.makeText(this, "closeSearchView", Toast.LENGTH_SHORT).show();
        closeKeyboard();
       search_loc.setVisibility(View.GONE);
        // rv.setVisibility(View.GONE);
        search_cab.setVisibility(View.VISIBLE);
        searchView.setText("");
    }

    private void closeSearchViewClickBackPressed() {
        //   Log.e(TAG, "onClose: " + String.valueOf(ste));
        closeKeyboard();
        search_loc.setVisibility(View.GONE);
        search_cab.setVisibility(View.VISIBLE);
        searchView.setText("");
        if (ste == 1) {
            //  homeFragment.searchQuery(location, 2);
        } else {
        }
    }

    private void intDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                closeKeyboard();
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        versionName = header.findViewById(R.id.txt_version_name);
        versionName.setText(Utils.getAppVersionName(this));
        profEdit = header.findViewById(R.id.profile_edit);
        butHome = header.findViewById(R.id.home);
        textCounter = header.findViewById(R.id.text_counter);
        // set for unread notification
        butRegistation = header.findViewById(R.id.registration);
        butReferDriver = header.findViewById(R.id.refer_driver);
        offers = header.findViewById(R.id.offers);
        butWallte = header.findViewById(R.id.wallet);
        incom = header.findViewById(R.id.income);
        butNotification = header.findViewById(R.id.notification);
        butReport = header.findViewById(R.id.report);
        butLogout = header.findViewById(R.id.logout);
        bookingList = header.findViewById(R.id.booking_list);
        // assignBooking = header.findViewById(R.id.assign_booking);
        butHome.setOnClickListener(this);
        butRegistation.setOnClickListener(this);
        butReferDriver.setOnClickListener(this);
        butWallte.setOnClickListener(this);
        incom.setOnClickListener(this);
        offers.setOnClickListener(this);
        butNotification.setOnClickListener(this);
        butReport.setOnClickListener(this);
        butLogout.setOnClickListener(this);
        profEdit.setOnClickListener(this);
        bookingList.setOnClickListener(this);
        //  assignBooking.setOnClickListener(this);
        userName = header.findViewById(R.id.name_of_user);
        userPhoneNo = header.findViewById(R.id.phone_no_of_user);
        userEmail = header.findViewById(R.id.email_id_user);
        userName.setText(PreferenceData.getString(ActivityDashboard.this, "cont_name"));
        userPhoneNo.setText(phoneNo);
        userEmail.setText(PreferenceData.getString(ActivityDashboard.this, "con_email"));

        setDefaultContent();
    }

    private void intLayoutReference() {
        searchView = findViewById(R.id.searh_view_rss);
        button_search_close = findViewById(R.id.button_serch_close);
        bott_cab = findViewById(R.id.id_cab);
        bott_driver = findViewById(R.id.id_driver);
        bott_vendor = findViewById(R.id.id_vendor);
        bott_wallate = findViewById(R.id.id_wallate);
        id_bottom_seet_layout = findViewById(R.id.id_bottom_sheet_layout);
        ivRefresh = findViewById(R.id.iv_refresh_cont_detail);
        progressDialog = findViewById(R.id.id_progressbar_re);
        ivSearch = findViewById(R.id.iv_search);
        drawer = findViewById(R.id.drawer_layout);
        search_loc = findViewById(R.id.lay_seach_loc);
        search_cab = findViewById(R.id.lay_search_cab);
        button_search_loc = findViewById(R.id.button_serch_loc);
        bott_wallate.setOnClickListener(view -> {
            startActivity(new Intent(ActivityDashboard.this, BookingAsignActivity.class));
        });

        bott_cab.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), VendorDetailActivity.class));
        });
        bott_vendor.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), RegistationActivity.class)));
        //cab list
        bott_driver.setOnClickListener(view -> {
            // ravi


            // vendorListFragment = VendorListFragment.newInstance();
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.container, vendorListFragment);
//            ft.addToBackStack("");
//            ft.commit();
            startActivity(new Intent(getApplicationContext(), CabDetailActivity.class));
        });

        button_search_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location = searchView.getText().toString();
                mapViewFragment.getCabLocation(location.toUpperCase().trim());
                //  mapViewFragment.showCabMap(location);
                if (!mapViewFragment.showCabMap(location)) {

                    /*   Utils.showOkAlert(ActivityDashboard.this, getString(R.string.info), "Information with this data does not exist on map", true);
                     */  //  mapViewFragment.getCabLocation(location.toUpperCase().trim());//.toUpperCase().trim()
                    /*    Toast.makeText(ActivityDashboard.this, "Cab Not Found", Toast.LENGTH_SHORT).show();*/

                }
                closeSearchView();
            }
        });
        button_search_close.setOnClickListener(v -> {
            closeSearchView();
        });
    }

    private void setDefaultContent() {
        mapViewFragment = MapViewFragment.newInstance(-1, (byte) 1);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.container, mapViewFragment);
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        int back = PreferenceData.getInt(ActivityDashboard.this, "no_bac");
        if (back == 11) {
            PreferenceData.saveLong(this, "mvid", -1);
            PreferenceData.saveInt(this, "msts", 1);
            PreferenceData.saveInt(ActivityDashboard.this, "no_bac", 10);
            finish();
        } else {
            new MaterialAlertDialogBuilder(ActivityDashboard.this, R.style.AlertDialogTheme)
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dialog.dismiss();
                        finish();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();
        }
    }

    //todo  Navigation
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_edit:
                drawer.closeDrawers();
                ProfileEditFragment profileEditPage = ProfileEditFragment.newInstance();
                profileEditPage.show(this);
                break;
            case R.id.iv_refresh_cont_detail:
                callServicegetalldata();
                break;
            case R.id.iv_search:
                drawer.closeDrawers();
                search_loc.setVisibility(View.VISIBLE);
                search_cab.setVisibility(View.GONE);
                searchView.requestFocus();
                searchView.setFocusable(true);
                break;
            case R.id.home:
                setDefaultContent();
                break;
            case R.id.registration:
                startActivity(new Intent(getApplicationContext(), RegistationActivity.class));
                break;
            case R.id.refer_driver:
                startActivity(new Intent(getApplicationContext(), ReferDriverActivity.class));
                break;
            case R.id.offers:
                startActivity(new Intent(getApplicationContext(), ActivityOfferList.class));
                break;
            case R.id.wallet:
                startActivity(new Intent(getApplicationContext(), ActivityWallet.class));
                break;
            case R.id.income:
                startActivity(new Intent(getApplicationContext(), IncomActivity.class));
                break;
            case R.id.booking_list:
                startActivity(new Intent(getApplicationContext(), BookingAsignActivity.class));
                break;
            case R.id.notification:
                Intent intent = new Intent(this, ActivityNotification.class);
                intent.putExtra("notificationServ", false);
                intent.putExtra("cont_id", contractorID);
                startActivity(intent);
                break;
            case R.id.report:
                Intent intent2 = new Intent(this, ReportActivity.class);
                startActivity(intent2);
                break;
            case R.id.logout:
                new MaterialAlertDialogBuilder(ActivityDashboard.this, R.style.AlertDialogTheme)
                        .setMessage("Are you sure you want to exit?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            removeDbInfo();
                            Intent intent1 = new Intent(ActivityDashboard.this, LoginActivity.class);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            loginStatus = false;
                            PreferenceData.saveBoolean(ActivityDashboard.this, "login_status", loginStatus);
                            startActivity(intent1);
                            stopService(new Intent(ActivityDashboard.this, MyBookingService.class));
                            dialog.dismiss();
                            finish();
                            dialog.dismiss();
                            finish();
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        /*closeKeyboard();*/

    }

    private void callServicegetalldata() {
        //  homeFragment.searchQuery("", 3);
        closeSearchViewClickBackPressed();
        runOnUiThread(() -> ServiceProcessor.downloadCoordinatorData(ActivityDashboard.this, contractorID, new TaskListener<String>() {
            @Override
            public void onStart() {
                startProcessing();
            }

            @Override
            public void onSuccess(String message) {
                try {
                    Log.e(TAG, message);
                    //   setDefaultContent();
                    //
                    mapViewFragment.refereceActivity(10);
                    // homeFragment.refereceActivity(10);
                    stopProcessing();
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(String message) {
                TastyToast.makeText(getApplicationContext(), " Server is not responding ", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                if (Utils.getConnectivityStatus(getApplicationContext()) == 0) {
                    Utils.showOkAlert(ActivityDashboard.this, getString(R.string.info), "Please check your internet connection", false);
                }
                stopProcessing();
            }
        }));

    }

    private void startProcessing() {
        progressDialog.setVisibility(View.VISIBLE);
        ivRefresh.setVisibility(View.GONE);
    }

    private void stopProcessing() {
        progressDialog.setVisibility(View.GONE);
        ivRefresh.setVisibility(View.VISIBLE);
    }

    private void removeDbInfo() {
        CoordinatorDbHelper dbHelper = new CoordinatorDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        DriverDBInfo.deleteAllDrivers(db);
        VendorDBInfo.deleteAllVendors(db);
        CabCategoryDBInfo.deleteAllCabCategory(db);
        CabModelDbInfo.deleteAllCabModels(db);
        db.close();
        dbHelper.close();
    }


    private void closeKeyboard() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            Log.d("FocusedChanged", "KeyBoard Closed");
        }
    }

    @Override
    protected void onStart() {
        //   callApiGetAppVersion();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int st = bundle.getInt("ss");
            if (st == 1) {
                try {
                    PreferenceData.saveInt(ActivityDashboard.this, "no_bac", 11);
                    mapViewFragment.updateMap();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onStart();
    }

    @Override
    public void userNo(String number) {
        userPhoneNo.setText(number);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    private void callApiGetNotificationList() {
        apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<NotificationCountResponce> call = apiInterface.notificationCount(new PostData((int) contractorID, "contractor"));
        call.enqueue(new Callback<NotificationCountResponce>() {
            @Override
            public void onResponse(Call<NotificationCountResponce> call, Response<NotificationCountResponce> response) {
                if (response.isSuccessful()) {
                    NotificationCountResponce responce = response.body();
                    String unRead = responce.getUnreadcount();
                    if (!unRead.equals("")) {
                        textCounter.setText(unRead);
                        //count = Integer.parseInt(unRead);
                    } else {
                        Toast.makeText(ActivityDashboard.this, "" + responce.getGetresponse().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationCountResponce> call, Throwable t) {

            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDashboard.this);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.fragment_message_dialog, null, false);
        ImageView imageView = view.findViewById(R.id.id_image_view_dialog);
        TextView message = view.findViewById(R.id.message);
        TextView title = view.findViewById(R.id.title);
        Button exit = view.findViewById(R.id.btn_close);
        exit.setText(getString(R.string.update));
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_info_icon));
        title.setText(getString(R.string.info));
        message.setText("App update available." + "\nPlease Update App");
        exit.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.gts.coordinator&hl=en_IN"));
            startActivity(intent);
            dialog.cancel();
        });
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.setView(view);
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.activityResumed();

    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.activityPaused();

    }

    @Override
    protected void onRestart() {
        callServicegetalldata();
        userPhoneNo.setText(PreferenceData.getString(ActivityDashboard.this, "cont_phone1"));
        callApiGetNotificationList();
        closeKeyboard();
        stopProcessing();
        super.onRestart();
    }

    // menwale update
    private void callApiGetAppVersion() {
        apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<VersionCode> call = apiInterface.getVersionCode();
        call.enqueue(new Callback<VersionCode>() {
            @Override
            public void onResponse(Call<VersionCode> call, Response<VersionCode> response) {
                if (response.isSuccessful()) {
                    VersionCode versionCode = response.body();
                    if (versionCode.getGetresponse().getStatus() == 0) {
                        int version_code = versionCode.getVersionCode();
                        if (Utils.getAppVersionCode(ActivityDashboard.this) != version_code) {
                            showAlertDialog();
                        } else {
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<VersionCode> call, Throwable t) {
                Toast.makeText(ActivityDashboard.this, "Some Thing want wrong ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        removeDbInfo();
        stopService(new Intent(getApplicationContext(), MyBookingService.class));
        stopService(new Intent(getApplicationContext(), DriverDataService.class));

        //  Log.d(TAG, "onDestroy: Activity Deshboard");
        // Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    // IN app update
    private void checkUpdate() {
        // Creates instance of the manager.
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(ActivityDashboard.this);
// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // For a flexible update, use AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.
                try {
                    Toast.makeText(this, "Update Available", Toast.LENGTH_SHORT).show();
                    appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                            AppUpdateType.IMMEDIATE,
                            // The current activity making the update request.
                            this,
                            // Include a request code to later monitor this update request.
                            Update_Available);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error While Checking update", Toast.LENGTH_SHORT).show();
                }

            } else {

            }
        });
    }

    // for autoupdate
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Update_Available) {
            if (resultCode != RESULT_OK) {
                TastyToast.makeText(getApplicationContext(), "Error While Downloading Please Try again!", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            } else {
                Toast.makeText(this, "Result  Ok", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        switch (v.getId()) {
            case R.id.searh_view_rss: {
                if (hasFocus) {
                    showKeyboard();
                    Log.d("FocusedChanged", "true");
                } else {
                    closeKeyboard();
                    Log.d("FocusedChanged", "false");
                }


                break;
            }
        }
    }

    public void showKeyboard() {
        Log.d("FocusedChanged", "KeyBoard Opened");
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

}
//658