package com.srdeveloppement.atelier.mypharmacy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.srdeveloppement.atelier.mypharmacy.Data.GetData;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

public class NewEmployee_Account extends AppCompatActivity  implements  TimePickerDialog.OnTimeSetListener,
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener
      {
          MaterialEditText certificationID,name,surname,Username,Password,ConfirmPassword,Email,HomeAdress,StoreCode,PhoneNumber;
          String name_S,surname_S,Username_S,Password_S,ConfirmPassword_S,Email_S, HomeAdress_S,StoreCode_S,PhoneNumber_S;
          String Gender="Male" ;
          String responce;
          String certification;
          Button confirm;
          RadioGroup radioGender;
          RadioButton r_male,r_femele;
          @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_employee_account_app_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.accoun_toolbar);
        setSupportActionBar(toolbar);


              name = (MaterialEditText)findViewById(R.id.Name);
              certificationID = (MaterialEditText)findViewById(R.id.certification);
              surname = (MaterialEditText)findViewById(R.id.Surname);
              Username = (MaterialEditText)findViewById(R.id.User_et);
              Password = (MaterialEditText)findViewById(R.id.Password_et);
              ConfirmPassword = (MaterialEditText)findViewById(R.id.conf_Password);
              Email = (MaterialEditText)findViewById(R.id.email);

              StoreCode = (MaterialEditText)findViewById(R.id.stor_id);
              HomeAdress = (MaterialEditText)findViewById(R.id.home_address);
              PhoneNumber = (MaterialEditText)findViewById(R.id.phone);
              confirm = (Button)findViewById(R.id.createAcc);
              radioGender =(RadioGroup)findViewById(R.id.sexe);
              r_male=(RadioButton)findViewById(R.id.male);
              r_male.setChecked(true);
              r_femele=(RadioButton)findViewById(R.id.female);




              confirm.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if (isEmpty(certificationID)) {
                          certificationID.setError("Enter certification");
                          Toast.makeText(v.getContext(), R.string.name_is_empty, Toast.LENGTH_LONG).show();
                      } else if (isEmpty(name)) {
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
                      }else if (isEmpty(HomeAdress)) {
                          HomeAdress.setError(getString(R.string.HomeAdress_is_empty));
                          Toast.makeText(v.getContext(), R.string.HomeAdress_is_empty, Toast.LENGTH_LONG).show();

                      } else if (isEmpty(PhoneNumber)) {
                          PhoneNumber.setError(getString(R.string.Phone_number_is_empty));
                          Toast.makeText(v.getContext(), R.string.Phone_number_is_empty, Toast.LENGTH_LONG).show();
                      }else{
                          name_S=name.getText().toString();
                          surname_S=surname.getText().toString();
                          certification=certificationID.getText().toString();
                          Username_S=Username.getText().toString();
                          Email_S=Email.getText().toString();
                          HomeAdress_S=HomeAdress.getText().toString();
                          PhoneNumber_S=PhoneNumber.getText().toString();
                          Password_S=Password.getText().toString();
                          ConfirmPassword_S=ConfirmPassword.getText().toString();
                          if(ConfirmPassword_S.equals(Password_S)&& !Password_S.trim().equals("")){
                              String Params="User_Name="+Username_S+"&Password="+Password_S+"&Nom="+name_S+"&Prenom="+surname_S
                                      +"&Email="+Email_S+"&gender="+Gender+"&Address="+HomeAdress_S+"&Numero_Telephone="+PhoneNumber_S
                                      +"&IDDiplome_Reconnaisance="+certification;
                              createTRUserAcount tr = new createTRUserAcount(Params);
                              tr.execute();
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
          //check if eddit text is not empty
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
          public class createTRUserAcount extends GetData {

              public createTRUserAcount(String Params) {
                  super(Params);
              }

              public void execute() {
                  ProcessData data = new ProcessData();
                  data.execute(GetData.LOCALHOST+"Pharmacie_Project/TR_NewAcount.php");
              }
              public class ProcessData extends DownloadData{
                  @Override
                  protected void onPostExecute(String WebData) {
                      super.onPostExecute(WebData);
                      responce=WebData;
                      if(!responce.equals("Error connecting to server")){
                          if(!responce.equals("you allredy have an acount\n\n")){
                              if(responce.equals("TRCS\n\n")){
                                  Toast.makeText(getApplicationContext(), getString(R.string.account_created_succ), Toast.LENGTH_LONG).show();
                                  finish();
                              }else{
                                  if(responce.equals("User Name alrredy used!\n\n")){
                                      Username.setError(getString(R.string.user_exist));
                                  }else if(responce.equals("Email already used!\n\n")){
                                      Email.setError(getString(R.string.email_used));
                                  }
                              } // executi w jarenb cree employee
                          }else{
                              Toast.makeText(getApplicationContext(), R.string.already_cer_emp,Toast.LENGTH_SHORT).show();
                          }
                      } // c bn slo7 ? oui cbon ani nhat fel al dossier li rah negravihe tebe3ni
                      // asma3 twelah dir kech ghota wela tmedlou memoire gdima wela hhhh chof m3aya 
                      else{
                          Toast.makeText(getApplicationContext(),R.string.SERVER_ERROR,Toast.LENGTH_SHORT).show();
                      }
                  }
              }
          }

      }
