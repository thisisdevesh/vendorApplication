package com.gts.coordinator.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gts.coordinator.Activity.UploadDoc.UploadDriverDoc;
import com.gts.coordinator.Model.ContractorData.UpdateDeiver.PostUpdateDriverDetail;
import com.gts.coordinator.Model.ContractorData.UpdateDeiver.UpdateDriverModel;
import com.gts.coordinator.R;
import com.gts.coordinator.db.DriverDBInfo;
import com.gts.coordinator.utils.AppConstants;
import com.gts.coordinator.utils.Utils;

import com.gts.coordinator.Retrofit.RetrofitApiInterface;
import com.gts.coordinator.Retrofit.RetrofitClientWcm;
import retrofit2.Call;
import retrofit2.Callback;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


public class UpdateDriver extends DialogFragment {
  // TODO: Rename parameter arguments, choose names that match
 private TextView txtDrivername;
 private EditText txtDriverPhno;
 private Button updateDrvr , btnSkip;
 private String name, driverPhno ,driverPhnoNew;
  private ProgressBar processBar;
  private long vendorID, driverID;
  boolean isVerifed;
  private DriverDBInfo driverDb;

  public UpdateDriver() {
    // Required empty public constructor
  }
  // TODO: Rename and change types and number of parameters
  public static UpdateDriver newInstance(String name, long driverID, long venId, String phoneNo, boolean isVerified) {
    UpdateDriver fragment = new UpdateDriver();
    Bundle args = new Bundle();
    args.putString("drvName", name);
    args.putLong("drvId", driverID);
    args.putLong("venId", venId);
    args.putBoolean("isVerified", isVerified);
    args.putString("driver_phone",phoneNo);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle arg = getArguments();
    name = String.valueOf(arg.get("drvName"));
    driverPhno = String.valueOf(arg.get("driver_phone"));
    driverID = arg.getLong("drvId");
    vendorID = arg.getLong("venId");
    isVerifed = arg.getBoolean("isVerified");
    driverDb = new DriverDBInfo(getActivity());
  }

  @Override
  public void onStart() {
    super.onStart();

  }
  @Override
  public void onResume() {
    super.onResume();
    Log.v("fgdg", "onResume");
    if (getShowsDialog()) {
      // Set the width of the dialog to the width of the screen in portrait mode
      DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
      int dialogWidth = Math.min(metrics.widthPixels - 20, metrics.heightPixels);
      getDialog().getWindow().setLayout(dialogWidth, WRAP_CONTENT);
    }
  }
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_update_driver, container, false);
    getDialog().getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
    getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    init(view);
    if (!isVerifed) // if verified, it does not go to documents upload page
    {
      btnSkip.setVisibility(View.VISIBLE);

    }else if (isVerifed){
        btnSkip.setVisibility(View.VISIBLE);
        btnSkip.setText(getResources().getString(R.string.cancel));
       // dismiss();
    }
    txtDrivername.setText(name);
    txtDriverPhno.setText(driverPhno);
    updateDrvr.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
//        Log.i("CLICKING", "*********************");
        driverPhnoNew = String.valueOf(txtDriverPhno.getText());

        if (driverPhnoNew.length() > 10) {
          Toast.makeText(getActivity(), "Please enter 10 digit number", Toast.LENGTH_SHORT).show();
        } else if (driverPhnoNew.length() < 10) {
          Toast.makeText(getActivity(), "Please enter 10 digit number", Toast.LENGTH_SHORT).show();
        }else if (driverPhnoNew.equals(driverPhno)){
            Toast.makeText(getActivity(), "Your Previous and new Number are same", Toast.LENGTH_SHORT).show();
        }else {
          callApiApdateDeiver(driverPhnoNew);
        }
      }

    });

    btnSkip.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (!isVerifed ) {
          Intent intent = new Intent(getActivity(), UploadDriverDoc.class);
          intent.putExtra("role", AppConstants.ROLE_DRIVER);
          intent.putExtra("id", driverID);
          intent.putExtra("key", "");
          intent.putExtra("st", "addcab");
          intent.putExtra("is_mandatory",false);
          startActivity(intent);
          dismiss();
        }
        else if (isVerifed){
            dismiss();
        }
      //  getActivity().finish();
      }
    });
    return view;
  }

  private void init(View view) {
    txtDrivername = (TextView) view.findViewById(R.id.tv_driver_name);
    txtDriverPhno = (EditText) view.findViewById(R.id.tv_driver_phno);
    updateDrvr = (Button) view.findViewById(R.id.btn_update_driver);
    btnSkip = (Button) view.findViewById(R.id.btn_skip_driver);
    processBar = (ProgressBar) view.findViewById(R.id.process_bar);
  }

  public void show(Activity activity) {
    FragmentManager fm = activity.getFragmentManager();
//        FragmentManager fm = getFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    android.app.Fragment prev = fm.findFragmentByTag("MsgAlert");
    if (prev != null) {
      ft.remove(prev);
    }
    ft.addToBackStack(null);
    super.show(ft, "MsgAlert");
  }

  private void callApiApdateDeiver(String driverPhnoNew) {
      try {
      startProcessing();
      RetrofitApiInterface retrofitApiInterface = RetrofitClientWcm.getClient().create(RetrofitApiInterface.class);
      Call<UpdateDriverModel> call =retrofitApiInterface.updateDriver(new PostUpdateDriverDetail(driverID, driverPhnoNew));
      call.enqueue(new Callback<UpdateDriverModel>() {
          @Override
          public void onResponse(Call<UpdateDriverModel> call, retrofit2.Response<UpdateDriverModel> response) {
              if (response.isSuccessful()){
                      stopProcessing();
                      UpdateDriverModel updateDriverModel = response.body();
                      int status = updateDriverModel.getD().getStatus();
                      String msg = updateDriverModel.getD().getMessage();
                      if (status == 0) {
                          byte st = 0;
                          stopProcessing();
                          Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                          dismiss();
                          driverDb.updateDriverPhone(driverID,driverPhno);
                          if (!isVerifed )// if verified, it does not go to documents upload page
                          {
                              Intent intent = new Intent(getActivity(), UploadDriverDoc.class);
                              intent.putExtra("role", AppConstants.ROLE_DRIVER);
                              intent.putExtra("id", driverID);
                              intent.putExtra("key", "");
                              intent.putExtra("is_mandatory",false);
                              startActivity(intent);
                          }
                          //   getActivity().finish();
                      }else{
                          Utils.showOkAlert(getActivity(), getString(R.string.error), msg, true);
                          stopProcessing();
                      }
                      Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
              }
          }

          @Override
          public void onFailure(Call<UpdateDriverModel> call, Throwable t) {
              Utils.showOkAlert(getActivity(), getString(R.string.error), "onFailure", true);
              stopProcessing();
          }
      });
      }catch (Exception e){
          stopProcessing();
          Utils.showOkAlert(getActivity(), getString(R.string.error), "Exception", true);
      }

  }
  private void startProcessing() {
    processBar.setVisibility(View.VISIBLE);
    btnSkip.setVisibility(View.GONE);
    updateDrvr.setVisibility(View.GONE);
  }
  private void stopProcessing() {
    processBar.setVisibility(View.GONE);
      btnSkip.setVisibility(View.VISIBLE);
      updateDrvr.setVisibility(View.VISIBLE);
  }


}
