package com.srdeveloppement.atelier.mypharmacy.User;

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
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.srdeveloppement.atelier.mypharmacy.Data.GetData;
import com.srdeveloppement.atelier.mypharmacy.Data.GetJsonMedicamentListeUser;
import com.srdeveloppement.atelier.mypharmacy.Data.GetJsonPharmacieListeUser;
import com.srdeveloppement.atelier.mypharmacy.Data.GetJsonUserProfile;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.MedicamentUser;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.PharmacieSmall;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.UserProfile;
import com.srdeveloppement.atelier.mypharmacy.LanguageSettings;
import com.srdeveloppement.atelier.mypharmacy.LocaleHelper;
import com.srdeveloppement.atelier.mypharmacy.MyProfile;
import com.srdeveloppement.atelier.mypharmacy.R;
import com.srdeveloppement.atelier.mypharmacy.User.SearchMedicament.SearchMedicament;
import com.srdeveloppement.atelier.mypharmacy.User.SearchPharmacy.SearchPharmacy;

import java.util.ArrayList;


public class UserNav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
// win yetafichaw  la tots tw lng ta3k ? asma3 aou kayen service google play fel al gradel chof
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude=0.0;
    private double currentLongitude=0.0;

// doak ana hna houwa kima yekliki yokhrejlou dialog heda, li fih l address , wech nzidou ? ndirlou button yedih l location ta3ou ?asma3 nssali ou ndji
    // ok
    Toolbar toolbar = null;
    NavigationView navigationView = null;
    Menu menu;
    String IDUser;
    int id;
    DrawerLayout drawer;
    TextView nav_User_Name;
    TextView nav_Email;
    String responce;
    Bundle Meds;
    SharedPreferences sharedPref;
    Bundle Pharms;
    double LatitudeP,LatitudeM,LangitudeP,LangitudeM;
    ArrayList<MedicamentUser> mMedicamentListeU;
    ArrayList<MedicamentUser> FavmMedicamentListeU;

    ArrayList<PharmacieSmall> mPharmaListU;
    ArrayList<UserProfile> mUserProfile;
    ArrayList<PharmacieSmall> FavmPharmaListU;
    ArrayList<PharmacieSmall> FavmPharmaListUF;

    private static final int LOCATION_PERMISSION_REQUEST_CODE=1;

    String NomDirecteur, PrenomDirecteur, Email;
    SharedPreferences isFavori;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleHelper.onCreate(this, "en");
        setContentView(R.layout.user_nav_activity);
        //lets first set the Default main/home fragment (here its the first item)

      isFavori = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        toolbar = (Toolbar) findViewById(R.id.user_nav_toolbar);
        setSupportActionBar(toolbar);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPref.edit().putInt("typeFromLogin", 0).apply();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        NomDirecteur = sharedPref.getString("name", "");
        PrenomDirecteur = sharedPref.getString("prenom", "");
        Email = sharedPref.getString("email", "");
        View header = navigationView.getHeaderView(0);
        nav_User_Name = (TextView) header.findViewById(R.id.nav_user_nameU);
        nav_Email = (TextView) header.findViewById(R.id.nav_emailU);
        nav_User_Name.setText(NomDirecteur + " " + PrenomDirecteur);
        nav_Email.setText(Email);
        IDUser = sharedPref.getString("IDUser", "");


        id = R.id.pharmacies;

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

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }


    void setMyFragment(int className, android.support.v4.app.Fragment MyFragment) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.user_nav_fragment_container, MyFragment);
        fragmentTransaction.commit();
        //change activity pharmacy_name
        setTitle(getResources().getText(className));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.user_nav_without_search, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // c bn

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStop() {
        super.onStop();
        sharedPref.edit().putInt("typeFromLogin", 0).commit();
    }
    public void exitAppMethod(){

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        id = item.getItemId();
        if (id == R.id.MyProfile) {
            userProfile pr = new userProfile("IDPersson="+IDUser);
            pr.execute();
        } else if (id == R.id.pharmacies) {
            Double LatToAdd= 0.0904371732957;
            Double LangToAdd= 0.0901997925404;

            LatitudeP=currentLatitude+LatToAdd;
            LatitudeM=currentLatitude-LatToAdd;
            LangitudeP=currentLongitude+LangToAdd;
            LangitudeM=currentLongitude-LangToAdd;



            String Params ="IDUtilisateur=" + IDUser+"&LatitudeP="+LatitudeP+"&LatitudeM="+LatitudeM+"&LangitudeP="+LangitudeP+
                    "&LangitudeM="+LangitudeM;
            printPharmaList mu = new printPharmaList(Params);
            id = R.id.pharmacies;
            mu.execute();

        } else if (id == R.id.favorite_pharmacies) {
            Double LatToAdd= 0.0904371732957;
            Double LangToAdd= 0.0901997925404;

            LatitudeP=currentLatitude+LatToAdd;
            LatitudeM=currentLatitude-LatToAdd;
            LangitudeP=currentLongitude+LangToAdd;
            LangitudeM=currentLongitude-LangToAdd;



            String Params ="IDUtilisateur=" + IDUser+"&LatitudeP="+LatitudeP+"&LatitudeM="+LatitudeM+"&LangitudeP="+LangitudeP+
                    "&LangitudeM="+LangitudeM;
            printPharmaList mu = new printPharmaList(Params);
            id = R.id.favorite_pharmacies;
            mu.execute();
        } else if (id == R.id.med_list) {
            String Params = "IDUtilisateur=" + IDUser;
            printMedicamentListeUser mu = new printMedicamentListeUser(Params);
            mu.execute();
        } else if (id == R.id.favorite_med_list) {
            String Params = "IDUtilisateur=" + IDUser;
            printMedicamentListeUser mu = new printMedicamentListeUser(Params);
            mu.execute();
        } else if (id == R.id.settings) {
            startActivity(new Intent(UserNav.this, LanguageSettings.class));
        } else if (id == R.id.log_out) {
            sharedPref.edit().putString("IDUser", "").apply();

            sharedPref.edit().putString("username", "").apply();

            sharedPref.edit().putString("name", "").apply();

            sharedPref.edit().putString("prenom","").apply();

            sharedPref.edit().putString("email","").apply();

            sharedPref.edit().putString("Date_Naissance","").apply();
            sharedPref.edit().putString("MedListOffUser","").apply();
            sharedPref.edit().putString("PharmListOffUser","").apply();
            sharedPref.edit().putString("UsrProf","").apply();
            sharedPref.edit().putString("PharmaInfo","").apply();

            sharedPref.edit().putString("Countery","").apply();

            sharedPref.edit().putString("First_Log","0").apply();


            sharedPref.edit().putInt("typeFromLogin", 0).apply();
            this.moveTaskToBack(true);
            finish();
            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage( getBaseContext().getPackageName() );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

            Double LatToAdd= Double.parseDouble(sharedPref.getString("latAdd","0.0904371732957"));
            Double LangToAdd= Double.parseDouble(sharedPref.getString("langAdd","0.0901997925404"));

            LatitudeP=currentLatitude+LatToAdd;
            LatitudeM=currentLatitude-LatToAdd;
            LangitudeP=currentLongitude+LangToAdd;
            LangitudeM=currentLongitude-LangToAdd;



            String Params ="IDUtilisateur=" + IDUser+"&LatitudeP="+LatitudeP+"&LatitudeM="+LatitudeM+"&LangitudeP="+LangitudeP+
                    "&LangitudeM="+LangitudeM;
            printPharmaList mu = new printPharmaList(Params);

            mu.execute();


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
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
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


    public class printMedicamentListeUser extends GetJsonMedicamentListeUser {

        public printMedicamentListeUser(String Params) {
            super(Params,getApplicationContext());
        }

        public void execute() {
            ProcessData data = new ProcessData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/List_Med_User.php");
        }

        public class ProcessData extends DownloadJsonData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce=WebData;
                mMedicamentListeU = getmMedicament();
                if(mMedicamentListeU!=null){
                    if(!responce.isEmpty()){
                        FavmMedicamentListeU=new ArrayList<>();
                        for(int i=0;i<mMedicamentListeU.size();i++){
                            if(mMedicamentListeU.get(i).getFavoriser().equals("true")){
                                FavmMedicamentListeU.add(mMedicamentListeU.get(i));
                            }
                        }

                         if (id == R.id.med_list) {
                            Meds = new Bundle();
                            Meds.putSerializable("ArrayListMeds", mMedicamentListeU);
                            SearchMedicament searchMedicament = new SearchMedicament();
                            searchMedicament.setArguments(Meds);
                            setMyFragment(R.string.searchMedicament, searchMedicament);

                        } else if (id == R.id.favorite_med_list) {
                            Meds = new Bundle();
                            Meds.putSerializable("ArrayListMeds", FavmMedicamentListeU);
                            SearchMedicament searchMedicament = new SearchMedicament();
                            searchMedicament.setArguments(Meds);
                            setMyFragment(R.string.searchMedicament, searchMedicament);

                        }
                    }else{
                        Toast.makeText(getApplicationContext(), R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), R.string.SERVER_ERROR,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public class printPharmaList extends GetJsonPharmacieListeUser {

        public printPharmaList(String Params) {
            super(Params,getApplicationContext());
        }

        public void execute() {
                ProcessData data = new ProcessData();
                data.execute(GetData.LOCALHOST+"Pharmacie_Project/Liste_Pharmacie_User.php");
        }

        public class ProcessData extends DownloadJsonData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce=WebData;
                mPharmaListU = getmPharmacList();
                if(mPharmaListU!=null){
                    if(!responce.isEmpty()){
                        FavmPharmaListU=new ArrayList<>();
                        FavmPharmaListUF=new ArrayList<>();
                        for(int i=0;i<mPharmaListU.size();i++){
                            if(mPharmaListU.get(i).getFavoriser().equals("true")){
                                FavmPharmaListU.add(mPharmaListU.get(i));
                            }
                            if(mPharmaListU.get(i).getLatitude()<=LatitudeP && mPharmaListU.get(i).getLatitude()>=LatitudeM
                            && mPharmaListU.get(i).getLangitude()<=LangitudeP &&mPharmaListU.get(i).getLangitude()>=LangitudeM){
                                FavmPharmaListUF.add(mPharmaListU.get(i));
                            }
                        }

                        if (id == R.id.pharmacies) {
                            Pharms = new Bundle();
                            isFavori.edit().putInt("isFavori", 0).commit();
                            Pharms.putSerializable("ArrayListPharms", FavmPharmaListUF);
                            SearchPharmacy searchPharmacy = new SearchPharmacy();
                            searchPharmacy.setArguments(Pharms);
                            setMyFragment(R.string.searchPharmacy, searchPharmacy);

                        } else if (id == R.id.favorite_pharmacies) {
                            Pharms = new Bundle();
                            isFavori.edit().putInt("isFavori", 1).commit();
                            Pharms.putSerializable("ArrayListPharms", FavmPharmaListU);
                            SearchPharmacy searchPharmacy = new SearchPharmacy();
                            searchPharmacy.setArguments(Pharms);
                            setMyFragment(R.string.searchPharmacy, searchPharmacy);

                        }

                    }else{
                        Toast.makeText(getApplicationContext(),  R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), R.string.SERVER_ERROR,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public class userProfile extends GetJsonUserProfile {

        public userProfile(String Params) {
            super(Params,getApplicationContext());
        }

        public void execute() {
            ProcessData data = new ProcessData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/ProfileUser.php");
        }

        public class ProcessData extends DownloadJsonData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce=WebData;
                mUserProfile = getmPerssone();
                if(mUserProfile!=null){
                    if(!responce.isEmpty()){
                        if (id == R.id.MyProfile) {
                            MyProfile MyProfile=new MyProfile();
                            String NameSTR,SurnameSTR,UsernameSTR,OldPasswordSTR,EmailSTR,DateofbirthSTR,CountrySTR, GenderSTR;

                            NameSTR=mUserProfile.get(0).getName();
                            SurnameSTR=mUserProfile.get(0).getPrenom();
                            UsernameSTR=mUserProfile.get(0).getUsername();
                            OldPasswordSTR=mUserProfile.get(0).getPassword();
                            EmailSTR=mUserProfile.get(0).getEmail();
                            DateofbirthSTR=mUserProfile.get(0).getDate_Naissance();
                            CountrySTR=mUserProfile.get(0).getCountery();
                            GenderSTR=mUserProfile.get(0).getGender();


                            Bundle bundle = new Bundle();

                            bundle.putString("NameSTR",NameSTR );
                            bundle.putString("SurnameSTR", SurnameSTR);
                            bundle.putString("UsernameSTR", UsernameSTR);
                            bundle.putString("OldPasswordSTR", OldPasswordSTR);
                            bundle.putString("EmailSTR", EmailSTR);
                            //   bundle.putString("HomeSTR", HomeSTR);//Employee
                            bundle.putString("DateofbirthSTR", DateofbirthSTR); //user
                            bundle.putString("CountrySTR", CountrySTR);// user
                            bundle.putString("GenderSTR", GenderSTR);
                            //   bundle.putString("PhoneSTR", PhoneSTR);// directeur / employee
                            MyProfile.setArguments(bundle);
                            setMyFragment(R.string.MyProfile, MyProfile);

                        }

                    }else{
                        Toast.makeText(getApplicationContext(),  R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), R.string.SERVER_ERROR,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}

