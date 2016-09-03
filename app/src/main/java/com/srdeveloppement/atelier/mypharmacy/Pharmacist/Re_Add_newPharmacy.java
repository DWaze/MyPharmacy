package com.srdeveloppement.atelier.mypharmacy.Pharmacist;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.srdeveloppement.atelier.mypharmacy.Data.GetData;
import com.srdeveloppement.atelier.mypharmacy.Pharmacist.myPharmacy.Dialog_timing;
import com.srdeveloppement.atelier.mypharmacy.R;
import com.srdeveloppement.atelier.mypharmacy.Set_phatmacy_dialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class Re_Add_newPharmacy extends Fragment {
    //TODO: doka khi shared pref kan =0; hih

    String responce;
    boolean evryThingAllright = false, contractNotNull = false;
    boolean atLeastOneIsChecked = false;
    String PharmacyName, PharmacyAdress;
    double lat, lng;
    public SharedPreferences sharedPref;
    public int wait_soon;
    String IDPDir;
    Button addPharm, setTime;
    FrameLayout wait;
    EditText contractID;
    FloatingActionButton ok;
    LinearLayout re_add_full_container;
    public String Sunday_f, Monday_f, Tuesday_f, Wednesday_f, Thursday_f, Friday_f, Saturday_f;
    public String Sunday_t, Monday_t, Tuesday_t, Wednesday_t, Thursday_t, Friday_t, Saturday_t;
    boolean cb_Sunday = false, cb_Monday = false, cb_Tuesday = false, cb_Wednesday = false, cb_Thursday = false, cb_Friday = false, cb_Saturday = false;
    public String contractValue;
    ImageView contract_done, location_done, timing_done;


    public Re_Add_newPharmacy() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_re__add_new_pharmacy, container, false);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());

        addPharm = (Button) view.findViewById(R.id.add_pharm_on_map);
        contract_done = (ImageView) view.findViewById(R.id.contract_done);
        location_done = (ImageView) view.findViewById(R.id.location_done);
        timing_done = (ImageView) view.findViewById(R.id.timing_done);
        setTime = (Button) view.findViewById(R.id.addTiming);
        contractID = (EditText) view.findViewById(R.id.contractID);
        wait = (FrameLayout) view.findViewById(R.id.wait);
        re_add_full_container = (LinearLayout) view.findViewById(R.id.re_add_full_container);
        ok = (FloatingActionButton) view.findViewById(R.id.fab);
        wait_soon = sharedPref.getInt("PharmacieState", 0);

        if (wait_soon == 1) {
            re_add_full_container.setVisibility(View.INVISIBLE);
            wait.setVisibility(View.VISIBLE);
            ok.setVisibility(View.INVISIBLE);
        }
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        IDPDir = sharedPref.getString("IDPDirecteur", "");

        addPharm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), Set_phatmacy_dialog.class), 1);
            }
        });
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), Dialog_timing.class), 2);
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                PharmacyName = data.getStringExtra("PharmacyName");
                PharmacyAdress = data.getStringExtra("PharmacyAdress");
                evryThingAllright = data.getBooleanExtra("evryThingAllright", false);
                lat = data.getDoubleExtra("lat", 0);
                lng = data.getDoubleExtra("lng", 0);
            }
        }
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                //TODO: values of workin time per day as a string
                Sunday_f = data.getStringExtra("Sunday_f");
                Sunday_t = data.getStringExtra("Sunday_t");
                Monday_f = data.getStringExtra("Monday_f");
                Monday_t = data.getStringExtra("Monday_t");
                Tuesday_f = data.getStringExtra("Tuesday_f");
                Tuesday_t = data.getStringExtra("Tuesday_t");
                Wednesday_f = data.getStringExtra("Wednesday_f");
                Wednesday_t = data.getStringExtra("Wednesday_t");
                Thursday_f = data.getStringExtra("Thursday_f");
                Thursday_t = data.getStringExtra("Thursday_t");
                Friday_f = data.getStringExtra("Friday_f");
                Friday_t = data.getStringExtra("Friday_t");
                Saturday_f = data.getStringExtra("Saturday_f");
                Saturday_t = data.getStringExtra("Saturday_t");

                //TODO: boolean of working days (the CheckBox) ex: cb_Sunday=true; means he works on sunday, if =false he does't work
                cb_Sunday = data.getBooleanExtra("cb_SundayB", false);
                cb_Monday = data.getBooleanExtra("cb_MondayB", false);
                cb_Tuesday = data.getBooleanExtra("cb_TuesdayB", false);
                cb_Wednesday = data.getBooleanExtra("cb_WednesdayB", false);
                cb_Thursday = data.getBooleanExtra("cb_ThursdayB", false);
                cb_Friday = data.getBooleanExtra("cb_FridayB", false);
                cb_Saturday = data.getBooleanExtra("cb_SaturdayB", false);
                atLeastOneIsChecked = data.getBooleanExtra("atLeastOneIsChecked", false);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (evryThingAllright == true) {
            addPharm.setText(R.string.location_done);
            location_done.setVisibility(View.VISIBLE);


        }
        if (atLeastOneIsChecked == true) {
            setTime.setText(R.string.timing_done);
            timing_done.setVisibility(View.VISIBLE);



        }
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contractValue = contractID.getText().toString().trim();
                if (contractValue.equals("")) {
                   // contractID.setHint(getResources().getString(R.string.storeCode_is_empty));
                   Toast.makeText(getContext(), R.string.storeCode_is_empty, Toast.LENGTH_SHORT).show();
                    contract_done.setImageResource(R.drawable.ic_not_done);
                    contract_done.setVisibility(View.VISIBLE);
                } else {
                    contract_done.setImageResource(R.drawable.ic_done);
                    contract_done.setVisibility(View.VISIBLE);
                    if (evryThingAllright == true && atLeastOneIsChecked == true) {

                        String Params = "Nom_Pharmacie=" + PharmacyName + "&Address_Pharmacie=" + PharmacyAdress + "&Latitude=" + lat +
                                "&Langitude=" + lng + "&IDPDirecteur=" + IDPDir+"&IDContratPharmacie="+contractValue;
                        if (cb_Sunday == true) {
                            Params = Params + "&Sunday_f=" + Sunday_f + "&Sunday_t=" + Sunday_t + "&cb_Sunday=true";
                        } else {
                            Params = Params + "&Sunday_f=00:00" + "&Sunday_t=00:00" + "&cb_Sunday=false";
                        }

                        if (cb_Monday == true) {
                            Params = Params + "&Monday_f=" + Monday_f + "&Monday_t=" + Monday_t + "&cb_Monday=true";
                        } else {
                            Params = Params + "&Monday_f=00:00" + "&Monday_t=00:00" + "&cb_Monday=false";
                        }

                        if (cb_Tuesday == true) {
                            Params = Params + "&Tuesday_f=" + Tuesday_f + "&Tuesday_t=" + Tuesday_t + "&cb_Tuesday=true";
                        } else {
                            Params = Params + "&Tuesday_f=00:00" + "&Tuesday_t=00:00" + "&cb_Tuesday=false";
                        }

                        if (cb_Wednesday == true) {
                            Params = Params + "&Wednesday_f=" + Wednesday_f + "&Wednesday_t=" + Wednesday_t + "&cb_Wednesday=true";
                        } else {
                            Params = Params + "&Wednesday_f=00:00" + "&Wednesday_t=00:00" + "&cb_Wednesday=false";
                        }

                        if (cb_Thursday == true) {
                            Params = Params + "&Thursday_f=" + Thursday_f + "&Thursday_t=" + Thursday_t + "&cb_Thursday=true";
                        } else {
                            Params = Params + "&Thursday_f=00:00" + "&Thursday_t=00:00" + "&cb_Thursday=false";
                        }

                        if (cb_Friday == true) {
                            Params = Params + "&Friday_f=" + Friday_f + "&Friday_t=" + Friday_t + "&cb_Friday=true";
                        } else {
                            Params = Params + "&Friday_f=00:00" + "&Friday_t=00:00" + "&cb_Friday=false";
                        }

                        if (cb_Saturday == true) {
                            Params = Params + "&Saturday_f=" + Saturday_f + "&Saturday_t=" + Saturday_t + "&cb_Saturday=true";
                        } else {
                            Params = Params + "&Saturday_f=00:00" + "&Saturday_t=00:00" + "&cb_Saturday=false";
                        }
                        createPharmacie cp = new createPharmacie(Params);
                        cp.execute();


                        re_add_full_container.setVisibility(View.INVISIBLE);
                        wait.setVisibility(View.VISIBLE);
                        ok.setVisibility(View.INVISIBLE);
                    } else {
                        if (evryThingAllright == false) {
                            startActivityForResult(new Intent(getContext(), Set_phatmacy_dialog.class), 1);
                        } else if (atLeastOneIsChecked == false) {
                            startActivityForResult(new Intent(getContext(), Dialog_timing.class), 2);
                        } else {
                            re_add_full_container.setVisibility(View.INVISIBLE);
                            wait.setVisibility(View.VISIBLE);
                            ok.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        });
    }


    public class createPharmacie extends GetData {

        public createPharmacie(String Params) {
            super(Params);
        }

        public void execute() {
            ProcessData data = new ProcessData();
            data.execute(GetData.LOCALHOST + "Pharmacie_Project/Add_New_Pharmacie.php");
        }

        public class ProcessData extends DownloadData {

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce = WebData;
                if (!responce.equals("Error connecting to server")) {
                    if (!responce.equals("Pharmacie created succefuly!\n")) {
                        evryThingAllright =true;
                        Toast.makeText(getContext(), R.string.pharm_succ_created, Toast.LENGTH_LONG).show();
                    } else {
                        evryThingAllright =false;
                        Toast.makeText(getContext(), R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    evryThingAllright =false;
                    Toast.makeText(getContext(), R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                }

            }
        }
    }


}
