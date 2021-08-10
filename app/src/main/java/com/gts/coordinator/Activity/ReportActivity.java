package com.gts.coordinator.Activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gts.coordinator.Adapter.ItemAdepter;
import com.gts.coordinator.Adapter.VendorAdapter;
import com.gts.coordinator.Model.ContractorData.BookingRequestReport.JobRequest;
import com.gts.coordinator.Model.ContractorData.BookingRequestReport.PostCabNumber;
import com.gts.coordinator.Model.ContractorData.BookingRequestReport.ResponseRequestHistory;
import com.gts.coordinator.Model.ContractorData.CategoryReport.CategoryList;
import com.gts.coordinator.Model.ContractorData.CategoryReport.ResponsCategoryReport;
import com.gts.coordinator.Model.ContractorData.LoginDetail.LoginReport;
import com.gts.coordinator.Model.ContractorData.LoginDetail.PostDetail;
import com.gts.coordinator.Model.ContractorData.LoginDetail.Session;
import com.gts.coordinator.Model.ContractorData.MaintenanceReport.PostVcabId;
import com.gts.coordinator.Model.ContractorData.MaintenanceReport.Record;
import com.gts.coordinator.Model.ContractorData.MaintenanceReport.ResponsMaintenanceReport;
import com.gts.coordinator.fragments.BookingRequestReport;
import com.gts.coordinator.fragments.MaintenanceReport;
import com.gts.coordinator.Model.ContractorData.PackageReport.PackageReport;
import com.gts.coordinator.Model.ContractorData.PackageReport.PostPackage;
import com.gts.coordinator.Model.ContractorData.PackageReport.ServiceList;
import com.gts.coordinator.R;
import com.gts.coordinator.Retrofit.RetrofitClientWcm;
import com.gts.coordinator.fragments.SelfServiceCatReport;
import com.gts.coordinator.fragments.SelfServicePkgReport;
import com.gts.coordinator.fragments.SessionReport;
import com.gts.coordinator.dao.BookingRequestDao;
import com.gts.coordinator.dao.Driver;
import com.gts.coordinator.dao.MaintenanceReportDao;
import com.gts.coordinator.dao.SelfServiceCatDao;
import com.gts.coordinator.dao.SelfServiceCatDao.CatAttemptDetails;
import com.gts.coordinator.dao.SelfServicePkgDao;
import com.gts.coordinator.dao.SelfServicePkgDao.PkgAttemptDetails;
import com.gts.coordinator.dao.SessionDao;
import com.gts.coordinator.dao.Vendor;
import com.gts.coordinator.db.DriverDBInfo;
import com.gts.coordinator.db.VendorDBInfo;
import com.gts.coordinator.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.gts.coordinator.Retrofit.RetrofitApiInterface;

import retrofit2.Call;
import retrofit2.Callback;


public class ReportActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private static final String TAG = "MyTag";
    private ViewPager viewPager;
    private TabLayout tableLayout;
    private AutoCompleteTextView spVendorName;
    private Spinner spCabNumber;
    private TextView text_item_notfound;
    private String date2, planType, time;
    private int attempt;
    private SessionReport loginLogoutReport;
    private BookingRequestReport bookingRequestReport;
    private SelfServicePkgReport selfServicePkgReport;
    private SelfServiceCatReport selfServiceCatReport;
    private MaintenanceReport maintenanceReport;
    private ReportPagerAdapter mReportPagerAdapter;
    private ProgressBar loginLogoutProgress;
    private String loginLogoutStatus, accepted, notConsiderd, notResponded, rejected, date, cabNumber, actualCategoty;
    private long vendorID, vcabId;
    private ArrayList<Vendor> vendorsList;
    private ArrayList<Driver> numberList;
    private ArrayList<CategoryList> categoryLists;
    private ArrayList<SessionDao> loginLogOutList;
    private ArrayList<BookingRequestDao> bookingreqList;
    private ArrayList<MaintenanceReportDao> maintenanceReportsList;
    private ArrayList<SelfServicePkgDao> pkgDaoDateWiseDetails;
    private ArrayList<SelfServiceCatDao> catDaoDateWiseDetails;
    private ItemAdepter itemAdepter;
    private RetrofitApiInterface retrofitApiInterface;
    //rsss
    // private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        text_item_notfound = findViewById(R.id.text_item_notfound);
        Toolbar toolbar = findViewById(R.id.toolbar_re);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Report");
        vendorsList = new ArrayList<>();
        numberList = new ArrayList<>();
        categoryLists = new ArrayList<>();
        vendorsList = VendorDBInfo.getAllVendors(this);
       // driverDBInfo = new DriverDBInfo(this);
        spVendorName = findViewById(R.id.sp_vendorName);
        spCabNumber = findViewById(R.id.sp_cabNumber);
        tableLayout = findViewById(R.id.tabs);
        tableLayout.addTab(tableLayout.newTab().setText(" Login "));
        tableLayout.addTab(tableLayout.newTab().setText(" Request "));
        tableLayout.addTab(tableLayout.newTab().setText(" Package "));
        tableLayout.addTab(tableLayout.newTab().setText(" Category "));
        tableLayout.addTab(tableLayout.newTab().setText(" Maintenance "));
        tableLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tableLayout.setTabTextColors(getResources().getColor(R.color.black), Color.BLUE);
        viewPager = findViewById(R.id.viewpager);
        loginLogoutProgress = findViewById(R.id.loginLogoutprogress);
        mReportPagerAdapter = new ReportPagerAdapter(getSupportFragmentManager(), tableLayout.getTabCount());
        viewPager.setAdapter(mReportPagerAdapter);
        loginLogOutList = new ArrayList<>();
        bookingreqList = new ArrayList<>();
        maintenanceReportsList = new ArrayList<>();
        pkgDaoDateWiseDetails = new ArrayList<>();
        catDaoDateWiseDetails = new ArrayList<>();
        tableLayout.setupWithViewPager(viewPager);
        spVendorName.setAdapter(new VendorAdapter(this, vendorsList, text_item_notfound));
        // select vendor name
        spVendorName.setOnItemClickListener((parent, view, position, id) -> {
            closeKeyboadrd();
            Vendor vl = vendorsList.get(position);
            vendorID = vl.getVndId();
            numberList = DriverDBInfo.getDrivers(ReportActivity.this, vendorID);
            spCabNumber.setAdapter(new CabNumberAdapter(ReportActivity.this, numberList));
            loginLogOutList.clear();
            bookingreqList.clear();
            maintenanceReportsList.clear();
            pkgDaoDateWiseDetails.clear();
            catDaoDateWiseDetails.clear();
            itemAdepter = new ItemAdepter(numberList, getApplicationContext());
            itemAdepter.setOnItemClickListener(position1 -> {
              //  Driver dl = numberList.get(position1);
             //   String name = dl.getName();
              //  long idd = dl.getDriverId();
               // String cab_no = dl.getCabNo();
                //textView.setText(cab_no);

            });

            // itemEvent();

        });
//rsss
        spCabNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loginLogOutList.clear();
                bookingreqList.clear();
                maintenanceReportsList.clear();
                pkgDaoDateWiseDetails.clear();
                catDaoDateWiseDetails.clear();
                categoryLists.clear();
                if (Utils.getConnectivityStatus(ReportActivity.this) == 0) {
                    Utils.showOkAlert(ReportActivity.this, "No Internet Connection", "Please check your internet connection", false);
                } else {
                    getLoginLogoutReport();
                    getBookingRequestReport();
                    getSelfServiceReport();
                    getCategoryReport();
                    getMaintenanceReport();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        tableLayout.setOnTabSelectedListener((TabLayout.BaseOnTabSelectedListener) this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    class ReportPagerAdapter extends FragmentPagerAdapter {
        int tabCount;

        public ReportPagerAdapter(FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount = tabCount;
            loginLogoutReport = new SessionReport();
            bookingRequestReport = new BookingRequestReport();
            selfServicePkgReport = new SelfServicePkgReport();
            selfServiceCatReport = new SelfServiceCatReport();
            maintenanceReport = new MaintenanceReport();
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return loginLogoutReport;
                case 1:
                    return bookingRequestReport;
                case 2:
                    return selfServicePkgReport;
                case 3:
                    return selfServiceCatReport;
                case 4:
                    return maintenanceReport;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {

            return tabCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String packageName;
            switch (position) {
                case 0:
                    packageName = "Login";
                    break;
                case 1:
                    packageName = "Request";
                    break;
                case 2:
                    packageName = "Package";
                    break;
                case 3:
                    packageName = "Category";
                    break;
                default:
                    packageName = "Maintenance";
                    break;
            }
            return packageName;
        }
    }

    public class CabNumberAdapter extends BaseAdapter {
        Context context;
        private ArrayList<Driver> cabNumberList;

        public CabNumberAdapter(Context context, ArrayList<Driver> numberList) {
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
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//      Log.i("****", "*********************");
            Driver driver = cabNumberList.get(position);
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View view = inflater.inflate(R.layout.custom_spinner, parent, false);
            TextView label = view.findViewById(R.id.spinner_text);
            label.setText(driver.getCabNo());
            vcabId = driver.getDriverId();
            cabNumber = driver.getCabNo();

            return view;
        }
    }

    // ok report
    private void getLoginLogoutReport() {
        loginLogoutProgress.setVisibility(View.VISIBLE);
        retrofitApiInterface = RetrofitClientWcm.getClient().create(RetrofitApiInterface.class);
        Call<LoginReport> call = retrofitApiInterface.getLoginHistory(new PostDetail(vendorID, vcabId));
        call.enqueue(new Callback<LoginReport>() {
            @Override
            public void onResponse(Call<LoginReport> call, retrofit2.Response<LoginReport> response) {
                if (response.isSuccessful()) {
                    LoginReport loginReport = response.body();
                    Log.d(TAG, "onResponse() returned: " + loginReport.getD().getResponse().getMessage());
                    if (loginReport.getD().getResponse().getStatus() == 0) {
                        ArrayList<Session> sessions = (ArrayList<Session>) loginReport.getD().getSessions();
                        for (int i = 0; i < sessions.size(); i++) {

                            loginLogoutStatus = sessions.get(i).getStatus();
                            Calendar time = null;
                            String strTime = String.format("%s %s", sessions.get(i).getDate(), sessions.get(i).getTime());
//              Log.i("ReportActivity", " strTime=" + strTime);
                            try {
                                time = Calendar.getInstance();
                                time.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(strTime));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            loginLogOutList.add(new SessionDao(loginLogoutStatus, time));
                        }
                        loginLogoutReport.getLoginLogoutStatus(loginLogOutList, loginLogoutProgress);
                    }

                }
            }

            @Override
            public void onFailure(Call<LoginReport> call, Throwable t) {
                Toast.makeText(ReportActivity.this, "Some Thing Want wrong ", Toast.LENGTH_SHORT).show();

            }
        });

    }

    // ok report
    private void getBookingRequestReport() {
        retrofitApiInterface = RetrofitClientWcm.getClient().create(RetrofitApiInterface.class);
        Call<ResponseRequestHistory> call = retrofitApiInterface.getRequestHistory(new PostCabNumber(cabNumber));
        call.enqueue(new Callback<ResponseRequestHistory>() {
            @Override
            public void onResponse(Call<ResponseRequestHistory> call, retrofit2.Response<ResponseRequestHistory> response) {
                if (response.isSuccessful()) {
                    ResponseRequestHistory history = response.body();
                    if (history.getD().getResponse().getStatus() == 0) {
                        ArrayList<JobRequest> requests = (ArrayList<JobRequest>) history.getD().getJobRequests();
                        for (int i = 0; i < requests.size(); i++) {
                            int acc = requests.get(i).getAccepted();
                            int notCon = requests.get(i).getNotConsidered();
                            int notRes = requests.get(i).getNotResponded();
                            int reg = requests.get(i).getRejected();
                            accepted = String.valueOf(acc);
                            notConsiderd = String.valueOf(notCon);
                            notResponded = String.valueOf(notRes);
                            rejected = String.valueOf(reg);
                            date = requests.get(i).getDate();
                            bookingreqList.add(new BookingRequestDao(accepted, notConsiderd, notResponded, rejected, date));
                        }
                        bookingRequestReport.getBookingRequestStatus(bookingreqList);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseRequestHistory> call, Throwable t) {
                Toast.makeText(ReportActivity.this, "Some Thing want wrong ", Toast.LENGTH_SHORT).show();

            }
        });
    }

    // ok report
    private void getMaintenanceReport() {

        retrofitApiInterface = RetrofitClientWcm.getClient().create(RetrofitApiInterface.class);
        Call<ResponsMaintenanceReport> call = retrofitApiInterface.getMaintenanceReport(new PostVcabId(vcabId));

        call.enqueue(new Callback<ResponsMaintenanceReport>() {
            @Override
            public void onResponse(Call<ResponsMaintenanceReport> call, retrofit2.Response<ResponsMaintenanceReport> response) {
                if (response.isSuccessful()) {
                    ResponsMaintenanceReport report = response.body();
                    Log.e(TAG, "onResponse: " + report.getD().getResponse().getMessage());
                    if (report.getD().getResponse().getStatus() == 0) {
                        ArrayList<Record> records = (ArrayList<Record>) report.getD().getRecords();
                        for (int i = 0; i < records.size(); i++) {
                            String inDate = records.get(i).getInDate();
                            String inTime = records.get(i).getInTime();
                            String outDate = records.get(i).getOutDate();
                            String outTime = records.get(i).getOutTime();
                            String totalMaintenaceTime = records.get(i).getTotalMaintenanceTime();
                            maintenanceReportsList.add(new MaintenanceReportDao(inDate, inTime, outDate, outTime, totalMaintenaceTime));
                        }
                        maintenanceReport.getMaintenaceReport(maintenanceReportsList);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsMaintenanceReport> call, Throwable t) {
                Toast.makeText(ReportActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
                //Log.e(TAG, "onFailure: "+t.toString() );
            }
        });

    }

    private void getSelfServiceReport() {
        retrofitApiInterface = RetrofitClientWcm.getClient().create(RetrofitApiInterface.class);
        Call<PackageReport> call = retrofitApiInterface.getPackage(new PostPackage(vcabId));
        call.enqueue(new Callback<PackageReport>() {
            @Override
            public void onResponse(Call<PackageReport> call, retrofit2.Response<PackageReport> response) {

                if (response.isSuccessful()) {
                    PackageReport report = response.body();
                    int status = report.getD().getResponse().getStatus();
                    if (status == 0) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        ArrayList<PkgAttemptDetails> pkgAttemptsList = new ArrayList<>();
                        PkgAttemptDetails pkgAttemptDetails = null;

                        ArrayList<ServiceList> serviceLists = (ArrayList<ServiceList>) report.getD().getServiceList();
                        for (int i = 0; i < serviceLists.size(); i++) {
                            if (serviceLists.get(i).getAttempt() != null) {
                                attempt = serviceLists.get(i).getAttempt();
                            }
                            date2 = serviceLists.get(i).getDate();
                            planType = serviceLists.get(i).getPlanType();
                            time = serviceLists.get(i).getTime();
                            String strTime = String.format("%s %s", date2, time);
                            Calendar dateTime = null;
                            try {
                                dateTime = Calendar.getInstance();
                                dateTime.setTime(dateFormat.parse(strTime));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (pkgAttemptDetails == null) {
                                pkgAttemptDetails = new PkgAttemptDetails(attempt, dateTime);
                            }

                            if (attempt != pkgAttemptDetails.getAttempt()) {
                                pkgAttemptsList.add(pkgAttemptDetails);
                                pkgAttemptDetails = new PkgAttemptDetails(attempt, dateTime);
//                }
                            }
                            pkgAttemptDetails.addPackage(planType);
                            if (i == report.getD().getServiceList().size() - 1) {
                                pkgAttemptsList.add(pkgAttemptDetails);
                            }
                        }
                        pkgDaoDateWiseDetails = distributeIntoCategories(pkgAttemptsList);
                        for (SelfServicePkgDao srvcDao : pkgDaoDateWiseDetails) {
                            ArrayList<PkgAttemptDetails> attList = srvcDao.getPkgAttempts();
                        }
                        selfServicePkgReport.getSelfServicepkgReport(pkgDaoDateWiseDetails);
                    } else if (status == 1) {
                        selfServicePkgReport.getSelfServicepkgReport(null);
                    }
                } else {
                    Toast.makeText(ReportActivity.this, "Something want wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PackageReport> call, Throwable t) {
                Toast.makeText(ReportActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<SelfServicePkgDao> distributeIntoCategories(ArrayList<PkgAttemptDetails> pkgAttemptsList) {
        ArrayList<SelfServicePkgDao> pkgDaoDateWiseDetails = new ArrayList<>();
        SelfServicePkgDao pkgAttemptDateWiseDao = null;
        Calendar date = Calendar.getInstance();
        int attemptsSize = pkgAttemptsList.size();
        for (int i = 0; i < attemptsSize; ++i) {
            PkgAttemptDetails att = pkgAttemptsList.get(i);
            Calendar currCal = att.getTime();
            if (pkgAttemptDateWiseDao == null) {
                date.set(currCal.get(Calendar.YEAR), currCal.get(Calendar.MONTH), currCal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
                pkgAttemptDateWiseDao = new SelfServicePkgDao(att.getTime());
            }
            Calendar currentDate = Calendar.getInstance();
            currentDate.set(currCal.get(Calendar.YEAR), currCal.get(Calendar.MONTH), currCal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            if (date.compareTo(currentDate) != 0) {
                pkgDaoDateWiseDetails.add(pkgAttemptDateWiseDao);
                pkgAttemptDateWiseDao = new SelfServicePkgDao(att.getTime());
                date = currentDate;
            }
            pkgAttemptDateWiseDao.addAttempt(att);
            if (i == attemptsSize - 1) {
                pkgDaoDateWiseDetails.add(pkgAttemptDateWiseDao);
            }
        }

        return pkgDaoDateWiseDetails;
    }

    // rsss
    private void getCategoryReport() {
        // categoryLists.clear();
        retrofitApiInterface = RetrofitClientWcm.getClient().create(RetrofitApiInterface.class);
        Call<ResponsCategoryReport> call = retrofitApiInterface.getCategoryReport(new PostVcabId(vcabId));
        call.enqueue(new Callback<ResponsCategoryReport>() {
            @Override
            public void onResponse(Call<ResponsCategoryReport> call, retrofit2.Response<ResponsCategoryReport> response) {
                if (response.isSuccessful()) {
                    ResponsCategoryReport categoryReport = response.body();
                    if (categoryReport.getD().getResponse().getStatus() == 0) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        ArrayList<CatAttemptDetails> catAttemptsList = new ArrayList<>();
                        CatAttemptDetails catAttemptDetails = null;
                        categoryLists = (ArrayList<CategoryList>) categoryReport.getD().getCategoryList();
                        //   CategoryReportAdapter cd = new CategoryReportAdapter();


                        selfServiceCatReport.getSelfServiceCatReport(categoryLists);

                     /*   for (int i = 0; i <categoryLists.size() ; i++) {
                            String date = categoryLists.get(i).getDate();
                            String time = categoryLists.get(i).getTime();
                            String planType = categoryLists.get(i).getCatName();
                            actualCategoty = categoryLists.get(i).getActualCat();
                            Calendar dateTime = null;
                            String strTime = String.format("%s %s", date, time);
                            *//**//*
                            try {
                                dateTime = Calendar.getInstance();
                                dateTime.setTime(dateFormat.parse(strTime));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if (catAttemptDetails == null) {
                                catAttemptDetails = new CatAttemptDetails(attempt, dateTime);
                            }

                            if (attempt != catAttemptDetails.getAttempt()) {
                                catAttemptsList.add(catAttemptDetails);
//                if (i != elementsCount-1) {
                                catAttemptDetails = new CatAttemptDetails(attempt, dateTime);
//                }
                            }
                            catAttemptDetails.addPackage(planType);
                            if (i == categoryLists.size() - 1) {
                                catAttemptsList.add(catAttemptDetails);
                            }

                        }
                        catDaoDateWiseDetails = distributeDataCabCategories(catAttemptsList);
                        for (SelfServiceCatDao srvcDao : catDaoDateWiseDetails) {
                            ArrayList<CatAttemptDetails> attList = srvcDao.getCatAttempts();

                        }
                        selfServiceCatReport.getSelfServiceCatReport(catDaoDateWiseDetails);*/
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsCategoryReport> call, Throwable t) {
                Toast.makeText(ReportActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<SelfServiceCatDao> distributeDataCabCategories(ArrayList<CatAttemptDetails> catAttemptsList) {
        //            Calendar date = null ;
//            ArrayList<PkgAttemptDetails> pkgAttemptDateWiseDetails = new ArrayList<>();
        ArrayList<SelfServiceCatDao> catDaoDateWiseDetails = new ArrayList<>();
        SelfServiceCatDao catAttemptDateWiseDao = null;
//    Date date = null ;
        Calendar date = Calendar.getInstance();

        int attemptsSize = catAttemptsList.size();
        for (int i = 0; i < attemptsSize; ++i) {
            CatAttemptDetails att = catAttemptsList.get(i);
            Calendar currCal = att.getTime();
            if (catAttemptDateWiseDao == null) {
                date.set(currCal.get(Calendar.YEAR), currCal.get(Calendar.MONTH), currCal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
                catAttemptDateWiseDao = new SelfServiceCatDao(att.getTime(), actualCategoty);
            }

//              pkgAttemptDateWiseDetails.add(att);


            Calendar currentDate = Calendar.getInstance();
            currentDate.set(currCal.get(Calendar.YEAR), currCal.get(Calendar.MONTH), currCal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            if (date.compareTo(currentDate) != 0) {
                catDaoDateWiseDetails.add(catAttemptDateWiseDao);
                catAttemptDateWiseDao = new SelfServiceCatDao(att.getTime(), actualCategoty);
                date = currentDate;
            }
            catAttemptDateWiseDao.addAttempt(att);
            if (i == attemptsSize - 1) {
                catDaoDateWiseDetails.add(catAttemptDateWiseDao);
            }
        }
        return catDaoDateWiseDetails;
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

    private void closeKeyboadrd() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showDate() {
        //   retrofitApiInterface.getCategoryReport(new PostVcabId(vcabId));
        //  Call<ResponsCategoryReport> call =retrofitApiInterface.getCategoryReport(vcabId).clone()
    }
}