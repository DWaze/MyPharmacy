package com.srdeveloppement.atelier.mypharmacy.Pharmacist;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.srdeveloppement.atelier.mypharmacy.Data.GetData;
import com.srdeveloppement.atelier.mypharmacy.Data.GetJsonDirecteurInfo;
import com.srdeveloppement.atelier.mypharmacy.Data.GetJsonPharmacieInfo;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.Directeur;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.Pharmacie;
import com.srdeveloppement.atelier.mypharmacy.Employee.AddMedicament.AddMedicament;
import com.srdeveloppement.atelier.mypharmacy.Employee.ModifyStock.ModifyStock;
import com.srdeveloppement.atelier.mypharmacy.LanguageSettings;
import com.srdeveloppement.atelier.mypharmacy.LocaleHelper;
import com.srdeveloppement.atelier.mypharmacy.MyProfile;
import com.srdeveloppement.atelier.mypharmacy.Pharmacist.TravailleurFragment.TravailleurFragment;
import com.srdeveloppement.atelier.mypharmacy.Pharmacist.myPharmacy.maPharmacieFragment;
import com.srdeveloppement.atelier.mypharmacy.R;

import java.util.ArrayList;

public class PharmasistNav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar=null;
    ActionBarDrawerToggle toggle=null;
    NavigationView navigationView=null;
    Menu menu;
    SharedPreferences sharedPref;
    ArrayList<Pharmacie> mPharmacie;
    String IDPdirecteur;
    ArrayList<Directeur> mDirecteurInfo;
    String responce;
    int id;
    int state=0;
    FrameLayout containerModiMed,containeraddMed;
    DrawerLayout drawer;
    TextView nav_User_Name;
    TextView nav_Email;

    String NomDirecteur,PrenomDirecteur,Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleHelper.onCreate(this, "en");
        setContentView(R.layout.pharmasist_nav_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle(getResources().getText(R.string.ModifyStock));
        setSupportActionBar(toolbar);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPref.edit().putInt("typeFromLogin", 1).apply();


        ModifyStock modifyStock=new ModifyStock();
        setMyFragment(R.string.ModifyStock, modifyStock);



        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        NomDirecteur = sharedPref.getString("name","");
        PrenomDirecteur = sharedPref.getString("prenom", "");
        Email = sharedPref.getString("email", "");
        IDPdirecteur=sharedPref.getString("IDPDirecteur","");
        View header = navigationView.getHeaderView(0);
        nav_User_Name=(TextView)header.findViewById(R.id.nav_user_nameD);
        nav_Email=(TextView)header.findViewById(R.id.nav_emailD);
        nav_User_Name.setText(NomDirecteur+" "+PrenomDirecteur);
        nav_Email.setText(Email);




    }

    void setMyFragment(int className,android.support.v4.app.Fragment MyFragment){

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.pharmacist_nav_fragment_container, MyFragment);
        fragmentTransaction.commit();
        //change activity pharmacy_name
        toolbar.setTitle(getResources().getText(className));

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
        getMenuInflater().inflate(R.menu.pharmacist_nav_without_search, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStop() {
        super.onStop();
        sharedPref.edit().putInt("typeFromLogin", 1).commit();
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

// jareb executi ouach sra
        // ani na7it les setting hedouk aaa ok

        if (id == R.id.MyProfile) {
            state=0;
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DirecteurPRofile dp = new DirecteurPRofile("IDPdirecteur="+IDPdirecteur);
            dp.execute();
            toolbar.setTitle(getResources().getText(R.string.MyProfile));
            toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

        } else if (id == R.id.ModifyStock) {
            state=0;
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            ModifyStock modifyStock=new ModifyStock();
            setMyFragment(R.string.ModifyStock, modifyStock);
            toolbar.setTitle(getResources().getText(R.string.ModifyStock));
            toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
        } else if (id == R.id.AddMedic) {
            state=0;
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            AddMedicament addMedicament=new AddMedicament();
            setMyFragment(R.string.AddMed, addMedicament);
            toolbar.setTitle(getResources().getText(R.string.AddMed));
            toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

        } else if (id == R.id.settings) {
            state=0;
            startActivity(new Intent(PharmasistNav.this, LanguageSettings.class));
        }else if (id == R.id.mPharmacien) {
            state=0;
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            TravailleurFragment fragment = new TravailleurFragment();
            setMyFragment(R.string.mPharmacien, fragment);
            toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
        } else if (id == R.id.mPharmacie) {
            state=1;
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String IDPDir = sharedPref.getString("IDPDirecteur", "");
            ShowPharmacieInfo mShowPharmacieInfo = new ShowPharmacieInfo("IDPDirecteur="+IDPDir);
            mShowPharmacieInfo.execute();
        }  else if (id == R.id.log_out) {
            state=0;
            sharedPref.edit().putString("IDPDirecteur", "").apply();

            sharedPref.edit().putString("username", "").apply();

            sharedPref.edit().putString("name", "").apply();

            sharedPref.edit().putString("prenom", "").apply();

            sharedPref.edit().putString("email", "").apply();

            sharedPref.edit().putString("IDDiplome_Reconnaisance","").apply();

            sharedPref.edit().putString("First_Log","0").apply();

            sharedPref.edit().putString("MedListOff","").apply();
            sharedPref.edit().putString("PharmaInfo","").apply();
            sharedPref.edit().putString("TrList","").apply();
            sharedPref.edit().putString("DirProf","").apply();

            sharedPref.edit().putInt("typeFromLogin", 1).apply();
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




    public class ShowPharmacieInfo extends GetJsonPharmacieInfo {

        public ShowPharmacieInfo(String Params) {
            super(Params,getApplicationContext());
        }

        public void execute() {
            GetPharmacieData data = new GetPharmacieData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/maPharmacie.php");
        }

        public class GetPharmacieData extends DownloadJsonData{
            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                String responce = WebData;
                mPharmacie=getmPharmacie();

                    if(!responce.equals("pharmacie not validated yet!\n")){
                        if(!responce.equals("Pharmacie doas not existe!\n")){
                            if(mPharmacie!=null){
                                toolbar = (Toolbar) findViewById(R.id.toolbar);
                                setTitle(getResources().getText(R.string.mPharmacie));
                                setSupportActionBar(toolbar);
                                maPharmacieFragment pharmacieFragment = new maPharmacieFragment();
                                setMyFragment(R.string.mPharmacie, pharmacieFragment);
                                toggle = new ActionBarDrawerToggle(PharmasistNav.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                                drawer.setDrawerListener(toggle);
                                toggle.syncState();
                            }else{
                                Toast.makeText(getApplicationContext(), R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                            }
                        }else{

                            sharedPref.edit().putInt("PharmacieState",0).commit();
                            toolbar = (Toolbar) findViewById(R.id.toolbar);
                            setSupportActionBar(toolbar);
                            Re_Add_newPharmacy add_pharm=new Re_Add_newPharmacy();
                            setMyFragment(R.string.mPharmacie, add_pharm);
                            toggle = new ActionBarDrawerToggle(PharmasistNav.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                            drawer.setDrawerListener(toggle);
                            toggle.syncState();
                            Toast.makeText(getApplicationContext(), R.string.no_ph_found_createone,Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        sharedPref.edit().putInt("PharmacieState",1).commit();
                        toolbar = (Toolbar) findViewById(R.id.toolbar);
                        setSupportActionBar(toolbar);
                        Re_Add_newPharmacy add_pharm=new Re_Add_newPharmacy();
                        setMyFragment(R.string.mPharmacie, add_pharm);
                        toggle = new ActionBarDrawerToggle(PharmasistNav.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                        drawer.setDrawerListener(toggle);
                        toggle.syncState();
                        Toast.makeText(getApplicationContext(),R.string.ph_no_valid,Toast.LENGTH_SHORT).show();
                    }

            }
        }}

    public class DirecteurPRofile extends GetJsonDirecteurInfo {

        public DirecteurPRofile(String Params) {
            super(Params,getApplicationContext());
        }

        public void execute() {
            ProcessData data = new ProcessData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/DirecteurInfo.php");
        }

        public class ProcessData extends DownloadJsonData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                ArrayList<Directeur> mDirecteurInfo = getmPerssone();
                responce=WebData;
                if(mDirecteurInfo!=null){
                    if(!responce.isEmpty()){

                        if (id == R.id.MyProfile) {
                            MyProfile MyProfile=new MyProfile();


                            String NameSTR,SurnameSTR,UsernameSTR,OldPasswordSTR,EmailSTR,HomeSTR,DateofbirthSTR,PhoneSTR, CountrySTR, GenderSTR;

                            NameSTR=mDirecteurInfo.get(0).getName(); //TODO get this from server
                            SurnameSTR=mDirecteurInfo.get(0).getPrenom();  //TODO get this from server
                            UsernameSTR=mDirecteurInfo.get(0).getUsername(); //TODO get this from server
                            OldPasswordSTR=mDirecteurInfo.get(0).getPassword(); //TODO get this from server
                            EmailSTR=mDirecteurInfo.get(0).getEmail(); //TODO get this from server
                            PhoneSTR=mDirecteurInfo.get(0).getDirecteurPhone(); //TODO get this from server
                            GenderSTR=mDirecteurInfo.get(0).getGender(); //TODO get this from server

                            Bundle bundle = new Bundle();

                            bundle.putString("NameSTR",NameSTR );
                            bundle.putString("SurnameSTR", SurnameSTR);
                            bundle.putString("UsernameSTR", UsernameSTR);
                            bundle.putString("OldPasswordSTR", OldPasswordSTR);
                            bundle.putString("EmailSTR", EmailSTR);
                            bundle.putString("GenderSTR", GenderSTR);
                            bundle.putString("PhoneSTR", PhoneSTR);// directeur / employee
                            MyProfile.setArguments(bundle);
                            setMyFragment(R.string.MyProfile, MyProfile);

                        }


                    }else{

                        Toast.makeText(getApplicationContext(),R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),R.string.SERVER_ERROR,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}
