package com.srdeveloppement.atelier.mypharmacy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.srdeveloppement.atelier.mypharmacy.Data.GetData;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class NewUser_Account extends AppCompatActivity  implements  com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener,
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener
      {
          MaterialEditText birth_place,name,surname,Username,Password,ConfirmPassword,Email, dateOfBirth,HomeAdress,StoreCode,PhoneNumber;
          String name_S,surname_S,Username_S,Password_S,ConfirmPassword_S,Email_S, dateOfBirth_S,HomeAdress_S,PhoneNumber_S;
          String Gender="Male" ;
          Button confirm;
          String responce;
          String countryS="Algeria";
          Spinner country;
          ArrayAdapter<CharSequence> CountryAdapter;
          RadioGroup radioGender;
          RadioButton r_male,r_femele;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user_account_app_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.accoun_toolbar);
        setSupportActionBar(toolbar);


        name = (MaterialEditText)findViewById(R.id.Name);
        surname = (MaterialEditText)findViewById(R.id.Surname);
        Username = (MaterialEditText)findViewById(R.id.User_et);
        Password = (MaterialEditText)findViewById(R.id.Password_et);
        ConfirmPassword = (MaterialEditText)findViewById(R.id.conf_Password);
        Email = (MaterialEditText)findViewById(R.id.email);
        dateOfBirth = (MaterialEditText)findViewById(R.id.date_of_birth);
        confirm = (Button)findViewById(R.id.createAcc);
        radioGender =(RadioGroup)findViewById(R.id.sexe);
        r_male=(RadioButton)findViewById(R.id.male);
        r_male.setChecked(true);
        country = (Spinner)findViewById(R.id.country_spinner);
        r_femele=(RadioButton)findViewById(R.id.female);

        CountryAdapter = ArrayAdapter.createFromResource(NewUser_Account.this, R.array.countries_array, R.layout.spinner_item_dark_text);
        CountryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(CountryAdapter);
        int spinnerPosition = CountryAdapter.getPosition(countryS);
        country.setSelection(spinnerPosition);
        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countryS = parent.getItemAtPosition(position).toString();
                }
            @Override public void onNothingSelected(AdapterView<?> parent) {}});

        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Calendar now = Calendar.getInstance();
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        NewUser_Account.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                Calendar MinDate = Calendar.getInstance();
                MinDate.set(1900, Calendar.JANUARY, 1);
                Calendar MaxDate = Calendar.getInstance();
                MaxDate.set(now.get(Calendar.YEAR)-10,now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));
                dpd.setMinDate(MinDate);
                dpd.setMaxDate(MaxDate);
                dpd.setTitle(getResources().getString(R.string.date_of_birth));
                dpd.show(getFragmentManager(), "Datepickerdialog");

            }
        });
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
                } else if (isEmpty(Email)) {
                    Email.setError(getString(R.string.Email_is_empty));
                    Toast.makeText(v.getContext(), R.string.Email_is_empty, Toast.LENGTH_LONG).show();
                } else if (isEmpty(dateOfBirth)) {
                    dateOfBirth.setError(getString(R.string.dateofbirth_is_empty));
                    Toast.makeText(v.getContext(), R.string.dateofbirth_is_empty, Toast.LENGTH_LONG).show();
                } else{
                    name_S=name.getText().toString();
                    surname_S=surname.getText().toString();
                    Username_S=Username.getText().toString();
                    Email_S=Email.getText().toString();
                    dateOfBirth_S=dateOfBirth.getText().toString();
                    Password_S=Password.getText().toString();

                    ConfirmPassword_S=ConfirmPassword.getText().toString();
                    if(ConfirmPassword_S.equals(Password_S)&& !Password_S.trim().equals("")){
                        String Params="User_Name="+Username_S+"&Password="+Password_S+"&Nom="+name_S+"&Prenom="+surname_S+
                                "&Email="+Email_S+"&Date_Naissance="+dateOfBirth_S+"&gender="+Gender+"&Countery="+ countryS;
                        createUserAcount cu = new createUserAcount(Params);
                        cu.execute();
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
    }
          boolean isEmpty(MaterialEditText etText) {
              return etText.getText().toString().trim().length() == 0;
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
          }

          @Override
          public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

          }

          @Override
          public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
              String date = +dayOfMonth+" / "+(++monthOfYear)+" / "+year;
              dateOfBirth.setText(date);
          }
    //////////////////////////// Option menu ////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);return true;}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);}

    public void showDate(View view) {

    }

          public class createUserAcount extends GetData {

              public createUserAcount(String Params) {
                  super(Params);
              }

              public void execute() {
                  ProcessData data = new ProcessData();
                  data.execute(GetData.LOCALHOST+"Pharmacie_Project/New_User_Acount.php");
              }
              public class ProcessData extends DownloadData{
                  @Override
                  protected void onPostExecute(String WebData) {
                      super.onPostExecute(WebData);
                      responce=WebData;
                      if(!responce.equals("Error connecting to server")){
                          if(responce.equals("user added succefuly !\n\n")){
                              Toast.makeText(getApplicationContext(), getString(R.string.account_created_succ), Toast.LENGTH_LONG).show();
                              finish();
                          }else{
                              if(responce.equals("User Name already used!\n\n")){
                                  Username.setError(getString(R.string.user_exist));
                              }else if(responce.equals("Email already used!\n\n")){
                                  Email.setError(getString(R.string.email_used));
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
