package com.srdeveloppement.atelier.mypharmacy.User.SearchPharmacy;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.srdeveloppement.atelier.mypharmacy.Data.GetData;
import com.srdeveloppement.atelier.mypharmacy.Data.GetJsonPharmacieInfo;
import com.srdeveloppement.atelier.mypharmacy.Data.GetJsonRouteData;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.Pharmacie;
import com.srdeveloppement.atelier.mypharmacy.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class pharmacyLocation extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude=0.0;
    private double currentLongitude=0.0;
    Polyline pol;

    private static final int LOCATION_PERMISSION_REQUEST_CODE=1;


    private GoogleMap mMap;
    Button close;
    ToggleButton litinirair,map_type;
    Button MyLocation;
    LatLng postion;

    //Todo
    String START;
    String idPharmaci;
    ArrayList<Pharmacie> mPharmacie;
    String END;
    String responce;
    SimpleDateFormat inputParser;
    Date CURRENT_TIME_date;
    Date StartTime;
    Date EndTime;
    boolean MARKET_IS_OPENED=false;
    int a=0;ScrollView info;
    LatLng mylocation;
    CameraPosition POSITION1;
    CameraPosition POSITION;
    private List<LatLng> stepsR;
    Button changeToInfo;
    FrameLayout changetoINFO;
    TextView adress,p_name,aviableMed,state;
    String pharm_name,pharm_adress;
    String pharm_av_med;
    RelativeLayout sunday,monday,tuesday,wednesday,thursday,friday,saturday;
    public boolean cb_SundayB,cb_MondayB,cb_TuesdayB,cb_WednesdayB,cb_ThursdayB,cb_FridayB,cb_SaturdayB;
    Button Sunday_from,Monday_from,Tuesday_from,Wednesday_from,Thursday_from,Friday_from,Saturday_from;
    Button Sunday_to,Monday_to,Tuesday_to,Wednesday_to,Thursday_to,Friday_to,Saturday_to;

    public String Sunday_f,Monday_f,Tuesday_f,Wednesday_f,Thursday_f,Friday_f,Saturday_f;
    public String Sunday_t,Monday_t,Tuesday_t,Wednesday_t,Thursday_t,Friday_t,Saturday_t;

    //TODO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pharmacy_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        close=(Button)findViewById(R.id.x_map_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        litinirair=(ToggleButton)findViewById(R.id.litinirair);
        MyLocation=(Button)findViewById(R.id.MyLocation);
        map_type=(ToggleButton)findViewById(R.id.map_type);

        mapFragment.getMapAsync(this);
        //TODO
        sunday=(RelativeLayout)findViewById(R.id.sunday);
        monday=(RelativeLayout)findViewById(R.id.monday);
        tuesday=(RelativeLayout)findViewById(R.id.tuesday);
        thursday=(RelativeLayout)findViewById(R.id.thursday);
        wednesday=(RelativeLayout)findViewById(R.id.wednesday);
        friday=(RelativeLayout)findViewById(R.id.friday);
        saturday=(RelativeLayout)findViewById(R.id.saturday);
        Sunday_from=(Button)findViewById(R.id.Sunday_from);
        Monday_from=(Button)findViewById(R.id.Monday_from);
        Tuesday_from=(Button)findViewById(R.id.Tuesday_from);
        Wednesday_from=(Button)findViewById(R.id.Wednesday_from);
        Thursday_from=(Button)findViewById(R.id.Thursday_from);
        Friday_from=(Button)findViewById(R.id.Friday_from);
        Saturday_from=(Button)findViewById(R.id.Saturday_from);
        Sunday_to=(Button)findViewById(R.id.Sunday_to);
        Monday_to=(Button)findViewById(R.id.Monday_to);
        Tuesday_to=(Button)findViewById(R.id.Tuesday_to);
        Wednesday_to=(Button)findViewById(R.id.Wednesday_to);
        Thursday_to=(Button)findViewById(R.id.Thursday_to);
        Friday_to=(Button)findViewById(R.id.Friday_to);
        Saturday_to=(Button)findViewById(R.id.Saturday_to);
        adress=(TextView)findViewById(R.id.p_adress);
        p_name=(TextView)findViewById(R.id.p_name);
        aviableMed=(TextView)findViewById(R.id.av_med);
        state=(TextView)findViewById(R.id.state);

        info=(ScrollView)findViewById(R.id.scrollINFO);
        changetoINFO=(FrameLayout)findViewById(R.id.changetoINFO);
        changeToInfo =(Button)findViewById(R.id.info_map);
        changeToInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {if (a == 0) {changetoINFO.setBackgroundResource(R.drawable.terrin_shape);info.setVisibility(View.VISIBLE);a = 1;} else {changetoINFO.setBackgroundResource(R.drawable.info_shape);info.setVisibility(View.GONE);a = 0;}}});


        litinirair.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    if(pol!=null){
                        pol.remove();
                    }
                    if(mPharmacie!=null){
                        ExecuteRoot root = new ExecuteRoot();
                        root.execute(mPharmacie.get(0).getLatitude(),mPharmacie.get(0).getLangitude());
                    }


                    // The toggle is enabled
                } else {
                    if(pol!=null){
                        pol.remove();
                    }
                }
            }
        });

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

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        idPharmaci=extras.getString("IDPHarmacie");
        ShowPharmacieInfo info = new ShowPharmacieInfo("IDPharmaci="+idPharmaci);
        info.execute();
    }

    //TODO
    public String getToday(){
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.UK);
        String today="";
        Calendar NOW= Calendar.getInstance();
        today=dayFormat.format(NOW.getTime());
        return today;
    }
    public void check_if_works(String startTime,String endTime){
        inputParser = new SimpleDateFormat("HH:mm", Locale.UK);
        Calendar NOW= Calendar.getInstance();
        int H=NOW.get(Calendar.HOUR_OF_DAY);
        int M=NOW.get(Calendar.MINUTE);
        String MM=""+M;
        String HH=""+H;
        if(M<=9 ){MM="0"+MM;}
        if(H<=9 ){HH="0"+HH;}
        String  CURRENT_TIME=HH + ":" + MM;
        //////////
        CURRENT_TIME_date = parseDate(HH + ":" + MM);
        StartTime = parseDate(startTime);
        EndTime = parseDate(endTime);
        if ( StartTime.before( CURRENT_TIME_date ) && EndTime.after(CURRENT_TIME_date)) {
            state.setText(" "+getResources().getString(R.string.opned)+" ");
            state.setTextColor(getResources().getColor(R.color.selectColor));
            MARKET_IS_OPENED=true;
        }else{
            state.setText(" "+getResources().getString(R.string.closed)+" ");
            state.setTextColor(getResources().getColor(R.color.errorColor));
            MARKET_IS_OPENED=false;}

    }
    public Date parseDate(String date) {

        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }
    public void showTimingItem(RelativeLayout myItem,boolean isActivated){

        if(isActivated==true){
            LinearLayout.LayoutParams Params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            myItem.setLayoutParams(Params);
            myItem.setGravity(Gravity.CENTER_VERTICAL );
        }else{
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) myItem.getLayoutParams();
            params.height = 0;
            myItem.setLayoutParams(params);
        }}
    //TODO

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
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    /* Getting the current location */

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            if(currentLatitude!=0 && currentLongitude!=0){
                mylocation = new LatLng(currentLatitude,currentLongitude);
                mMap.addMarker(new MarkerOptions().position(mylocation).title("My Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.m_location))); //todo

            }
        }
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

    public class ShowPharmacieInfo extends GetJsonPharmacieInfo {

        public ShowPharmacieInfo(String Params) {
            super(Params,getApplicationContext());
        }

        public void execute() {
            GetPharmacieData data = new GetPharmacieData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/PharmacieInfo.php");
        }

        public class GetPharmacieData extends DownloadJsonData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce = WebData;
                if(!responce.equals("Error connecting to server")){
                    if(!responce.equals("pharmacie not validated yet!\n")){
                        if(!responce.equals("Pharmacie doas not existe!\n")){
                            if(!responce.isEmpty()){
                                mPharmacie=getmPharmacie();
                                if(mPharmacie!=null){
                                pharm_name = mPharmacie.get(0).getNom_Pharmacie();
                                pharm_adress = mPharmacie.get(0).getAddress_Pharmacie();
                                pharm_av_med=""+mPharmacie.get(0).getNumberOfMeds();

                                cb_SundayB=Boolean.parseBoolean(mPharmacie.get(0).getCb_SundayB()); // fi blaset TRUE nta jib l variable men serveur ok
                                cb_MondayB=Boolean.parseBoolean(mPharmacie.get(0).getCb_MondayB());;
                                cb_TuesdayB=Boolean.parseBoolean(mPharmacie.get(0).getCb_TuesdayB());;
                                cb_WednesdayB=Boolean.parseBoolean(mPharmacie.get(0).getCb_WednesdayB());;
                                cb_ThursdayB=Boolean.parseBoolean(mPharmacie.get(0).getCb_ThursdayB());;
                                cb_FridayB=Boolean.parseBoolean(mPharmacie.get(0).getCb_FridayB());;
                                cb_SaturdayB=Boolean.parseBoolean(mPharmacie.get(0).getCb_SaturdayB());;
                                //FROM // get them from server
                                Sunday_f=mPharmacie.get(0).getSunday_f(); // hna tani  ok
                                Sunday_from.setText(Sunday_f);
                                Monday_f=mPharmacie.get(0).getMonday_f();
                                Monday_from.setText(Monday_f);
                                Tuesday_f=mPharmacie.get(0).getTuesday_f();
                                Tuesday_from.setText(Tuesday_f);
                                Wednesday_f=mPharmacie.get(0).getWednesday_f();
                                Wednesday_from.setText(Wednesday_f);
                                Thursday_f =mPharmacie.get(0).getTuesday_f();
                                Thursday_from.setText(Thursday_f);
                                Friday_f=mPharmacie.get(0).getFriday_f();
                                Friday_from.setText(Friday_f);
                                Saturday_f=mPharmacie.get(0).getSaturday_f();
                                Saturday_from.setText(Saturday_f);
                                //TO // get them from server
                                Sunday_t=mPharmacie.get(0).getSunday_t();
                                Sunday_to.setText(Sunday_t);
                                Monday_t=mPharmacie.get(0).getMonday_t();
                                Monday_to.setText(Monday_t);
                                Tuesday_t=mPharmacie.get(0).getTuesday_t();
                                Tuesday_to.setText(Tuesday_t);
                                Wednesday_t=mPharmacie.get(0).getWednesday_t();
                                Wednesday_to.setText(Wednesday_t);
                                Thursday_t =mPharmacie.get(0).getThursday_t();
                                Thursday_to.setText(Thursday_t);
                                Friday_t=mPharmacie.get(0).getFriday_t();
                                Friday_to.setText(Friday_t);
                                Saturday_t=mPharmacie.get(0).getSaturday_t();
                                Saturday_to.setText(Saturday_t);


                                p_name.setText(" "+pharm_name+" ");
                                adress.setText(" "+pharm_adress+" ");
                                aviableMed.setText(" "+pharm_av_med+" ");
                                switch (getToday()){
                                    case "Sunday": check_if_works(Sunday_f,Sunday_t);break;
                                    case "Monday": check_if_works(Monday_f,Monday_t);break;
                                    case "Tuesday":check_if_works(Tuesday_f,Tuesday_t); break;
                                    case "Wednesday":check_if_works(Wednesday_f,Wednesday_t); break;
                                    case "Thursday":check_if_works(Thursday_f,Thursday_t); break;
                                    case "Friday": check_if_works(Friday_f,Friday_t);break;
                                    case "Saturday":check_if_works(Saturday_f,Saturday_t); break;
                                }
                                showTimingItem(sunday,cb_SundayB);
                                showTimingItem(monday,cb_MondayB);
                                showTimingItem(tuesday,cb_TuesdayB);
                                showTimingItem(wednesday,cb_WednesdayB);
                                showTimingItem(thursday,cb_ThursdayB);
                                showTimingItem(friday,cb_FridayB);
                                showTimingItem(saturday,cb_SaturdayB);




                                double lat=mPharmacie.get(0).getLatitude();
                                double lang=mPharmacie.get(0).getLangitude();

                                postion = new LatLng(lat,lang);
                                POSITION = CameraPosition.builder()
                                        .target(postion)
                                        .zoom(14)
                                        .bearing(0)
                                        .tilt(60)
                                        .build();
                                mMap.addMarker(new MarkerOptions().position(postion).title("Pharmacie: " + mPharmacie.get(0).getNom_Pharmacie()).icon(BitmapDescriptorFactory.fromResource(R.drawable.m_pharmacy)));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(postion));
                                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(POSITION), 2000, null);// c bn ok dok kima netlagaou zide djarebha andek haka bah nkono sure oui
                            }}
                        }else{
                            Toast.makeText(getApplicationContext(),R.string.ph_no_exist,Toast.LENGTH_SHORT).show();//cheft hadi ay lahna ana andi ouin kima la pharmacie tkon makanech ygolo makanech
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),R.string.ph_no_valid,Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        }}

    public class ExecuteRoot extends GetJsonRouteData {


        public void execute(double Destlatitude,double Destlongitude){
            if(currentLatitude!=0.0 && currentLongitude!=0.0){
                StartRun run = new StartRun();
                run.execute("https://maps.googleapis.com/maps/api/directions/json?origin="+ currentLatitude+","+currentLongitude+"&destination="+Destlatitude+","+Destlongitude+"&key=AIzaSyDHryZmsqhjzu32XybUZB-2q-GbMFo_wV4");
            }else{
                Toast.makeText(getApplicationContext(),R.string.activ_location_service,Toast.LENGTH_SHORT).show();
            }
        }

        public class StartRun extends getData {
            @Override
            protected void onPostExecute(String Rinformation) {
                super.onPostExecute(Rinformation);
                stepsR =getSteps();
                if(stepsR!=null){
                CameraPosition Constantine = CameraPosition.builder()
                        .target(mylocation)
                        .zoom(15)
                        .bearing(0)
                        .tilt(0)
                        .build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(Constantine), 2000, null);
                PolylineOptions options = new PolylineOptions().width(8).color(getBaseContext().getResources().getColor(R.color.selectColor)).geodesic(true);
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
