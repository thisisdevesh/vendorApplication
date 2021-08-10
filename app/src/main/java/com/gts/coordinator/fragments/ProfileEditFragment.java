package com.gts.coordinator.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.gts.coordinator.BroadcastReceiver.SMSReceiver;
import com.gts.coordinator.BroadcastReceiver.SmsBroadcastReceiver;
import com.gts.coordinator.Model.ContractorData.GetOtp.GetOtpModel;
import com.gts.coordinator.Model.ContractorData.GetOtp.PostMobileNo;
import com.gts.coordinator.Model.ContractorData.UpdateProfile.PostContractorDetail;
import com.gts.coordinator.Model.ContractorData.UpdateProfile.UpdateProfileModel;
import com.gts.coordinator.R;
import com.gts.coordinator.utils.PreferenceData;
import com.gts.coordinator.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClient;
import com.gts.coordinator.Retrofit.RetrofitClientWcm;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class ProfileEditFragment extends DialogFragment {
    private static final long START_TIME_IN_MILLIS = 200000;
    private TextView userName;
    private MaterialButton buttonSubmit, cancel, save;
    private long contractorID;
    //  private JSONObject jsonObject;
    // private CurrentUser currentUser;
    private ProgressBar progressBar;
    public updateNumber numberUpadte;
    private TextView mTextViewCountDown, resend_otp;
    private CountDownTimer mCountDownTimer;
    private ImageView edit_password, edit_phone_no;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private RetrofitApiInterface apiInterface;
    private SMSReceiver smsReceiver;
    EditText otp_text;

    private ImageView ivProfile;
    private Uri imageFileUri;
    private String path;


    EditText editPhone, editDob, editPassword;
    TextView tvAddress, textPhone, textPassword;
    private AlertDialog dialog = null;
    String eName, ePhone, eDob, ePic, eAddress, epassword, old_phone_no, message;
    private static final int REQ_USER_CONSENT = 200;
    SmsBroadcastReceiver smsBroadcastReceiver;


    public static ProfileEditFragment newInstance() {
        ProfileEditFragment pf = new ProfileEditFragment();
        return pf;
        //23.08.2019
    }

    public void show(Activity activity) {
        FragmentManager fm = activity.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        android.app.Fragment prev = fm.findFragmentByTag("ProfEdit");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        super.show(ft, "ProfEdit");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile_fragment_layout, container, false);
        init(view);
        contractorID = PreferenceData.getLong(getActivity(), "cont_id");
        return view;
    }

    private void init(View view) {
        ivProfile = view.findViewById(R.id.profile_image);
        userName = view.findViewById(R.id.user_name);
        save = view.findViewById(R.id.save_btn);
        tvAddress = view.findViewById(R.id.et_address);
        editPassword = view.findViewById(R.id.et_profile_password);
        editPhone = view.findViewById(R.id.et_phone_no);
        textPassword = view.findViewById(R.id.t_profile_password);
        textPhone = view.findViewById(R.id.t_phone_no);
        buttonSubmit = view.findViewById(R.id.btn_update);
        cancel = view.findViewById(R.id.btn_canceled);
        progressBar = view.findViewById(R.id.edit_progress);
        edit_password = view.findViewById(R.id.edit_password);
        edit_phone_no = view.findViewById(R.id.edit_number);
        userName.setText(PreferenceData.getString(getActivity(), "cont_name"));
        tvAddress.setText(PreferenceData.getString(getActivity(), "cont_address"));
        textPhone.setText(PreferenceData.getString(getActivity(), "cont_phone1"));
        textPassword.setText(PreferenceData.getString(getActivity(), "password"));
        buttonSubmit.setOnClickListener(view14 -> updateContectorDetail());
        cancel.setOnClickListener(view13 -> dismiss());
        edit_phone_no.setOnClickListener(view1 -> {
            if (editPhone.getVisibility() == View.GONE) {
                editPhone.setVisibility(View.VISIBLE);
                textPhone.setVisibility(View.GONE);
            } else if (editPhone.getVisibility() == View.VISIBLE) {
                editPhone.setVisibility(View.GONE);
                textPhone.setVisibility(View.VISIBLE);
            }
        });
        edit_password.setOnClickListener(view12 -> {
            if (editPassword.getVisibility() == View.GONE) {
                editPassword.setVisibility(View.VISIBLE);
                textPassword.setVisibility(View.GONE);
            } else if (editPassword.getVisibility() == View.VISIBLE) {
                editPassword.setVisibility(View.GONE);
                textPassword.setVisibility(View.VISIBLE);
            }
        });
        ivProfile.setOnClickListener(view99 ->{
            pickImage(view);
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Holo_Dialog_MinWidth);

        // currentUser = new CurrentUser();
/*
        try {
            jsonObject = new JSONObject(PreferenceData.getString(getActivity(), "key_user_info"));

            contractorID = jsonObject.getLong("Id");
            eAddress = jsonObject.getString("contractorAddress");
            ePhone = jsonObject.getString("contractorPhoneNo");
            epassword = jsonObject.getString("password");
            eName = jsonObject.getString("contractorName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
*/
        super.onCreate(savedInstanceState);
    }







    public void pickImage(View view) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 101);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 102);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 102) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 101);
            } else {
                Toast.makeText(getActivity(), R.string.permission_req, Toast.LENGTH_SHORT).show();
            }
        }
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
        }else if (requestCode == 101) {
            if (resultCode == RESULT_OK) {

                if (data != null) {
                    imageFileUri = data.getData();

                    // Bitmap bm = BitmapFactory.decodeFile(imageFileUri.getPath());
                    String file = getActivity().getFilesDir().getAbsolutePath();
                    File dir = new File(file, "imageDir");//create Directory
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    File myPath = new File(dir, "driver.jpg");//create a file
                    try {
                        FileOutputStream fos = null;
                        fos = new FileOutputStream(myPath);
                        // write image in local data base
//                        bm.compress(Bitmap.CompressFormat.JPEG, 75, fos);
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                    path = myPath.getAbsolutePath();

                    Log.d("AbsolutePath", path);
                    ivProfile.setImageURI(imageFileUri);
                }

            }
        }
    }









    private void updateContectorDetail() {
        ePhone = String.valueOf(editPhone.getText());
        epassword = String.valueOf(editPassword.getText());
        eAddress = String.valueOf(tvAddress.getText());

        if (ePhone.equals("0000000000")) {
            Toast toast = Toast.makeText(getActivity(), "please fill valid number", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else if (ePhone.equals("") && epassword.equals("")) {
            Toast toast = Toast.makeText(getActivity(), "please fill Update Field", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else if (ePhone.equals(old_phone_no)) {
            Toast toast = Toast.makeText(getActivity(), "Your Mobile No. is same ", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            callOtpApi();
        }
        //
    }

    private void showOtpDialog(String otp) {
        //
        startTimer();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.otp_layout, null);
        otp_text = view.findViewById(R.id.enter_otp);
        MaterialButton verify = view.findViewById(R.id.button_otp_verify);
        MaterialButton cancle = view.findViewById(R.id.button_otp_cancle);
        TextView message = view.findViewById(R.id.message_text);
        resend_otp = view.findViewById(R.id.resend_otp);
        //rsss
        mTextViewCountDown = view.findViewById(R.id.text_view_countdown);
        message.setText(getString(R.string.we_have_sent_otp_to) + "\n" + old_phone_no);
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
            callOtpApi();

        });
        //if (otp_text.equals(""))

        //rsss

        dialog = builder.create();
        dialog.setView(view);
        dialog.show();

        // progressBar.setVisibility(View.VISIBLE);
        //   updateInfo();
    }

    private void callOtpApi() {
        //23.08.2019
        //  PreferenceData.saveString(getActivity(),"cont_phone1",ePhone);
        old_phone_no = PreferenceData.getString(getActivity(), "cont_phone1");
        apiInterface = RetrofitClient.getClient().create(RetrofitApiInterface.class);
        Call<GetOtpModel> call = apiInterface.postMobileNo(new PostMobileNo(old_phone_no));
        call.enqueue(new Callback<GetOtpModel>() {
            @Override
            public void onResponse(Call<GetOtpModel> call, Response<GetOtpModel> response) {
                if (response.isSuccessful()) {
                    try {
                        //9634458092
                        GetOtpModel getOtpModel = response.body();
                        message = getOtpModel.getGetresponse().getMessage();
                        int status = getOtpModel.getGetresponse().getStatus();
                        if (status == 0) {
                            String otp = getOtpModel.getOtp();
                            Log.e("OTP", otp);
                            startSmsUserConsent();
                            showOtpDialog(otp);

                        }
                    } catch (Exception e) {
                        Utils.showOkAlert(getActivity(), getString(R.string.error), "Server Error " + message, true);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetOtpModel> call, Throwable t) {
                Utils.showOkAlert(getActivity(), getString(R.string.error), "Device Error " + t.toString(), true);
            }
        });
    }


    private void verfyOtp(String otp) {
        String user_otp = otp_text.getText().toString();
        if (user_otp.equals("")) {
            Utils.showOkAlert(getActivity(), getString(R.string.error), "Please Enter OTP", true);
        } else if (!otp.equals(user_otp)) {
            Utils.showOkAlert(getActivity(), getString(R.string.error), "Please Enter Valid OTP ", true);
        } else if (otp.equals(user_otp)) {
            dialog.cancel();
            progressBar.setVisibility(View.VISIBLE);
            updateInfo();
            //  startTimer();
        }
    }
// call api through Volley comment by Ravi

    private void updateInfo() {
        //rsss
        progressBar.setVisibility(View.VISIBLE);
        apiInterface = RetrofitClientWcm.getClient().create(RetrofitApiInterface.class);
        if (epassword.equals("")) {
            epassword = PreferenceData.getString(getActivity(), "password");
        } else if (ePhone.equals("")) {
            ePhone = PreferenceData.getString(getActivity(), "cont_phone1");
        }
        Call<UpdateProfileModel> call = apiInterface.updateContractor(new PostContractorDetail(contractorID, epassword, ePhone, eAddress));
        call.enqueue(new Callback<UpdateProfileModel>() {
            @Override
            public void onResponse(Call<UpdateProfileModel> call, Response<UpdateProfileModel> response) {
                if (response.isSuccessful()) {
                    try {
                        UpdateProfileModel updateProfileModel = response.body();
                        int status = updateProfileModel.getD().getStatus();
                        String message = updateProfileModel.getD().getMessage();
                        Log.e("rss", message);
                        if (status == 0) {
                            progressBar.setVisibility(View.GONE);
                            //  currentUser.setPassword(epassword);
                            Utils.showOkAlert(getActivity(), getString(R.string.successful), message, true);
                            Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
                            //   currentUser.setContractorPhoneNo(ePhone);
                            //   currentUser.setContractorAddress(eAddress);
                            //   currentUser.setContractorName(eName);
                            //  currentUser.setId(String.valueOf(contractorID));
                            // CurrentUser.toJson(getActivity(), currentUser);
                            PreferenceData.saveString(getActivity(), "cont_phone1", ePhone);
                            PreferenceData.saveString(getActivity(), "password", epassword);
                            //rssss
                            textPhone.setText(ePhone);
                            textPassword.setText(epassword);
                            numberUpadte.userNo(ePhone);
                            progressBar.setVisibility(View.GONE);
                            dismiss();
                            dialog.cancel();
                            //  showDialogue();
                        }
                    } catch (Exception e) {
                        //  Utils.showOkAlert(getActivity(),getString(R.string.error),e.toString(),true);
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileModel> call, Throwable t) {
                //Toast.makeText(getActivity(), "onFailure", Toast.LENGTH_SHORT).show();
                // Utils.showOkAlert(getActivity(),getString(R.string.error),"onFailure"+t.toString(),true);
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


    public interface updateNumber {
        void userNo(String number);
    }

    @Override
    public void onAttach(Context context) {
        numberUpadte = (updateNumber) context;
        super.onAttach(context);
    }

/*    //23.08.2019
    private void startSMSListener() {
        try {
            smsReceiver = new SMSReceiver();
            smsReceiver.setOTPListener(this);
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
    }*/
/*
    public void getOTPForSms(String message) {
        *//*
         * Now extract the otp*//*
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






}