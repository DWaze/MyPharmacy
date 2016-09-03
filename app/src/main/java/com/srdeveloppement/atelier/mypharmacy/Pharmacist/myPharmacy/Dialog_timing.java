package com.srdeveloppement.atelier.mypharmacy.Pharmacist.myPharmacy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.srdeveloppement.atelier.mypharmacy.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class Dialog_timing extends AppCompatActivity  implements   com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener,
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    CheckBox cb_Sunday,cb_Monday,cb_Tuesday,cb_Wednesday,cb_Thursday,cb_Friday,cb_Saturday;
    Button Sunday_from,Monday_from,Tuesday_from,Wednesday_from,Thursday_from,Friday_from,Saturday_from;
    Button Sunday_to,Monday_to,Tuesday_to,Wednesday_to,Thursday_to,Friday_to,Saturday_to;
    RelativeLayout item_timing1,item_timing2,item_timing3,item_timing4,item_timing5,item_timing6,item_timing7;
    public int from, to;
    Button x,ok;
    public int atLeastOneIsChecked=0;
    public String Sunday_f,Monday_f,Tuesday_f,Wednesday_f,Thursday_f,Friday_f,Saturday_f;
    public String Sunday_t,Monday_t,Tuesday_t,Wednesday_t,Thursday_t,Friday_t,Saturday_t;

    boolean cb_SundayB,cb_MondayB,cb_TuesdayB,cb_WednesdayB,cb_ThursdayB,cb_FridayB,cb_SaturdayB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_timing);
        Sunday_f=Monday_f=Tuesday_f=Wednesday_f=Thursday_f=Friday_f=Saturday_f="00:00";
        Sunday_t=Monday_t=Tuesday_t=Wednesday_t=Thursday_t=Friday_t=Saturday_t="00:00";

        x=(Button)findViewById(R.id.x);
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();}});
        ok=(Button)findViewById(R.id.ok);

        // TODO: containers to change the background when checBox selected:
        item_timing1=(RelativeLayout)findViewById(R.id.item_timing1);
        item_timing2=(RelativeLayout)findViewById(R.id.item_timing2);
        item_timing3=(RelativeLayout)findViewById(R.id.item_timing3);
        item_timing4=(RelativeLayout)findViewById(R.id.item_timing4);
        item_timing5=(RelativeLayout)findViewById(R.id.item_timing5);
        item_timing6=(RelativeLayout)findViewById(R.id.item_timing6);
        item_timing7=(RelativeLayout)findViewById(R.id.item_timing7);
        // TODO: checkBoxes:
        cb_Sunday=(CheckBox)findViewById(R.id.cb_Sunday);
        cb_Monday=(CheckBox)findViewById(R.id.cb_Monday);
        cb_Tuesday=(CheckBox)findViewById(R.id.cb_Tuesday);
        cb_Wednesday=(CheckBox)findViewById(R.id.cb_Wednesday);
        cb_Thursday=(CheckBox)findViewById(R.id.cb_Thursday);
        cb_Friday=(CheckBox)findViewById(R.id.cb_Friday);
        cb_Saturday=(CheckBox)findViewById(R.id.cb_Saturday);
        // TODO: from buttons:
        Sunday_from=(Button)findViewById(R.id.Sunday_from);
        Monday_from=(Button)findViewById(R.id.Monday_from);
        Tuesday_from=(Button)findViewById(R.id.Tuesday_from);
        Wednesday_from=(Button)findViewById(R.id.Wednesday_from);
        Thursday_from=(Button)findViewById(R.id.Thursday_from);
        Friday_from=(Button)findViewById(R.id.Friday_from);
        Saturday_from=(Button)findViewById(R.id.Saturday_from);
        // TODO: to buttons:
        Sunday_to=(Button)findViewById(R.id.Sunday_to);
        Monday_to=(Button)findViewById(R.id.Monday_to);
        Tuesday_to=(Button)findViewById(R.id.Tuesday_to);
        Wednesday_to=(Button)findViewById(R.id.Wednesday_to);
        Thursday_to=(Button)findViewById(R.id.Thursday_to);
        Friday_to=(Button)findViewById(R.id.Friday_to);
        Saturday_to=(Button)findViewById(R.id.Saturday_to);



    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.cb_Sunday:
                if (checked){
                    cb_SundayB=true;
                    item_timing1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    Sunday_from.setEnabled(true);
                    Sunday_to.setEnabled(true);
                    Sunday_from.setText(Sunday_f);
                    Sunday_to.setText(Sunday_t);
                    atLeastOneIsChecked++;
                } else{
                    cb_SundayB=false;
                    item_timing1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    Sunday_from.setEnabled(false);
                    Sunday_to.setEnabled(false);
                    Sunday_from.setText("00:00");
                    Sunday_to.setText("00:00");
                    atLeastOneIsChecked--;
                }
                break;

            case R.id.cb_Monday:
                if (checked){
                    cb_MondayB=true;
                    item_timing2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    Monday_from.setEnabled(true);
                    Monday_to.setEnabled(true);
                    Monday_from.setText(Monday_f);
                    Monday_to.setText(Monday_t);
                    atLeastOneIsChecked++;
                } else{
                    cb_MondayB=false;
                    item_timing2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    Monday_from.setEnabled(false);
                    Monday_to.setEnabled(false);
                    Monday_from.setText("00:00");
                    Monday_to.setText("00:00");
                    atLeastOneIsChecked--;
                }
                break;
            case R.id.cb_Tuesday:
                if (checked){
                    cb_TuesdayB=true;
                    item_timing3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    Tuesday_from.setEnabled(true);
                    Tuesday_to.setEnabled(true);
                    Tuesday_from.setText(Tuesday_f);
                    Tuesday_to.setText(Tuesday_t);
                    atLeastOneIsChecked++;
                } else{
                    cb_TuesdayB=false;
                    item_timing3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    Tuesday_from.setEnabled(false);
                    Tuesday_to.setEnabled(false);
                    Tuesday_from.setText("00:00");
                    Tuesday_to.setText("00:00");
                    atLeastOneIsChecked--;
                }
                break;
            case R.id.cb_Wednesday:
                if (checked){
                    cb_WednesdayB=true;
                    item_timing4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    Wednesday_from.setEnabled(true);
                    Wednesday_to.setEnabled(true);
                    Wednesday_from.setText(Wednesday_f);
                    Wednesday_to.setText(Wednesday_t);
                    atLeastOneIsChecked++;
                } else{
                    cb_WednesdayB=false;
                    item_timing4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    Wednesday_from.setEnabled(false);
                    Wednesday_to.setEnabled(false);
                    Wednesday_from.setText("00:00");
                    Wednesday_to.setText("00:00");
                    atLeastOneIsChecked--;
                }
                break;
            case R.id.cb_Thursday:
                if (checked){
                    cb_ThursdayB=true;
                    item_timing5.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    Thursday_from.setEnabled(true);
                    Thursday_to.setEnabled(true);
                    Thursday_from.setText(Thursday_f);
                    Thursday_to.setText(Thursday_t);
                    atLeastOneIsChecked++;
                } else{
                    cb_ThursdayB=false;
                    item_timing5.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    Thursday_from.setEnabled(false);
                    Thursday_to.setEnabled(false);
                    Thursday_from.setText("00:00");
                    Thursday_to.setText("00:00");
                    atLeastOneIsChecked--;
                }
                break;
            case R.id.cb_Friday:
                if (checked){
                    cb_FridayB=true;
                    item_timing6.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    Friday_from.setEnabled(true);
                    Friday_to.setEnabled(true);
                    Friday_from.setText(Friday_f);
                    Friday_to.setText(Friday_t);
                    atLeastOneIsChecked++;
                } else{
                    cb_FridayB=false;
                    item_timing6.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    Friday_from.setEnabled(false);
                    Friday_to.setEnabled(false);
                    Friday_from.setText("00:00");
                    Friday_to.setText("00:00");
                    atLeastOneIsChecked--;
                }
                break;
            case R.id.cb_Saturday:
                if (checked) {
                    cb_SaturdayB=true;
                    item_timing7.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    Saturday_from.setEnabled(true);
                    Saturday_to.setEnabled(true);
                    Saturday_from.setText(Saturday_f);
                    Saturday_to.setText(Saturday_t);
                    atLeastOneIsChecked++;
                } else{
                    cb_SaturdayB=false;
                    item_timing7.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    Saturday_from.setEnabled(false);
                    Saturday_to.setEnabled(false);
                    Saturday_from.setText("00:00");
                    Saturday_to.setText("00:00");
                    atLeastOneIsChecked--;
                }
                break;


        }
    }
    public void onResume() {
        super.onResume();
        TimePickerDialog tpd = (TimePickerDialog) getFragmentManager().findFragmentByTag("Timepickerdialog");
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = (com.wdullaer.materialdatetimepicker.date.DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");

        if(tpd != null) tpd.setOnTimeSetListener(this);
        if(dpd != null) dpd.setOnDateSetListener(this);
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

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

    public void checkWorkDay(String from,String to,boolean b, int day){
        //// TODO: 07/05/201
        if(from.equals("00:00") && to.equals("00:00") && b==true){
            switch (day){
                case 1 :
                        cb_Sunday.setChecked(false);
                        cb_SundayB=false;
                        item_timing1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        Sunday_from.setEnabled(false);
                        Sunday_to.setEnabled(false);
                        Sunday_from.setText("00:00");
                        Sunday_to.setText("00:00");
                    break;

                case 2 :
                        cb_Monday.setChecked(false);
                        cb_MondayB=false;
                        item_timing2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        Monday_from.setEnabled(false);
                        Monday_to.setEnabled(false);
                        Monday_from.setText("00:00");
                        Monday_to.setText("00:00");
                    break;
                case 3 :

                        cb_Tuesday.setChecked(false);
                        cb_TuesdayB=false;
                        item_timing3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        Tuesday_from.setEnabled(false);
                        Tuesday_to.setEnabled(false);
                        Tuesday_from.setText("00:00");
                        Tuesday_to.setText("00:00");
                    break;
                case 4 :
                        cb_Wednesday.setChecked(false);
                        cb_WednesdayB=false;
                        item_timing4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        Wednesday_from.setEnabled(false);
                        Wednesday_to.setEnabled(false);
                        Wednesday_from.setText("00:00");
                        Wednesday_to.setText("00:00");
                    break;
                case 5 :

                        cb_Thursday.setChecked(false);
                        cb_ThursdayB=false;
                        item_timing5.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        Thursday_from.setEnabled(false);
                        Thursday_to.setEnabled(false);
                        Thursday_from.setText("00:00");
                        Thursday_to.setText("00:00");
                    break;
                case 6 :

                        cb_Friday.setChecked(false);
                        cb_FridayB=false;
                        item_timing6.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        Friday_from.setEnabled(false);
                        Friday_to.setEnabled(false);
                        Friday_from.setText("00:00");
                        Friday_to.setText("00:00");
                    break;
                case 7 :
                        cb_Saturday.setChecked(false);
                        cb_SaturdayB=false;
                        item_timing7.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        Saturday_from.setEnabled(false);
                        Saturday_to.setEnabled(false);
                        Saturday_from.setText("00:00");
                        Saturday_to.setText("00:00");
                    break;
            }
            atLeastOneIsChecked--;
        }
    }

    public void setTime(View view) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(Dialog_timing.this,now.get(Calendar.HOUR),now.get(Calendar.MINUTE),now.get(Calendar.SECOND),false);
        tpd.setAccentColor(getResources().getColor(R.color.colorAccent));
        tpd.show(getFragmentManager(), "Pick Time");
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

    public void done(View view) {
        checkWorkDay(Sunday_f,Sunday_t,cb_SundayB,1);
        checkWorkDay(Monday_f,Monday_t,cb_MondayB,2);
        checkWorkDay(Tuesday_f,Tuesday_t,cb_TuesdayB,3);
        checkWorkDay(Wednesday_f,Wednesday_t,cb_WednesdayB,4);
        checkWorkDay(Thursday_f,Thursday_t,cb_ThursdayB,5);
        checkWorkDay(Friday_f,Friday_t,cb_FridayB,6);
        checkWorkDay(Saturday_f,Saturday_t,cb_SaturdayB,7);
        if(atLeastOneIsChecked <= 0){
            Toast.makeText(this, R.string.atleast_select_oneday, Toast.LENGTH_LONG).show();
        }else{
            Intent myInent = new Intent();
            Bundle b = new Bundle();

            myInent.putExtra("Sunday_f", Sunday_f);
            myInent.putExtra("Sunday_t", Sunday_t);
            myInent.putExtra("Monday_f", Monday_f);
            myInent.putExtra("Monday_t", Monday_t);
            myInent.putExtra("Tuesday_f", Tuesday_f);
            myInent.putExtra("Tuesday_t", Tuesday_t);
            myInent.putExtra("Wednesday_f", Wednesday_f);
            myInent.putExtra("Wednesday_t", Wednesday_t);
            myInent.putExtra("Thursday_f", Thursday_f);
            myInent.putExtra("Thursday_t", Thursday_t);
            myInent.putExtra("Friday_f", Friday_f);
            myInent.putExtra("Friday_t", Friday_t);
            myInent.putExtra("Saturday_f", Saturday_f);
            myInent.putExtra("Saturday_t", Saturday_t);


            myInent.putExtra("atLeastOneIsChecked", true);

            myInent.putExtra("cb_SundayB",cb_SundayB);
            myInent.putExtra("cb_MondayB",cb_MondayB);
            myInent.putExtra("cb_TuesdayB",cb_TuesdayB);
            myInent.putExtra("cb_WednesdayB",cb_WednesdayB);
            myInent.putExtra("cb_ThursdayB",cb_ThursdayB);
            myInent.putExtra("cb_FridayB",cb_FridayB);
            myInent.putExtra("cb_SaturdayB",cb_SaturdayB);

            setResult(RESULT_OK, myInent);
            Toast.makeText(this, R.string.done,Toast.LENGTH_LONG).show();
            finish();
        }
    }
}

