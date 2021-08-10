package com.gts.coordinator.ui.vendorIncome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import android.widget.TextView;
import android.widget.Toast;

import com.gts.coordinator.Activity.ActivityDashboard;
import com.gts.coordinator.Model.income.IncomeRespose;
import com.gts.coordinator.Model.income.Vendordatum;
import com.gts.coordinator.R;
import com.gts.coordinator.ui.login.LoginViewModel;
import com.gts.coordinator.utils.PreferenceData;
import com.sdsmdg.tastytoast.TastyToast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class IncomActivity extends AppCompatActivity implements View.OnClickListener {
    TextView fromDate, toDate;
    private int mYear, mMonth, mDay;
    private Date frmDate, toDates;
    private IncomeViewModel viewModel;
    RecyclerView recyclerView;
    private IncomeAdepter adepter;
    private Calendar calendar = Calendar.getInstance();
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    private DatePickerDialog datePickerDialog;
    public String fromdateData, todateData, fDate,tDate;
    private ProgressBar progressBar;
    private ImageView bacButton;
    private SearchView searchView;
    private TextView itemNotFound;
    List<Vendordatum> list;
    Button apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incom);
        viewModel = new ViewModelProvider(this).get(IncomeViewModel.class);
        recyclerView = findViewById(R.id.recycler_view);
        fromDate = findViewById(R.id.from_date);
        toDate = findViewById(R.id.to_date);
        progressBar = findViewById(R.id.process_bar);
        bacButton = findViewById(R.id.back);
        searchView = findViewById(R.id.search_view);
        apply = findViewById(R.id.apply);
        itemNotFound = findViewById(R.id.item_not_found);
        searchView.onActionViewExpanded();
        bacButton.setOnClickListener(this);
        fromDate.setOnClickListener(this);
        toDate.setOnClickListener(this);
        apply.setOnClickListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String currentDate = format.format(new Date());
        String default_from_date =  calendar.get(Calendar.YEAR)+"/"+calendar.get(Calendar.MONTH)+"/1";
        fromdateData = currentDate;
        todateData=currentDate;
      //  toDate.setText(currentDate);
      //  fromDate.setText(currentDate);
        Log.e("currentDate", "onCreate: " + currentDate);
        callApi(String.valueOf(PreferenceData.getLong(IncomActivity.this, "cont_id")), default_from_date, todateData);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adepter.getFilter().filter(s);
                return false;
            }
        });
        searchView.setOnCloseListener(() -> {
            adepter.notifyDataSetChanged();
            return false;
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.from_date:
                calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(IncomActivity.this, R.style.DialogTheme,
                        (view, year, monthOfYear, dayOfMonth) -> {
                            fromDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            fDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                            //2020/01/03
                            try {
                                frmDate = format.parse(fromdateData);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
                break;
            case R.id.to_date:
                if (fromdateData == null) {
                    TastyToast.makeText(getApplicationContext(), "Please Choose From Date First", TastyToast.LENGTH_LONG, TastyToast.INFO).show();
//
                } else {

                    mYear = calendar.get(Calendar.YEAR);
                    mMonth = calendar.get(Calendar.MONTH);
                    mDay = calendar.get(Calendar.DAY_OF_MONTH);

                    datePickerDialog = new DatePickerDialog(IncomActivity.this, R.style.DialogTheme,
                            (view, year, monthOfYear, dayOfMonth) -> {
                                toDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                tDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                                try {
                                    toDates = format.parse(todateData);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }, mYear, mMonth, mDay);

                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
                break;
            case R.id.back:
                onBackPressed();
                break;
                case R.id.apply:
                    if (fDate == null) {
                        TastyToast.makeText(getApplicationContext(), "Please Choose From Date First", TastyToast.LENGTH_LONG, TastyToast.INFO).show();
                 }else if (tDate==null) {
                        TastyToast.makeText(getApplicationContext(), "Please Choose To Date First", TastyToast.LENGTH_LONG, TastyToast.INFO).show();
                    }
                    else
                        if (progressBar.getVisibility()==View.GONE)
                            progressBar.setVisibility(View.VISIBLE);
                    callApi(String.valueOf(PreferenceData.getLong(IncomActivity.this, "cont_id")), fDate, tDate);
                    break;
        }
    }

    private void callApi(String s, String fromdateData, String todateData) {
        list = new ArrayList<>();
        viewModel.getIncom(s, fromdateData, todateData).observe(this, incomeRespose -> {
            if (progressBar.getVisibility() == View.VISIBLE)
                progressBar.setVisibility(View.GONE);
            for (int i = 0; i <incomeRespose.getVendordata().size() ; i++) {
                if (incomeRespose.getVendordata().get(i).getTotalincome()>0){
                    String venid= incomeRespose.getVendordata().get(i).getVid();
                    String name =incomeRespose.getVendordata().get(i).getVendorname();
                    Double ti=incomeRespose.getVendordata().get(i).getTotalincome();
                    String tb =incomeRespose.getVendordata().get(i).getTotalbooking();
                    list.add(new Vendordatum(venid,name,ti,tb));
                }

            }
            if (list.isEmpty()){
                itemNotFound.setText("Total income is = 0 ");
                Toast.makeText(this, "List not found", Toast.LENGTH_SHORT).show();
            }else {
                itemNotFound.setText("");
            }

            adepter = new IncomeAdepter((ArrayList<Vendordatum>) list,fromdateData,todateData, IncomActivity.this);
            Toast.makeText(IncomActivity.this, "" + incomeRespose.getGetresponse().getMessage(), Toast.LENGTH_SHORT).show();
            recyclerView.setAdapter(adepter);
        });
        viewModel.getErrorMessage().observe(this, s1 -> {
            if (s1 != null) {
                if (progressBar.getVisibility() == View.VISIBLE)
                    progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), s1, TastyToast.LENGTH_SHORT, TastyToast.INFO).show();
            }
        });

    }
}
