package com.gts.coordinator.otp.UpdateVendorNumber;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.gts.coordinator.Activity.From.AddVendorForm;
import com.gts.coordinator.BroadcastReceiver.SMSReceiver;
import com.gts.coordinator.BroadcastReceiver.SmsBroadcastReceiver;
import com.gts.coordinator.R;
import com.gts.coordinator.utils.EncryptionUtils;
import com.sdsmdg.tastytoast.TastyToast;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


public class OtpFragmentUpdateDriver extends DialogFragment {

    private static final String TAG = "OtpFragment";
    private OtpUpdateVendorViewModel otpViewModel;
    private SMSReceiver smsReceiver;
    private TextView otp_text, resend_otp, mTextViewCountDown, message, header;
    private String receiveOtp,phoneNo,vendorName,address;
    private long vendorID;
    private boolean isVerified;
    private CountDownTimer mCountDownTimer;
    private static final long START_TIME_IN_MILLIS = 200000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    MaterialButton cancel;
    MaterialButton verify;
    private static final int REQ_USER_CONSENT = 200;
    SmsBroadcastReceiver smsBroadcastReceiver;


    public static OtpFragmentUpdateDriver newInstance(long vndId, String vendorName, String vendorAddress, String phNo, boolean isVerified) {
        OtpFragmentUpdateDriver fragment = new OtpFragmentUpdateDriver();
        Bundle bundle = new Bundle();
        bundle.putLong("vendorID",vndId);
        bundle.putString("vendorName",vendorName);
        bundle.putString("address",vendorAddress);
        bundle.putString("vendor_phone",phNo);
        bundle.putBoolean("isVerified",isVerified);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//
        View view = inflater.inflate(R.layout.fragment_update_vendor_no, container, false);
        header = view.findViewById(R.id.header_text);
        otp_text = view.findViewById(R.id.enter_otp);
        message = view.findViewById(R.id.message_text);
        resend_otp = view.findViewById(R.id.resend_otp);
        verify = view.findViewById(R.id.button_Otp_Verify);
        cancel = view.findViewById(R.id.button_otp_cancle);

        //rsss
        mTextViewCountDown = view.findViewById(R.id.text_view_countdown);
        Bundle arg = getArguments();
        phoneNo = arg.getString("vendor_phone");
        vendorName= arg.getString("vendorName");
        address= arg.getString("address");
        vendorID = arg.getLong("vendorID");
        isVerified= arg.getBoolean("isVerified");
        message.setText(getString(R.string.otp_cofirmation_message) + phoneNo +"\n"+getString(R.string.otp_cofirmation_message_hindi));
        otpViewModel = new ViewModelProvider( getActivity()).get(OtpUpdateVendorViewModel.class);
        // for get Api Response
//        sendOtp(otpViewModel);
        // for check error
        otpViewModel.getErrorMessage().observe(getActivity(), s -> {
            if (!s.isEmpty()){
                TastyToast.makeText(getActivity(), ""+s, TastyToast.LENGTH_SHORT,TastyToast.ERROR).show();
            }
        });
        eventHandaling(view,otpViewModel);
        return view;
    }



    private void sendOtp(OtpUpdateVendorViewModel otpViewModel) {

        EncryptionUtils en = new EncryptionUtils();
        otpViewModel.getApiResponse(phoneNo).observe(getActivity(), enOtpRespose -> {
            header.setText("Enter OTP");
            otp_text.setVisibility(View.VISIBLE);
            mTextViewCountDown.setVisibility(View.VISIBLE);
            startTimer();
            if (enOtpRespose.getGetresponse().getStatus()==0){
//                receiveOtp = enOtpRespose.getOtp();
                try {
                    en.decrypt(enOtpRespose.getOtp(),"1121212121212");
                    receiveOtp = en.decT;
                    Log.e("rssddddd", "onResponse:OTP "+receiveOtp );

                    startSmsUserConsent();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
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


   /* private void startSMSListener() {
        try {
            smsReceiver = new SMSReceiver();
            smsReceiver.setOTPListener((SMSReceiver.OTPReceiveListener) getActivity());
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
            getActivity().registerReceiver(smsReceiver, intentFilter);
            SmsRetrieverClient client = SmsRetriever.getClient(getActivity());
            Task<Void> task = client.startSmsRetriever();
            task.addOnSuccessListener(aVoid -> {
                Log.e("my_otp", String.valueOf(aVoid));
                // API successfully started
             *//*     otp_text.setText();*//*
            });

            task.addOnFailureListener(e -> {
                Log.e("my_otp", String.valueOf(e));
                // Fail to start API
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    private void startSmsUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(getActivity());
        //We can add sender phone number or leave it blank
        // I'm adding null here
        client.startSmsUserConsent(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                /*    Toast.makeText(getContext(), "On Success", Toast.LENGTH_LONG).show();*/
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                /* Toast.makeText(getContext(), "On OnFailure", Toast.LENGTH_LONG).show();*/
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }

    private void registerBroadcastReceiver() {
        smsBroadcastReceiver = new SmsBroadcastReceiver();
        smsBroadcastReceiver.smsBroadcastReceiverListener =
                new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, REQ_USER_CONSENT);
                    }
                    @Override
                    public void onFailure() {
                    }
                };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        getActivity().registerReceiver(smsBroadcastReceiver, intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(smsBroadcastReceiver);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_USER_CONSENT) {
            if ((resultCode == RESULT_OK) && (data != null)) {
                //That gives all message to us.
                // We need to get the code from inside with regex
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);

                /*     Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();*/


                getOtpFromMessage(message);
            }
        }
    }

    private void getOtpFromMessage(String message) {
        if (message != null) {
            Pattern p = Pattern.compile("(|^)\\d{4}");
            Matcher m = p.matcher(message);
            if (m.find()) {
                Log.e("otp", "" + m.group(0));
                String otp_recieved = m.group(0);
                otp_text.setText(otp_recieved);
            } else {
                Log.e("wrong ", message);
            }
        }
    }

    private void eventHandaling(View view, OtpUpdateVendorViewModel otpViewModel) {


        cancel.setOnClickListener(view13 -> dismiss());
        verify.setOnClickListener(view12 -> {
            if (verify.getText().toString().equals("SEND OTP")){
                verify.setText("VERIFY");
                header.setText("Enter OTP");
                message.setText(getString(R.string.we_have_sent_otp_to) + "\n" + phoneNo);
                otp_text.setVisibility(View.VISIBLE);
                mTextViewCountDown.setVisibility(View.VISIBLE);
                sendOtp(otpViewModel);
            }else{
                String user_otp = otp_text.getText().toString();
                if (user_otp.equals("")) {
                    TastyToast.makeText(getActivity(), " Please Enter OTP ", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (!receiveOtp.equals(user_otp)) {
                    TastyToast.makeText(getActivity(), " Please Enter Valid OTP ", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                } else if (receiveOtp.equals(user_otp)) {

                    Intent intent = new Intent(getContext(), AddVendorForm.class);
                    intent.putExtra("page_type", "EDIT"); //EDITVENDORPhoneNo
                    intent.putExtra("vendorID", vendorID);
                    intent.putExtra("vendorName", vendorName);
                    intent.putExtra("address", address);
                    intent.putExtra("vendor_phone", phoneNo);
                    intent.putExtra("isVerified", isVerified);
                    getContext().startActivity(intent);
                    dismiss();
                }
            }


        });
        resend_otp.setOnClickListener(view1 -> sendOtp(otpViewModel));
    }





/*
    private void getOTPForSms(String opt) {
        if (opt != null) {
            Pattern p = Pattern.compile("(|^)\\d{4}");
            Matcher m = p.matcher(opt);
            if (m.find()) {
                Log.e("otp", "" + m.group(0));
                String otp_recieved = m.group(0);
                otp_text.setText(otp_recieved);
            } else {
                Log.e("wrong ", opt);
            }
        }
    }*/


    @Override
    public void onResume() {
        super.onResume();
        Log.v("fgdg", "onResume");
        if (getShowsDialog()) {
            DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
            int dialogWidth = Math.min(metrics.widthPixels - 20, metrics.heightPixels);
            getDialog().getWindow().setLayout(dialogWidth, WRAP_CONTENT);
        }
    }
}
