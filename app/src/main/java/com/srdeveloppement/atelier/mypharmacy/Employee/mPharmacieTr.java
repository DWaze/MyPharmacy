package com.srdeveloppement.atelier.mypharmacy.Employee;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.srdeveloppement.atelier.mypharmacy.Data.GetData;
import com.srdeveloppement.atelier.mypharmacy.Data.GetJsonPharmacieInfo;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.Pharmacie;
import com.srdeveloppement.atelier.mypharmacy.R;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class mPharmacieTr extends Fragment implements com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener,OnMapReadyCallback {


    EditText EditNB,EditPN,EditAP;
    int i=1;
    TextView WarrningMessage ;
    Marker mMarker;
    Boolean isEditFab;

    String IDPharmaci;
    String responce;
    String Address_Pharmacie;
    String Nom_Pharmacie;
    Double Lat;
    Double Lang;
    //// TODO: 11/04/2016
    RelativeLayout cb_container_Sunday,cb_container_Monday,cb_container_Tuesday,cb_container_Wednesday,cb_container_Thursday,cb_container_Friday,cb_container_Saturday;
    Button DeletButton ;
    Button Sunday_from,Monday_from,Tuesday_from,Wednesday_from,Thursday_from,Friday_from,Saturday_from;
    Button Sunday_to,Monday_to,Tuesday_to,Wednesday_to,Thursday_to,Friday_to,Saturday_to;
    public String Sunday_f,Monday_f,Tuesday_f,Wednesday_f,Thursday_f,Friday_f,Saturday_f;
    public String Sunday_t,Monday_t,Tuesday_t,Wednesday_t,Thursday_t,Friday_t,Saturday_t;
    RelativeLayout sunday,monday,tuesday,wednesday,thursday,friday,saturday;
    public boolean cb_SundayB,cb_MondayB,cb_TuesdayB,cb_WednesdayB,cb_ThursdayB,cb_FridayB,cb_SaturdayB,works;
    CheckBox cb_Sunday,cb_Monday,cb_Tuesday,cb_Wednesday,cb_Thursday,cb_Friday,cb_Saturday;
    int atLeastOneIsChecked=0;
    public int from, to;


    LatLng postion;
    CameraPosition POSITION;

    String Params;
    ArrayList<Pharmacie> mPharmacie;
    String IDPDir;

    private GoogleMap mMap;  ToggleButton map_type;

    public mPharmacieTr() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        IDPDir = sharedPref.getString("IDPDirecteur", "");



        final View view = inflater.inflate(R.layout.ma_pharmacietr_activity, container, false);

        final FloatingActionButton fabEdit = (FloatingActionButton)view.findViewById(R.id.editMaPharmacieTr);


        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);



        isEditFab=true;
        EditAP =(EditText)view.findViewById(R.id.EditAP);
        EditPN =(EditText)view.findViewById(R.id.EditPN);
        EditNB =(EditText)view.findViewById(R.id.EditNB);

        // TODO: containers of checkboxes:
        cb_container_Sunday=(RelativeLayout)view.findViewById(R.id.cb_container_Sunday);
        cb_container_Monday=(RelativeLayout)view.findViewById(R.id.cb_container_Monday);
        cb_container_Tuesday=(RelativeLayout)view.findViewById(R.id.cb_container_Tuesday);
        cb_container_Wednesday=(RelativeLayout)view.findViewById(R.id.cb_container_Wednesday);
        cb_container_Thursday=(RelativeLayout)view.findViewById(R.id.cb_container_Thursday);
        cb_container_Friday=(RelativeLayout)view.findViewById(R.id.cb_container_Friday);
        cb_container_Saturday=(RelativeLayout)view.findViewById(R.id.cb_container_Saturday);
        sunday=(RelativeLayout)view.findViewById(R.id.sunday);
        monday=(RelativeLayout)view.findViewById(R.id.monday);
        tuesday=(RelativeLayout)view.findViewById(R.id.tuesday);
        thursday=(RelativeLayout)view.findViewById(R.id.thursday);
        wednesday=(RelativeLayout)view.findViewById(R.id.wednesday);
        friday=(RelativeLayout)view.findViewById(R.id.friday);
        saturday=(RelativeLayout)view.findViewById(R.id.saturday);
        cb_Sunday=(CheckBox)view.findViewById(R.id.cbx_Sunday);
        cb_Monday=(CheckBox)view.findViewById(R.id.cbx_Monday);
        cb_Tuesday=(CheckBox)view.findViewById(R.id.cbx_Tuesday);
        cb_Wednesday=(CheckBox)view.findViewById(R.id.cbx_Wednesday);
        cb_Thursday=(CheckBox)view.findViewById(R.id.cbx_Thursday);
        cb_Friday=(CheckBox)view.findViewById(R.id.cbx_Friday);
        cb_Saturday=(CheckBox)view.findViewById(R.id.cbx_Saturday);
        Sunday_from=(Button)view.findViewById(R.id.Sunday_from);
        Monday_from=(Button)view.findViewById(R.id.Monday_from);
        Tuesday_from=(Button)view.findViewById(R.id.Tuesday_from);
        Wednesday_from=(Button)view.findViewById(R.id.Wednesday_from);
        Thursday_from=(Button)view.findViewById(R.id.Thursday_from);
        Friday_from=(Button)view.findViewById(R.id.Friday_from);
        Saturday_from=(Button)view.findViewById(R.id.Saturday_from);
        Sunday_to=(Button)view.findViewById(R.id.Sunday_to);
        Monday_to=(Button)view.findViewById(R.id.Monday_to);
        Tuesday_to=(Button)view.findViewById(R.id.Tuesday_to);
        Wednesday_to=(Button)view.findViewById(R.id.Wednesday_to);
        Thursday_to=(Button)view.findViewById(R.id.Thursday_to);
        Friday_to=(Button)view.findViewById(R.id.Friday_to);
        Saturday_to=(Button)view.findViewById(R.id.Saturday_to);



        //// TODO: 11/04/2016
        WarrningMessage = (TextView)view.findViewById(R.id.warrningText);
        DeletButton = (Button)view.findViewById(R.id.DeleteBtn);

        map_type=(ToggleButton)view.findViewById(R.id.map_type);




        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        EditAP.setFocusable(false);
        EditAP.setFocusableInTouchMode(false);
        EditPN.setFocusable(false);
        EditPN.setFocusableInTouchMode(false);
        EditNB.setFocusable(false);



        Params="IDPDirecteur="+IDPDir;
        ShowPharmacieInfo show = new ShowPharmacieInfo(Params,getContext());
        show.execute();

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(),DirecteurDialog.class);
                startActivity(in);
            }
        });


        AppBarLayout mAppBarLayout = (AppBarLayout)view.findViewById(R.id.app_bar);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(AppBarLayout appBarLayout) {
                return false;
            }
        });
        params.setBehavior(behavior);


        return view;
    }
    public void setCheck(CheckBox cb,boolean bool){
        cb.setChecked(bool);
    }

    public void setSizeToZero(RelativeLayout myFrameBox,boolean editOrSave){

        if(editOrSave==true){
            RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            myFrameBox.setLayoutParams(Params);
            myFrameBox.setGravity(Gravity.CENTER_VERTICAL );
        }else{
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) myFrameBox.getLayoutParams();
            params.width = 0;
            myFrameBox.setLayoutParams(params);
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
        }

    }
    public void visibel_items(View v,View day_from,View day_to){
        boolean checked = ((CheckBox) v).isChecked();
        if (checked) {
            works=true;
            day_from.setEnabled(true);
            day_to.setEnabled(true);
            atLeastOneIsChecked++;
        } else {
            works=false;
            day_from.setEnabled(false);
            day_to.setEnabled(false);
            ((Button)day_from).setText("00:00");
            ((Button)day_to).setText("00:00");
            atLeastOneIsChecked--;
        }
    }
    public void myCalendar(){
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance( mPharmacieTr.this,now.get(Calendar.HOUR),now.get(Calendar.MINUTE),now.get(Calendar.SECOND),false);
        tpd.setAccentColor(getResources().getColor(R.color.colorAccent));
        tpd.show(getActivity().getFragmentManager(), "Pick Time");
    }
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        String time = hourString+":"+minuteString;
        // Sunday_from.setText(time);
        switch (from){
            case 1: Sunday_f=time;Sunday_from.setText(Sunday_f); from=0; break;
            case 2: Monday_f=time;Monday_from.setText(Monday_f); from=0; break;
            case 3: Tuesday_f=time;Tuesday_from.setText(Tuesday_f); from=0; break;
            case 4: Wednesday_f=time;Wednesday_from.setText(Wednesday_f); from=0; break;
            case 5: Thursday_f=time;Thursday_from.setText(Thursday_f); from=0; break;
            case 6: Friday_f=time;Friday_from.setText(Friday_f); from=0; break;
            case 7: Saturday_f=time;Saturday_from.setText(Saturday_f); from=0; break;
        }
        switch (to){
            case 1: Sunday_t=time;Sunday_to.setText(Sunday_t); to=0; break;
            case 2: Monday_t=time;Monday_to.setText(Monday_t); to=0; break;
            case 3: Tuesday_t=time;Tuesday_to.setText(Tuesday_t); to=0; break;
            case 4: Wednesday_t=time;Wednesday_to.setText(Wednesday_t); to=0; break;
            case 5: Thursday_t=time;Thursday_to.setText(Thursday_t); to=0; break;
            case 6: Friday_t=time;Friday_to.setText(Friday_t); to=0; break;
            case 7: Saturday_t=time;Saturday_to.setText(Saturday_t); to=0; break;
        }


    }
    public void setTime(View view) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance((TimePickerDialog.OnTimeSetListener) getContext(),now.get(Calendar.HOUR),now.get(Calendar.MINUTE),now.get(Calendar.SECOND),true);
        tpd.setAccentColor(getResources().getColor(R.color.colorAccent));
        tpd.show(getActivity().getFragmentManager(), "Pick Time");
        switch(view.getId()) {
            case R.id.Sunday_from:
                from=1;
                break;
            case R.id.Monday_from:
                from=2;
                break;
            case R.id.Tuesday_from:
                from=3;
                break;
            case R.id.Wednesday_from:
                from=4;
                break;
            case R.id.Thursday_from:
                from=5;
                break;
            case R.id.Friday_from:
                from=6;
                break;
            case R.id.Saturday_from:
                from=7;
                break;
            case R.id.Sunday_to:
                to=1;
                break;
            case R.id.Monday_to:
                to=2;
                break;
            case R.id.Tuesday_to:
                to=3;
                break;
            case R.id.Wednesday_to:
                to=4;
                break;
            case R.id.Thursday_to:
                to=5;
                break;
            case R.id.Friday_to:
                to=6;
                break;
            case R.id.Saturday_to:
                to=7;
                break;
        }
    }
//// TODO: 11/04/2016

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


    public class ShowPharmacieInfo extends GetJsonPharmacieInfo {

        public ShowPharmacieInfo(String Params, Context C) {
            super(Params,C);
        }

        public void execute() {
            GetPharmacieData data = new GetPharmacieData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/maPharmacie.php");
        }

        public class GetPharmacieData extends DownloadJsonData{
            // jarebha fel LG w na3atli ki tedkhol kifah tji

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce = WebData;
                mPharmacie=getmPharmacie();
                if(mPharmacie!=null){
                    if(!responce.equals("pharmacie not validated yet!\n")){
                        if(!responce.equals("Pharmacie doas not existe!\n")){
                            if(!responce.isEmpty()){

                                EditPN.setText(mPharmacie.get(0).getNom_Pharmacie().toString());
                                EditAP.setText(mPharmacie.get(0).getAddress_Pharmacie().toString());
                                EditNB.setText(mPharmacie.get(0).getNumberOfMeds()+"");
                                Sunday_from.setText(mPharmacie.get(0).getSunday_f().toString());
                                Sunday_to.setText(mPharmacie.get(0).getSunday_t().toString());
                                Monday_from.setText(mPharmacie.get(0).getMonday_f().toString());
                                Monday_to.setText(mPharmacie.get(0).getMonday_t().toString());
                                Wednesday_from.setText(mPharmacie.get(0).getWednesday_f().toString());
                                Wednesday_to.setText(mPharmacie.get(0).getWednesday_t().toString());
                                Thursday_from.setText(mPharmacie.get(0).getThursday_f().toString());
                                Thursday_to.setText(mPharmacie.get(0).getThursday_t().toString());
                                Tuesday_from.setText(mPharmacie.get(0).getTuesday_f().toString());
                                Tuesday_to.setText(mPharmacie.get(0).getTuesday_t().toString());
                                Friday_from.setText(mPharmacie.get(0).getFriday_f().toString());
                                Friday_to.setText(mPharmacie.get(0).getFriday_t().toString());
                                Saturday_from.setText(mPharmacie.get(0).getSaturday_f().toString());
                                Saturday_to.setText(mPharmacie.get(0).getSaturday_t().toString());

                                cb_SundayB=Boolean.parseBoolean(mPharmacie.get(0).getCb_SundayB());
                                cb_MondayB=Boolean.parseBoolean(mPharmacie.get(0).getCb_MondayB());
                                cb_TuesdayB=Boolean.parseBoolean(mPharmacie.get(0).getCb_TuesdayB());
                                cb_WednesdayB=Boolean.parseBoolean(mPharmacie.get(0).getCb_WednesdayB());
                                cb_ThursdayB=Boolean.parseBoolean(mPharmacie.get(0).getCb_ThursdayB());
                                cb_FridayB=Boolean.parseBoolean(mPharmacie.get(0).getCb_FridayB());
                                cb_SaturdayB=Boolean.parseBoolean(mPharmacie.get(0).getCb_SaturdayB());

                                setCheck(cb_Sunday,cb_SundayB);
                                setCheck(cb_Monday,cb_MondayB);
                                setCheck(cb_Tuesday,cb_TuesdayB);
                                setCheck(cb_Wednesday,cb_WednesdayB);
                                setCheck(cb_Thursday,cb_ThursdayB);
                                setCheck(cb_Friday,cb_FridayB);
                                setCheck(cb_Saturday,cb_SaturdayB);

                                showTimingItem(sunday, cb_SundayB);
                                showTimingItem(monday, cb_MondayB);
                                showTimingItem(tuesday, cb_TuesdayB);
                                showTimingItem(wednesday, cb_WednesdayB);
                                showTimingItem(thursday, cb_ThursdayB);
                                showTimingItem(friday, cb_FridayB);
                                showTimingItem(saturday, cb_SaturdayB); //osber

                                double lat=mPharmacie.get(0).getLatitude();
                                double lang=mPharmacie.get(0).getLangitude();

                                postion = new LatLng(lat,lang);
                                POSITION = CameraPosition.builder()
                                        .target(postion)
                                        .zoom(15)
                                        .bearing(0)
                                        .tilt(50)
                                        .build();
                                mMarker=mMap.addMarker(new MarkerOptions().position(postion).title("Pharmacie: " + mPharmacie.get(0).getNom_Pharmacie()).icon(BitmapDescriptorFactory.fromResource(R.drawable.m_pharmacy)));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(postion));
                                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(POSITION), 3000, null);// c bn ok dok kima netlagaou zide djarebha andek haka bah nkono sure oui
                            }//

                        }else{
                            Toast.makeText(getContext(), R.string.ph_no_exist,Toast.LENGTH_SHORT).show();//cheft hadi ay lahna ana andi ouin kima la pharmacie tkon makanech ygolo makanech
                        }
                    }else{
                        Toast.makeText(getContext(), R.string.ph_no_valid,Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        }}
}
