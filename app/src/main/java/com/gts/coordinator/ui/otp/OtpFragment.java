package com.gts.coordinator.ui.otp;

import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.gts.coordinator.BroadcastReceiver.SMSReceiver;
import com.gts.coordinator.Model.ContractorData.ResetPassword.PostResetPasswod;
import com.gts.coordinator.Model.ContractorData.ResetPassword.ResponseResetPassword;
import com.gts.coordinator.R;
import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;
import com.gts.coordinator.utils.EncryptionUtils;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtpFragment extends DialogFragment implements SMSReceiver.OTPReceiveListener {
    private static final String TAG = "OtpFragment";
    private OtpViewModel otpViewModel;
    private SMSReceiver smsReceiver;
    private TextView otp_text, resend_otp, mTextViewCountDown, message;
    private LinearLayout otp_layout_, lay_enter_otp;
    private String receiveMobileNo, receiveOtp,userName;
    private CountDownTimer mCountDownTimer;
    private static final long START_TIME_IN_MILLIS = 40000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    public OtpFragment() {
    }

    public static OtpFragment newInstance(String userName) {
        OtpFragment fragment = new OtpFragment();
        Bundle bundle = new Bundle();
        bundle.putString("user_name",userName);
        fragment.setArguments(bundle);
     return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//
        View view = inflater.inflate(R.layout.fragment_otp2, container, false);
        otp_text = view.findViewById(R.id.enter_otp);
        message = view.findViewById(R.id.message_text);
        resend_otp = view.findViewById(R.id.resend_otp);
        otp_layout_ = view.findViewById(R.id.update_password_layout);
        lay_enter_otp = view.findViewById(R.id.lay_enter_otp);
        //rsss
        mTextViewCountDown = view.findViewById(R.id.text_view_countdown);
        Bundle arg = getArguments();
        userName = arg.getString("user_name");
        otpViewModel = new ViewModelProvider( getActivity()).get(OtpViewModel.class);
        // for get Api Response
        sendOtp(otpViewModel);
        // for check error
        otpViewModel.getErrorMessage().observe(getActivity(), s -> {
          if (!s.isEmpty()){
              TastyToast.makeText(getActivity(), ""+s, TastyToast.LENGTH_SHORT,TastyToast.ERROR).show();
          }
        });
        eventHandaling(view,otpViewModel);
        return view;
    }

    private void sendOtp(OtpViewModel otpViewModel) {

        EncryptionUtils en = new EncryptionUtils();
        otpViewModel.getApiResponse(userName).observe(getActivity(), responseResetOtp -> {
            if (responseResetOtp.getGetresponse().getStatus()==0){
                receiveMobileNo = responseResetOtp.getMobileNo();
                receiveOtp = responseResetOtp.getOTP();
                try {
                    en.decrypt(responseResetOtp.getOTP(),"coyuiopvgt61298745236tuiop");
                    receiveOtp = en.decT;
                    Log.e("rssddddd", "onResponse:OTP "+receiveOtp );
                    message.setText(getString(R.string.we_have_sent_otp_to) + "\n" + receiveMobileNo);
                    startTimer();
                    startSMSListener();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }

    private void eventHandaling(View view, OtpViewModel otpViewModel) {

        MaterialButton verify = view.findViewById(R.id.button_otp_verify);
        MaterialButton cancle = view.findViewById(R.id.button_otp_cancle);
        TextInputLayout updatePassword = view.findViewById(R.id.update_password_text);
        MaterialButton butnUpdatePassword = view.findViewById(R.id.update_password_button);
        cancle.setOnClickListener(view13 -> dismiss());
        verify.setOnClickListener(view12 -> {
            String user_otp = otp_text.getText().toString();
            if (user_otp.equals("")) {
                TastyToast.makeText(getActivity(), " Please Enter OTP ", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            } else if (!receiveOtp.equals(user_otp)) {
                TastyToast.makeText(getActivity(), " Please Enter Valid OTP ", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            } else if (receiveOtp.equals(user_otp)) {
                //   dialog.cancel();
                if (otp_layout_.getVisibility() == View.GONE) {
                    otp_layout_.setVisibility(View.VISIBLE);
                    lay_enter_otp.setVisibility(View.GONE);
                  //  updateInfo(updatePassword, butnUpdatePassword);
                } else {
                    otp_layout_.setVisibility(View.GONE);
                    lay_enter_otp.setVisibility(View.VISIBLE);
                }
            }
        });
        butnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (updatePassword.getEditText().getText().equals("")){
                    updatePassword.setEnabled(true);
                    updatePassword.setError("Please Enter Password");
                }else if (updatePassword.getEditText().getText().length()<4){
                    updatePassword.setError("Please Enter Up to 4 Digit Password");
                }else {
                    callApiUpdatePassword(updatePassword.getEditText().getText().toString(),userName);
                 //   updateInfo();
                }
                //rsss
            }
        });
        resend_otp.setOnClickListener(view1 -> sendOtp(otpViewModel));
    }

    @Override
    public void onOTPReceived(String otp) {
        getOTPForSms(otp);
        Log.e("onOTPReceived", otp);
    }


    @Override
    public void onOTPTimeOut() {
        Toast.makeText(getActivity(), "onOTPTimeOut", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOTPReceivedError(String error) {
        Toast.makeText(getActivity(), "onOTPReceivedError", Toast.LENGTH_SHORT).show();
    }

    private void getOTPForSms(String message) {
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

    private void startSMSListener() {
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
                //  otp_text.setText();
            });
            task.addOnFailureListener(e -> {
                Log.e("my_otp", String.valueOf(e));
                // Fail to start API
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    private void callApiUpdatePassword(String updatedPasswod,String userName) {
        RetrofitApiInterface apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<ResponseResetPassword> call = apiInterface.resetPassword(new PostResetPasswod(userName, updatedPasswod));
        call.enqueue(new Callback<ResponseResetPassword>() {
            @Override
            public void onResponse(Call<ResponseResetPassword> call, Response<ResponseResetPassword> response) {
                if (response.isSuccessful()) {
                    ResponseResetPassword responseResetPassword = response.body();
                    Log.e(TAG, "onResponse: "+responseResetPassword.getMessage() );
                    if (responseResetPassword.getStatus() == 0){
                        TastyToast.makeText(getActivity(), " Your Password Update Successful ", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                        dismiss();}
                    else {
                        TastyToast.makeText(getActivity(), " "+responseResetPassword.getMessage(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseResetPassword> call, Throwable t) {
                TastyToast.makeText(getActivity()," Server is not responding ",TastyToast.LENGTH_SHORT,TastyToast.ERROR).show();
            }
        });
    }


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
/*



        */