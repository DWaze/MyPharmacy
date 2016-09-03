package com.srdeveloppement.atelier.mypharmacy.User.SearchPharmacy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.srdeveloppement.atelier.mypharmacy.Data.GetData;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.PharmacieSmall;
import com.srdeveloppement.atelier.mypharmacy.R;

public class PharmacyViewHolder extends RecyclerView.ViewHolder {

    private final TextView pharmacy_name;
    TextView pharmacy_real_adress;
    String responce;
    final ToggleButton fav_button;
    String IDUser;
    final RelativeLayout myItem;
    String Params;
    public PharmacyViewHolder(View itemView) {
        super(itemView);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(itemView.getContext());
        IDUser= sharedPref.getString("IDUser", "");
        pharmacy_name = (TextView) itemView.findViewById(R.id.pharmacy_name);
        myItem=(RelativeLayout)itemView.findViewById(R.id.myItem);
        pharmacy_real_adress = (TextView) itemView.findViewById(R.id.pharmacy_real_adress);
        fav_button = (ToggleButton) itemView.findViewById(R.id.fav_button);
    }

    public void bind(final PharmacieSmall model) {
        pharmacy_name.setText(model.getNom_Pharmacie());



        pharmacy_name.setText(model.getNom_Pharmacie());
        pharmacy_real_adress.setText(model.getAddress_Pharmacie());


        if(model.getFavoriser().equals("true")){
            fav_button.setChecked(true);
        }else{
            fav_button.setChecked(false);
        }



        fav_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fav_button.isChecked()) {
                    fav_button.setChecked(true);
                    Params="IDUtilisateur="+IDUser+"&IDPharmacie="+model.getIDPharmaci()+"&Operation="+"add";
                    PharmaAddFav adf = new PharmaAddFav(Params);
                    adf.execute();
                } else {
                    fav_button.setChecked(false);
                    Params="IDUtilisateur="+IDUser+"&IDPharmacie="+model.getIDPharmaci()+"&Operation="+"delete";
                    PharmaAddFav adf = new PharmaAddFav(Params);
                    adf.execute();
                }
            }
        });

        myItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myInent = new Intent(v.getContext(), pharmacyLocation.class);
                myInent.putExtra("IDPHarmacie", model.getIDPharmaci()+"");
                v.getContext().startActivity(myInent);

            }
        });

    }
    public class PharmaAddFav extends GetData {

        public PharmaAddFav(String Params) {
            super(Params);
        }

        public void execute() {
            ProcessData data = new ProcessData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/Fav_Pharmacie.php");
        }

        public class ProcessData extends DownloadData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce=WebData;
                if(!responce.equals("Error connecting to server")){
                    if(responce.equals("Fav Added succefuly\n")){
                        Toast.makeText(itemView.getContext(), R.string.fav_added, Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(itemView.getContext(), R.string.fav_removed, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(itemView.getContext(),R.string.SERVER_ERROR,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
