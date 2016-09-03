package com.srdeveloppement.atelier.mypharmacy.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.Travailleur;

import java.util.ArrayList;

/**
 * Created by lhadj on 2/20/2016.
 */
public class GetJsonTravailleurList extends GetData {
    SharedPreferences sharedPreferences ;
    private String LOG_TAG = GetJsonTravailleurList.class.getSimpleName() ;
    private ArrayList<Travailleur> mPerssone;

    public GetJsonTravailleurList(String Params, Context C) {
        super(Params);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(C);
    }


    public ArrayList<Travailleur> getmPerssone() {
        return mPerssone;
    }
    public class DownloadJsonData extends DownloadData {


        public void processResult(){
            mPerssone = new ArrayList<>();
            try {
                Gson gs = new Gson();

                String result = sharedPreferences.getString("TrList","");

                Travailleur[] liste= gs.fromJson(result, Travailleur[].class);
//                JSONObject jsonData = new JSONObject(getmData());
//                JSONArray itemArray = jsonData.getJSONArray(PERSSONE_ITEMS);
                for(int i=0;i<=liste.length-1;i++){
                    Travailleur jsonPerssone = liste[i];
                    String IDPersson= jsonPerssone.getIDPersson();
                    String username = jsonPerssone.getUsername();
                    String Password = jsonPerssone.getPassword();
                    String name = jsonPerssone.getName();
                    String prenom = jsonPerssone.getPrenom();
                    String email = jsonPerssone.getEmail();
                    String gender = jsonPerssone.getGender();
                    String Telephone =jsonPerssone.getTelephone();
                    String Address =jsonPerssone.getAddress();
                    String IDDiplome_Reconnaisance =jsonPerssone.getIDDiplome_Reconnaisance();


                    Travailleur PerssoneObject = new Travailleur(IDPersson,username,Password,name,prenom,email,Telephone,gender,Address,IDDiplome_Reconnaisance);

                    mPerssone.add(PerssoneObject);
                }
            }catch (Exception e){
                return;
            }

            for(Travailleur singlePhoto:mPerssone){
                Log.v(LOG_TAG, singlePhoto.toString());
            }
        }
        protected void onPostExecute(String webData){
            super.onPostExecute(webData);
            if(!webData.equals("Error connecting to server")){
                if(!webData.isEmpty()){
                    sharedPreferences.edit().putString("TrList",webData).apply();
                    processResult();
                }
            }
            else{
                webData=sharedPreferences.getString("TrList","");
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
