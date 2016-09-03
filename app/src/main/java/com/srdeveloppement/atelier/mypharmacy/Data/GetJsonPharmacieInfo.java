package com.srdeveloppement.atelier.mypharmacy.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.srdeveloppement.atelier.mypharmacy.Data.Model.Pharmacie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lhadj on 2/20/2016.
 */
public class GetJsonPharmacieInfo extends GetData {
    SharedPreferences sharedPreferences ;
    private String LOG_TAG = GetJsonPharmacieInfo.class.getSimpleName() ;
    private ArrayList<Pharmacie> maPharmacie;
    String cb_SundayB,cb_MondayB,cb_TuesdayB,cb_WednesdayB,cb_ThursdayB,cb_FridayB,cb_SaturdayB;

    String Sunday_t,Sunday_f,Monday_t,Monday_f,Tuesday_t,Tuesday_f,Wednesday_t,Wednesday_f,Thursday_t,Thursday_f,Friday_t,Friday_f,Saturday_t,Saturday_f;


    public GetJsonPharmacieInfo(String Params, Context C) {
        super(Params);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(C);
    }


    public ArrayList<Pharmacie> getmPharmacie() {
        return maPharmacie;
    }
    public class DownloadJsonData extends DownloadData {


        public void processResult(){
            maPharmacie = new ArrayList<>();

            try {
                String result =sharedPreferences.getString("PharmaInfo","");
                JSONArray itemArray = new JSONArray(result);
                int IDPharmaci = 0;
                String Nom_Pharmacie = null;
                String Address_Pharmacie = null;
                Double Latitude = null;
                Double Langitude = null;
                int NumberOfMed = 0;
                for (int i = 0; i < itemArray.length(); i++) {
                    JSONObject jsonPharmacie = itemArray.getJSONObject(i);
                    if (i == 0) {
                        IDPharmaci = jsonPharmacie.getInt("IDPharmaci");
                        Nom_Pharmacie = jsonPharmacie.getString("Nom_Pharmacie");
                        Address_Pharmacie = jsonPharmacie.getString("Address_Pharmacie");
                        Latitude = jsonPharmacie.getDouble("Latitude");
                        Langitude = jsonPharmacie.getDouble("Langitude");
                        NumberOfMed=jsonPharmacie.getInt("NumberOfMeds");

                    }
                    if (i == 1) {
                        cb_SundayB = jsonPharmacie.getString("cb_SundayB");
                        Sunday_t = jsonPharmacie.getString("Sunday_t");
                        Sunday_f = jsonPharmacie.getString("Sunday_f");
                    }
                    if (i == 2) {
                        cb_MondayB = jsonPharmacie.getString("cb_MondayB");
                        Monday_t = jsonPharmacie.getString("Monday_t");
                        Monday_f = jsonPharmacie.getString("Monday_f");
                    }
                    if (i == 3) {
                        cb_TuesdayB = jsonPharmacie.getString("cb_TuesdayB");
                        Tuesday_t = jsonPharmacie.getString("Tuesday_t");
                        Tuesday_f = jsonPharmacie.getString("Tuesday_f");
                    }
                    if (i == 4) {
                        cb_WednesdayB = jsonPharmacie.getString("cb_WednesdayB");
                        Wednesday_t = jsonPharmacie.getString("Wednesday_t");
                        Wednesday_f = jsonPharmacie.getString("Wednesday_f");
                    }
                    if (i == 5) {
                        cb_ThursdayB = jsonPharmacie.getString("cb_ThursdayB");
                        Thursday_t = jsonPharmacie.getString("Thursday_t");
                        Thursday_f = jsonPharmacie.getString("Thursday_f");
                    }
                    if (i == 6) {
                        cb_FridayB = jsonPharmacie.getString("cb_FridayB");
                        Friday_t = jsonPharmacie.getString("Friday_t");
                        Friday_f = jsonPharmacie.getString("Friday_f");
                    }
                    if (i == 7) {
                        cb_SaturdayB = jsonPharmacie.getString("cb_SaturdayB");
                        Saturday_t = jsonPharmacie.getString("Saturday_t");
                        Saturday_f = jsonPharmacie.getString("Saturday_f");
                    }

                }

                Pharmacie mPharmacie = new Pharmacie(IDPharmaci, Nom_Pharmacie, Address_Pharmacie, Latitude, Langitude,NumberOfMed,
                        cb_SundayB,cb_MondayB,cb_TuesdayB,cb_WednesdayB,cb_ThursdayB,cb_FridayB,cb_SaturdayB,Sunday_t,Sunday_f,
                        Monday_t,Monday_f,Tuesday_t,Tuesday_f,Wednesday_t,Wednesday_f,Thursday_t,Thursday_f,Friday_t,Friday_f,
                        Saturday_t,Saturday_f);

                maPharmacie.add(mPharmacie);

            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        protected void onPostExecute(String webData){
            super.onPostExecute(webData);
            if(!webData.equals("Error connecting to server")){
                if(!webData.equals("pharmacie not validated yet!\n")){
                    if(!webData.equals("Pharmacie doas not existe!\n")){
                        if(!webData.isEmpty()){
                            sharedPreferences.edit().putString("PharmaInfo",webData).apply();
                            processResult();
                        }
                    }
                }
            }
            else{
                webData=sharedPreferences.getString("PharmaInfo","");
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
