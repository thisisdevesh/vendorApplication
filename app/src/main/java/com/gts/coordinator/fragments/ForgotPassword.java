package com.gts.coordinator.fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gts.coordinator.R;
import com.gts.coordinator.utils.PreferenceData;
import com.gts.coordinator.utils.Utils;


public class ForgotPassword extends DialogFragment implements View.OnClickListener {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

  private EditText etEmail;
  private EditText etOTP;
  private EditText etPasswordFirst;
  private EditText etPasswordSecond;
  private Button btnSbmtEmail;
  private Button btnSbmtOTP;
  private Button btnSbmtPassword;
  private String email;
  private String otp;
  private String userOtp;
  private TextView tvTitle;
  private ProgressBar processBar;
  private Context context;

  private TextView waring;


  // TODO: Rename and change types and number of parameters
  public static ForgotPassword newInstance(String email) {
    ForgotPassword fragment = new ForgotPassword();
    Bundle args = new Bundle();
    args.putString("email", email);
    fragment.setArguments(args);
    return fragment;
  }

  public static ForgotPassword autoVeriInstance(String email, String otp) {
    ForgotPassword fragment = new ForgotPassword();
    Bundle args = new Bundle();
    args.putString("email", email);
    args.putString("OTP", otp);
    fragment.setArguments(args);
    return fragment;
  }

  public ForgotPassword() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      email = getArguments().getString("email");
      otp = getArguments().getString("OTP");
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    etEmail.setText(email);
    if (userOtp!=null)
    {
      otp = PreferenceData.getString(getActivity(),"SERVOTP");

      sendOTP();
    }

  }

  private void sendOTP() {
    if (userOtp.equals(otp)) {
      btnSbmtPassword.setVisibility(View.GONE);
//      Log.i("YOu are in =","send OTP");
      tvTitle.setText("Please Change Password");
      btnSbmtEmail.setVisibility(View.GONE);
      etPasswordFirst.setVisibility(View.VISIBLE);
      etPasswordSecond.setVisibility(View.VISIBLE);
      btnSbmtEmail.setVisibility(View.VISIBLE);
     /* password.setVisibility(View.VISIBLE);
      re_enter_password.setVisibility(View.VISIBLE);
*/


//                                        Log.i("Password 1", "" + re_enter_password.getCabNumbers().toString());
//                                        Log.i("Password 1", "" + password.getCabNumbers().toString());
//                                        final String password_first = password.getCabNumbers().toString();
//                                        final String password_second = re_enter_password.getCabNumbers().toString();
//                                        Log.i("Password 0",""+password_first);
      btnSbmtEmail.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//                                                Log.i("Password 1", "" + re_enter_password.getCabNumbers().toString());
//                                                Log.i("Password 1", "" + password.getCabNumbers().toString());
          if (etPasswordFirst.getText().toString().equals(etPasswordSecond.getText().toString())) {
//                                                    Log.i("Password 1", "" + re_enter_password.getCabNumbers().toString());
//                                                    Log.i("Password 1", "" + password.getCabNumbers().toString());
//            new PasswordChangeTask(getActivity()).execute(etEmail.getCabNumbers().toString(), etPasswordSecond.getCabNumbers().toString());


          } else {

//                                                  Toast toast =  Toast.makeText(context, "Your password did not match.... try again", Toast.LENGTH_SHORT).show();
            waring.setVisibility(View.VISIBLE);
                                                    /*Toast to= Toast.makeText(getActivity(),
                                                            " Your password did not match.... try again", Toast.LENGTH_SHORT);
                                                    to.setGravity(Gravity.CENTER|Gravity.CLIP_HORIZONTAL, 0,0);
                                                    to.show();*/
            etPasswordFirst.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View v, MotionEvent event) {
                waring.setVisibility(View.GONE);
                return false;
              }
            });
            etPasswordSecond.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View v, MotionEvent event) {
                waring.setVisibility(View.GONE);
                return false;
              }
            });

          }
        }
      });


    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
    init(view);
    etEmail.setText(email);


    etOTP.setVisibility(View.GONE);
    btnSbmtOTP.setVisibility(View.GONE);
    etPasswordFirst.setVisibility(View.GONE);
    etPasswordSecond.setVisibility(View.GONE);
    btnSbmtPassword.setVisibility(View.GONE);
    btnSbmtEmail.setVisibility(View.VISIBLE);
    waring.setVisibility(View.GONE);
    btnSbmtEmail.setOnClickListener(this);
    btnSbmtOTP.setOnClickListener(this);
    btnSbmtPassword.setOnClickListener(this);

    return view;
  }


  private void init(View v) {
    tvTitle = (TextView) v.findViewById(R.id.tv_forgot_title);
    etEmail = (EditText) v.findViewById(R.id.et_forgot_email);
    etOTP = (EditText) v.findViewById(R.id.et_OTP);
    etPasswordFirst = (EditText) v.findViewById(R.id.et_password);
    etPasswordSecond = (EditText) v.findViewById(R.id.et_re_enter_password);
    btnSbmtEmail = (Button) v.findViewById(R.id.btn_email_submit);
    btnSbmtOTP = (Button) v.findViewById(R.id.btn_otp_confirm_btn);
    btnSbmtPassword = (Button) v.findViewById(R.id.btn_submit_password);
    waring = (TextView) v.findViewById(R.id.tv_warning);
  }


  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_email_submit:
        email = String.valueOf(etEmail.getText());
        PreferenceData.saveString(getActivity(),"FORGOTEMAIL",email);
        if (email.equals("")) {
//
          Utils.showOkAlert(getActivity(), "Blank Email", "Please fill up email.", true);
        } else {
          if (Utils.getConnectivityStatus(getActivity()) == 0) {
//                            ToastClass.showToast(getActivity(), "No Internet Connection Detected");
            Utils.showOkAlert(getActivity(), "No Internet Connection", "Please check your internet connection", false);
          } else {
      // volly to submitt email
          //  submitEmail();
          }
        }
        break;

      case R.id.btn_otp_confirm_btn:

        break;

      case R.id.btn_submit_password:

        break;
    }

  }
// todo forgot password service...

/*
  private void submitEmail() {
      startProcess();
Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>()
{

  @Override
  public void onResponse(JSONObject response) {
    if (response == null || "".equals(response)) {
      Utils.showOkAlert(getActivity(), "No Response", "Please Try again.", false);

    }
    else
    {

    }
  }
};
   Response.ErrorListener errorListener = new Response.ErrorListener() {
     @Override
     public void onErrorResponse(VolleyError error) {

     }
   };
    try {
      JSONObject jsonRequest = WebServiceRequest.fgtPwdSubmitEmail(email) ;
      WebServiceRequest submitEmailRequest = new WebServiceRequest(getActivity(),ServiceConstants.FORGOT_PASSWORD_METHOD,jsonRequest,successListener,errorListener);
      submitEmailRequest.setInitTimeout(15000); // 15 seconds
      submitEmailRequest.setMaxRetryCount(1);
      submitEmailRequest.send();
    } catch (JSONException e) {
      Log.e("LoginActivity", "JSON Error in Login"  ,e);
    }
  }
*/

  private void startProcess() {
    processBar.setVisibility(View.VISIBLE);
  }
  private void stopProcessing(){
    processBar.setVisibility(View.GONE);

  }
}
/*    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
    String url ="";
    Map<String,String> postParam = new HashMap<String, String>();
    final ProgressDialog progress=new ProgressDialog(getActivity());
    progress.show();
    final JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(postParam), new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        try {
//         get response here and save data
          JSONObject j = new JSONObject(String.valueOf(response));
          JSONObject mainObject = j.getJSONObject("d");

          progress.dismiss();

        } catch (JSONException e) {
          e.printStackTrace();
        }


      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {

      }
    });
    requestQueue.add(jsonObj);*/







