package com.srdeveloppement.atelier.mypharmacy;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.srdeveloppement.atelier.mypharmacy.Data.GetData;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;


public class MyProfile extends Fragment implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {


    public MyProfile() {
        // Required empty public constructor
    }

    int typeFromLogin;
    SharedPreferences sharedPref;
    ImageView imageProfile;
    String IDPerssone;
    String responce;
    RelativeLayout Container,changePassword_container, home_container, country_container, gender_container, phone_container, dateOfBirth_container;
    MaterialEditText genderEdit, countryEdit;
    MaterialEditText nameEdit, surnameEdit, old_password, Username, Password, ConfirmPassword, emailEdit, dateOfBirth, HomeAdress, StoreCode, PhoneNumber;
    //  public String Name, Surname, User_name, country_name, gender_name;
    TextView nav_user_name,nav_email;
    Spinner country, gender;
    FloatingActionButton editFAB,resetInfo;
    ArrayAdapter<CharSequence> CountryAdapter,genderAdapter;
    Button Button_new_pass;
    int spinnerPosition ,genderPosition;
    String currentPass,NameSTR,SurnameSTR,UsernameSTR, Original_PasswordSTR,NewPasswordSTR,ConfPasswordSTR,EmailSTR,HomeSTR,DateofbirthSTR,PhoneSTR, CountrySTR, GenderSTR;
    String NameSTR0,SurnameSTR0,UsernameSTR0, Original_PasswordSTR0,NewPasswordSTR0,ConfPasswordSTR0,EmailSTR0,HomeSTR0,DateofbirthSTR0,PhoneSTR0, CountrySTR0, GenderSTR0;

    boolean isNew=true, isSave =false, everyThingAllright=false,existOnDataBase=true, passwordInEditMode=false,savedBefor=false;
    String GenderArray[],GenderArrayL[];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        imageProfile = (ImageView) view.findViewById(R.id.imageProfile);
        Container = (RelativeLayout) view.findViewById(R.id.Container);

        ///////////////////////////////////////////////////////////////////////
        home_container = (RelativeLayout) view.findViewById(R.id.home_container);
        country_container = (RelativeLayout) view.findViewById(R.id.country_container);
        phone_container = (RelativeLayout) view.findViewById(R.id.phone_container);
        dateOfBirth_container = (RelativeLayout) view.findViewById(R.id.dateOfBirth_container);
        changePassword_container = (RelativeLayout) view.findViewById(R.id.changePassword_container);
        Button_new_pass = (Button) view.findViewById(R.id.Button_new_pass);
////////////////////////////////////////////////////////////////////////////////
        nav_user_name = (TextView) view.findViewById(R.id.nav_user_name);
        nav_email = (TextView) view.findViewById(R.id.nav_email);
        emailEdit = (MaterialEditText) view.findViewById(R.id.email);
        nameEdit = (MaterialEditText) view.findViewById(R.id.Surname);
        surnameEdit = (MaterialEditText) view.findViewById(R.id.Name);
        Username = (MaterialEditText) view.findViewById(R.id.User_et);
        old_password = (MaterialEditText) view.findViewById(R.id.old_password);
        Password = (MaterialEditText) view.findViewById(R.id.Password_et);
        ConfirmPassword = (MaterialEditText) view.findViewById(R.id.conf_Password);
        genderEdit = (MaterialEditText) view.findViewById(R.id.genderEdit);
        countryEdit = (MaterialEditText) view.findViewById(R.id.countryEdit);
        ///
        country = (Spinner) view.findViewById(R.id.country_spinner);
        gender = (Spinner) view.findViewById(R.id.gender_spinner);
        //adapters
        CountryAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.countries_array, R.layout.spinner_item_dark_text);
        CountryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.gender_array, R.layout.spinner_item_dark_text);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dateOfBirth = (MaterialEditText) view.findViewById(R.id.date_of_birth);
        HomeAdress = (MaterialEditText) view.findViewById(R.id.home_address);
        PhoneNumber = (MaterialEditText) view.findViewById(R.id.phone);

        editFAB = (FloatingActionButton) view.findViewById(R.id.editMaInfo);
        resetInfo = (FloatingActionButton) view.findViewById(R.id.resetInfo);


        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        typeFromLogin = sharedPref.getInt("typeFromLogin", 0);

        switch (typeFromLogin){
            case 0:getProfileData(0);break;
            case 1: getProfileData(1);break;
            case 2: getProfileData(2);break;
        }
        //////////////////
        country.setEnabled(false);
        country.setClickable(false);
        country.setAdapter(CountryAdapter);

        gender.setEnabled(false);
        gender.setClickable(false);
        dateOfBirth.setClickable(false);
        gender.setAdapter(genderAdapter);
        ////////////////////////////////


        spinnerPosition = CountryAdapter.getPosition(CountrySTR);

        int g=0;
        GenderArrayL=getResources().getStringArray(R.array.gender_array);

        for(int i=0;i<GenderArrayL.length;i++){
            GenderArray=getResources().getStringArray(R.array.gender_array_OR);
            if(!GenderSTR.equals(GenderArray[i])){
                g++;
            }else{break;}
        }
        GenderSTR=GenderArrayL[g];
//TODO
        genderPosition = genderAdapter.getPosition(GenderSTR);

        country.setSelection(spinnerPosition);
        gender.setSelection(genderPosition);

        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CountrySTR = parent.getItemAtPosition(position).toString();
              }
            @Override public void onNothingSelected(AdapterView<?> parent) {}});
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GenderArray=getResources().getStringArray(R.array.gender_array_OR);
                GenderSTR = GenderArray[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });




        switch (typeFromLogin) {
            case 0:
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 0, 0);
                Container.setLayoutParams(params);
                /////////////////////////////////
                showItem(phone_container, false);
                showItem(home_container, false);
                imageProfile.setImageResource(R.drawable.usr_nv);
                break;
            case 1:
                showItem(home_container, false);
                showItem(country_container, false);
                showItem(dateOfBirth_container, false);
                imageProfile.setImageResource(R.drawable.ps_nv);
                break;
            case 2:
                showItem(country_container, false);
                showItem(dateOfBirth_container, false);
                imageProfile.setImageResource(R.drawable.tr_nv);
                break;
        }

        editFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSave ==false){
                    editFAB.setImageResource(R.drawable.ic_save);
                    resetInfo.setVisibility(View.VISIBLE);
                    showUnderline(true);
                    country.setEnabled(true);
                    country.setClickable(true);
                    country.setAdapter(CountryAdapter);
                    gender.setEnabled(true);
                    gender.setClickable(true);
                    dateOfBirth.setClickable(true);
                    gender.setAdapter(genderAdapter);
                    spinnerPosition = CountryAdapter.getPosition(CountrySTR);

                    int g=0;
                    GenderArrayL=getResources().getStringArray(R.array.gender_array);
                    for(int i=0;i<GenderArrayL.length;i++){
                        GenderArray=getResources().getStringArray(R.array.gender_array_OR);
                        if(!GenderSTR.equals(GenderArray[i])){
                            g++;
                        }else{break;}
                    }
                    GenderSTR=GenderArrayL[g];

                    genderPosition = genderAdapter.getPosition(GenderSTR);
                    country.setSelection(spinnerPosition);
                    gender.setSelection(genderPosition);
                    Button_new_pass.setVisibility(View.VISIBLE);
                    dateOfBirth.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar now = Calendar.getInstance();
                            com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                                    MyProfile.this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                            Calendar MinDate = Calendar.getInstance();
                            MinDate.set(1900, Calendar.JANUARY, 1);
                            Calendar MaxDate = Calendar.getInstance();
                            MaxDate.set(now.get(Calendar.YEAR) - 10, now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                            dpd.setMinDate(MinDate);
                            dpd.setMaxDate(MaxDate);
                            dpd.setTitle(getResources().getString(R.string.date_of_birth));
                            dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
                        }
                    });
                    Button_new_pass.setEnabled(true);
                    Button_new_pass.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(isNew==true){
                                old_password.setText("");
                                old_password.setEnabled(true);
                                old_password.setFocusableInTouchMode(true);
                                Button_new_pass.setText(getResources().getString(R.string.x));
                                changePassword_container.setVisibility(View.VISIBLE);
                                isNew=false;
                                passwordInEditMode=true;
                            }else{
                                Button_new_pass.setText(getResources().getString(R.string.newpss));
                                changePassword_container.setVisibility(View.GONE);
                                //showItem(changePassword_container, false);
                                Password.setText("");
                                ConfirmPassword.setText("");
                                old_password.setText(Original_PasswordSTR);
                                old_password.setFocusableInTouchMode(false);
                                old_password.setFocusable(false);
                                old_password.setEnabled(false);
                                //old_password.setNextFocusForwardId(R.id.email);
                                isNew=true;
                                passwordInEditMode=false;
                            }}});
                    isSave=true;
                }else{

                    currentPass= old_password.getText().toString();
                    NewPasswordSTR=Password.getText().toString();
                    ConfPasswordSTR=ConfirmPassword.getText().toString();
                    dateOfBirth.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    if (isEmpty(nameEdit)) {
                        everyThingAllright=false;
                        nameEdit.setError(getResources().getString(R.string.name_is_empty));
                        Toast.makeText(v.getContext(), R.string.name_is_empty, Toast.LENGTH_LONG).show();
                    } else if (isEmpty(surnameEdit)) {
                        everyThingAllright=false;
                        surnameEdit.setError(getResources().getString(R.string.Surname_is_empty));
                        Toast.makeText(v.getContext(), R.string.Surname_is_empty, Toast.LENGTH_LONG).show();
                    } else if (isEmpty(Username)) {
                        everyThingAllright=false;
                        Username.setError(getResources().getString(R.string.Username_is_empty));
                        Toast.makeText(v.getContext(), R.string.Username_is_empty, Toast.LENGTH_LONG).show();
                    }else if (isEmpty(emailEdit)) {
                        everyThingAllright=false;
                        emailEdit.setError(getResources().getString(R.string.Email_is_empty));
                        Toast.makeText(v.getContext(), R.string.Email_is_empty, Toast.LENGTH_LONG).show();
                    }else {

                        if(passwordInEditMode==true){
                            if(Original_PasswordSTR.equals(currentPass) && !currentPass.equals("")){

                                if(!ConfPasswordSTR.trim().equals("")  && !NewPasswordSTR.trim().equals("")){

                                    if(!ConfPasswordSTR.equals(NewPasswordSTR) ){
                                        everyThingAllright=false;
                                        ConfirmPassword.setText("");
                                        ConfirmPassword.setError(getResources().getString(R.string.Confirm_password_error));
                                        Toast.makeText(v.getContext(),R.string.Confirm_password_error, Toast.LENGTH_LONG).show();
                                    }else{
                                        Original_PasswordSTR=NewPasswordSTR;
                                        everyThingAllright=true;
                                    }
                                }else{
                                    everyThingAllright=false;
                                    Password.setText("");
                                    ConfirmPassword.setText("");
                                    Password.setError(getResources().getString(R.string.Confirm_password_error));
                                    ConfirmPassword.setError(getResources().getString(R.string.Confirm_password_error));
                                }
                            }else{
                                everyThingAllright=false;
                                old_password.setError(getResources().getString(R.string.wrong_pass));
                                Toast.makeText(v.getContext(),R.string.Confirm_password_error, Toast.LENGTH_LONG).show();
                            }
                        }else{
                            NewPasswordSTR=Original_PasswordSTR;
                            everyThingAllright=true;
                        }

                        switch (typeFromLogin){
                            case 0:
                                ;break;
                            case 1: if (isEmpty(PhoneNumber)) {
                                everyThingAllright=false;
                                PhoneNumber.setError(getString(R.string.Phone_number_is_empty));
                                Toast.makeText(v.getContext(), R.string.Phone_number_is_empty, Toast.LENGTH_LONG).show();
                            }else{
                                everyThingAllright=true;};break;
                            case 2: if (isEmpty(PhoneNumber)) {everyThingAllright=false;
                                PhoneNumber.setError(getString(R.string.Phone_number_is_empty));
                                Toast.makeText(v.getContext(), R.string.Phone_number_is_empty, Toast.LENGTH_LONG).show();
                            }else if(isEmpty(HomeAdress)){everyThingAllright=false;
                                HomeAdress.setError(getResources().getString(R.string.HomeAdress_is_empty));
                                Toast.makeText(v.getContext(), R.string.HomeAdress_is_empty, Toast.LENGTH_LONG).show();
                            };break;
                        }
                        if(everyThingAllright==true){
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            //TODO: D'nt forget to check the username on DATABASE check it here
                            UsernameSTR=Username.getText().toString();//TODO check with this one UsernameSTR   New Username


                            NameSTR=nameEdit.getText().toString(); // TODO UPDATE  New Name
                            SurnameSTR=surnameEdit.getText().toString(); // TODO UPDATE  New Surname
                            NewPasswordSTR=Original_PasswordSTR; // TODO UPDATE  New Password
                            EmailSTR=emailEdit.getText().toString(); // TODO UPDATE   New Email



                            GenderSTR0=GenderSTR;

                            GenderSTR=GenderSTR;  // TODO UPDATE    New Gender
                            String Para="User_Name="+UsernameSTR+"&Password="+NewPasswordSTR+"&Nom="+NameSTR+
                                    "&Prenom="+SurnameSTR+"&Email="+EmailSTR+"&gender="+GenderSTR;



                            //Todo  Send your link inside One of This Switches Depends on the UserType:
                            switch (typeFromLogin){
                                case 0:
                                    CountrySTR=CountrySTR; // TODO UPDATE  New Country
                                    IDPerssone=sharedPref.getString("IDUser","");
                                    DateofbirthSTR=dateOfBirth.getText().toString(); // TODO UPDATE  New Date Of Birth
                                    String Params=Para+"&Date_Naissance="+DateofbirthSTR+"&Countery="+CountrySTR+"&IDPerssone="+IDPerssone;
                                    SaveUserProfileInfo pro= new SaveUserProfileInfo(Params);
                                    pro.execute();
                                    break;
                                case 1:
                                    IDPerssone=sharedPref.getString("IDPDirecteur","");
                                    PhoneSTR=PhoneNumber.getText().toString();
                                    String ParamsDi=Para+"&DirecteurPhone="+PhoneSTR+"&IDPerssone="+IDPerssone;;
                                    SaveDirecteurProfileInfo proD= new SaveDirecteurProfileInfo(ParamsDi);
                                    proD.execute();
                                    break;
                                case 2:
                                    PhoneSTR=PhoneNumber.getText().toString(); // TODO UPDATE  New Phone
                                    HomeSTR=HomeAdress.getText().toString(); // TODO UPDATE  New Home Adress
                                    IDPerssone=sharedPref.getString("IDTravialleur","");
                                    String ParamsU = Para + "&Address=" + HomeSTR + "&Numero_Telephone=" + PhoneSTR + "&IDPerssone=" + IDPerssone;
                                    SaveTravailleurProfileInfo proU= new SaveTravailleurProfileInfo(ParamsU);
                                    proU.execute();
                                    break;
                            }







////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    }
                    }


                }
            }
        });
        resetInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(savedBefor==false ){
                    getOldData(typeFromLogin);
                    NewPasswordSTR0=Original_PasswordSTR0; }

                editFAB.setImageResource(R.drawable.ic_edit_white);
                Button_new_pass.setEnabled(false);
                showUnderline(false);
                country.setEnabled(false);
                country.setClickable(false);
                country.setAdapter(CountryAdapter);
                gender.setEnabled(false);
                gender.setClickable(false);
                dateOfBirth.setFocusableInTouchMode(false);
                gender.setAdapter(genderAdapter);
                spinnerPosition = CountryAdapter.getPosition(CountrySTR0);
               //todo sami

                int g=0;
                GenderArrayL=getResources().getStringArray(R.array.gender_array);
                for(int i=0;i<GenderArrayL.length;i++){
                    GenderArray=getResources().getStringArray(R.array.gender_array_OR);
                    if(!GenderSTR0.equals(GenderArray[i])){
                        g++;
                    }else{break;}
                }
                GenderSTR0=GenderArrayL[g];
                genderPosition = genderAdapter.getPosition(GenderSTR0);
                GenderSTR0=GenderArray[g];
                country.setSelection(spinnerPosition);
                gender.setSelection(genderPosition);
                old_password.setFocusableInTouchMode(false);
                old_password.setFocusable(false);
                old_password.setEnabled(false);
                Button_new_pass.setText(getResources().getString(R.string.newpss));
                Button_new_pass.setVisibility(View.GONE);
                changePassword_container.setVisibility(View.GONE);
                old_password.setUnderlineColor(getResources().getColor(R.color.mytransparent));
                resetInfo.setVisibility(View.GONE);

                emailEdit.setText(EmailSTR0);
                old_password.setText(NewPasswordSTR0);
                nameEdit.setText(NameSTR0);
                surnameEdit.setText(SurnameSTR0);
                Username.setText(UsernameSTR0);
                nav_email.setText(EmailSTR0);
                nav_user_name.setText(NameSTR0 + "  " + SurnameSTR0);
                if(typeFromLogin==0){
                    dateOfBirth.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            
                        }
                    });
                    dateOfBirth.setText(DateofbirthSTR0); // user
                }
                if(typeFromLogin==1){
                    PhoneNumber.setText(PhoneSTR0); // directeur / employee
                }
                if(typeFromLogin==2){
                    HomeAdress.setText(HomeSTR0); //Employee
                    PhoneNumber.setText(PhoneSTR0); // directeur / employee
                }
                isSave=false;
            }
        });

        return view;
    }
    boolean isEmpty(MaterialEditText etText) {return etText.getText().toString().trim().length() == 0;}
    void getProfileData(int type){

        NameSTR=getArguments().getString("NameSTR");
        SurnameSTR=getArguments().getString("SurnameSTR" );
        UsernameSTR= getArguments().getString("UsernameSTR" );
        Original_PasswordSTR = getArguments().getString("OldPasswordSTR");
        EmailSTR= getArguments().getString("EmailSTR" );
        CountrySTR=getArguments().getString("CountrySTR" );
        GenderSTR=getArguments().getString("GenderSTR" );



        emailEdit.setText(EmailSTR);
        nav_email.setText(EmailSTR);
        old_password.setText(Original_PasswordSTR);
        nav_user_name.setText(NameSTR+"  "+SurnameSTR);
        nameEdit.setText(NameSTR);
        surnameEdit.setText(SurnameSTR);
        Username.setText(UsernameSTR);
        if(type==0){
            DateofbirthSTR=getArguments().getString("DateofbirthSTR" ); // user
            dateOfBirth.setText(DateofbirthSTR); // user
        }
        if(type==1){
            PhoneSTR= getArguments().getString("PhoneSTR" );// directeur / employee
            PhoneNumber.setText(PhoneSTR); // directeur / employee
        }
        if(type==2){
            HomeSTR=getArguments().getString("HomeSTR" );//Employee
            HomeAdress.setText(HomeSTR); //Employee
            PhoneSTR= getArguments().getString("PhoneSTR" );// directeur / employee
            PhoneNumber.setText(PhoneSTR); // directeur / employee
        }
    }
    void getOldData(int type){

        NameSTR0=getArguments().getString("NameSTR");
        SurnameSTR0=getArguments().getString("SurnameSTR" );
        UsernameSTR0= getArguments().getString("UsernameSTR" );
        Original_PasswordSTR0 = getArguments().getString("OldPasswordSTR");
        EmailSTR0= getArguments().getString("EmailSTR" );
        CountrySTR0=getArguments().getString("CountrySTR" );
        GenderSTR0=getArguments().getString("GenderSTR" );

        if(type==0){
            DateofbirthSTR0=getArguments().getString("DateofbirthSTR" ); // user
        }
        if(type==1){
            PhoneSTR0= getArguments().getString("PhoneSTR" );// directeur / employee
        }
        if(type==2){
            HomeSTR0=getArguments().getString("HomeSTR" );//Employee
            PhoneSTR0= getArguments().getString("PhoneSTR" );// directeur / employee
        }
    }
    public void showUnderline(boolean show) {
        MaterialEditText[] edits = {emailEdit, nameEdit, surnameEdit, Username, dateOfBirth, HomeAdress, PhoneNumber, genderEdit, countryEdit};
        MaterialEditText[] focus = {emailEdit, nameEdit, surnameEdit, Username,  dateOfBirth ,Password, ConfirmPassword,  HomeAdress, PhoneNumber};
        if (show == true) {
            for (int i = 0; i < edits.length; i++) {edits[i].setUnderlineColor(getResources().getColor(R.color.colorAccent));}
            for (int i = 0; i < focus.length; i++) {focus[i].setFocusableInTouchMode(true);
                focus[i].setFocusable(true);
                }
            dateOfBirth.setClickable(true);
        } else {
            for (int i = 0; i < edits.length; i++) {edits[i].setUnderlineColor(getResources().getColor(R.color.mytransparent));}
            for (int i = 0; i < focus.length; i++) {focus[i].setFocusableInTouchMode(false);

                focus[i].setFocusable(false);}}
        dateOfBirth.setClickable(false);


    }

    public void showItem(RelativeLayout myItem, boolean isActivated) {
        if (isActivated == true) {
            myItem.setVisibility(View.VISIBLE);
        } else {
            myItem.setVisibility(View.GONE);
        }
    }

    public void onResume() {
        super.onResume();
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = (com.wdullaer.materialdatetimepicker.date.DatePickerDialog) getActivity().getFragmentManager().findFragmentByTag("Datepickerdialog");
        if (dpd != null) dpd.setOnDateSetListener(this);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = +dayOfMonth + " / " + (++monthOfYear) + " / " + year;
        dateOfBirth.setText(date);
    }

    public class SaveUserProfileInfo extends GetData {

        public SaveUserProfileInfo(String Params) {
            super(Params);
        }

        public void execute() {
            GetPharmacieData data = new GetPharmacieData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/Update_userProfile.php");
        }

        public class GetPharmacieData extends DownloadData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce = WebData;
                if (!responce.equals("Error connecting to server")){
                    if (!responce.equals("User Name alrredy used!\n")){
                       if(responce.equals("Email already used!\n")){
                            everyThingAllright=false;
                            emailEdit.setError(getString(R.string.email_used));
                        }else{
                           Toast.makeText(getContext(),R.string.ed_saved,Toast.LENGTH_SHORT).show();
                           //TODO Do not touch this:
                           UsernameSTR0=UsernameSTR;
                           NameSTR0=NameSTR;
                           SurnameSTR0=SurnameSTR;
                           NewPasswordSTR0=NewPasswordSTR;
                           EmailSTR0=EmailSTR;
                           CountrySTR0=CountrySTR;
                           DateofbirthSTR0=DateofbirthSTR;
                           PhoneSTR0=PhoneSTR;
                           HomeSTR0=HomeSTR;
//GenderSTR0
                           GenderSTR0=GenderSTR;
//GenderSTR0
                           editFAB.setImageResource(R.drawable.ic_edit_white);
                           Button_new_pass.setEnabled(false);
                           showUnderline(false);
                           country.setEnabled(false);
                           country.setClickable(false);
                           country.setAdapter(CountryAdapter);
                           gender.setEnabled(false);
                           gender.setClickable(false);
                           dateOfBirth.setClickable(false);
                           dateOfBirth.setFocusableInTouchMode(false);
                           gender.setAdapter(genderAdapter);
                           //TODO
                           int g=0;
                           GenderArrayL=getResources().getStringArray(R.array.gender_array);
                           for(int i=0;i<GenderArrayL.length;i++){
                               GenderArray=getResources().getStringArray(R.array.gender_array_OR);
                               if(!GenderSTR.equals(GenderArray[i])){
                                   g++;
                               }else{break;}
                           }
                           GenderSTR=GenderArrayL[g];

                           spinnerPosition = CountryAdapter.getPosition(CountrySTR);
                           genderPosition = genderAdapter.getPosition(GenderSTR);
                           country.setSelection(spinnerPosition);
                           gender.setSelection(genderPosition);
                           old_password.setFocusableInTouchMode(false);
                           old_password.setFocusable(false);
                           old_password.setEnabled(false);
                           old_password.setText(NewPasswordSTR);
                           Button_new_pass.setText(getResources().getString(R.string.newpss));
                           Button_new_pass.setVisibility(View.GONE);
                           changePassword_container.setVisibility(View.GONE);
                           old_password.setUnderlineColor(getResources().getColor(R.color.mytransparent));
                           nav_email.setText(EmailSTR);
                           resetInfo.setVisibility(View.GONE);
                           nav_user_name.setText(NameSTR + "  " + SurnameSTR);
                           savedBefor=true;
                           isSave=false;
                       }
                } else{

                        dateOfBirth.setFocusableInTouchMode(true);
                        everyThingAllright=false;
                        Username.setError(getResources().getString(R.string.user_exist));
                }
                }else{
                    Toast.makeText(getContext(), R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        }
}
    public class SaveTravailleurProfileInfo extends GetData {

        public SaveTravailleurProfileInfo(String Params) {
            super(Params);
        }

        public void execute() {
            GetPharmacieData data = new GetPharmacieData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/Update_TravailleurProf.php");
        }

        public class GetPharmacieData extends DownloadData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce = WebData;
                if (!responce.equals("Error connecting to server")){
                    if (!responce.equals("User Name alrredy used!\n")){
                        if(responce.equals("Email already used!\n")){
                            everyThingAllright=false;
                            emailEdit.setError(getString(R.string.email_used));
                        }else {
                          //  if(responce.equals("success\n" )){
                                Toast.makeText(getContext(), R.string.ed_saved, Toast.LENGTH_SHORT).show();
                                //TODO Do not touch this:
                                UsernameSTR0 = UsernameSTR;
                                NameSTR0 = NameSTR;
                                SurnameSTR0 = SurnameSTR;
                                NewPasswordSTR0 = NewPasswordSTR;
                                EmailSTR0 = EmailSTR;
                                CountrySTR0 = CountrySTR;
                                DateofbirthSTR0 = DateofbirthSTR;
                                PhoneSTR0 = PhoneSTR;
                                HomeSTR0 = HomeSTR;

                                GenderSTR0 = GenderSTR;


                                editFAB.setImageResource(R.drawable.ic_edit_white);
                                Button_new_pass.setEnabled(false);
                                showUnderline(false);
                                country.setEnabled(false);
                                country.setClickable(false);
                                country.setAdapter(CountryAdapter);
                                gender.setEnabled(false);
                                gender.setClickable(false);
                            dateOfBirth.setClickable(false);
                                gender.setAdapter(genderAdapter);
                                spinnerPosition = CountryAdapter.getPosition(CountrySTR);
                            int g=0;
                            GenderArrayL=getResources().getStringArray(R.array.gender_array);
                            for(int i=0;i<GenderArrayL.length;i++){
                                GenderArray=getResources().getStringArray(R.array.gender_array_OR);
                                if(!GenderSTR.equals(GenderArray[i])){
                                    g++;
                                }else{break;}
                            }
                            GenderSTR=GenderArrayL[g];
                                genderPosition = genderAdapter.getPosition(GenderSTR);
                                country.setSelection(spinnerPosition);
                                gender.setSelection(genderPosition);
                                old_password.setFocusableInTouchMode(false);
                                old_password.setFocusable(false);
                                old_password.setEnabled(false);
                                old_password.setText(NewPasswordSTR);
                                Button_new_pass.setText(getResources().getString(R.string.newpss));
                                Button_new_pass.setVisibility(View.GONE);
                                changePassword_container.setVisibility(View.GONE);
                                old_password.setUnderlineColor(getResources().getColor(R.color.mytransparent));
                                nav_email.setText(EmailSTR);
                                resetInfo.setVisibility(View.GONE);
                                nav_user_name.setText(NameSTR + "  " + SurnameSTR);
                                savedBefor = true;
                                isSave = false;

                          //  }
                        }
                    }else{
                        everyThingAllright=false;
                        Username.setError(getResources().getString(R.string.user_exist));
                    }
                }else{
                    Toast.makeText(getContext(), R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



    public class SaveDirecteurProfileInfo extends GetData {

        public SaveDirecteurProfileInfo(String Params) {
            super(Params);
        }

        public void execute() {
            GetPharmacieData data = new GetPharmacieData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/Update_DirecteurProf.php");
        }

        public class GetPharmacieData extends DownloadData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce = WebData;
                if (!responce.equals("Error connecting to server")){
                    if (!responce.equals("User Name alrredy used!\n")){
                        if(responce.equals("Email already used!\n")){
                            everyThingAllright=false;
                            emailEdit.setError(getString(R.string.email_used));
                        }else {
                            Toast.makeText(getContext(), R.string.ed_saved, Toast.LENGTH_SHORT).show();
                            //TODO Do not touch this:
                            UsernameSTR0 = UsernameSTR;
                            NameSTR0 = NameSTR;
                            SurnameSTR0 = SurnameSTR;
                            NewPasswordSTR0 = NewPasswordSTR;
                            EmailSTR0 = EmailSTR;
                            CountrySTR0 = CountrySTR;
                            DateofbirthSTR0 = DateofbirthSTR;
                            PhoneSTR0 = PhoneSTR;
                            HomeSTR0 = HomeSTR;

                            GenderSTR0 = GenderSTR;

                            editFAB.setImageResource(R.drawable.ic_edit_white);
                            Button_new_pass.setEnabled(false);
                            showUnderline(false);
                            country.setEnabled(false);
                            country.setClickable(false);
                            country.setAdapter(CountryAdapter);
                            gender.setEnabled(false);
                            gender.setClickable(false);
                            dateOfBirth.setClickable(false);
                            gender.setAdapter(genderAdapter);
                            spinnerPosition = CountryAdapter.getPosition(CountrySTR);
                            int g=0;
                            GenderArrayL=getResources().getStringArray(R.array.gender_array);
                            for(int i=0;i<GenderArrayL.length;i++){
                                GenderArray=getResources().getStringArray(R.array.gender_array_OR);
                                if(!GenderSTR.equals(GenderArray[i])){
                                    g++;
                                }else{break;}
                            }
                            GenderSTR=GenderArrayL[g];
                            genderPosition = genderAdapter.getPosition(GenderSTR);
                            country.setSelection(spinnerPosition);
                            gender.setSelection(genderPosition);
                            old_password.setFocusableInTouchMode(false);
                            old_password.setFocusable(false);
                            old_password.setEnabled(false);
                            old_password.setText(NewPasswordSTR);
                            Button_new_pass.setText(getResources().getString(R.string.newpss));
                            Button_new_pass.setVisibility(View.GONE);
                            changePassword_container.setVisibility(View.GONE);
                            old_password.setUnderlineColor(getResources().getColor(R.color.mytransparent));
                            nav_email.setText(EmailSTR);
                            resetInfo.setVisibility(View.GONE);
                            nav_user_name.setText(NameSTR + "  " + SurnameSTR);
                            savedBefor = true;
                            isSave = false;

                        }
                    }else{
                        everyThingAllright=false;
                        Username.setError(getResources().getString(R.string.user_exist));
                    }
                }else{
                    Toast.makeText(getContext(), R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}
