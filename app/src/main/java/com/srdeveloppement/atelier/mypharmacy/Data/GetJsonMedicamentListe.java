package com.srdeveloppement.atelier.mypharmacy.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.Medicament;

import java.util.ArrayList;

/**
 * Created by lhadj on 2/20/2016.
 */
public class GetJsonMedicamentListe extends GetData {
    SharedPreferences sharedPreferences ;
    private String LOG_TAG = GetJsonMedicamentListe.class.getSimpleName() ;
    private ArrayList<Medicament> mMedicament;
    private Context C;
    public GetJsonMedicamentListe(String Params, Context C) {
        super(Params);
        this.C=C;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(C);
    }


    public ArrayList<Medicament> getmMedicament() {
        return mMedicament;
    }

    public class DownloadJsonData extends DownloadData {


        public void processResult(){
            mMedicament = new ArrayList<>();

            try {
                Gson gs = new Gson();

                String result = sharedPreferences.getString("MedListOff","");

                Medicament[] liste= gs.fromJson(result, Medicament[].class);

                for(int i=0;i<=liste.length-1;i++){
                    Medicament jsonMedicament = liste[i];
                    String IDMedicament= jsonMedicament.getIDMedicament();
                    String Nom_Med = jsonMedicament.getNom_Med();
                    String Dosage = jsonMedicament.getDosage();
                    String Uniter = jsonMedicament.getUniter();
                    String Age = jsonMedicament.getAge();
                    String Type = jsonMedicament.getType();
                    String Quantiter_Stocker = jsonMedicament.getQuantiter_Stocker();


                    Medicament medicamentObject = new Medicament(IDMedicament,Nom_Med,Dosage,Uniter,Age,Type,Quantiter_Stocker);

                    mMedicament.add(medicamentObject);
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
                        sharedPreferences.edit().putString("MedListOff",webData).apply();
                        processResult();
                    }
                }
            }
            else{
                webData=sharedPreferences.getString("MedListOff","");
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
