package com.gts.coordinator.Activity.From;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
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

import com.gts.coordinator.Activity.UploadDoc.UploadCabDoc;
import com.gts.coordinator.Adapter.VendorAdapter;
import com.gts.coordinator.Model.ContractorData.AddCab.AddCabModel;
import com.gts.coordinator.Model.ContractorData.AddCab.PostCabDetail;
import com.gts.coordinator.Model.ContractorData.CheckStetus.CheckCabStetus.CheckCabStetus;
import com.gts.coordinator.Model.ContractorData.CheckStetus.CheckCabStetus.PostCabNo;
import com.gts.coordinator.Model.ContractorData.UpdateCab.PostUpdateCabDetail;
import com.gts.coordinator.Model.ContractorData.UpdateCab.UpdateCabModel;
import com.gts.coordinator.R;
import com.gts.coordinator.dao.CabCategory;
import com.gts.coordinator.dao.CabModel;
import com.gts.coordinator.dao.Vendor;
import com.gts.coordinator.db.CabCategoryDBInfo;
import com.gts.coordinator.db.CabModelDbInfo;
import com.gts.coordinator.db.CityListDBInfo;
import com.gts.coordinator.db.DriverDBInfo;
import com.gts.coordinator.db.VendorDBInfo;
import com.gts.coordinator.utils.AppConstants;
import com.gts.coordinator.utils.PreferenceData;
import com.gts.coordinator.utils.Utils;

import java.util.ArrayList;

import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClientWcm;
import retrofit2.Call;
import retrofit2.Callback;

public class AddCabFrom extends AppCompatActivity {
    private ArrayList<Vendor> vendorNameList;
    private ArrayList<CabCategory> cabCategoryList;
    private ArrayList<Vendor> vendorsList;
    private ArrayList<CabCategory> categoryList;
    private long vendorID, earlierVenID, cabCategoryID, contractorID, vendorCabID, modelID, updateVendorId;
    private boolean isVerified;
    private byte st;
    private String cabNumber, pageType, cabCateoryname, cabModelName, cityName, resMessage;
    private Button btnNext, btnSkip;
    private EditText tvCabNumber, tvVendorName,text;
    private TextView tvTitle;
    private ProgressBar processBar,processBar_serch;
    private Spinner spCategory, spModelName, spCityName;
    private AutoCompleteTextView spVendorName;
    private VendorDBInfo vendorDBInfo;
    private CabCategoryDBInfo cabCategoryDBInfo;
    private CabModelDbInfo cabModelDbInfo;
    private DriverDBInfo driverDBInfo;
    private CityListDBInfo cityListDBInfo;
    private String message;
    private RelativeLayout layoutCategory, layoutcityname, layoutModelName, select_vendor;
    private ImageView cab_check_stetus;
    private LinearLayout layout_add_cab, layout_button;
    private ProgressBar pg;
    private TextView textView;
    private RetrofitApiInterface apiInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_cab_from);
        getData();
        textView = findViewById(R.id.test_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cf);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        vendorDBInfo = new VendorDBInfo(this);
        cabCategoryDBInfo = new CabCategoryDBInfo(this);
        cabModelDbInfo = new CabModelDbInfo(this);
        driverDBInfo = new DriverDBInfo(this);
        cityListDBInfo = new CityListDBInfo(this);
        vendorsList = new ArrayList<Vendor>();
        categoryList = new ArrayList<CabCategory>();
        vendorNameList = vendorDBInfo.getAllVendors(this);
        cabCategoryList = cabCategoryDBInfo.getCabCategory();
        viewReference();
        contractorID = PreferenceData.getLong(AddCabFrom.this, "cont_id");
        for (CabCategory category : cabCategoryList) {
            categoryList.add(category);
        }
        for (Vendor v : vendorNameList) {
            vendorsList.add(v);
        }
        //todo  edit by Ravindra Singh May 2019 add Autocomplate text view search name and mobile no.
        listData();
    }

    // vendor ,cab name,cab type list
    private void listData() {
        //rssss
        spVendorName.setThreshold(1);
        VendorAdapter vendorAdapter = new VendorAdapter(this, vendorsList,textView);
        spVendorName.setAdapter(vendorAdapter);
        spVendorName.setOnItemClickListener((parent, view, position, id) -> {
            Vendor v = vendorsList.get(position);
            vendorID = v.getVndId();
            spCategory.setAdapter(new CabCategoryAdapter(AddCabFrom.this, categoryList));
        });

        spCityName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityName = String.valueOf(spCityName.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<CabModel> cabModelList = cabModelDbInfo.getCabModel(cabCategoryID);
                spModelName.setAdapter(new CabModelAdapter(AddCabFrom.this, cabModelList));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void viewReference() {
        layout_add_cab = findViewById(R.id.lay_cab_add);
        layout_button = findViewById(R.id.lay_button);
        cab_check_stetus = findViewById(R.id.check_stetus_cab_fom);
        spVendorName = findViewById(R.id.vendor_name_add_cab);
        spCategory =  findViewById(R.id.sp_vendorName);
        spModelName =  findViewById(R.id.sp_model_name);
        spCityName = findViewById(R.id.sp_city_name);
        processBar =  findViewById(R.id.process_bar_ca);
        processBar_serch =  findViewById(R.id.process_bar_search);
        btnNext =  findViewById(R.id.btn_next);
        btnSkip =  findViewById(R.id.btn_skip_cab);
        tvCabNumber = findViewById(R.id.tv_cab_number);
        tvCabNumber.setFilters(new InputFilter[]{filter});
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(15);
        tvCabNumber.setFilters(filters);
        layoutCategory = findViewById(R.id.layout_category);
        select_vendor = findViewById(R.id.select_vendor);
        layoutcityname = findViewById(R.id.layout_city_name);
        layoutModelName = findViewById(R.id.layout_model_name);
        tvVendorName = findViewById(R.id.et_ven_name);
        tvTitle = findViewById(R.id.title);

        if (pageType.equals("EDIT")) {
            if (!isVerified) {
                btnSkip.setVisibility(View.VISIBLE);
            }
            select_vendor.setVisibility(View.VISIBLE);
            tvCabNumber.setText(cabNumber);
            tvCabNumber.setEnabled(false);
            cab_check_stetus.setVisibility(View.GONE);
            layout_button.setVisibility(View.VISIBLE);
            btnNext.setText("Update");

        }
        cab_check_stetus.setOnClickListener(v -> {
            cabNumber = String.valueOf(tvCabNumber.getText()).toUpperCase().replaceAll("\\s+", "");
            if (cabNumber.equals("")) {
                Toast.makeText(AddCabFrom.this, "Please fill Cab Number", Toast.LENGTH_SHORT).show();
                closeKeyboard();
            } else {
                callApiSearchCab();
            }
        });
        if (pageType.equals("EDIT")) {
            btnNext.setVisibility(View.VISIBLE);
            btnSkip.setVisibility(View.VISIBLE);
        }
        btnNext.setOnClickListener(v -> {
            closeKeyboard();
            cabNumber = String.valueOf(tvCabNumber.getText()).toUpperCase().replaceAll("\\s+", "");
            if (pageType.equals("EDIT")) {

                callApiUpdatCab();
            } else if (pageType.equals("ADD")) {
                if (cabNumber.equals("")) {
                    Toast.makeText(AddCabFrom.this, "Please fill Cab Number", Toast.LENGTH_SHORT).show();
                } else {
                    callApiAddCab();

                }
            }
        });
        btnSkip.setOnClickListener(v -> {
            Intent intent = new Intent(AddCabFrom.this, UploadCabDoc.class);
//            intent.putExtra("vendor_cab_id",vendorCabID);
            intent.putExtra("id", vendorCabID);
            intent.putExtra("role", AppConstants.ROLE_CAB);
            intent.putExtra("st", "addcab");
            intent.putExtra("is_mandatory", true);
            startActivity(intent);
//              vendorDBInfo.insertVendor(strVendorName,0, strVendorPhNo,strVendorAddress, strVendorEmail );
            finish();
        });
        //  pg =findViewById(R.id.process_bar_ca);
    }
    // todo call api addVendor through com.gts.coordinator.Retrofit by Ravindra 24-07-19
    private void callApiUpdatCab() {
       try {
           btnNext.setVisibility(View.GONE);
           btnSkip.setVisibility(View.GONE);
           processBar.setVisibility(View.VISIBLE);
           apiInterface = RetrofitClientWcm.getClient().create(RetrofitApiInterface.class);
           Call<UpdateCabModel> call = apiInterface.updateCab(new PostUpdateCabDetail(vendorCabID, vendorID));
           call.enqueue(new Callback<UpdateCabModel>() {
               @Override
               public void onResponse(Call<UpdateCabModel> call, retrofit2.Response<UpdateCabModel> response) {
                   if (response.isSuccessful()) {
                           UpdateCabModel updateCabModel = response.body();
                           startProcessing();
                           int status = updateCabModel.getD().getStatus();
                           resMessage = updateCabModel.getD().getMessage();
                           if (status == 0) {
                               stopProcessing();
                               Toast.makeText(AddCabFrom.this, "" + resMessage, Toast.LENGTH_SHORT).show();
                               byte st = 1;
                               driverDBInfo.updateCab(vendorCabID, vendorID);
                               if (!isVerified) {
                                   Intent intent = new Intent(AddCabFrom.this, UploadCabDoc.class);
                                   ///Intent intent = new Intent(AddCabFrom.this, UploadDocumentActivity.class);
                                   intent.putExtra("st", "updateCab");
                                   intent.putExtra("is_mandatory", false);
                                   intent.putExtra("id", vendorCabID);
                                   intent.putExtra("role", AppConstants.ROLE_CAB);
                                   startActivity(intent);
                               }
                               //rss
                               finish();
                           }else {
                               btnNext.setVisibility(View.VISIBLE);
                               btnSkip.setVisibility(View.VISIBLE);
                               processBar.setVisibility(View.GONE);
                           }
                   }
               }

               @Override
               public void onFailure(Call<UpdateCabModel> call, Throwable t) {
                   btnNext.setVisibility(View.VISIBLE);
                   btnSkip.setVisibility(View.VISIBLE);
                   processBar.setVisibility(View.GONE);
                  // stopProcessing();
                   Utils.showOkAlert(AddCabFrom.this, getString(R.string.error), message, false);
               }
           });
       }catch (Exception e){
           btnNext.setVisibility(View.VISIBLE);
           btnSkip.setVisibility(View.VISIBLE);
           processBar.setVisibility(View.GONE);
           Utils.showOkAlert(AddCabFrom.this, getString(R.string.error),"Exception "+ message, false);
       }
    }

    // todo call api addVendor through com.gts.coordinator.Retrofit by Ravindra 24-07-19
    private void callApiAddCab() {
       try {
           int cityId = PreferenceData.getInt(AddCabFrom.this, "city_id");
           String city_id = String.valueOf(cityId);
           btnNext.setVisibility(View.GONE);
           startProcessing();
           apiInterface = RetrofitClientWcm.getClient().create(RetrofitApiInterface.class);
           Call<AddCabModel> call = apiInterface.addcCab(new PostCabDetail(contractorID, vendorID, cabNumber, modelID, city_id));
           call.enqueue(new Callback<AddCabModel>() {
               @Override
               public void onResponse(Call<AddCabModel> call, retrofit2.Response<AddCabModel> response) {
                   if (response.isSuccessful()) {
                       AddCabModel model = response.body();
                       com.gts.coordinator.Model.ContractorData.AddCab.Response response1 = model.getD().getResponse();
                       int status = response1.getStatus();
                       String massage = response1.getMessage();
                       vendorCabID = model.getD().getVcabid();
                       if (status == 0) {
                           stopProcessing();
                           Toast.makeText(AddCabFrom.this, "" + massage, Toast.LENGTH_SHORT).show();
                           byte st = 1;
                           driverDBInfo.insertCab("", "", 0,
                                   0, st, vendorCabID, false, vendorID, cabNumber, "", cabModelName,
                                   cabCategoryID, false);
                           Log.e("rss", "onResponse: " + vendorID);
                           // Intent intent = new Intent(AddCabFrom.this, UploadDocumentActivity.class);
                           Intent intent = new Intent(AddCabFrom.this, UploadCabDoc.class);
                           intent.putExtra("id", vendorCabID);
                           intent.putExtra("role", AppConstants.ROLE_CAB);
                           intent.putExtra("st", "addcab");
                           intent.putExtra("is_mandatory", true);
                           startActivity(intent);
                           finish();
                       } else {
                           Utils.showOkAlert(AddCabFrom.this, message, "", true);
                       }
                   }
               }
               @Override
               public void onFailure(Call<AddCabModel> call, Throwable t) {
                   btnNext.setVisibility(View.VISIBLE);
               }
           });
       }catch (Exception e){
           btnNext.setVisibility(View.VISIBLE);
       }
    }

    private void getData() {
        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            pageType = intent.getString("page_type", "");
            cabNumber = intent.getString("cabNumber");
            vendorCabID = intent.getLong("vcabId", 0);
            updateVendorId = intent.getLong("earlierVenID", 0);
            earlierVenID = intent.getLong("earlierVenID", 0);
            st = intent.getByte("status", (byte) 0);
            isVerified = intent.getBoolean("isVerified");
        }
    }

    //   rss error
//todo check cab status ok
    private void callApiSearchCab() {
        try {
            processBar_serch.setVisibility(View.VISIBLE);
            cab_check_stetus.setVisibility(View.GONE);
            tvCabNumber.setEnabled(false);

            apiInterface = RetrofitClientWcm.getClient().create(RetrofitApiInterface.class);
            Call<CheckCabStetus> call = apiInterface.cabStetus(new PostCabNo(cabNumber));
            call.enqueue(new Callback<CheckCabStetus>() {
                @Override
                public void onResponse(Call<CheckCabStetus> call, retrofit2.Response<CheckCabStetus> response) {
                    if (response.isSuccessful()) {
                        stopProcessing();
                        CheckCabStetus checkCabStetus = response.body();
                        int status = checkCabStetus.getD().getGetResponse().getStatus();
                        message = checkCabStetus.getD().getGetResponse().getMessage();
                        long vendorCabID = checkCabStetus.getD().getVcabid();
                        Toast.makeText(AddCabFrom.this, "" + message, Toast.LENGTH_SHORT).show();
                        if (status == 25) {
                            tvCabNumber.setEnabled(true);
                            processBar_serch.setVisibility(View.GONE);
                            cab_check_stetus.setVisibility(View.VISIBLE);
                            Utils.showOkAlert(AddCabFrom.this, getString(R.string.info), "This Cab already Exist And Verified", true);
                        } else if (status == 0) {
                            processBar_serch.setVisibility(View.GONE);
                            cab_check_stetus.setVisibility(View.VISIBLE);
                            btnNext.setVisibility(View.VISIBLE);
                            if (layout_add_cab.getVisibility() == View.GONE) {
                                layout_add_cab.setVisibility(View.VISIBLE);
                                layout_button.setVisibility(View.VISIBLE);
                                select_vendor.setVisibility(View.VISIBLE);
                                cab_check_stetus.setVisibility(View.GONE);
                            }
                        } else if (status == 28)
                        {
                            Intent intent = new Intent(AddCabFrom.this, UploadCabDoc.class);
//            intent.putExtra("vendor_cab_id",vendorCabID);
                            intent.putExtra("id", vendorCabID);
                            intent.putExtra("role", AppConstants.ROLE_CAB);
                            intent.putExtra("st", "addcab");
                            intent.putExtra("is_mandatory", true);
                            startActivity(intent);
//              vendorDBInfo.insertVendor(strVendorName,0, strVendorPhNo,strVendorAddress, strVendorEmail );
                            finish();
                        }else if (status == 29){
                            processBar_serch.setVisibility(View.GONE);
                            cab_check_stetus.setVisibility(View.VISIBLE);
                            tvCabNumber.setEnabled(true);
                            Utils.showOkAlert(AddCabFrom.this, getString(R.string.info), "Your Documents Are Under Verification", false);
                        }
                    }
                }
                @Override
                public void onFailure(Call<CheckCabStetus> call, Throwable t) {
                    processBar_serch.setVisibility(View.GONE);
                    cab_check_stetus.setVisibility(View.VISIBLE);
                    Utils.showOkAlert(AddCabFrom.this, getString(R.string.error), "onFailure Please Try again.", true);
                }
            });
        } catch (Exception e) {
            processBar_serch.setVisibility(View.GONE);
            cab_check_stetus.setVisibility(View.VISIBLE);
            Utils.showOkAlert(AddCabFrom.this, getString(R.string.error), "Exception Please Try again.", true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //todo  edit by Ravindra Singh May 2019 add Autocomplate text view search name and mobile no.

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
            TextView label =  view.findViewById(R.id.spinner_text);
            label.setText(cabCategory.getCategoryName());
            cabCategoryID = cabCategory.getCategoryID();
            cabCateoryname = cabCategory.getCategoryName();
            return view;
        }
    }

    public class CabModelAdapter extends BaseAdapter {
        Context context;
        private ArrayList<CabModel> modelList;

        public CabModelAdapter(Context context, ArrayList<CabModel> modelList) {
            this.context = context;
            this.modelList = modelList;
        }

        @Override
        public int getCount() {
            return modelList.size();
        }

        @Override
        public Object getItem(int position) {
            return modelList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//      Log.i("****","*********************");
            CabModel cabModel = modelList.get(position);
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View view = inflater.inflate(R.layout.custom_spinner, parent, false);
            TextView label =  view.findViewById(R.id.spinner_text);
            label.setText(cabModel.getModelName());
            modelID = cabModel.getModelID();
            cabModelName = cabModel.getModelName();
            return view;
        }
    }

    private void startProcessing() {
        processBar.setVisibility(View.VISIBLE);
    }

    private void stopProcessing() {
        processBar.setVisibility(View.GONE);
    }


    // todo update cab

    @Override
    protected void onRestart() {
        getData();
        super.onRestart();
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
        if (Utils.getConnectivityStatus(AddCabFrom.this) == 0) {
            Utils.showOkAlert(AddCabFrom.this, "No Internet Connection", "Please check your internet connection", false);
        }

    }

    private String blockCharacterSet = "#@()-_;?=,.'~#^$+/|$%&*!:\"";
    private InputFilter filter = (source, start, end, dest, dstart, dend) -> {

        if (source != null && blockCharacterSet.contains(("" + source))) {
            return "";
        }
        return null;
    };

    //total No of line 566
}