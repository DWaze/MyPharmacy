package com.srdeveloppement.atelier.mypharmacy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.srdeveloppement.atelier.mypharmacy.Data.GetData;
import com.srdeveloppement.atelier.mypharmacy.Pharmacist.myPharmacy.Dialog_timing;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

// chet hedak layout ta3 wra ma tsuprimi la pharmacy
//  suprrimi une pharmacy , w ajouti wa7da jdida w cliki 3la l fab
public class NewPharmacist_Account extends AppCompatActivity implements  TimePickerDialog.OnTimeSetListener,
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener
{
    public String Sunday_f,Monday_f,Tuesday_f,Wednesday_f,Thursday_f,Friday_f,Saturday_f;
    public String Sunday_t,Monday_t,Tuesday_t,Wednesday_t,Thursday_t,Friday_t,Saturday_t;
    boolean atLeastOneIsChecked=false;
    boolean cb_Sunday=false,cb_Monday=false,cb_Tuesday=false,cb_Wednesday=false,cb_Thursday=false,cb_Friday=false,cb_Saturday=false;
    ImageView image_done,image_cb;


    MaterialEditText name,surname,Username,Password,ConfirmPassword,Email, dateOfBirth,HomeAdress,StoreCode,PhoneNumber;
    String name_S,surname_S,Username_S,Password_S,ConfirmPassword_S,Email_S, dateOfBirth_S,HomeAdress_S,StoreCode_S,PhoneNumber_S;
    String responce;

    boolean evryThingAllright=false;
    double lat,lng;
    String PharmacyName,PharmacyAdress,Gender="Male" ;
    Button confirm;
    RadioGroup radioGender;
    RadioButton r_male,r_femele;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_pharmacist_account_app_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.accoun_toolbar);
        setSupportActionBar(toolbar);



        name = (MaterialEditText)findViewById(R.id.Name);
        surname = (MaterialEditText)findViewById(R.id.Surname);
        Username = (MaterialEditText)findViewById(R.id.User_et);
        Password = (MaterialEditText)findViewById(R.id.Password_et);
        ConfirmPassword = (MaterialEditText)findViewById(R.id.conf_Password);
        Email = (MaterialEditText)findViewById(R.id.email);
        //dateOfBirth = (MaterialEditText)findViewById(R.id.date_of_birth);
        StoreCode = (MaterialEditText)findViewById(R.id.stor_id);
        PhoneNumber = (MaterialEditText)findViewById(R.id.phone);

        image_done = (ImageView)findViewById(R.id.image_done);
        image_cb = (ImageView)findViewById(R.id.image_cb);

        confirm = (Button)findViewById(R.id.createAcc);
        radioGender =(RadioGroup)findViewById(R.id.radioGender);
        r_male=(RadioButton)findViewById(R.id.male);
        r_male.setChecked(true);
        r_femele=(RadioButton)findViewById(R.id.female);
//        dateOfBirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                Toast.makeText(getBaseContext(),
//                        ((EditText) v).getId() + " has focus - " + hasFocus,
//                        Toast.LENGTH_LONG).show();
//            }
//        });

    }
    //check if eddit text is not empty
    boolean isEmpty(MaterialEditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                PharmacyName=data.getStringExtra("PharmacyName");
                PharmacyAdress=data.getStringExtra("PharmacyAdress");
                evryThingAllright=data.getBooleanExtra("evryThingAllright",false);
                lat=data.getDoubleExtra("lat", 0);
                lng=data.getDoubleExtra("lng",0);
            }
        }// bien dok houwa li takhdem bih ki tjib l variable hedak li gotlk ok osbor d9i9a

        if (requestCode == 2) {
            if(resultCode ==  Activity.RESULT_OK){
                //TODO: values of workin time per day as a string
                Sunday_f=data.getStringExtra("Sunday_f");
                Sunday_t=data.getStringExtra("Sunday_t");
                Monday_f=data.getStringExtra("Monday_f");
                Monday_t=data.getStringExtra("Monday_t");
                Tuesday_f=data.getStringExtra("Tuesday_f");
                Tuesday_t=data.getStringExtra("Tuesday_t");
                Wednesday_f=data.getStringExtra("Wednesday_f");
                Wednesday_t=data.getStringExtra("Wednesday_t");
                Thursday_f=data.getStringExtra("Thursday_f");
                Thursday_t=data.getStringExtra("Thursday_t");
                Friday_f=data.getStringExtra("Friday_f");
                Friday_t=data.getStringExtra("Friday_t");
                Saturday_f=data.getStringExtra("Saturday_f");
                Saturday_t=data.getStringExtra("Saturday_t");

                //TODO: boolean of working days (the CheckBox)
                cb_Sunday=data.getBooleanExtra("cb_SundayB", false);
                cb_Monday=data.getBooleanExtra("cb_MondayB", false);
                cb_Tuesday=data.getBooleanExtra("cb_TuesdayB", false);
                cb_Wednesday=data.getBooleanExtra("cb_WednesdayB", false);
                cb_Thursday =data.getBooleanExtra("cb_ThursdayB", false);
                cb_Friday=data.getBooleanExtra("cb_FridayB", false);
                cb_Saturday=data.getBooleanExtra("cb_SaturdayB", false);
                atLeastOneIsChecked=data.getBooleanExtra("atLeastOneIsChecked",false);
            }
        }
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.male:
                if (checked){
                    Gender="Male"; }
                break;
            case R.id.female:
                if (checked){
                    Gender="Female";}
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        TimePickerDialog tpd = (TimePickerDialog) getFragmentManager().findFragmentByTag("Timepickerdialog");
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = (com.wdullaer.materialdatetimepicker.date.DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");

        if(tpd != null) tpd.setOnTimeSetListener(this);
        if(dpd != null) dpd.setOnDateSetListener(this);

        /////////////////////////////////////////////////////



        if(evryThingAllright==true){
            image_done.setVisibility(View.VISIBLE);
        }
        if(atLeastOneIsChecked==true){
            image_cb.setVisibility(View.VISIBLE);
        }
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(name)) {
                    name.setError(getString(R.string.name_is_empty));
                    Toast.makeText(v.getContext(), R.string.name_is_empty, Toast.LENGTH_LONG).show();
                } else if (isEmpty(surname)) {
                    surname.setError(getString(R.string.Surname_is_empty));
                    Toast.makeText(v.getContext(), R.string.Surname_is_empty, Toast.LENGTH_LONG).show();
                } else if (isEmpty(Username)) {
                    Username.setError(getString(R.string.Username_is_empty));
                    Toast.makeText(v.getContext(), R.string.Username_is_empty, Toast.LENGTH_LONG).show();
                }else if (isEmpty(Email)) {
                    Email.setError(getString(R.string.Email_is_empty));
                    Toast.makeText(v.getContext(), R.string.Email_is_empty, Toast.LENGTH_LONG).show();
                } else if (isEmpty(StoreCode)) {
                    StoreCode.setError(getString(R.string.storeCode_is_empty));
                    Toast.makeText(v.getContext(), R.string.storeCode_is_empty, Toast.LENGTH_LONG).show();
                } else if (isEmpty(PhoneNumber)) {
                    PhoneNumber.setError(getString(R.string.Phone_number_is_empty));
                    Toast.makeText(v.getContext(), R.string.Phone_number_is_empty, Toast.LENGTH_LONG).show();
                }else if(evryThingAllright==false){
                    startActivityForResult(new Intent(NewPharmacist_Account.this, Set_phatmacy_dialog.class), 1);
                }else if(atLeastOneIsChecked==false){
                    startActivityForResult(new Intent(NewPharmacist_Account.this, Dialog_timing.class),2);
                }else{
                    name_S=name.getText().toString();
                    surname_S=surname.getText().toString();
                    Username_S=Username.getText().toString();
                    Email_S=Email.getText().toString();
                    //dateOfBirth_S=dateOfBirth.getText().toString();
                   // HomeAdress_S=HomeAdress.getText().toString();
                    StoreCode_S=StoreCode.getText().toString();
                    PhoneNumber_S=PhoneNumber.getText().toString();
                    Password_S=Password.getText().toString();
                    ConfirmPassword_S=ConfirmPassword.getText().toString();
                    if(ConfirmPassword_S.equals(Password_S)&& !Password_S.trim().equals("")){

                        String Params="Nom_Pharmacie="+PharmacyName+"&Address_Pharmacie="+PharmacyAdress+"&Latitude="+lat+
                                "&Langitude="+lng+"&User_Name="+Username_S+"&Password="+Password_S+"&Nom="+name_S+
                                "&Prenom="+surname_S+"&Email="+Email_S+"&IDContratPharmacie="+StoreCode_S+
                                "&DirecteurPhone="+PhoneNumber_S+"&gender="+Gender;
                        if(cb_Sunday==true){
                            Params=Params+"&Sunday_f="+Sunday_f+"&Sunday_t="+Sunday_t+"&cb_Sunday=true";
                        }else{
                            Params=Params+"&Sunday_f=00:00"+"&Sunday_t=00:00"+"&cb_Sunday=false";
                        }

                        if(cb_Monday==true){
                            Params=Params+"&Monday_f="+Monday_f+"&Monday_t="+Monday_t+"&cb_Monday=true";
                        }else{
                            Params=Params+"&Monday_f=00:00"+"&Monday_t=00:00"+"&cb_Monday=false";
                        }

                        if(cb_Tuesday==true){
                            Params=Params+"&Tuesday_f="+Tuesday_f+"&Tuesday_t="+Tuesday_t+"&cb_Tuesday=true";
                        }else{
                            Params=Params+"&Tuesday_f=00:00"+"&Tuesday_t=00:00"+"&cb_Tuesday=false";
                        }

                        if(cb_Wednesday==true){
                            Params=Params+"&Wednesday_f="+Wednesday_f+"&Wednesday_t="+Wednesday_t+"&cb_Wednesday=true";
                        }else{
                            Params=Params+"&Wednesday_f=00:00"+"&Wednesday_t=00:00"+"&cb_Wednesday=false";
                        }

                        if(cb_Thursday==true){
                            Params=Params+"&Thursday_f="+Thursday_f+"&Thursday_t="+Thursday_t+"&cb_Thursday=true";
                        }else{
                            Params=Params+"&Thursday_f=00:00"+"&Thursday_t=00:00"+"&cb_Thursday=false";
                        }

                        if(cb_Friday==true){
                            Params=Params+"&Friday_f="+Friday_f+"&Friday_t="+Friday_t+"&cb_Friday=true";
                        }else{
                            Params=Params+"&Friday_f=00:00"+"&Friday_t=00:00"+"&cb_Friday=false";
                        }

                        if(cb_Saturday==true){
                            Params=Params+"&Saturday_f="+Saturday_f+"&Saturday_t="+Saturday_t+"&cb_Saturday=true";
                        }else{
                            Params=Params+"&Saturday_f=00:00"+"&Saturday_t=00:00"+"&cb_Saturday=false";
                        }
                        createPDAcount cp = new createPDAcount(Params);
                        cp.execute();

                        Toast.makeText(v.getContext(), getString(R.string.account_created_succ)+Gender, Toast.LENGTH_LONG).show();

                    }else{
                        if(Password_S.trim().equals("")) {
                            Password.setError(getString(R.string.Password_is_empty));
                        }else{
                            ConfirmPassword.setText("");
                            ConfirmPassword.setError(getString(R.string.Confirm_password_error));
                            Toast.makeText(v.getContext(),R.string.Confirm_password_error, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
        //Toast.makeText(this, PharmacyName + " \n " + PharmacyAdress +"\n"+lat+"\n"+lng, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

    }

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = +dayOfMonth+" / "+(++monthOfYear)+" / "+year;
        dateOfBirth.setText(date);
    }

    public void add_pharmacy_onMap(View view) {
        startActivityForResult(new Intent(NewPharmacist_Account.this, Set_phatmacy_dialog.class), 1);
    }
    public void add_timing(View view) {
        startActivityForResult(new Intent(NewPharmacist_Account.this, Dialog_timing.class),2);
    }


    //////////////////////////// Option menu ////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);return true;}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);}

    public class createPDAcount extends GetData {

        public createPDAcount(String Params) {
            super(Params);
        }

        public void execute() {
            ProcessData data = new ProcessData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/PD_NewAcount.php");
        }
        public class ProcessData extends DownloadData{
            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce=WebData;
                if(!responce.equals("Error connecting to server")){
                    if(responce.equals("Pharmacie created succefuly!\n\n")){
                        Toast.makeText(getApplicationContext(), R.string.acc_cret_succ,Toast.LENGTH_LONG).show();
                        finish();
                    }else{

                        if(responce.equals("User Name alrredy used!\n\n")){
                            Username.setError(getString(R.string.user_exist));
                        }else if(responce.equals("Email already used!\n\n")){
                            Email.setError(getString(R.string.email_used));
                        }else if(!responce.equals("invalide Pharmacy Contract!\n\n")){
                                Toast.makeText(getApplicationContext(), R.string.pharm_succ,Toast.LENGTH_SHORT).show();
                            }else{
                                StoreCode.setError(getResources().getString(R.string.invalid_contact));
                                Toast.makeText(getApplicationContext(), R.string.invalid_contact,Toast.LENGTH_SHORT).show();
                            }

                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),R.string.SERVER_ERROR,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}