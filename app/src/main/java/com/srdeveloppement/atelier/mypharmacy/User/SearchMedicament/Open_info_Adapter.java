package com.srdeveloppement.atelier.mypharmacy.User.SearchMedicament;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.srdeveloppement.atelier.mypharmacy.Data.Model.MedPharmacie;
import com.srdeveloppement.atelier.mypharmacy.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Sam on 06/03/2016.
 */
public class Open_info_Adapter extends RecyclerView.Adapter<Open_info_Adapter.ViewHolder> {
    private ArrayList<MedPharmacie> Open_info;
    SimpleDateFormat inputParser;
    boolean MARKET_IS_OPENED=false;
    String START;
    String END;
    Date CURRENT_TIME_date;
    Date StartTime;
    Date EndTime;
    View item;


    TextView state;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View View;
        public ViewHolder(View itemView) {
            super(itemView);
            View = itemView;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public Open_info_Adapter(ArrayList<MedPharmacie> medPharmacies) {
        this.Open_info = medPharmacies;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Open_info_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_med_pharmacy, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final LinearLayout myItem=(LinearLayout)holder.View.findViewById(R.id.myItem);
        TextView pharmacy_name = (TextView) holder.View.findViewById(R.id.pharmacy_name);
        TextView pharmacy_adress = (TextView) holder.View.findViewById(R.id.pharmacy_adress);
        TextView medStorage = (TextView) holder.View.findViewById(R.id.medStorage);
        final TextView state = (TextView) holder.View.findViewById(R.id.state);

        pharmacy_name.setText(" "+Open_info.get(position).getNom_Pharmacie()+" ");
        pharmacy_adress.setText(" "+Open_info.get(position).getAddress_Pharmacie()+" ");
        medStorage.setText(" "+Open_info.get(position).getQuantiter_Stocker()+" ");

        state.setText(" "+Open_info.get(position).getNom_Pharmacie()+" ");

        if(check_if_works(Open_info.get(position).getTemps_Ouverture(),Open_info.get(position).getTemps_Fermeture()) == true){
            state.setText(" "+holder.View.getResources().getString(R.string.opned)+" ");
            state.setTextColor(holder.View.getResources().getColor(R.color.selectColor));
        }else{
            state.setText(" "+holder.View.getResources().getString(R.string.closed)+" ");
            state.setTextColor(holder.View.getResources().getColor(R.color.errorColor));
        }


        myItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    //Todo
    public String getToday(){
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.UK);
        String today="";
        Calendar NOW= Calendar.getInstance();
        today=dayFormat.format(NOW.getTime());

        return today;
    }
    public boolean check_if_works(String startTime,String endTime){
        inputParser = new SimpleDateFormat("HH:mm", Locale.UK);
        Calendar NOW= Calendar.getInstance();
        int H=NOW.get(Calendar.HOUR_OF_DAY);
        int M=NOW.get(Calendar.MINUTE);
        String MM=""+M;
        String HH=""+H;
        if(M<=9 ){MM="0"+MM;}
        if(H<=9 ){HH="0"+HH;}
        String  CURRENT_TIME=H + ":" + MM;
        //////////
        CURRENT_TIME_date = parseDate(H + ":" + MM);
        StartTime = parseDate(startTime);
        EndTime = parseDate(endTime);
        if ( StartTime.before( CURRENT_TIME_date ) && EndTime.after(CURRENT_TIME_date)) {
            MARKET_IS_OPENED=true;
        }else{ MARKET_IS_OPENED=false;}
        return MARKET_IS_OPENED;
    }
    public Date parseDate(String date) {

        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }
    //Todo

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return Open_info.size();
    }

}