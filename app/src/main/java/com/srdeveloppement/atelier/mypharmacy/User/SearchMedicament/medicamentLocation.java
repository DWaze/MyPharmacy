package com.srdeveloppement.atelier.mypharmacy.User.SearchMedicament;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.srdeveloppement.atelier.mypharmacy.Data.GetData;
import com.srdeveloppement.atelier.mypharmacy.Data.GetJsonMedPharmacieList;
import com.srdeveloppement.atelier.mypharmacy.Data.GetJsonRouteData;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.MedPharmacie;
import com.srdeveloppement.atelier.mypharmacy.Pharmacist.TravailleurFragment.RecyclerItemClickListener;
import com.srdeveloppement.atelier.mypharmacy.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class medicamentLocation extends AppCompatActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {



    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude=0.0;
    LatLng pharmacy;
    private List<LatLng> stepsR;
    private double currentLongitude=0.0;
    double LatitudeP,LatitudeM,LangitudeP,LangitudeM;
    private GoogleMap mMap;
    private LatLng mylocation;
    Button close;
    String responce;
    CameraPosition POSITION;
    CameraPosition POSITION1;
    SharedPreferences sharedPref;
    ArrayList<MedPharmacie> MedPharmacieList;
    ToggleButton map_type;
    Button MyLocation;
    Marker pharm_marker;
    Polyline pol;
    LinearLayout frame_adress;

    private static final int LOCATION_PERMISSION_REQUEST_CODE=1;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    RelativeLayout list_container;
    Button changeToList;int a=1;
    FrameLayout changetolist;
    TextView dist,dur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_medicament_location);

        close=(Button)findViewById(R.id.x_map_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        MyLocation=(Button)findViewById(R.id.MyLocation);
        map_type=(ToggleButton)findViewById(R.id.map_type);
        list_container=(RelativeLayout)findViewById(R.id.list_container);
        changeToList =(Button)findViewById(R.id.help_map);
        changetolist =(FrameLayout)findViewById(R.id.changetolist);
        dur=(TextView)findViewById(R.id.dur);
        dist=(TextView)findViewById(R.id.dist);
        frame_adress=(LinearLayout) findViewById(R.id.frame_adress);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        changeToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (a == 0) {
                    changetolist.setBackgroundResource(R.drawable.terrin_shape);
                    list_container.setVisibility(View.VISIBLE);
                    a = 1;
                } else {
                    changetolist.setBackgroundResource(R.drawable.list_shape);
                    list_container.setVisibility(View.INVISIBLE);
                    a = 0;
                }

            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_medicament);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

        MyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mylocation!=null){
                    POSITION1 = CameraPosition.builder()
                            .target(mylocation)
                            .zoom(20)
                            .bearing(0)
                            .tilt(0)
                            .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(POSITION1), 2000, null);
                }
            }
        });


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds



        mGoogleApiClient.connect();

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, final int position) {
                if(pol!=null){
                    pol.remove();
                }
                executeRoot root = new executeRoot();
                root.execute(MedPharmacieList.get(position).getLatitude(),MedPharmacieList.get(position).getLangitude());
                changetolist.setBackgroundResource(R.drawable.list_shape);
                list_container.setVisibility(View.INVISIBLE);
                a = 0;

            }

            @Override
            public void onItemLongClick(View view, final int position) {
            }
        }));
    }





    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        map_type.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                    // The toggle is enabled
                } else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    // The toggle is disabled
                }
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            if(currentLatitude!=0 && currentLongitude!=0)
            mylocation = new LatLng(currentLatitude,currentLongitude);

            Double LatToAdd= 0.0904371732957;
            Double LangToAdd= 0.0901997925404;

            LatitudeP=currentLatitude+LatToAdd;
            LatitudeM=currentLatitude-LatToAdd;
            LangitudeP=currentLongitude+LangToAdd;
            LangitudeM=currentLongitude-LangToAdd;

            Intent intent = getIntent();

            String Parms="IDMedicament="+intent.getStringExtra("IDMED")+"&LatitudeP="+LatitudeP+"&LatitudeM="+LatitudeM+
                    "&LangitudeP="+LangitudeP+"&LangitudeM="+LangitudeM+"&Joure="+getToday();
            MedPharmacielist medPharmacielist = new MedPharmacielist(Parms);
            medPharmacielist.execute();
        }
    }
    public String getToday(){
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.UK);
        String today="";
        Calendar NOW= Calendar.getInstance();
        today=dayFormat.format(NOW.getTime());

        return today;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }


    public class MedPharmacielist extends GetJsonMedPharmacieList {

        public MedPharmacielist(String Params) {
            super(Params);
        }

        public void execute() {
            ProcessData data = new ProcessData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/FindPharmacieMed.php");
        }

        public class ProcessData extends DownloadJsonData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce = WebData;
                if(!responce.equals("Error connecting to server")){
                            MedPharmacieList=getmMedPharmacie();
                    if(MedPharmacieList!=null){
                        mAdapter = new Open_info_Adapter(MedPharmacieList);
                        mRecyclerView.setAdapter(mAdapter);
                        for(int i =0;i<MedPharmacieList.size();i++ ){
                            pharmacy = new LatLng(MedPharmacieList.get(i).getLatitude(), MedPharmacieList.get(i).getLangitude());
                            pharm_marker=mMap.addMarker(new MarkerOptions().position(pharmacy)
                                    .title(MedPharmacieList.get(i).getNom_Pharmacie()).snippet(MedPharmacieList.get(i).getAddress_Pharmacie())
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.m_pharmacy)));

                            pharm_marker.showInfoWindow();
                            //TODO: visible/invisible distance

                        }
                        if(pharmacy!=null){
                            POSITION = CameraPosition.builder()
                                    .target(pharmacy)
                                    .zoom(13)
                                    .bearing(0)
                                    .tilt(45)
                                    .build();
                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(POSITION), 2000, null);
                        }

                    }
                    }
                if(mylocation!=null){
                    pharm_marker=mMap.addMarker(new MarkerOptions().position(mylocation)
                            .title("My location").icon(BitmapDescriptorFactory.fromResource(R.drawable.m_location)));
                }
                }
            }
        }
    public class executeRoot extends GetJsonRouteData{


        public void execute(double Destlatitude,double Destlongitude){
            if(currentLatitude!=0.0 && currentLongitude!=0.0){
                StartRun run = new StartRun();
                run.execute("https://maps.googleapis.com/maps/api/directions/json?origin="+ currentLatitude+","+currentLongitude+"&destination="+Destlatitude+","+Destlongitude+"&key=AIzaSyDHryZmsqhjzu32XybUZB-2q-GbMFo_wV4");
            }else{
                Toast.makeText(getApplicationContext(), R.string.activ_location_service,Toast.LENGTH_SHORT).show();
            }
        }

        public class StartRun extends getData {
            @Override
            protected void onPostExecute(String Rinformation) {
                super.onPostExecute(Rinformation);
                stepsR =getSteps();
                String Distance = getDistanceStr();
                String Duration = getDurationStr();
                if((!Distance.equals("") && !Duration.equals("")||(!Distance.equals(null) && !Duration.equals(null)))){
                //TODO: visible/invisible distance
                    frame_adress.setVisibility(View.VISIBLE);
                    dist.setText(" "+Distance+" ");
                    dur.setText(" "+Duration+" ");
                }else{
                    frame_adress.setVisibility(View.GONE);
                }



                CameraPosition Constantine = CameraPosition.builder()
                        .target(mylocation)
                        .zoom(15)
                        .bearing(0)
                        .tilt(0)
                        .build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(Constantine), 2000, null);
                PolylineOptions options = new PolylineOptions().width(8).color(getBaseContext().getResources().getColor(R.color.selectColor)).geodesic(true);
                if(stepsR!=null){
                    for (int z = 0; z < stepsR.size(); z++) {
                        LatLng point = stepsR.get(z);
                        options.add(point);
                    }
                    pol=mMap.addPolyline(options);
                }

            }
        }
    }
}
