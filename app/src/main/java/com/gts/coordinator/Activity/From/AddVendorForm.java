package com.gts.coordinator.Activity.From;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gts.coordinator.Model.ContractorData.AddVendor.AddVendorModel;
import com.gts.coordinator.Model.ContractorData.AddVendor.PostVendorDetail;
import com.gts.coordinator.Model.ContractorData.AddVendorTesting.AddVendorTesting;
import com.gts.coordinator.Model.ContractorData.CheckStetus.CheckVendorStetus.CheckVendorStetus;
import com.gts.coordinator.Model.ContractorData.CheckStetus.CheckVendorStetus.PostVendorNo;
import com.gts.coordinator.Model.ContractorData.UpdateVendor.PostUpdateVendorDetail;
import com.gts.coordinator.Model.ContractorData.UpdateVendor.UpdateVendorModel;
import com.gts.coordinator.Model.cityModel.CitiesList;
import com.gts.coordinator.Model.cityModel.CityRequest;
import com.gts.coordinator.Model.cityModel.CityResponse;
import com.gts.coordinator.R;
import com.gts.coordinator.Activity.UploadDoc.UploadVendorDoc;
import com.gts.coordinator.Retrofit.RetrofitClient;
import com.gts.coordinator.Retrofit.RetrofitClientForCities;
import com.gts.coordinator.db.VendorDBInfo;
import com.gts.coordinator.otp.OtpFragmentAddVendor.OtpFragmentAddVendor;
import com.gts.coordinator.utils.AppConstants;
import com.gts.coordinator.utils.PreferenceData;
import com.gts.coordinator.utils.Utils;

import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClientWcm;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddVendorForm extends AppCompatActivity implements View.OnClickListener, OtpFragmentAddVendor.OnInputListener {
    public final static int READ_STORAGE_PERMISSION = 3;
    long contractorID;
    long vendorID;
    private VendorDBInfo vendorDBInfo;
    private EditText vendorName, vendorEmail, vendorPhoneNo, vendorAddress, vendorAcNO, bankName, branchName, ifscCode;
    private ProgressBar processBar, processBar_search;
    private String strVendorName, strVendorEmail, strVendorPhNo, strVendorAddress, strCityId, strVendorAcNo, strBankName, strBranchName, strIfscCode, VendorPhNo;
    private Button submitVenInfo, skipVendorInf;
    private boolean isVerified;
    private LinearLayout lay_vendor;
    private RelativeLayout search_layout;
    private Spinner spCityName;
    private EditText text;
    private ArrayList<CitiesList> citiesLists;
    String pageType;
    private RelativeLayout relativeLayout;
    private RetrofitApiInterface apiInterface;
    private ImageView search_image;

    // TODO: Rename and change types of parameters
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_vendor_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_vf);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        pageType = intent.getStringExtra("page_type");
        strVendorName = intent.getStringExtra("vendorName");
        strVendorAddress = intent.getStringExtra("address");
        isVerified = intent.getBooleanExtra("isVerified", true);
        vendorID = intent.getLongExtra("vendorID", 0);
        strVendorPhNo = intent.getStringExtra("vendor_phone");
        init();

        contractorID = PreferenceData.getLong(AddVendorForm.this, "cont_id");
        submitVenInfo.setOnClickListener(this);
        skipVendorInf.setOnClickListener(this);
        vendorDBInfo = new VendorDBInfo(AddVendorForm.this);
        text.setFilters(new InputFilter[]{filter});
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(10);
        text.setFilters(filters);
        vendorPhoneNo.setFilters(filters);
    }

    private void init() {
        lay_vendor = findViewById(R.id.lay_vendor);
        search_layout = findViewById(R.id.layy_search);
        vendorName = findViewById(R.id.et_ven_name);
        vendorEmail =  findViewById(R.id.et_ven_email);
        vendorPhoneNo = findViewById(R.id.et_ven_PhNo);
        vendorAddress =  findViewById(R.id.et_ven_address);
        submitVenInfo =  findViewById(R.id.btn_Submit_ven);
        skipVendorInf =  findViewById(R.id.btn_skip);
        processBar =  findViewById(R.id.process_bar_dr);
        processBar_search =  findViewById(R.id.process_bar_dr_sarch);
        search_image = findViewById(R.id.check_stetus_cab);
        spCityName = findViewById(R.id.sp_city_name);
        text = findViewById(R.id.test_vendor_number);
        relativeLayout = findViewById(R.id.layout_city_name);
        text.setFilters(new InputFilter[]{filter});
        vendorPhoneNo.setFilters(new InputFilter[]{filter});
        vendorName.setFilters(new InputFilter[]{filter});
        if (vendorID != 0) {
            getSupportActionBar().setTitle("Edit Vendor");
            vendorName.setFocusable(false);
            vendorName.setEnabled(false);
            if (relativeLayout.getVisibility()==View.VISIBLE) {
                relativeLayout.setVisibility(View.GONE);
            }

            vendorName.setText(strVendorName);
            vendorAddress.setText(strVendorAddress);
            vendorAddress.setFocusable(false);
            vendorAddress.setEnabled(false);
            vendorPhoneNo.setText(strVendorPhNo);
            search_layout.setVisibility(View.GONE);
            lay_vendor.setVisibility(View.VISIBLE);

            if (!isVerified) // if verified, it does not go to documents upload page
            {
                skipVendorInf.setVisibility(View.GONE);
            }
        } else {
            getSupportActionBar().setTitle("Vendor Registration Form");
        }
    }

    private void listSpinnerData(ArrayList<CitiesList> citiesLists){

        spCityName.setAdapter(new AddVendorForm.CityAdapter(AddVendorForm.this, citiesLists));

        spCityName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              /*  cityName= citiesLists.get(position).getCityName();
                Log.d("CityyName",cityName);*/
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public class CityAdapter extends BaseAdapter {

        Context context;
        ArrayList<CitiesList> citiesLists;

        public CityAdapter(Context context, ArrayList<CitiesList> citiesLists) {
            this.context = context;
            this.citiesLists = citiesLists;
        }

        @Override
        public int getCount() {
            return citiesLists.size();
        }

        @Override
        public Object getItem(int position) {
            return citiesLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CitiesList citiesList  = citiesLists.get(position);
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View view = inflater.inflate(R.layout.custom_spinner, parent, false);
            TextView label =  view.findViewById(R.id.spinner_text);
            label.setText(citiesList.getCityName());
            strCityId = citiesList.getCityID();
            Log.d("CityIddd", strCityId);
            return view;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Submit_ven:
                closeKeyboard();
                strVendorName = String.valueOf(vendorName.getText()).trim();
                strVendorEmail = String.valueOf(vendorEmail.getText()).trim();
                strVendorAddress = String.valueOf(vendorAddress.getText()).trim();
                if (vendorID != 0) {
                    String newVendorPhNo = String.valueOf(vendorPhoneNo.getText()).trim();
                    if (newVendorPhNo.equals("")) {
                        Toast.makeText(this, "Please enter Phone number", Toast.LENGTH_SHORT).show();
                    } else if (newVendorPhNo.length() > 10 || newVendorPhNo.length() < 10) {
                        Toast.makeText(this, "Please enter 10 digit Phone number", Toast.LENGTH_SHORT).show();
                    } else if (newVendorPhNo.equals(strVendorPhNo)) {
                        Toast.makeText(this, "Your Previous and new Number are same", Toast.LENGTH_SHORT).show();
                    } else {
                        checkNetWorkConntion();
                        submitVenInfo.setVisibility(View.GONE);
                        skipVendorInf.setVisibility(View.GONE);
                        // 26aug
                        callApiUpdateVendor(newVendorPhNo);
                    }
                } else {
                    strVendorPhNo = String.valueOf(vendorPhoneNo.getText()).trim();
                    if (strVendorName.equals("") || strVendorAddress.equals("")|| strVendorPhNo.equals("")) {
                        Toast.makeText(AddVendorForm.this, "Please fill all field", Toast.LENGTH_LONG).show();
                    } else if (strVendorPhNo.length() < 10 || strVendorPhNo.length() > 11) {
                        Toast.makeText(AddVendorForm.this, "Please Enter 10 digit Number", Toast.LENGTH_LONG).show();
                    } else if (Utils.getConnectivityStatus(AddVendorForm.this) == 0) {
                        Utils.showOkAlert(AddVendorForm.this, "No Internet Connection", "Please check your internet connection", false);
                    } else {
                        checkNetWorkConntion();
                        callApiAddVendor();
                    }
                }
                break;
            case R.id.btn_skip:
                Intent intent = new Intent(AddVendorForm.this, UploadVendorDoc.class);
                intent.putExtra("role", AppConstants.ROLE_VENDOR);
                intent.putExtra("is_mandatory", false);
                intent.putExtra("id", vendorID);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void callApiUpdateVendor(String vendorPhNo) {
        try {
            startProcessing();
            vendorPhoneNo.setFocusable(true);
            apiInterface = RetrofitClientWcm.getClient().create(RetrofitApiInterface.class);
            Call<UpdateVendorModel> call = apiInterface.updateVendor(new PostUpdateVendorDetail(vendorID, vendorPhNo));
            call.enqueue(new Callback<UpdateVendorModel>() {
                @Override
                public void onResponse(Call<UpdateVendorModel> call, retrofit2.Response<UpdateVendorModel> response) {
                    if (response.isSuccessful()) {
                        stopProcessing();
                        UpdateVendorModel updateVendorModel = response.body();
                        int status = updateVendorModel.getD().getStatus();
                        String mess = updateVendorModel.getD().getMessage();
                        if (status == 0) {
                            byte venStatus = 0;
                            Toast.makeText(AddVendorForm.this, "" + mess, Toast.LENGTH_SHORT).show();
                            submitVenInfo.setVisibility(View.VISIBLE);
                            skipVendorInf.setVisibility(View.GONE);
                            stopProcessing();
                            vendorDBInfo.updateVendor(strVendorPhNo, vendorID);
                            Toast.makeText(AddVendorForm.this, mess, Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Utils.showOkAlert(AddVendorForm.this, getString(R.string.info), mess, true);
                            submitVenInfo.setVisibility(View.VISIBLE);
                            skipVendorInf.setVisibility(View.GONE);
                            stopProcessing();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UpdateVendorModel> call, Throwable t) {
                    Utils.showOkAlert(AddVendorForm.this, getString(R.string.info), "onFailure", true);
                    submitVenInfo.setVisibility(View.VISIBLE);
                    skipVendorInf.setVisibility(View.VISIBLE);
                    stopProcessing();
                }
            });
        } catch (Exception e) {
            Utils.showOkAlert(AddVendorForm.this, getString(R.string.info), "Exception", true);
            submitVenInfo.setVisibility(View.VISIBLE);
            skipVendorInf.setVisibility(View.VISIBLE);
            stopProcessing();
        }
    }

    // todo call api addVendor through com.gts.coordinator.Retrofit by Ravindra 24-07-19
    private void callApiAddVendor() {
        try {
            vendorPhoneNo.setEnabled(false);
            startProcessing();
            submitVenInfo.setVisibility(View.GONE);
            apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
            Call<AddVendorTesting> call = apiInterface.addVendorTesting(new PostVendorDetail(strVendorName,
                    strVendorEmail, strVendorPhNo, strVendorAddress, contractorID,strCityId));
            call.enqueue(new Callback<AddVendorTesting>() {
                @Override
                public void onResponse(Call<AddVendorTesting> call, retrofit2.Response<AddVendorTesting> response) {
                    if (response.isSuccessful()) {
                        AddVendorTesting model = response.body();
                        com.gts.coordinator.Model.ContractorData.AddVendorTesting.Response response1 = model.getResponse();
                        int status = response1.getStatus();
                        String massage = response1.getMessage();
                        vendorID = model.getVendorId();
                        if (status == 0) {
                            byte venStatus = 0;
                            stopProcessing();
                            Toast.makeText(AddVendorForm.this, "" + massage, Toast.LENGTH_SHORT).show();
                            vendorDBInfo.insertVendor(strVendorName, venStatus, strVendorPhNo, strVendorAddress, vendorID, false, 0);
                            Intent intent = new Intent(AddVendorForm.this, UploadVendorDoc.class);
                            intent.putExtra("key", "");
                            intent.putExtra("role", AppConstants.ROLE_VENDOR);
                            intent.putExtra("id", vendorID);
                            intent.putExtra("st", "addv");
                            intent.putExtra("is_mandatory", true);
                            startActivity(intent);
                            finish();
                        } else {
                            Utils.showOkAlert(AddVendorForm.this, getString(R.string.info), massage, true);
                            stopProcessing();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddVendorTesting> call, Throwable t) {
                    Utils.showOkAlert(AddVendorForm.this, getString(R.string.info), "onFailure info Please Try again.", true);
                    submitVenInfo.setVisibility(View.VISIBLE);
                }
            });
        } catch (Exception e) {
            Utils.showOkAlert(AddVendorForm.this, getString(R.string.info), "Exception info Please Try again.", true);
            submitVenInfo.setVisibility(View.VISIBLE);
        }
    }

    private void startProcessing() {
        processBar.setVisibility(View.VISIBLE);
    }

    private void stopProcessing() {
        processBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        vendorDBInfo.close();
        super.onDestroy();
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

    // ok report
    public void searchVendor(View view) {
        VendorPhNo = text.getText().toString().trim();
        if (VendorPhNo.equals("")) {
            Toast.makeText(this, "Please Enter Vendor Number", Toast.LENGTH_SHORT).show();
        } else if (VendorPhNo.length() < 10) {
            Toast.makeText(this, "Please Enter 10 Digit Number", Toast.LENGTH_SHORT).show();
        } else {
            checkNetWorkConntion();
            text.setEnabled(false);
            search_image.setVisibility(View.GONE);
            processBar_search.setVisibility(View.VISIBLE);


            OtpFragmentAddVendor otpFragment;
            otpFragment = OtpFragmentAddVendor.newInstance(VendorPhNo);
            otpFragment.setCancelable(false);
            FragmentTransaction ft =(AddVendorForm.this.getSupportFragmentManager().beginTransaction());
            Fragment prev = (AddVendorForm.this.getSupportFragmentManager().findFragmentByTag("dialog"));
            ft = (AddVendorForm.this.getSupportFragmentManager().beginTransaction());
            prev = (AddVendorForm.this.getSupportFragmentManager().findFragmentByTag("dialog"));
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            otpFragment.show(ft, "dialog");

//
//            callApiSearchVendorName(VendorPhNo);
        }

        //rsss
    }

    // call api thought  retrofit by Ravindra ok
    private void callApiSearchVendorName(String vendorPhNo) {
        try {
            apiInterface = RetrofitClientWcm.getClient().create(RetrofitApiInterface.class);
            Call<CheckVendorStetus> call = apiInterface.vendorStetus(new PostVendorNo(vendorPhNo));
            call.enqueue(new Callback<CheckVendorStetus>() {
                @Override
                public void onResponse(Call<CheckVendorStetus> call, retrofit2.Response<CheckVendorStetus> response) {
                    if (response.isSuccessful()) {
                        CheckVendorStetus checkVendorStetus = response.body();
                        String message = checkVendorStetus.getD().getResponse().getMessage();
                        long vendorID = checkVendorStetus.getD().getVendorId();
                        int status = checkVendorStetus.getD().getResponse().getStatus();

                        if (status == 25) {
                            search_image.setVisibility(View.VISIBLE);
                            enableText();
                            Utils.showOkAlert(AddVendorForm.this, getString(R.string.info), "This Number Already Exist And Verified", true);
                        } else if (status == 0) {
                            processBar_search.setVisibility(View.GONE);
                            text.setEnabled(true);
                            vendorPhoneNo.setText(VendorPhNo);
                            //rsss
                            if (lay_vendor.getVisibility() == View.GONE) {
                                lay_vendor.setVisibility(View.VISIBLE);
                                search_layout.setVisibility(View.GONE);
                                callApiCities();
                            }
                        } else if (status == 26)
                        {
                            Intent intent = new Intent(AddVendorForm.this, UploadVendorDoc.class);
                            intent.putExtra("key", "");
                            intent.putExtra("role", AppConstants.ROLE_VENDOR);
                            intent.putExtra("id", vendorID);
                            intent.putExtra("st", "addv");
                            intent.putExtra("is_mandatory", true);
                            startActivity(intent);
                            finish();
                        } else if (status == 27)
                        {
                            search_image.setVisibility(View.VISIBLE);
                            enableText();
                            Utils.showOkAlert(AddVendorForm.this, getString(R.string.info), "Your Documents Are Under Verification", true);
                        }
                    } else {
                        Utils.showOkAlert(AddVendorForm.this, getString(R.string.info), "Network issue Please Try again.", true);
                        search_image.setVisibility(View.VISIBLE);
                        enableText();
                    }
                }

                @Override
                public void onFailure(Call<CheckVendorStetus> call, Throwable t) {
                    stopProcessing();
                    Utils.showOkAlert(AddVendorForm.this, getString(R.string.info), "Please Try again.", true);
                    search_image.setVisibility(View.VISIBLE);
                    enableText();
                }
            });
        } catch (Exception e) {
            // Utils.showOkAlert(AddVendorForm.this, getString(R.string.info), "Exception issue Please Try again.", true);
            enableText();
        }
    }

    private void enableText() {
        //  processBar_search.setVisibility(View.GONE);
        text.setEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkNetWorkConntion();


    }

    private void checkNetWorkConntion() {
        if (Utils.getConnectivityStatus(AddVendorForm.this) == 0) {
            Utils.showOkAlert(AddVendorForm.this, "No Internet Connection", "Please check your internet connection", false);
        }
    }

    private String blockCharacterSet = "#@()-_;?=,.'~#^$+/|$%&*!:\"";
    private InputFilter filter = (source, start, end, dest, dstart, dend) -> {

        if (source != null && blockCharacterSet.contains(("" + source))) {
            return "";
        }
        return null;
    };

    @Override
    public void sendInput(String input) {
        if (input.equals("verified")){
            callApiSearchVendorName(VendorPhNo);
        }else if(input.equals("cancel")){
            search_image.setVisibility(View.VISIBLE);
        }
    }

    private void callApiCities(){
        apiInterface = RetrofitClientForCities.getClient().create(RetrofitApiInterface.class);
        Call<CityResponse> call = apiInterface.getCities(new CityRequest("Out Station"));
        call.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if (response.isSuccessful())
                {
                    CityResponse cityResponse = response.body();
                    citiesLists = new ArrayList<>();
                    if (cityResponse.getD().getResponse().getMessage().equals("Successful")){
                        citiesLists.addAll(cityResponse.getD().getCitiesList());
                        listSpinnerData(citiesLists);
                    }
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                Utils.showOkAlert(AddVendorForm.this, getString(R.string.info), "Somthing Went Wrong", true);
                call.cancel();
            }
        });

    }
}
//total no of line 424