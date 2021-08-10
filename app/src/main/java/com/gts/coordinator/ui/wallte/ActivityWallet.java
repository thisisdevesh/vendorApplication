package com.gts.coordinator.ui.wallte;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.gts.coordinator.Adapter.WalletActivityAdepter;
import com.gts.coordinator.Adapter.WalletHistoryAdapter;
import com.gts.coordinator.BroadcastReceiver.MyApplication;
import com.gts.coordinator.Model.ContractorData.PayU.PayuApiResponce;
import com.gts.coordinator.Model.ContractorData.PayU.PayuResponce;
import com.gts.coordinator.Model.ContractorData.PayU.PostRechargeWalateDetailPayu;
import com.gts.coordinator.Model.ContractorData.PayUPaymentFail.ResponsePaymentFail;
import com.gts.coordinator.Model.ContractorData.TransferMoney.PostTransferMoneyDetail;
import com.gts.coordinator.Model.ContractorData.TransferMoney.TransferMoneyModel;
import com.gts.coordinator.Payumoneypnp.AppEnvironment;
import com.gts.coordinator.Payumoneypnp.AppPreference;
import com.gts.coordinator.R;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;
import com.gts.coordinator.Retrofit.RetrofitClientWcm;
import com.gts.coordinator.dao.Driver;
import com.gts.coordinator.dao.Vendor;
import com.gts.coordinator.db.DriverDBInfo;
import com.gts.coordinator.db.VendorDBInfo;
import com.gts.coordinator.utils.NetworkUtil;
import com.gts.coordinator.utils.PreferenceData;
import com.gts.coordinator.utils.Utils;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityWallet extends AppCompatActivity implements WalletHistoryAdapter.Update, WalletHistoryDataSource.showPrograss {
    private static final String TAG = "MyTag";
    double netBalence = 0;
    ShimmerFrameLayout shimmer_container;
    RecyclerView.LayoutManager mLayoutManager;
    WalletHistoryViewModel walletHistoryViewModel;
    private RecyclerView recyclerwallet;
    private TextView tvWalletBal;
    private RetrofitApiInterface apiInterface;
    private String contractorName, conractorPhno, vendorPhno, strVenName, userCredentials;
    private long vendorID, contractorID;
    private AppPreference mAppPreference;
    private AlertDialog dialog;
    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;
    private ArrayList<Driver> driverList;
    private ArrayList<Driver> driverNameList;
    private VendorDBInfo vendorDBInfo;
    private ArrayList<Vendor> vendorNameList;
    private WalletActivityAdepter driverAdapter;
    private int cccc = 10;
    private WalletHistoryAdapter adapter;
    private ProgressDialog progressDialog;
    private TextView testText;
    private DriverDBInfo driverDBInfo;
    private boolean callApiStetus;
    private String blockCharacterSet = "#@()-_;?=,..'~#^$+/|$%&*!:\"";
    private InputFilter filter = (source, start, end, dest, dstart, dend) -> {

        if (source != null && blockCharacterSet.contains(("" + source))) {
            return "";
        }
        return null;
    };

    public static String hashCal(String str) {
        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (byte aMessageDigest : messageDigest) {
                String hex = Integer.toHexString(0xFF & aMessageDigest);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }
        return hexString.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__wallet);
        init();
        checkNetworkStatus();
        progressDialog = new ProgressDialog(ActivityWallet.this);
        contractorID = PreferenceData.getLong(this, "cont_id");
        contractorName = PreferenceData.getString(this, "cont_name");
        conractorPhno = PreferenceData.getString(this, "cont_phone1");
        Toolbar toolbar = findViewById(R.id.toolbar_wallet);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Wallet Transaction");
        mLayoutManager = new LinearLayoutManager(this);
        mAppPreference = new AppPreference();
        vendorDBInfo = new VendorDBInfo(this);
        driverDBInfo = new DriverDBInfo(this);
        //rsss
        getTransationDetailsApi();

        //PreferenceData.saveBoolean(ActivityWallet.this,"api_stetus",false);
        callApiStetus = PreferenceData.getBoolean(this, "api_stetus");

        if (PreferenceData.getBoolean(this, "api_stetus") != false) {
            //rsss
            callApiAddMoney();
        }

    }

    private void init() {
        testText = findViewById(R.id.test_text);
        shimmer_container = findViewById(R.id.shimmer_wallet_container);
        recyclerwallet = findViewById(R.id.recycler_wallet);
        tvWalletBal = findViewById(R.id.wallet_balance_value);
    }

    private void getTransationDetailsApi() {

        walletHistoryViewModel = new ViewModelProvider(this).get(WalletHistoryViewModel.class);
        adapter = new WalletHistoryAdapter(ActivityWallet.this, this);
        recyclerwallet.setHasFixedSize(true);
        recyclerwallet.setLayoutManager(mLayoutManager);
        shimmer_container.startShimmer();
        walletHistoryViewModel.itemPagedList.observe(this, items -> adapter.submitList(items));
        // walletHistoryViewModel.showData(1);
        recyclerwallet.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        recyclerwallet.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //   int childCount = mLayoutManager.getChildCount();
                int itemCont = mLayoutManager.getItemCount();
                if (Utils.getConnectivityStatus(getApplicationContext()) == 0) {
                    Utils.showOkAlert(ActivityWallet.this, "No Internet Connection", "Please check your internet connection", false);
                }
                if (itemCont == cccc) {
                    //   progressBar.setVisibility(View.VISIBLE);
                    cccc = cccc + 10;
                } else if (itemCont != cccc) {
                    //  progressBar.setVisibility(View.GONE);
                }
                //  Toast.makeText(ActivityNotification.this, "childCount :-" + String.valueOf(dx) + "\n itemCont" + String.valueOf(dy), Toast.LENGTH_SHORT).show();


            }
        });

    }

    private void startProcessing() {
        progressDialog.show();
        progressDialog.setMessage(getString(R.string.lod));
        progressDialog.setCancelable(false);
    }

    private void stopProcessing() {
        progressDialog.cancel();

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
    public void walletEvent(View view) {
        switch (view.getId()) {
            case R.id.recharge_now:
                showAlertDialog("recharge");
                break;
            case R.id.transfer_money:
                if (netBalence != 0) {
                    showAlertDialog("transfer");
                } else {
                    Utils.showOkAlert(ActivityWallet.this, getString(R.string.info), "Balance Not Available ", false);
                }
                break;
        }
    }

    private void showAlertDialog(String type) {
        //rsss
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityWallet.this);
        int lay;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            lay = R.layout.fragment_wallet_recharge_dialog;
        } else {
            lay = R.layout.fragment_wallet_recharge_dialog2;
        }
        View view = LayoutInflater.from(getApplicationContext()).inflate(lay, null, false);
        Button add = view.findViewById(R.id.add);
        Button cancel = view.findViewById(R.id.cancel);
        EditText ammount = view.findViewById(R.id.et_amount);
        TextView totalAmount = view.findViewById(R.id.totle_amount);
        TextView itemNotFound = view.findViewById(R.id.text_item_notfound);
        AutoCompleteTextView vendor_list = view.findViewById(R.id.vendor_dropdown);
        RelativeLayout lay_out = view.findViewById(R.id.vendor_dropdown_lay);
        TextView text_view_wallet = view.findViewById(R.id.text_view_wallet);
        ImageView wallet_image = view.findViewById(R.id.id_image);
        ammount.setFilters(new InputFilter[]{filter});
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(8);
        ammount.setFilters(filters);
        driverList = new ArrayList<Driver>();
        driverNameList = driverDBInfo.getAllDriverData();
        for (Driver v : driverNameList) {
            driverList.add(v);
        }
        driverAdapter = new WalletActivityAdepter(this, driverList, itemNotFound);

        if (type.equals("recharge")) {
            text_view_wallet.setText(getString(R.string.recharge_gts_wallet));
            wallet_image.setImageDrawable(getResources().getDrawable(R.drawable.wallet_bg));
            if (lay_out.getVisibility() == View.VISIBLE) {
                lay_out.setVisibility(View.GONE);
            }
            add.setOnClickListener(v -> {
                String money = ammount.getText().toString();
                if (NetworkUtil.getConnectivityStatus(ActivityWallet.this) == 0) {
                    Utils.showOkAlert(ActivityWallet.this, "Internet Disconnected", "Please check your internet connection", false);
                } else {
                    addMoneyWallte(money, ammount);
                }
            });
        } else if (type.equals("transfer")) {
            text_view_wallet.setText(getString(R.string.gts_transfer_money));
            wallet_image.setImageDrawable(getResources().getDrawable(R.drawable.wallet_money));
            vendor_list.setThreshold(1);
            vendor_list.setAdapter(driverAdapter);
            vendor_list.setOnItemClickListener((parent, view1, position, id) -> {
                Driver list = driverList.get(position);
                vendorID = list.getVendorId();
                String cabNo = list.getCabNo();
                vendorNameList = vendorDBInfo.getVendors(cabNo);
                strVenName = vendorNameList.get(0).getName();
                vendorPhno = vendorNameList.get(0).getPhno();
            });

            if (lay_out.getVisibility() == View.GONE) {
                lay_out.setVisibility(View.VISIBLE);
            }
            add.setOnClickListener(v -> {
                try {
                    checkNetworkStatus();
                    String money = ammount.getText().toString();
                    if (strVenName == null) {
                        Utils.showOkAlert(ActivityWallet.this, getString(R.string.info), "Please Select Vendor ", true);
                    } else if (money.equals("")) {
                        Utils.showOkAlert(ActivityWallet.this, getString(R.string.info), "Please Enter Amount", true);
                    } else if (money.equals("0") || money.equals("00") || money.equals("000") || money.equals("0000") ||
                            money.equals("00000") || money.equals("000000") || money.equals("0000000") || money.equals("00000000") || money.equals("000000000") || money.equals("0000000000")) {
                        Utils.showOkAlert(ActivityWallet.this, getString(R.string.info), "Please Enter Valid Amount", false);
                    } else {
                        transferMoneyData(money);
                    }
                } catch (Exception e) {
                }
            });
        }
        cancel.setOnClickListener(v -> dialog.cancel());
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.setView(view);
        dialog.show();
    }

    //rdd
    private void checkNetworkStatus() {
        if (NetworkUtil.getConnectivityStatus(ActivityWallet.this) == 0) {
            Utils.showOkAlert(ActivityWallet.this, "Internet Disconnected", "Please check your internet connection", false);
        }
    }

    private void addMoneyWallte(String money, EditText ammount) {
        if (money.equals("")) {
            Utils.showOkAlert(ActivityWallet.this, getString(R.string.info), "Please Enter Amount", true);
        } else if (money.equals("0") || money.equals("00") || money.equals("000") || money.equals("0000") ||
                money.equals("00000") || money.equals("000000") || money.equals("0000000") || money.equals("00000000") || money.equals("000000000") || money.equals("0000000000")) {
            Utils.showOkAlert(ActivityWallet.this, getString(R.string.info), "Please Enter Valid Amount", false);
        } else {
            ammount.setEnabled(false);
            double moneyVal = Double.valueOf(money);
            launchPayUMoneyFlow(moneyVal);
            dialog.cancel();
            startProcessing();
        }
    }

    private void transferMoneyData(String money) {
        try {
            double toTransferMoneyVal = Double.parseDouble(money);
            if (netBalence < toTransferMoneyVal) {
                Utils.showOkAlert(ActivityWallet.this, getString(R.string.info), "Balance Not Available ", false);
            } else {

                new MaterialAlertDialogBuilder(ActivityWallet.this, R.style.AlertDialogTheme)
                        .setMessage("Are you sure you want to exit?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            transferMoney(toTransferMoneyVal);

                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .show();

                dialog.cancel();

            }

        } catch (Exception e) {

        }
    }

    private void transferMoney(double money) {
        startProcessing();
        apiInterface = RetrofitClientWcm.getClient().create(RetrofitApiInterface.class);
        Call<TransferMoneyModel> call = apiInterface.transferMoney(new PostTransferMoneyDetail(money, contractorID, contractorName, vendorID, strVenName, conractorPhno, vendorPhno));
        call.enqueue(new Callback<TransferMoneyModel>() {
            @Override
            public void onResponse(Call<TransferMoneyModel> call, retrofit2.Response<TransferMoneyModel> response) {
                if (response.isSuccessful()) {
                    try {
                        stopProcessing();
                        TransferMoneyModel transferMoneyModel = response.body();
                        int staus = transferMoneyModel.getD().getStatus();
                        String mess = transferMoneyModel.getD().getMessage();
                        if (staus == 0) {
                            showAlertDialog2(mess);
                        } else {
                            Utils.showOkAlert(ActivityWallet.this, getString(R.string.info), mess, true);
                        }
                    } catch (Exception e) {
                        //  showUi();
                    }
                }
            }

            @Override
            public void onFailure(Call<TransferMoneyModel> call, Throwable t) {
                Utils.showOkAlert(ActivityWallet.this, getString(R.string.error), "onFailure", true);
                //  showUi();
            }
        });

    }

    private void showAlertDialog2(String mess) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityWallet.this);
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.fragment_message_dialog, null, false);
            ImageView imageView = view.findViewById(R.id.id_image_view_dialog);
            TextView message = view.findViewById(R.id.message);
            TextView title = view.findViewById(R.id.title);
            Button exit = view.findViewById(R.id.btn_close);
            exit.setText(getString(R.string.btn_ok));
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_successful_icon));
            title.setText(getString(R.string.info));
            message.setText(mess);
            exit.setOnClickListener(view1 -> {
                Intent in = new Intent(getApplicationContext(), ActivityWallet.class);
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                dialog.cancel();
                finish();
            });
            builder.setCancelable(false);
            dialog = builder.create();
            dialog.setView(view);
            dialog.show();
        } catch (Exception e) {
        }
    }

    // todo start payu
    private void launchPayUMoneyFlow(double moneyVal) {

        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();
        payUmoneyConfig.setPayUmoneyActivityTitle("Payment");
        payUmoneyConfig.disableExitConfirmation(true);
        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();
        double amount = 0;
        try {
            amount = moneyVal;

        } catch (Exception e) {
            e.printStackTrace();
        }
        String txnId = System.currentTimeMillis() + "";

        String phone = PreferenceData.getString(ActivityWallet.this, "cont_phone1");
        String productName = "gts_partner";
        String firstName = PreferenceData.getString(ActivityWallet.this, "cont_name");
        String email = PreferenceData.getString(ActivityWallet.this, "con_email");
        // set app environment
        AppEnvironment appEnvironment = AppEnvironment.PRODUCTION;
        builder.setAmount(String.valueOf(amount))
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl(appEnvironment.surl())
                .setfUrl(appEnvironment.furl())
                .setIsDebug(appEnvironment.debug())
                .setKey(appEnvironment.merchant_Key())
                .setMerchantId(appEnvironment.merchant_ID());

        try {
            mPaymentParams = builder.build();
            // mPaymentParams.user

            mPaymentParams = calculateServerSideHashAndInitiatePayment1(mPaymentParams);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, ActivityWallet.this, R.style.AppTheme_blue, mAppPreference.isOverrideResultScreen());
            } else {
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, ActivityWallet.this, R.style.AppTheme_Base, mAppPreference.isOverrideResultScreen());

            }
        } catch (Exception e) {
            // some exception occurred
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            //  payNowButton.setEnabled(true);
        }
    }

    /**
     * Thus function calculates the hash for transaction
     *
     * @param paymentParam payment params of transaction
     * @return payment params along with calculated merchant hash
     */
    private PayUmoneySdkInitializer.PaymentParam calculateServerSideHashAndInitiatePayment1(final PayUmoneySdkInitializer.PaymentParam paymentParam) {

        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, String> params = paymentParam.getParams();
        stringBuilder.append(params.get(PayUmoneyConstants.KEY) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.TXNID) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.AMOUNT) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.PRODUCT_INFO) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.FIRSTNAME) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.EMAIL) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF1) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF2) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF3) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF4) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF5) + "||||||");
        AppEnvironment appEnvironment = ((MyApplication) getApplication()).getAppEnvironment();
        stringBuilder.append(appEnvironment.salt());
        String hash = hashCal(stringBuilder.toString());
        paymentParam.setMerchantHash(hash);

        return paymentParam;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        stopProcessing();
        Log.d("MainActivityWallte", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data !=
                null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                    .INTENT_EXTRA_TRANSACTION_RESPONSE);
            ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);
            String payuResponse = transactionResponse.getPayuResponse();
            // Response from SURl and FURL
            // Check which object is non-null
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                //rsss
                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    PreferenceData.saveBoolean(ActivityWallet.this, "api_stetus", true);
                    String merchantResponse = transactionResponse.getTransactionDetails();
                    Gson gson = new Gson();
                    PayuResponce payuResponce = gson.fromJson(payuResponse, PayuResponce.class);
                    PreferenceData.saveString(ActivityWallet.this, "t_id", payuResponce.getResult().getTxnid());
                    PreferenceData.saveString(ActivityWallet.this, "net_amount", payuResponce.getResult().getAmount());
                    PreferenceData.saveString(ActivityWallet.this, "bank_reference", payuResponce.getResult().getBankRefNum());
                    callApiAddMoney();

                } else if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.FAILED)) {
                    String merchantResponse = transactionResponse.getTransactionDetails();
                    Gson gson = new Gson();
                    PayuResponce payuResponceFail = gson.fromJson(payuResponse, PayuResponce.class);
                    String f_tid = payuResponceFail.getResult().getTxnid();
                    String net_amount = payuResponceFail.getResult().getAmount();
                    String bank_reference = payuResponceFail.getResult().getBankRefNum();
                    callApiFailPayment(f_tid, net_amount, bank_reference);
                    Log.d(TAG, "onActivityResult: " + f_tid + net_amount + bank_reference);
                    //rsssss

                }

            } else if (resultModel != null && resultModel.getError() != null) {
                Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
            } else {
                Log.d(TAG, "Both objects are null!");
            }
        }
    }

    private void callApiAddMoney() {


        String transId, bankrefrenceNumber;
        String amount = PreferenceData.getString(ActivityWallet.this, "net_amount");
        double net_amount = Double.parseDouble(amount);
        transId = PreferenceData.getString(ActivityWallet.this, "t_id");
        bankrefrenceNumber = PreferenceData.getString(ActivityWallet.this, "bank_reference");
        apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<PayuApiResponce> call = apiInterface.rechargeWalatePayu(new PostRechargeWalateDetailPayu(contractorID, net_amount, transId, bankrefrenceNumber));
        call.enqueue(new Callback<PayuApiResponce>() {
            @Override
            public void onResponse(Call<PayuApiResponce> call, Response<PayuApiResponce> response) {
                if (response.isSuccessful()) {
                    PayuApiResponce re = response.body();
                    int status = re.getGetresponse().getStatus();
                    if (status == 0) {
                        // callApiStetus = false;
                        PreferenceData.saveBoolean(ActivityWallet.this, "api_stetus", false);
                        String mess = "Your Transaction is Successful";
                        showAlertDialog2(mess);
                    } else {
                        //   Utils.showOkAlert(ActivityWallet.this, getString(R.string.info), "Failure", true);
                    }
                }
            }

            @Override
            public void onFailure(Call<PayuApiResponce> call, Throwable t) {
                Utils.showOkAlert(ActivityWallet.this, getString(R.string.error), "Failure" + t.toString(), true);

            }
        });
    }

    private void callApiFailPayment(String f_tid, String netamount, String bank_reference) {
        double net_amount = Double.parseDouble(netamount);
        apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<ResponsePaymentFail> call = apiInterface.paymentFail(new PostRechargeWalateDetailPayu(contractorID, net_amount, f_tid, bank_reference));
        call.enqueue(new Callback<ResponsePaymentFail>() {
            @Override
            public void onResponse(Call<ResponsePaymentFail> call, Response<ResponsePaymentFail> response) {
                if (response.isSuccessful()) {
                    ResponsePaymentFail paymentFail = response.body();

                    Log.e(TAG, "onResponse: " + paymentFail.getGetresponse().getMessage());
                    if (paymentFail.getGetresponse().getStatus() == 0) {
                        Toast.makeText(ActivityWallet.this, " Payment Fail.... ", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePaymentFail> call, Throwable t) {
                Toast.makeText(ActivityWallet.this, "onFailure " + t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    //todo end payu
    @Override
    public void updateAvailableBalance(double amount) {
        netBalence = amount;
        tvWalletBal.setText(getString(R.string.rupaya_logo) + " " + String.valueOf(netBalence));
        //  tvWalletBal.setText(""+amount);
        if (shimmer_container.getVisibility() == View.VISIBLE) {
            shimmer_container.setVisibility(View.GONE);
            recyclerwallet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void progressBarStatus(boolean a) {
        if (a == true)
            Toast.makeText(this, "Start Progress...", Toast.LENGTH_SHORT).show();
    }
}
//582