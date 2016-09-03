package com.srdeveloppement.atelier.mypharmacy;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.srdeveloppement.atelier.mypharmacy.Data.GetData;
import com.srdeveloppement.atelier.mypharmacy.Data.GetJsonDirecteurInfo;
import com.srdeveloppement.atelier.mypharmacy.Data.GetJsonTravailleurLog;
import com.srdeveloppement.atelier.mypharmacy.Data.GetJsonUserInfo;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.Directeur;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.TravailleurD;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.User;
import com.srdeveloppement.atelier.mypharmacy.Employee.EmployeeNav;
import com.srdeveloppement.atelier.mypharmacy.Pharmacist.PharmasistNav;
import com.srdeveloppement.atelier.mypharmacy.User.UserNav;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private RadioGroup radioUsers;
     RadioButton r0,r1,r2;
    Button create,login,langue;
    public int type=0;
    Toolbar toolbar=null;
    String Params;
    MaterialEditText usr,pass;
    String responce;
    public static final String USER="User";
    public static final String DIRECTEUR="PDirecteur";
    public static final String EMPLOYE="PEmployee";
    private ArrayList<Directeur> mDirecteur;
    private ArrayList<User> mUser;
    private ArrayList<TravailleurD> mTravailleur;

    SharedPreferences sharedPref;
    //TODO
    Button forget_password;
    //TODO

    private static final int LOCATION_PERMISSION_REQUEST_CODE=2;
    int typeFromLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleHelper.onCreate(this, "en");
        setContentView(R.layout.home_app_bar);
        toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        r0=(RadioButton)findViewById(R.id.r0);
        r1=(RadioButton)findViewById(R.id.r1);
        r2=(RadioButton)findViewById(R.id.r2);






        radioUsers =(RadioGroup)findViewById(R.id.radioUsers);

        create=(Button)findViewById(R.id.create);
        login=(Button)findViewById(R.id.login);
        langue=(Button)findViewById(R.id.langue);
        usr=(MaterialEditText)findViewById(R.id.User_et);
        pass=(MaterialEditText)findViewById(R.id.Password_et);




// asma3

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String language=sharedPref.getString("lng","en");
        switch (language){
            case "en":
                langue.setText(getResources().getString(R.string.enh));
                break;
            case "fr":
                langue.setText(getResources().getString(R.string.frh));
                break;
            case "ar":
                langue.setText(getResources().getString(R.string.arh));
                break;
            default:
                langue.setText(getResources().getString(R.string.enh));
                break;
        }


        ///// ou hadi la version li khdemt biha
        // wini la version li fiha offline hadi
        // dok ndirlk update hna ba3d ok


        langue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, LanguageSettings.class));
            }
        });
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        typeFromLogin = sharedPref.getInt("typeFromLogin", 0);
        switch (typeFromLogin){
            case 0:setOperation(r0);type=0;
                sharedPref.edit().putInt("typeFromLogin", 0).apply();break;
            case 1: setOperation(r1);type=1;
                sharedPref.edit().putInt("typeFromLogin", 1).apply();break;
            case 2: setOperation(r2);type=2;
                sharedPref.edit().putInt("typeFromLogin", 2).apply();break;

        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        //TODO
        forget_password=(Button)findViewById(R.id.forget_password);
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Dialog_restor_password.class));
            }
        });
        //TODO
    }
    public void setOperation(RadioButton r){
        r.setChecked(true);
        r.setSelected(true);
       // r.setFocusableInTouchMode(true);
        r.setClickable(true);
      //  r.setFocusable(true); ro7 trach executi
        // mchat ? oui normalment cbon osbor d9i9a cbon ay mcha kolech hamdolilah , bien aya, aya sa7a aw ra7 l7el lazem nakhdem navigation ta3 admin w noiumip rimi oui aya mba3ed
    }

    @Override
    protected void onStart() {
        super.onStart();

            sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String first_log=sharedPref.getString("First_Log","0");
            if(first_log.equals("1")){
                int typeLogin= sharedPref.getInt("typeFromLogin", 0);
                switch (typeLogin){
                    case 0:
                        sharedPref.edit().putInt("typeFromLogin", 0).commit();
                        startActivity(new Intent(Home.this, UserNav.class));
                        finish();
                        ;break;
                    case 1:
                        sharedPref.edit().putInt("typeFromLogin", 1).commit();
                        startActivity(new Intent(Home.this, PharmasistNav.class));
                        finish();
                        ;break;
                    case 2:
                        sharedPref.edit().putInt("typeFromLogin",2).commit();
                        startActivity(new Intent(Home.this, EmployeeNav.class));
                        finish();
                        ;break;
                    default:
                        break;

                }
            }
    }
// eww aw l version lakhra mela zid hedou
    @Override
    protected void onResume() {
        super.onResume();
        r0.setVisibility(View.INVISIBLE);
        r1.setVisibility(View.INVISIBLE);
        r2.setVisibility(View.INVISIBLE);

        String language=sharedPref.getString("lng","en");
        switch (language){
            case "en":
                langue.setText(getResources().getString(R.string.enh));
                break;
            case "fr":
                langue.setText(getResources().getString(R.string.frh));
                break;
            case "ar":
                langue.setText(getResources().getString(R.string.arh));
                break;
            default:
                langue.setText(getResources().getString(R.string.enh));
                break;
        }
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(hasFocus){
            r0.setVisibility(View.VISIBLE);
            r0.startAnimation(AnimationUtils.loadAnimation(Home.this,
                    R.anim.my_anim0));
            r1.setVisibility(View.VISIBLE);
            r1.startAnimation(AnimationUtils.loadAnimation(Home.this,
                    R.anim.my_anim1));
            r2.setVisibility(View.VISIBLE);
            r2.startAnimation(AnimationUtils.loadAnimation(Home.this,
                    R.anim.my_anim2 ));
        }
    }
    boolean isEmpty(MaterialEditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
    public void login(View view) {
        String surname_S=usr.getText().toString();
        String pass_S=pass.getText().toString();
        if (isEmpty(usr)) {
            usr.setError(getString(R.string.Username_is_empty));
        }else if(pass_S.equals("")) {
            pass.setError(getString(R.string.Password_is_empty));
        }else{

                switch (type){
                    case 0:
                        Params="User_Name="+surname_S+"&Password="+pass_S+"&Type="+USER;
                        UserInfo US = new UserInfo(Params);
                        US.execute();
                        break;
                    case 1:
                        Params="User_Name="+surname_S+"&Password="+pass_S+"&Type="+DIRECTEUR;
                        DirecteurInfo DI = new DirecteurInfo(Params);
                        DI.execute();
                        break;
                    case 2:
                        Params="User_Name="+surname_S+"&Password="+pass_S+"&Type="+EMPLOYE;
                        TravailleurInfo Tr = new TravailleurInfo(Params);
                        Tr.execute();
                        break;
                    default:
                        break;
            }
    }}



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }


    public class DirecteurInfo extends GetJsonDirecteurInfo {

        public DirecteurInfo(String Params) {
            super(Params,getApplicationContext());
        }

        public void execute() {
            ProcessData data = new ProcessData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/Login.php");
        }

        public class ProcessData extends DownloadJsonData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce = WebData;
                if(!responce.equals("Error connecting to server")){
                        if(!responce.equals("Sorry invalide acount Type !\n")){
                            if(!responce.equals("User Not Found !\n")){
                                if(!responce.equals("Please try againe later !\n")){
                                    mDirecteur=getmPerssone();
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.welcome)+" " + mDirecteur.get(0).getName() + " " + mDirecteur.get(0).getPrenom(), Toast.LENGTH_SHORT).show();

                                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                                    sharedPref.edit().putString("IDPDirecteur", mDirecteur.get(0).getIDPersson()).apply();

                                    sharedPref.edit().putString("username", mDirecteur.get(0).getUsername()).apply();

                                    sharedPref.edit().putString("name", mDirecteur.get(0).getName()).apply();

                                    sharedPref.edit().putString("prenom", mDirecteur.get(0).getPrenom()).apply();

                                    sharedPref.edit().putString("email", mDirecteur.get(0).getEmail()).apply();

                                    sharedPref.edit().putString("IDDiplome_Reconnaisance",mDirecteur.get(0).getIDContratPharmacie()).apply();

                                    sharedPref.edit().putString("First_Log","1").apply();

                                    startActivity(new Intent(Home.this, PharmasistNav.class));
                                    finish();
                                }else{

                                    Toast.makeText(getApplicationContext(), R.string.your_acc_willbe_activated,Toast.LENGTH_LONG).show();
                                }
                            }else{
                                usr.setError(getString(R.string.user_not_found));
                                Toast.makeText(getApplicationContext(), R.string.user_not_found,Toast.LENGTH_LONG).show();
                            }
                        }else{
                            usr.setError(getString(R.string.user_not_found));
                            Toast.makeText(getApplicationContext(),R.string.user_not_found,Toast.LENGTH_LONG).show();
                        }
                }else{
                    Toast.makeText(getApplicationContext(), R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    public class UserInfo extends GetJsonUserInfo {

        public UserInfo(String Params) {
            super(Params);
        }

        public void execute() {
            ProcessData data = new ProcessData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/Login.php");
        }

        public class ProcessData extends DownloadJsonData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce = WebData;
                if(!responce.equals("Error connecting to server")){
                    if(!responce.equals("Sorry invalide acount Type !\n")){
                        if(!responce.equals("User Not Found !\n")){
                                mUser=getmPerssone();
                                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                                sharedPref.edit().putString("IDUser", mUser.get(0).getIDPersson()).apply();

                                sharedPref.edit().putString("username", mUser.get(0).getUsername()).apply();

                                sharedPref.edit().putString("name", mUser.get(0).getName()).apply();

                                sharedPref.edit().putString("prenom", mUser.get(0).getPrenom()).apply();

                                sharedPref.edit().putString("email", mUser.get(0).getEmail()).apply();

                                sharedPref.edit().putString("Date_Naissance",mUser.get(0).getDate_Naissance()).apply();

                                sharedPref.edit().putString("Countery",mUser.get(0).getCountery()).apply();

                                sharedPref.edit().putString("First_Log","1").apply();

                            Toast.makeText(getApplicationContext(),  getResources().getString(R.string.welcome)+" " + mUser.get(0).getName() + " " + mUser.get(0).getPrenom(), Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(Home.this, UserNav.class));
                            finish();
                        }else{
                            usr.setError(getString(R.string.user_not_found));
                            Toast.makeText(getApplicationContext(),R.string.user_not_found,Toast.LENGTH_LONG).show();
                        }
                    }else{
                        usr.setError(getString(R.string.user_not_found));
                        Toast.makeText(getApplicationContext(),R.string.user_not_found,Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        }}

    public class TravailleurInfo extends GetJsonTravailleurLog {

        public TravailleurInfo(String Params) {
            super(Params);
        }

        public void execute() {
            ProcessData data = new ProcessData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/Login.php");
        }

        public class ProcessData extends DownloadJsonData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce = WebData;
                if(!responce.equals("Error connecting to server")){
                    if(!responce.equals("Sorry invalide acount Type !\n")){
                        if(!responce.equals("User Not Found !\n")){
                            mTravailleur=getmPerssone();
                            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                            sharedPref.edit().putString("IDTravialleur", mTravailleur.get(0).getIDPersson()).apply();

                            sharedPref.edit().putString("username", mTravailleur.get(0).getUsername()).apply();

                            sharedPref.edit().putString("name", mTravailleur.get(0).getName()).apply();

                            sharedPref.edit().putString("prenom", mTravailleur.get(0).getPrenom()).apply();

                            sharedPref.edit().putString("email", mTravailleur.get(0).getEmail()).apply();

                            sharedPref.edit().putString("Numero_Telephone",mTravailleur.get(0).getTelephone()).apply();

                            sharedPref.edit().putString("Address",mTravailleur.get(0).getAddress()).apply();

                            sharedPref.edit().putString("IDDiplome_Reconnaisance",mTravailleur.get(0).getIDDiplome_Reconnaisance()).apply();

                            sharedPref.edit().putString("IDPDirecteur", mTravailleur.get(0).getIDDirecteur()).apply();

                            sharedPref.edit().putString("First_Log","1").apply();

                            Toast.makeText(getApplicationContext(),  getResources().getString(R.string.welcome)+" " + mTravailleur.get(0).getName() + " " + mTravailleur.get(0).getPrenom(), Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(Home.this, EmployeeNav.class));
                            finish();
                        }else{
                            usr.setError(getString(R.string.user_not_found));
                            Toast.makeText(getApplicationContext(),R.string.user_not_found,Toast.LENGTH_LONG).show();
                        }
                    }else{
                        usr.setError(getString(R.string.user_not_found));
                        Toast.makeText(getApplicationContext(),R.string.user_not_found,Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        }}



    public void CreatAccount(View view) {
        switch (type){
            case 0:
                startActivity(new Intent(Home.this, NewUser_Account.class));
                break;
            case 1:
                startActivity(new Intent(Home.this, NewPharmacist_Account.class));
                break;
            case 2:
                startActivity(new Intent(Home.this, NewEmployee_Account.class));
                break;
            default:
                break;
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.r0:
                if (checked){type=0;
                    sharedPref.edit().putInt("typeFromLogin", type).commit();
                    create.setText(getResources().getString(R.string.acc_user));}
                break;
            case R.id.r1:
                if (checked){type=1;
                    sharedPref.edit().putInt("typeFromLogin", type).commit();
                    create.setText(getResources().getString(R.string.acc_phrma));}
                break;
            case R.id.r2:
                if (checked){type=2;
                    sharedPref.edit().putInt("typeFromLogin",type).commit();
                    create.setText(getResources().getString(R.string.acc_emp));}
                break;

        }

    }


//////////////////////////// Option menu ////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);return true;}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);}


}
