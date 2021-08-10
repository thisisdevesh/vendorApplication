package com.gts.coordinator.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.gts.coordinator.Activity.ActivityDashboard;
import com.gts.coordinator.Model.ContractorData.ContractorDetail.DriverList;
import com.gts.coordinator.Model.getAll.Drivervendorlist;
import com.gts.coordinator.R;
import com.gts.coordinator.dao.Driver;
import com.gts.coordinator.dao.MarkerItems;
import com.gts.coordinator.db.DriverDBInfo;
import com.gts.coordinator.roomDB.MyDatabase;
import com.gts.coordinator.utils.AppConstants;
import com.gts.coordinator.utils.PreferenceData;
import com.gts.coordinator.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//SupportMapFragment
public class MapViewFragment extends Fragment
        implements OnMapReadyCallback, ClusterManager.OnClusterClickListener<MarkerItems>,
        LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ClusterManager.OnClusterItemClickListener<MarkerItems>,
        ClusterManager.OnClusterItemInfoWindowClickListener<MarkerItems>, View.OnClickListener {
    public GoogleMap googleMap;
    public int image;
    public String name, driverStatus;
    MyDatabase appDatabase;
    List<Drivervendorlist> driverLists = new ArrayList<>();
    private long vendorId;
    private byte status;
    private int CALLING_PERMISSION = 23;
    private ClusterManager<MarkerItems> mClusterManager;
    private SupportMapFragment supportMapFragment;
    private ProgressBar processBar;
    private TextView tvCabsStatus[];
    String qureyText;
    private int[] statusCount;
    TextView t1, t2, t3;
    //new LatLng(23.63936, 68.14712), new LatLng(28.20453, 97.34466)
    // in app
    public static final LatLngBounds BOUNDS_INDIA = new LatLngBounds(new LatLng(7.798000, 68.14712), new LatLng(37.090000, 97.34466));
    //  public static final LatLngBounds BOUNDS_INDIA = new LatLngBounds(new LatLng(11.855663, 75.512636), new LatLng(28.224456, 89.463438));//ok 28.050772, 91.806704
    Location mLastLocation;
    Marker mCurrLocationMarker;
    /* new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));*/
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    MarkerOptions markerOptions;
    byte request_type = 0;

    //rss
    public static MapViewFragment newInstance(long vendorId, byte status) {
        MapViewFragment fragment = new MapViewFragment();
        Bundle args = new Bundle();
        args.putLong("VENDOR_ID", vendorId);
        args.putByte("STATUS", status);
        fragment.setArguments(args);
        return fragment;
    }

    //todo create by ravi for search place location in map..
    public boolean showCabMap(String location) {
        boolean status = false;
        List<Address> addressList = null;
        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(getActivity());
            try {
                addressList = geocoder.getFromLocationName(location, 1);
                if (addressList.size() == 0) {

                    //     Utils.showOkAlert(getActivity(), getString(R.string.info), "Address Not Found", false);
                    status = false;
                    //  Toast.makeText(getActivity(),"Address Not Found ",Toast.LENGTH_LONG).show();
                } else {
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(14));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    status = true;
                    //  Toast.makeText(getActivity(),address.getLatitude()+" "+address.getLongitude(),Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

        }
        return status;
    }

    public void updateData(int updateStetus) {
        if (updateStetus == 10) {
            try {
                new GetDriversDetails().execute();
            } catch (Exception e) {
            }

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvCabsStatus = new TextView[AppConstants.CAB_STATUS_COUNT];
        statusCount = DriverDBInfo.getCabsStatus(getContext());
        Bundle b = getArguments();
        if (b != null) {
            image = b.getInt("IMAGE");
            vendorId = b.getLong("VENDOR_ID");
            status = b.getByte("STATUS");
        }
        //   PreferenceData.saveLong(getActivity(),"mvid",-1);
        //  PreferenceData.saveInt(getActivity(),"msts",1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_view, container, false);


        t1 = view.findViewById(R.id.free);
        t2 = view.findViewById(R.id.busy);
        t3 = view.findViewById(R.id.logout);
        t1.setBackgroundResource(R.color.lightgray);

        processBar = view.findViewById(R.id.process_bar);
        tvCabsStatus[0] = view.findViewById(R.id.free);
        tvCabsStatus[1] = view.findViewById(R.id.busy);
        tvCabsStatus[2] = view.findViewById(R.id.logout);
        tvCabsStatus[3] = view.findViewById(R.id.deactivate);

        tvCabsStatus[0].setText(String.valueOf(statusCount[0]));
        tvCabsStatus[1].setText(String.valueOf(statusCount[1]));
        tvCabsStatus[2].setText(String.valueOf(statusCount[2]));
        tvCabsStatus[3].setText(String.valueOf(statusCount[3]));
        //  PreferenceData.saveLong(getActivity(),"mvid",-1);
        // set On Click Listeners
        for (TextView tv : tvCabsStatus) {
            tv.setOnClickListener(this);
        }
        //  update(-1,(byte) 1);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        FragmentManager fm = getChildFragmentManager(); //getActivity().getSupportFragmentManager();
        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map_container);
        supportMapFragment.getMapAsync(this);
    }

    public void updateMap() {
        mClusterManager = new ClusterManager<>(getActivity(), googleMap);
        mClusterManager.setRenderer(new RenderClusterInfoWindow(getActivity(), googleMap, mClusterManager));
        //  this.vendorId = vendorId;
        //  this.status = status;

        new GetDriversDetails().execute();
    }

    public void update(long vendorId, byte status) {
//          Toast.makeText(getActivity(), "Hello", Toast.LENGTH_SHORT).show();
//        ToastClass.showToast(getContext(), "map updated");
        Log.i("rss", String.format("onMapReady():map=%s;\tvendorId=%s;\tstatus=%s", googleMap, vendorId, status));
        if (googleMap != null) {

            this.vendorId = vendorId;
            this.status = status;
            new GetDriversDetails2().execute();
        }
    }

    public void getCabLocation(String queary) {
        appDatabase = MyDatabase.getInstance(getActivity());
        this.qureyText = queary;
        new GetDriversDetails3().execute();
    }

    private void setCameraPosition(LatLngBounds bounds) {
        try {
            //rss
            //     googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 5));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));
            googleMap.setMaxZoomPreference(20.0f);
            googleMap.setMinZoomPreference(5.1f);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        setCurrentLocation();
        mClusterManager = new ClusterManager<>(getActivity(), googleMap);
        mClusterManager.setRenderer(new RenderClusterInfoWindow(getActivity(), googleMap, mClusterManager));
        googleMap.setMaxZoomPreference(20.0f);
        googleMap.setMinZoomPreference(5.1f);
        googleMap.setOnCameraIdleListener(mClusterManager);
        googleMap.setOnMarkerClickListener(mClusterManager);
        googleMap.setOnInfoWindowClickListener(mClusterManager);
        googleMap.setLatLngBoundsForCameraTarget(BOUNDS_INDIA);
        if ((ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);

        new GetDriversDetails().execute();
    }

    private void setCurrentLocation() {
//    Log.i("MapViewFragment", "= setCurrentLocation()***************************************************************");
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        //("MapViewFragment", "#################### setCurrentLocation =========== " + location);
        if (location != null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 9));
//      Log.i("MapViewFrag", "---------ashu------ Lat=" + location.getLatitude() + "Lng = " + location.getLongitude());
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(10)                   // Sets the zoom
                    .bearing(10)                // Sets the orientation of the camera to east
                    .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            googleMap.setMaxZoomPreference(20.0f);
            googleMap.setMinZoomPreference(5.1f);
        } else {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.314018, 78.030524), 4)); // if locatin is  null then by default location is dehradun with min zoom
        }
    }

    @Override
    public boolean onClusterClick(Cluster<MarkerItems> cluster) {
        Log.i("MapViewFragment", "onClusterClick()");
        return false;
    }

    @Override
    public boolean onClusterItemClick(MarkerItems markerItems) {
        Log.i("MapViewFragment", "onClusterItemClick()");
        return false;
    }

    @Override
    public void onClusterItemInfoWindowClick(MarkerItems markerItems) {
        Log.i("MapViewFragment", "onClusterItemInfoWindowClick()");
        calling(markerItems.getPhoneNumber());
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        //  markerOptions.title("Current Position");
        //  markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        //  mCurrLocationMarker = googleMap.addMarker(markerOptions);
        //move map camera
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        googleMap.setMaxZoomPreference(20.0f);
        googleMap.setMinZoomPreference(5.1f);
        //  googleMap.setLatLngBoundsForCameraTarget();
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) getActivity());
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) getActivity());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public interface OnMapUpdateListener {
        void setVendorInfo(long vendorId, byte status);

    }

    public void calling(String phone) {
        if (Utils.checkPermission(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE))) {
            Utils.callContact(getActivity(), phone);
        } else {
            //    requestCallingPermission();
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CALL_PHONE)) {
                Utils.showOkAlert(getActivity(), null, "User has to provide permission to call", true);

            }

            //And finally ask for the permission
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, CALLING_PERMISSION);
//      Log.i("DrawerActivity", " ActivityCompat.requestPermissions");
        }
    }

    private class RenderClusterInfoWindow extends DefaultClusterRenderer<MarkerItems> {
        public RenderClusterInfoWindow(Context context, GoogleMap map, ClusterManager<MarkerItems> clusterManager) {
            super(context, map, clusterManager);
            Log.i("MapViewFragment", "RenderClusterInfoWindow:constructor");
        }

        @Override
        protected void onClusterRendered(Cluster<MarkerItems> cluster, Marker marker) {
            super.onClusterRendered(cluster, marker);
            Log.i("MapViewFragment", "RenderClusterInfoWindow:onClusterRendered()");
        }

        @Override
        protected void onBeforeClusterItemRendered(final MarkerItems item, MarkerOptions markerOptions) {
            markerOptions.title(item.getName());
//            mClusterManager.removeItem(item);
            Log.i("MapViewFragment", "RenderClusterInfoWindow:onBeforeClusterItemRendered():Items removed");
//      markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

            if (item.getStatus() == 1) {

         /*   Drawable drawable =getActivity().getResources().getDrawable(R.drawable.ic_cab_icon_svg);
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth() - 60, drawable.getIntrinsicHeight() - 100, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);*/
                image = R.drawable.car_green_new;
                driverStatus = "Login";
                markerOptions.icon(BitmapDescriptorFactory.fromResource(image));//BitmapDescriptorFactory.fromResource(image)

            } else if (item.getStatus() == 2) {
                image = R.drawable.car_red_new;
                driverStatus = "Busy";
                markerOptions.icon(BitmapDescriptorFactory.fromResource(image));
            } else if (item.getStatus() == 3) {
                image = R.drawable.car_black_new;
                driverStatus = "LogOut";
                markerOptions.icon(BitmapDescriptorFactory.fromResource(image));
            } else {
                image = R.drawable.deactivate;
                driverStatus = "deactivated";
                markerOptions.icon(BitmapDescriptorFactory.fromResource(image));
            }
            super.onBeforeClusterItemRendered(item, markerOptions);
        }

    }

    class GetDriversDetails extends AsyncTask<Void, Void, LatLngBounds> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("rss", "GetDriversDetails:onPreExecute()");
            try {
                if (mClusterManager != null)
                    mClusterManager.clearItems();
                //   processBar.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected LatLngBounds doInBackground(Void... voids) {
            vendorId = PreferenceData.getLong(getActivity(), "mvid");
            int stt = PreferenceData.getInt(getActivity(), "msts");
            ArrayList<Driver> driverList = DriverDBInfo.getDrivers(getActivity(), vendorId, (byte) stt);
            Log.i("rss", String.format("doInBackground():vendorId=%s;\tstatus=%s", vendorId, status));
            Log.i("rss", "GetDriversDetails:doInBackground(): got driver list:driverList=" + driverList);
            Log.i("rss", "GetDriversDetails:doInBackground(): got driver list:driverList.size=" + driverList.size());
            if (driverList.size() > 0) {
                LatLngBounds.Builder builder = LatLngBounds.builder();
                for (Driver driver : driverList) {
                    LatLng position = new LatLng(driver.getLat(), driver.getLng());
                    builder.include(position);
                    mClusterManager.addItem(new MarkerItems(position, driver.getName(), driver.getCabNo(), driver.getPhoneNo(), driver.getStatus()));
                }
                return builder.build();
            }

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.i("rss", "GetDriversDetails:doInBackground(): got driver list");
            processBar.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(LatLngBounds bounds) {
            super.onPostExecute(bounds);
            Log.i("rss", "GetDriversDetails:onPostExecute()");
            setCameraPosition(bounds);
            processBar.setVisibility(View.GONE);
        }
    }

    class GetDriversDetails2 extends AsyncTask<Void, Void, LatLngBounds> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("rss", "GetDriversDetails:onPreExecute()");
            try {
                if (mClusterManager != null)
                    mClusterManager.clearItems();
                processBar.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected LatLngBounds doInBackground(Void... voids) {
            // vendorId = PreferenceData.getLong(getActivity(),"mvid");
            // int stt = PreferenceData.getInt(getActivity(),"msts");
            ArrayList<Driver> driverList = DriverDBInfo.getDrivers(getActivity(), vendorId, status);
            Log.i("rss", String.format("doInBackground():vendorId=%s;\tstatus=%s", vendorId, status));
            Log.i("rss", "GetDriversDetails:doInBackground(): got driver list:driverList=" + driverList);
            Log.i("rss", "GetDriversDetails:doInBackground(): got driver list:driverList.size=" + driverList.size());
            if (driverList.size() > 0) {
                LatLngBounds.Builder builder = LatLngBounds.builder();
                for (Driver driver : driverList) {
                    LatLng position = new LatLng(driver.getLat(), driver.getLng());
                    builder.include(position);
                    mClusterManager.addItem(new MarkerItems(position, driver.getName(), driver.getCabNo(), driver.getPhoneNo(), driver.getStatus()));
                }
                return builder.build();
            }

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.i("rss", "GetDriversDetails:doInBackground(): got driver list");
            processBar.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(LatLngBounds bounds) {
            super.onPostExecute(bounds);
            Log.i("rss", "GetDriversDetails:onPostExecute()");
            setCameraPosition(bounds);
            processBar.setVisibility(View.GONE);
        }
    }

    class GetDriversDetails3 extends AsyncTask<Void, Void, LatLngBounds> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("rss", "GetDriversDetails:onPreExecute()");
            try {
                if (mClusterManager != null)
                    mClusterManager.clearItems();
                processBar.setVisibility(View.VISIBLE);
                driverLists.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected LatLngBounds doInBackground(Void... voids) {
            if (isValidNumber(qureyText)) {
                driverLists.addAll(appDatabase.myDao().getVendorDetailByPhoneNo(qureyText));
                if (driverLists.isEmpty()){
                    driverLists.addAll(appDatabase.myDao().getDriverDetailByPhoneNo(qureyText));
                }
            } else if (isValidName(qureyText)) {
                driverLists.addAll(appDatabase.myDao().getDriverDetailByCabNo(qureyText));
            } else {
                driverLists.addAll(appDatabase.myDao().getVendorDetailByName(qureyText));
                if (driverLists.isEmpty()){
                    driverLists.addAll(appDatabase.myDao().getDriverDetailByName(qureyText));
                }
            }
            Log.e("TAG", "doInBackground: " + driverLists);
            int status;
            if (driverLists.size() > 0) {
                LatLngBounds.Builder builder = LatLngBounds.builder();
                for (Drivervendorlist driver : driverLists) {
                    String latLongData = driver.getDriverLatLong();
                    if (latLongData!=null){
                        String[] latLong = latLongData.split(",", 2);
                        double let = Double.parseDouble(latLong[0]);
                        double longs = Double.parseDouble(latLong[1]);
                        LatLng position = new LatLng(let, longs);
                        builder.include(position);
                        if (driver.getCabStatus().equalsIgnoreCase("Busy")) {
                            status = 2;
                        } else if (driver.getCabStatus().equalsIgnoreCase("Logout")) {
                            status = 3;
                        } else if (driver.getCabStatus().equalsIgnoreCase("Free")) {
                            status = 1;
                            //0 =deactivate 1 ==free 2 == busy 3 == logout
                        } else {
                            status = 0;
                        }
                        mClusterManager.addItem(new MarkerItems(position, driver.getDriverName(), driver.getCabNo(), driver.getDriverNo(), status));
                    }else {
                        break;
                    }
                }
                return builder.build();
            }else {
                Utils.showOkAlert(getActivity(), getString(R.string.info), "Information with this data does not exist on map", true);
            }

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.i("rss", "GetDriversDetails:doInBackground(): got driver list");
            processBar.setVisibility(View.GONE);
        }

        //ReportVen
        @Override
        protected void onPostExecute(LatLngBounds bounds) {
            super.onPostExecute(bounds);
            Log.i("rss", "GetDriversDetails:onPostExecute()");
            setCameraPosition(bounds);
            processBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //  PreferenceData.saveLong(getActivity(),"mvid",1);
        } catch (ClassCastException e) {

        }
    }

    public static boolean isValidNumber(String s) {
        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

    public static boolean isValidName(String s) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9]+$");//Pattern p = Pattern.compile("^[a-zA-Z]*$");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

    //
    public void refereceActivity(int reStetus) {
        try {
            statusCount = DriverDBInfo.getCabsStatus(getContext());
            tvCabsStatus[0].setText(String.valueOf(statusCount[0]));
            tvCabsStatus[1].setText(String.valueOf(statusCount[1]));
            tvCabsStatus[2].setText(String.valueOf(statusCount[2]));
            tvCabsStatus[3].setText(String.valueOf(statusCount[3]));
            updateData(reStetus);
        } catch (Exception e) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.free:

                t1.setBackgroundResource(R.color.lightgray);
                t2.setBackgroundResource(R.color.white);
                t3.setBackgroundResource(R.color.white);
                //PreferenceData.saveLong(getActivity(),"mvid",-1);
                //PreferenceData.saveInt(getActivity(),"msts",1);
                // updateMap();//-1, (byte) -1
                update(-1, (byte) 1);
                Toast.makeText(getActivity(), "free", Toast.LENGTH_SHORT).show();

                break;
            case R.id.busy:
                //PreferenceData.saveLong(getActivity(),"mvid",-1);
                // PreferenceData.saveInt(getActivity(),"msts",2);
                // updateMap();//-1, (byte) 2
                update(-1, (byte) 2);

                t2.setBackgroundResource(R.color.lightgray);
                t1.setBackgroundResource(R.color.white);
                t3.setBackgroundResource(R.color.white);
                Toast.makeText(getActivity(), "busy", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                //PreferenceData.saveLong(getActivity(),"mvid",-1);
                // PreferenceData.saveInt(getActivity(),"msts",3);
                // updateMap();//-1, (byte) 3

                t3.setBackgroundResource(R.color.lightgray);
                t2.setBackgroundResource(R.color.white);
                t1.setBackgroundResource(R.color.white);
                update(-1, (byte) 3);
                Toast.makeText(getActivity(), "logout", Toast.LENGTH_SHORT).show();

                break;
            case R.id.deactivate:
                //  updateMap();
                // PreferenceData.saveLong(getActivity(),"mvid",-1);
                //  PreferenceData.saveInt(getActivity(),"msts",4);
                //-1,(byte)4
                // Utils.showOkAlert(getActivity(),getString(R.string.info),"This Function Temporarily locked",false);
                break;
        }
    }

}
//474