package com.srdeveloppement.atelier.mypharmacy.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.PharmacieSmall;

import java.util.ArrayList;

/**
 * Created by lhadj on 2/20/2016.
 */
public class GetJsonPharmacieListeUser extends GetData {
    private String LOG_TAG = GetJsonPharmacieListeUser.class.getSimpleName() ;
    private ArrayList<PharmacieSmall> mPharmaList;
    SharedPreferences sharedPreferences ;

    public GetJsonPharmacieListeUser(String Params, Context C) {
        super(Params);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(C);
    }


    public ArrayList<PharmacieSmall> getmPharmacList() {
        return mPharmaList;
    }

    public class DownloadJsonData extends DownloadData {


        public void processResult(){
            mPharmaList = new ArrayList<>();
            try {
                Gson gs = new Gson();

                String result = sharedPreferences.getString("PharmListOffUser","");

                PharmacieSmall[] liste= gs.fromJson(result, PharmacieSmall[].class);

                for(int i=0;i<=liste.length-1;i++){
                    PharmacieSmall jsonMedicament = liste[i];
                    int IDPharmaci= jsonMedicament.getIDPharmaci();
                    String Nom_Pharmacie = jsonMedicament.getNom_Pharmacie();
                    String Address_Pharmacie = jsonMedicament.getAddress_Pharmacie();
                    Double Latitude = jsonMedicament.getLatitude();
                    Double Langitude = jsonMedicament.getLangitude();
                    int NumberOfMeds = jsonMedicament.getNumberOfMeds();
                    String Favoriser = jsonMedicament.getFavoriser();


                    PharmacieSmall medicamentObject = new PharmacieSmall(IDPharmaci,Nom_Pharmacie,Address_Pharmacie,Latitude,Langitude,NumberOfMeds,Favoriser);

                    mPharmaList.add(medicamentObject);
                }
            }catch (Exception e){
                return;
            }

        }
        protected void onPostExecute(String webData){
            super.onPostExecute(webData);
            if(!webData.equals("Error connecting to server")){
                if(!webData.equals("You have No Pharmacie !\n")){
                    if(!webData.isEmpty()){
                        sharedPreferences.edit().putString("PharmListOffUser",webData).apply();
                        processResult();
                    }
                }
            }
            else{
                webData=sharedPreferences.getString("PharmListOffUser","");
                if(!webData.equals("")){
                    processResult();
                }
                Log.d("WebData==Error","ConnectionProblem");
            }
        }
        protected String doInBackground(String... params) {
            return super.doInBackground(params[0]);
        }
}
}
