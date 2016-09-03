package com.srdeveloppement.atelier.mypharmacy.Employee;

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
import android.widget.TextView;
import android.widget.Toast;

import com.srdeveloppement.atelier.mypharmacy.Data.GetData;
import com.srdeveloppement.atelier.mypharmacy.Data.GetJsonPharmacieInfo;
import com.srdeveloppement.atelier.mypharmacy.Data.GetJsonTravailleurProfile;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.Pharmacie;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.Travailleur;
import com.srdeveloppement.atelier.mypharmacy.Employee.AddMedicament.AddMedicament;
import com.srdeveloppement.atelier.mypharmacy.Employee.ModifyStock.ModifyStock;
import com.srdeveloppement.atelier.mypharmacy.LanguageSettings;
import com.srdeveloppement.atelier.mypharmacy.LocaleHelper;
import com.srdeveloppement.atelier.mypharmacy.MyProfile;
import com.srdeveloppement.atelier.mypharmacy.R;

import java.util.ArrayList;

public class EmployeeNav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    NavigationView navigationView;
    SharedPreferences sharedPref;
    DrawerLayout drawer;
    int id;
    ArrayList <Travailleur> mTravailleurPr;
    TextView nav_User_Name;
    String NomTravailleur="",PrenomTravailleur="",Email="",ID="";
    TextView nav_Email;
    TextView nav_ID;
    String IDPtravailleur;
    ArrayList<Pharmacie> mPharmacie;
    String responce;
    ActionBarDrawerToggle toggle;
    Menu menu;
    boolean myPharm=false,addmed=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleHelper.onCreate(this, "en");
        setContentView(R.layout.employee_nav_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle(getResources().getText(R.string.ModifyStock));
        setSupportActionBar(toolbar);

        ModifyStock modifyStock=new ModifyStock();
        setMyFragment(R.string.ModifyStock, modifyStock);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPref.edit().putInt("typeFromLogin", 2).apply();



        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        NomTravailleur = sharedPref.getString("name", "");
        IDPtravailleur=sharedPref.getString("IDTravialleur","");
        PrenomTravailleur = sharedPref.getString("prenom", "");
        Email = sharedPref.getString("email", "");
        ID = sharedPref.getString("IDTravialleur", "");
        View header = navigationView.getHeaderView(0);
        nav_User_Name = (TextView) header.findViewById(R.id.nav_user_name);
        nav_Email = (TextView) header.findViewById(R.id.nav_email);
        nav_ID = (TextView) header.findViewById(R.id.id_of_employee);
        nav_User_Name.setText(NomTravailleur + " " + PrenomTravailleur);
        nav_Email.setText(Email);
        nav_ID.setText("ID"+"   "+ID);
    }
    void setMyFragment(int className,android.support.v4.app.Fragment MyFragment){
        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.employee_nav_fragment_container, MyFragment);
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
        getMenuInflater().inflate(R.menu.employee_nav_without_search, menu);
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
        sharedPref.edit().putInt("typeFromLogin", 2).commit();
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

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            TravailleurPRofile tr = new TravailleurPRofile("IDPersson="+IDPtravailleur);
            tr.execute();
            toolbar.setTitle(getResources().getText(R.string.MyProfile));
            toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
        } else if (id == R.id.ModifyStock) { // testi trach la recherch w toolbar f travailleur merd dok ytaouel dert la verssion onnline ou dorka ani ndire fi hadi

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            ModifyStock modifyStock=new ModifyStock();
            setMyFragment(R.string.ModifyStock, modifyStock);
            toolbar.setTitle(getResources().getText(R.string.ModifyStock));
            toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
         //   shit ay version gdima hedi ana khi dert check ta3 loukan mayakhdemch , ay ma3andekch hna hhhhh mana3ref
            // ak ma remplacitech la class kima gotlk hedak nhar , normal ay sahla dok ndirha
        } else if (id == R.id.AddMedic) {
            addmed=true;
            sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String IDPDir = sharedPref.getString("IDPDirecteur", "");
            ShowPharmacieInfo mShowPharmacieInfo = new ShowPharmacieInfo("IDPDirecteur="+IDPDir);
            mShowPharmacieInfo.execute();
            toolbar.setTitle(getResources().getText(R.string.AddMed));
        } else if (id == R.id.maPharmacie) {
            addmed=false;
            sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String IDPDir = sharedPref.getString("IDPDirecteur", "");
            ShowPharmacieInfo mShowPharmacieInfo = new ShowPharmacieInfo("IDPDirecteur="+IDPDir);
            mShowPharmacieInfo.execute();
            toolbar.setTitle(getResources().getText(R.string.mPharmacie));

        } if (id == R.id.settings) {
            startActivity(new Intent(EmployeeNav.this, LanguageSettings.class));
        } else if (id == R.id.log_out) {
            sharedPref.edit().putString("IDTravialleur", "").apply();

            sharedPref.edit().putString("username", "").apply();

            sharedPref.edit().putString("name","").apply();

            sharedPref.edit().putString("prenom","").apply();

            sharedPref.edit().putString("email","").apply();

            sharedPref.edit().putString("Numero_Telephone","").apply();

            sharedPref.edit().putString("Address","").apply();

            sharedPref.edit().putString("MedListOff","").apply();
            sharedPref.edit().putString("PharmaInfo","").apply();
            sharedPref.edit().putString("TrProf","").apply();

            sharedPref.edit().putString("IDDiplome_Reconnaisance","").apply();

            sharedPref.edit().putString("IDPDirecteur", "").apply();

            sharedPref.edit().putString("First_Log","0").apply();

            sharedPref.edit().putInt("typeFromLogin", 2).apply();
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
                                if(addmed==true){
                                    toolbar = (Toolbar) findViewById(R.id.toolbar);
                                    setSupportActionBar(toolbar);
                                    AddMedicament addMedicament=new AddMedicament();
                                    setMyFragment(R.string.AddMed, addMedicament);
                                    toolbar.setTitle(getResources().getText(R.string.AddMed));
                                    toggle = new ActionBarDrawerToggle(EmployeeNav.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                                    drawer.setDrawerListener(toggle);
                                    toggle.syncState();
                                }else{
                                    toolbar = (Toolbar) findViewById(R.id.toolbar);
                                    setTitle(getResources().getText(R.string.mPharmacie));
                                    setSupportActionBar(toolbar);
                                    mPharmacieTr pharmacieFragment = new mPharmacieTr();
                                    setMyFragment(R.string.mPharmacie, pharmacieFragment);
                                    toggle = new ActionBarDrawerToggle(EmployeeNav.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                                    drawer.setDrawerListener(toggle);
                                    toggle.syncState();
                                }
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), R.string.no_ph_please_wait,Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), R.string.wait_conf_ph,Toast.LENGTH_SHORT).show();
                    }
            }
        }}



    public class TravailleurPRofile extends GetJsonTravailleurProfile {

        public TravailleurPRofile(String Params) {
            super(Params,getApplicationContext());
        }

        public void execute() {
            ProcessData data = new ProcessData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/TravailleurInfo.php");
        }

        public class ProcessData extends DownloadJsonData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                mTravailleurPr = getmPerssone();
                responce=WebData;
                if(mTravailleurPr!=null){
                    if(!responce.isEmpty()){

                        if (id == R.id.MyProfile) {
                            MyProfile MyProfile=new MyProfile();


                            String NameSTR,SurnameSTR,UsernameSTR,OldPasswordSTR,EmailSTR,HomeSTR,DateofbirthSTR,PhoneSTR, CountrySTR, GenderSTR;

                            NameSTR=mTravailleurPr.get(0).getName(); //TODO get this from server
                            SurnameSTR=mTravailleurPr.get(0).getPrenom();  //TODO get this from server
                            UsernameSTR=mTravailleurPr.get(0).getUsername(); //TODO get this from server
                            OldPasswordSTR=mTravailleurPr.get(0).getPassword(); //TODO get this from server
                            EmailSTR=mTravailleurPr.get(0).getEmail(); //TODO get this from server
                            PhoneSTR=mTravailleurPr.get(0).getTelephone(); //TODO get this from server
                            GenderSTR=mTravailleurPr.get(0).getGender(); //TODO get this from server
                            HomeSTR=mTravailleurPr.get(0).getAddress(); //TODO get this from server

                            Bundle bundle = new Bundle();

                            bundle.putString("NameSTR",NameSTR );
                            bundle.putString("SurnameSTR", SurnameSTR);
                            bundle.putString("UsernameSTR", UsernameSTR);
                            bundle.putString("OldPasswordSTR", OldPasswordSTR);
                            bundle.putString("EmailSTR", EmailSTR);
                            bundle.putString("GenderSTR", GenderSTR);
                            bundle.putString("HomeSTR", HomeSTR);//Employee
                            bundle.putString("PhoneSTR", PhoneSTR);// directeur / employee
                            MyProfile.setArguments(bundle);
                            setMyFragment(R.string.MyProfile, MyProfile);

                        }


                    }else{

                        Toast.makeText(getApplicationContext(), R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),R.string.SERVER_ERROR,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
