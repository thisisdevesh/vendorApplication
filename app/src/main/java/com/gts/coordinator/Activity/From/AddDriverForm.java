package com.gts.coordinator.Activity.From;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.gts.coordinator.Activity.UploadDoc.UploadDriverDoc;
import com.gts.coordinator.Adapter.VendorAdapter;
import com.gts.coordinator.Model.ContractorData.AddDriver.AddDriverModel;
import com.gts.coordinator.Model.ContractorData.AddDriver.Testing.AddDriverModelTesting;
import com.gts.coordinator.Model.ContractorData.AddDriver.Testing.PostDriverDetail;
import com.gts.coordinator.Model.ContractorData.CheckStetus.CheckDriverSatusTesting.CheckDriverStetusTesting;
import com.gts.coordinator.Model.ContractorData.CheckStetus.CheckDriverSatusTesting.PostDriverNoTesting;
import com.gts.coordinator.Model.ContractorData.CheckStetus.CheckDriverStetusNew.PostDriverNoNew;
import com.gts.coordinator.Model.ContractorData.CheckStetus.CheckDriverStetusNew.SetBookingActivities;
import com.gts.coordinator.Model.ContractorData.GetOtp.GetOtpModel;
import com.gts.coordinator.Model.ContractorData.GetOtp.PostMobileNo;
import com.gts.coordinator.R;
import com.gts.coordinator.Retrofit.RetrofitClient;

import com.gts.coordinator.dao.CabCategory;
import com.gts.coordinator.dao.Driver;
import com.gts.coordinator.dao.Vendor;
import com.gts.coordinator.db.CabCategoryDBInfo;
import com.gts.coordinator.db.DriverDBInfo;
import com.gts.coordinator.db.VendorDBInfo;
import com.gts.coordinator.utils.AppConstants;
import com.gts.coordinator.utils.PreferenceData;
import com.gts.coordinator.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.gts.coordinator.Retrofit.RetrofitApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDriverForm extends AppCompatActivity implements View.OnClickListener {
    Calendar myCalendar = Calendar.getInstance();
    String theDate;
    private Button submitDetail;
    private ProgressBar processBar, processBar_search;
    private EditText etDriverName, etDriverPhno, etDlNum;
    private String cabNumber, driverName, driverPhno, dlNumber, dlExpiryDate;
    private TextView etDlExpiry, textView, resend_otp, mTextViewCountDown;
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

        private void updateLabel() {
//            String myFormat = "dd/MM/yy"; //In which you need put here
            String myFormat = "yyyy/MM/dd"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
            etDlExpiry.setText(sdf.format(myCalendar.getTime()));
        }
    };
    private long vendorID, contractorId, driverID;
    private long cabCategoryID;
    private Spinner cabCatSpin, cabNumSpin;
    private AutoCompleteTextView venNameSpin;
    private ArrayList<Vendor> vendorNameList;
    private ArrayList<CabCategory> categoryList;
    private ArrayList<Vendor> vendorsList;
    private ArrayList<Driver> numberList;
    private ArrayList<String> cabNumList;
    private ArrayList<CabCategory> cabCategoryList;
    private CabCategoryDBInfo cabCategoryDBInfo;
    private VendorDBInfo vendorDBInfo;
    private DriverDBInfo driverDBInfo;
    private LinearLayout lay_add_driver;
    private ImageView driver_check_stetus;
    private RetrofitApiInterface apiInterface;
    private EditText et_driver_name, et_driver_dl_no;
    private TextView et_dl_expiry_date;
    private int checkStatus;
    private String otpVerifiedValue = "0";
    private EditText otp_text;
    private AlertDialog dialog = null;
    private static final long START_TIME_IN_MILLIS = 200000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private CountDownTimer mCountDownTimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_driver_form);
        textView = findViewById(R.id.test_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_df);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.driver_registration_form));
        vendorDBInfo = new VendorDBInfo(this);
        cabCategoryDBInfo = new CabCategoryDBInfo(this);
        driverDBInfo = new DriverDBInfo(this);
        cabNumList = new ArrayList<String>();
        categoryList = new ArrayList<CabCategory>();
        numberList = new ArrayList<Driver>();
        vendorsList = new ArrayList<Vendor>();
        vendorNameList = vendorDBInfo.getAllVendors(this);
        cabCategoryList = cabCategoryDBInfo.getCabCategory();
        numberList = driverDBInfo.getDrivers(vendorID);
        contractorId = PreferenceData.getLong(AddDriverForm.this, "cont_id");
        init();
        for (Vendor v : vendorNameList) {
            vendorsList.add(v);
        }
        venNameSpin = findViewById(R.id.vendor_name_add_driv);
        //todo  edit by Ravindra Singh May 2019 add Autocomplate text view search name and mobile no.
        venNameSpin.setThreshold(1);
        VendorAdapter vendorAdapter = new VendorAdapter(this, vendorsList, textView);
        venNameSpin.setAdapter(vendorAdapter);
        venNameSpin.setOnItemClickListener((parent, view, position, id) -> {
            Vendor vn = vendorsList.get(position);
            vendorID = vn.getVndId();
            cabCatSpin.setAdapter(new CabCategoryAdapter(AddDriverForm.this, categoryList));
        });
        for (CabCategory category : cabCategoryList) {
            categoryList.add(category);
            etDlExpiry.setOnClickListener(this);
            submitDetail.setOnClickListener(this);
            cabCatSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    cabNumList = driverDBInfo.getCabNumbers(vendorID, cabCategoryID);
                    CabNumberAdapter cd = new CabNumberAdapter(getApplicationContext(), cabNumList);
                    cabNumSpin.setAdapter(cd);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            cabNumSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    driverID = driverDBInfo.getDriverID(cabNumber.toUpperCase());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
        etDlExpiry.setOnClickListener(this);
    }

    private void init() {
        et_driver_name = findViewById(R.id.et_driver_name);
        et_driver_dl_no = findViewById(R.id.et_driver_dl_no);
        et_dl_expiry_date = findViewById(R.id.et_dl_expiry_date);
        driver_check_stetus = findViewById(R.id.check_stetus_driver);
        lay_add_driver = findViewById(R.id.lay_add_driver);
        cabCatSpin = findViewById(R.id.cabCatSpinner);
        cabNumSpin = findViewById(R.id.caNumSpinner);
        submitDetail = findViewById(R.id.submit);
        processBar = findViewById(R.id.process_bar);
        processBar_search = findViewById(R.id.process_bar_search);
        etDriverName = findViewById(R.id.et_driver_name);
        etDriverPhno = findViewById(R.id.et_driver_phno);
        etDlNum = findViewById(R.id.et_driver_dl_no);
        etDlExpiry = findViewById(R.id.et_dl_expiry_date);
        cabNumList = driverDBInfo.getCabNumbers(vendorID, cabCategoryID);
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(10);
        etDriverPhno.setFilters(filters);
        driver_check_stetus.setOnClickListener(v -> {
            driverPhno = String.valueOf(etDriverPhno.getText());
            if (driverPhno.equals("")) {
                Toast.makeText(AddDriverForm.this, "Please Enter Driver Number", Toast.LENGTH_LONG).show();
            } else if (driverPhno.length() < 10 || driverPhno.length() > 11) {
                Toast.makeText(AddDriverForm.this, "Please Enter 10 digit Number", Toast.LENGTH_LONG).show();
            } else {
                //  startProcessing();
                callApiSearchDriver();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                driverName = String.valueOf(etDriverName.getText());
                driverPhno = String.valueOf(etDriverPhno.getText());
                dlNumber = String.valueOf(etDlNum.getText());
                dlExpiryDate = String.valueOf(etDlExpiry.getText());
                /*        Log.i("AddDriverForm", "onClick():submit: cabNumber=" + cabNumber + ";");*/
                if (cabNumber == null || "".equals(cabNumber)) {
                    Toast.makeText(this, "Please Select Cab Number", Toast.LENGTH_SHORT).show();
                } else if ("".equals(driverName)) {
                    Toast.makeText(this, "Please Fill Driver Name", Toast.LENGTH_SHORT).show();
//                    etDriverName.setSelected(true);
                } else if (driverPhno.length() < 10 || driverPhno.length() > 11) {
                    Toast.makeText(this, "Please Enter 10 digit Number", Toast.LENGTH_LONG).show();
                } else if ("".equals(dlNumber)) {
                    Toast.makeText(this, "Please Enter Licence Number", Toast.LENGTH_LONG).show();
                } else if ("".equals(dlExpiryDate)) {
                    Toast.makeText(this, "Please Enter Expiry Date", Toast.LENGTH_LONG).show();
                } else if (Utils.getConnectivityStatus(this) == 0) {
//                            ToastClass.showToast(getActivity(), "No Internet Connection Detected");
                    Utils.showOkAlert(this, "No Internet Connection", "Please check your internet connection", false);
                } else {
                    callApiAddDriver();
                }
                break;
            case R.id.et_dl_expiry_date:
                closeKeyboard();
                new DatePickerDialog(this, R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }

    private void callApiSearchDriver() {
        driver_check_stetus.setVisibility(View.GONE);
        processBar_search.setVisibility(View.VISIBLE);
        apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<CheckDriverStetusTesting> call = apiInterface.driverStatusTesting(new PostDriverNoTesting(driverPhno));

        call.enqueue(new Callback<CheckDriverStetusTesting>() {
            @Override
            public void onResponse(Call<CheckDriverStetusTesting> call, Response<CheckDriverStetusTesting> response) {
                if (response.isSuccessful()) {
                    etDriverPhno.setEnabled(false);
                    CheckDriverStetusTesting checkDriverStetusTesting = response.body();
                    int status = checkDriverStetusTesting.getResponse().getStatus();
                    checkStatus = status;
                    String CabId = checkDriverStetusTesting.getCabid();
                    String message = checkDriverStetusTesting.getResponse().getMessage();
                    if (status == 25) {
                        //Driver Already Exists
                        Utils.showOkAlert(AddDriverForm.this, getString(R.string.info), message, true);
                        driver_check_stetus.setVisibility(View.VISIBLE);
                        processBar_search.setVisibility(View.GONE);
                        etDriverPhno.setEnabled(true);
                    } else if (status == 0) {
                        //   Its A new Driver
                        processBar_search.setVisibility(View.GONE);
                        //  stopProcessing();
                        if (lay_add_driver.getVisibility() == View.GONE) {
                            lay_add_driver.setVisibility(View.VISIBLE);
                            driver_check_stetus.setVisibility(View.GONE);
                        }
                    } else if (status == 31) {
                        //Driver Documents Not Found
                        if (CabId != null) {
                            Intent intent = new Intent(AddDriverForm.this, UploadDriverDoc.class);
                            intent.putExtra("from_name", "df");
                            intent.putExtra("role", AppConstants.ROLE_DRIVER);
                            intent.putExtra("id", Long.parseLong(CabId));
                            intent.putExtra("key", "");
                            intent.putExtra("st", "adddv");
                            intent.putExtra("is_mandatory", true);
                            startActivity(intent);
                            finish();
                        }
                    } else if (status == 30) {
                        //Driver Documents found but not verified
                        Utils.showOkAlert(AddDriverForm.this, getString(R.string.info), "Your Documents Are Under Verification", false);
                        driver_check_stetus.setVisibility(View.VISIBLE);
                        processBar_search.setVisibility(View.GONE);
                        etDriverPhno.setEnabled(true);
                    } else if (status == 33 || status == 32) {
                        //33 Driver Exist, Doc Exist, But No Cab
                        //32 Driver Exist But No Cab, No Doc
                        processBar_search.setVisibility(View.GONE);
                        //  stopProcessing();
                        if (lay_add_driver.getVisibility() == View.GONE) {
                            lay_add_driver.setVisibility(View.VISIBLE);
                            driver_check_stetus.setVisibility(View.GONE);
                            etDriverPhno.setEnabled(true);
                            et_driver_name.setText(checkDriverStetusTesting.getDriverName());
                            et_driver_name.setEnabled(false);
                            et_dl_expiry_date.setText(checkDriverStetusTesting.getDlExpiryDate());
                            et_dl_expiry_date.setEnabled(false);
                            et_driver_dl_no.setText(checkDriverStetusTesting.getDriverDlNumber());
                            et_driver_dl_no.setEnabled(false);
                        }
                    } else {
                        Utils.showOkAlert(AddDriverForm.this, getString(R.string.info), message, true);
                        driver_check_stetus.setVisibility(View.VISIBLE);
                        processBar_search.setVisibility(View.GONE);
                        etDriverPhno.setEnabled(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckDriverStetusTesting> call, Throwable t) {
                etDriverPhno.setEnabled(true);
                driver_check_stetus.setVisibility(View.VISIBLE);
                processBar_search.setVisibility(View.GONE);
            }
        });



/*        call.enqueue(new Callback<SetBookingActivities>() {
            @Override
            public void onResponse(Call<SetBookingActivities> call, retrofit2.Response<SetBookingActivities> response) {
                if (response.isSuccessful()) {
                    etDriverPhno.setEnabled(false);
                    SetBookingActivities checkStetus = response.body();
                    int status = checkStetus.getD().getResponse().getStatus();
                    String CabId = checkStetus.getD().getCabid();
                    String message = checkStetus.getD().getResponse().getMessage();
                    if (status == 25) {
                        //Driver Already Exists

                        Utils.showOkAlert(AddDriverForm.this, getString(R.string.info), message, true);
                        driver_check_stetus.setVisibility(View.VISIBLE);
                        processBar_search.setVisibility(View.GONE);
                        etDriverPhno.setEnabled(true);
                    } else if (status == 0) {
                        //   Its A new Driver

                        processBar_search.setVisibility(View.GONE);
                        //  stopProcessing();
                        if (lay_add_driver.getVisibility() == View.GONE) {
                            lay_add_driver.setVisibility(View.VISIBLE);
                            driver_check_stetus.setVisibility(View.GONE);
                        }
                    } else if (status == 31) {
                        //Driver Documents Not Found

                        if (CabId != null) {
                            Intent intent = new Intent(AddDriverForm.this, UploadDriverDoc.class);
                            intent.putExtra("from_name", "df");
                            intent.putExtra("role", AppConstants.ROLE_DRIVER);
                            intent.putExtra("id", Long.parseLong(CabId));
                            intent.putExtra("key", "");
                            intent.putExtra("st", "adddv");
                            intent.putExtra("is_mandatory", true);
                            startActivity(intent);
                            finish();
                        }
                    } else if (status == 30) {
                        //Driver Documents found but not verified
                        if (CabId != null) {
                            Utils.showOkAlert(AddDriverForm.this, getString(R.string.info), "Your Documents Are Under Verification", false);
                            driver_check_stetus.setVisibility(View.VISIBLE);
                            processBar_search.setVisibility(View.GONE);
                            etDriverPhno.setEnabled(true);
                        } else {
                            Utils.showOkAlert(AddDriverForm.this, getString(R.string.info), "Driver Exist Already!! Please attach Cab with Driver App", true);
                            driver_check_stetus.setVisibility(View.VISIBLE);
                            processBar_search.setVisibility(View.GONE);
                            etDriverPhno.setEnabled(true);
                        }
                    } else if (status == 33) {
                        //Driver Exist, Doc Exist, But No Cab

                    } else if (status == 32) {
                        //Driver Exist But No Cab, No Doc
                    }

                }
            }

            @Override
            public void onFailure(Call<SetBookingActivities> call, Throwable t) {
                etDriverPhno.setEnabled(true);
                driver_check_stetus.setVisibility(View.VISIBLE);
                processBar_search.setVisibility(View.GONE);
            }
        });*/
    }

    // todo call api callApiAddDriver
    private void callApiAddDriver() {
        startProcessing();
        apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<AddDriverModelTesting> call = apiInterface.addDriver(new PostDriverDetail(String.valueOf(vendorID), String.valueOf(contractorId), cabNumber, driverName,
                driverPhno, dlNumber, dlExpiryDate, String.valueOf(checkStatus), otpVerifiedValue));

        call.enqueue(new Callback<AddDriverModelTesting>() {
            @Override
            public void onResponse(Call<AddDriverModelTesting> call, retrofit2.Response<AddDriverModelTesting> response) {
                if (response.isSuccessful()) {
                    stopProcessing();
                    AddDriverModelTesting driverModel = response.body();
                    int status = driverModel.getGetresponse().getStatus();
                    String massage = driverModel.getGetresponse().getMessage();
                    String Otp = driverModel.getOtp();
                    if (Otp != null) {
                        //Open Otp Dialog Fragment

                        startSmsUserConsent();
                        showOtpDialog(Otp);

                    } else {
                        otpVerifiedValue = "0";
                        Toast.makeText(AddDriverForm.this, "" + massage, Toast.LENGTH_SHORT).show();
                        if (checkStatus == 0 || checkStatus == 32) {
                            if (status == 0) {
                                byte st = 0;
                                stopProcessing();
                                driverDBInfo.attachDriverWithCab(driverPhno, driverName, 0, 0, st, driverID, false, vendorID, cabNumber, "", "", cabCategoryID, false, dlNumber, dlExpiryDate);
                                Intent intent = new Intent(AddDriverForm.this, UploadDriverDoc.class);
                                intent.putExtra("from_name", "df");
                                intent.putExtra("role", AppConstants.ROLE_DRIVER);
                                intent.putExtra("id", driverID);
                                intent.putExtra("key", "");
                                intent.putExtra("st", "adddv");
                                intent.putExtra("is_mandatory", true);
                                startActivity(intent);
                                finish();
                            } else if (status == 26) {
                                Utils.showOkAlert(AddDriverForm.this, getString(R.string.info), massage, true);
                                stopProcessing();


                            } else {
                                Utils.showOkAlert(AddDriverForm.this, getString(R.string.info), massage, true);
                                stopProcessing();
                            }
                        } else if (checkStatus == 33) {
                            Toast.makeText(AddDriverForm.this, "" + massage, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<AddDriverModelTesting> call, Throwable t) {
                stopProcessing();
                Utils.showOkAlert(AddDriverForm.this, getString(R.string.error), "onFailure", true);
            }
        });
    }

    private void startSmsUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(AddDriverForm.this);
        //We can add sender phone number or leave it blank
        // I'm adding null here
        client.startSmsUserConsent(null).addOnSuccessListener(aVoid -> {
            /*    Toast.makeText(getContext(), "On Success", Toast.LENGTH_LONG).show();*/
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                /* Toast.makeText(getContext(), "On OnFailure", Toast.LENGTH_LONG).show();*/
            }
        });
    }


    private void showOtpDialog(String otp) {
        //
        startTimer();
        AlertDialog.Builder builder = new AlertDialog.Builder(AddDriverForm.this);
        View view = LayoutInflater.from(AddDriverForm.this).inflate(R.layout.otp_layout, null);
        otp_text = view.findViewById(R.id.enter_otp);
        MaterialButton verify = view.findViewById(R.id.button_otp_verify);
        MaterialButton cancle = view.findViewById(R.id.button_otp_cancle);
        TextView message = view.findViewById(R.id.message_text);
        resend_otp = view.findViewById(R.id.resend_otp);
        //rsss
        mTextViewCountDown = view.findViewById(R.id.text_view_countdown);
        message.setText(getString(R.string.we_have_sent_otp_to) + "\n" +"Your Number");
        cancle.setOnClickListener(view13 -> dialog.cancel());
        verify.setOnClickListener(view12 -> {
            verfyOtp(otp);
            //callOtpApi();
        });
        resend_otp.setOnClickListener(view1 -> {
            //dialog.cancel();
            mTextViewCountDown.setText("");
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
            resend_otp.setVisibility(View.GONE);
            mTextViewCountDown.setVisibility(View.VISIBLE);
            startTimer();
            callApiAddDriver();
        });
        //if (otp_text.equals(""))
        //rsss
        dialog = builder.create();
        dialog.setView(view);
        dialog.show();

        // progressBar.setVisibility(View.VISIBLE);
        //   updateInfo();
    }

    private void verfyOtp(String otp) {
        String user_otp = otp_text.getText().toString();
        if (user_otp.equals("")) {
            Utils.showOkAlert(AddDriverForm.this, getString(R.string.error), "Please Enter OTP", true);
        } else if (!otp.equals(user_otp)) {
            Utils.showOkAlert(AddDriverForm.this, getString(R.string.error), "Please Enter Valid OTP ", true);
        } else if (otp.equals(user_otp)) {
            dialog.cancel();
            otpVerifiedValue = "1";
            updateInfo();
            //  startTimer();
        }else if (!otp.equals(user_otp)){
            updateInfo();
        }
    }
    private void updateInfo() {
        callApiAddDriver();
    }
    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTextViewCountDown.setVisibility(View.GONE);
                resend_otp.setVisibility(View.VISIBLE);
                //rsss
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }


    private void startProcessing() {
        submitDetail.setVisibility(View.GONE);
        processBar.setVisibility(View.VISIBLE);
    }

    private void stopProcessing() {
        submitDetail.setVisibility(View.VISIBLE);
        processBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Utils.getConnectivityStatus(AddDriverForm.this) == 0) {
            Utils.showOkAlert(AddDriverForm.this, "No Internet Connection", "Please check your internet connection", false);
        }

    }

    public class CabCategoryAdapter extends BaseAdapter {
        Context context;
        ArrayList<CabCategory> categoryList;

        public CabCategoryAdapter(Context context, ArrayList<CabCategory> categoryList) {
            this.context = context;
            this.categoryList = categoryList;
        }

        @Override
        public int getCount() {
            return categoryList.size();
        }

        @Override
        public Object getItem(int position) {
            return categoryList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//      Log.i("****", "*********************");
            CabCategory cabCategory = categoryList.get(position);
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View view = inflater.inflate(R.layout.custom_spinner, parent, false);
            TextView label = (TextView) view.findViewById(R.id.spinner_text);
            label.setText(cabCategory.getCategoryName());
            cabCategoryID = cabCategory.getCategoryID();
            return view;
        }
    }

    public class CabNumberAdapter extends BaseAdapter {
        Context context;
        private ArrayList<String> cabNumberList;

        //    private Driver driv;
        public CabNumberAdapter(Context context, ArrayList<String> numberList) {
            this.context = context;
            this.cabNumberList = numberList;
        }

        @Override
        public int getCount() {
            return cabNumberList.size();
        }

        @Override
        public Object getItem(int position) {
            return cabNumberList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_spinner, parent, false);
            TextView label = view.findViewById(R.id.spinner_text);
            label.setText(cabNumberList.get(position));
            cabNumber = cabNumberList.get(position);
            return view;
        }
    }

}