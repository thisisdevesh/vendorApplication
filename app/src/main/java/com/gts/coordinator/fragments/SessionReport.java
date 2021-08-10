package com.gts.coordinator.fragments;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.gts.coordinator.Adapter.SessionAdapter;
import com.gts.coordinator.R;
import com.gts.coordinator.dao.SessionDao;
import java.util.ArrayList;
import java.util.Calendar;
public class SessionReport extends Fragment  {
  private RecyclerView loginLogoutReycler;
  private SessionAdapter loginLogutAdapter;
  private TextView noRecods;
  ProgressBar progressBar;
  private ArrayList<SessionDao> sessionDaos;
  Calendar prevTime;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    sessionDaos = new ArrayList<>();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_login_logou_report, container, false);
    noRecods = view.findViewById(R.id.log_noRecords);
    loginLogoutReycler = view.findViewById(R.id.login_logout_layout);
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
    loginLogoutReycler.setLayoutManager(mLayoutManager);
    loginLogoutReycler.setItemAnimator(new DefaultItemAnimator());
    if (sessionDaos.size() == 0) {
      noRecods.setVisibility(View.VISIBLE);
      noRecods.setText(" No Records Found ");
    }
    loginLogutAdapter = new SessionAdapter(getActivity(), sessionDaos,prevTime,progressBar,noRecods);
    loginLogoutReycler.setAdapter(loginLogutAdapter);

    return view;
  }

  public void getLoginLogoutStatus(ArrayList<SessionDao> sessionDaos,ProgressBar progressBar) {

    this.sessionDaos = sessionDaos;
    this.progressBar =progressBar;
    if (sessionDaos.size() == 0) {
      noRecods.setVisibility(View.VISIBLE);
      noRecods.setText(" No Records Found ");
    } else {
      noRecods.setVisibility(View.GONE);
    }
    loginLogutAdapter = new SessionAdapter(getActivity(), sessionDaos,prevTime,progressBar,noRecods);
    loginLogoutReycler.setAdapter(loginLogutAdapter);
  }
}
