package com.srdeveloppement.atelier.mypharmacy.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.MedicamentUser;

import java.util.ArrayList;

/**
 * Created by lhadj on 2/20/2016.
 */
public class GetJsonMedicamentListeUser extends GetData {
    SharedPreferences sharedPreferences ;
    private String LOG_TAG = GetJsonMedicamentListeUser.class.getSimpleName() ;
    private ArrayList<MedicamentUser> mMedicament;

    public GetJsonMedicamentListeUser(String Params, Context C) {
        super(Params);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(C);
    }


    public ArrayList<MedicamentUser> getmMedicament() {
        return mMedicament;
    }

    public class DownloadJsonData extends DownloadData {


        public void processResult(){
            mMedicament = new ArrayList<>();
            try {
                Gson gs = new Gson();

                String result = sharedPreferences.getString("MedListOffUser","");

                MedicamentUser[] liste= gs.fromJson(result, MedicamentUser[].class);

                for(int i=0;i<=liste.length-1;i++){
                    MedicamentUser jsonMedicament = liste[i];
                    String IDMedicament= jsonMedicament.getIDMedicament();
                    String Nom_Med = jsonMedicament.getNom_Med();
                    String Dosage = jsonMedicament.getDosage();
                    String Uniter = jsonMedicament.getUniter();
                    String Age = jsonMedicament.getAge();
                    String Type = jsonMedicament.getType();
                    String Favoriser = jsonMedicament.getFavoriser();


                    MedicamentUser medicamentObject = new MedicamentUser(IDMedicament,Nom_Med,Dosage,Uniter,Age,Type,Favoriser);

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
                        sharedPreferences.edit().putString("MedListOffUser",webData).apply();
                        processResult();
                    }
                }
            }
            else{
                webData=sharedPreferences.getString("MedListOffUser","");
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
