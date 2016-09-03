package com.srdeveloppement.atelier.mypharmacy.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.Directeur;

import java.util.ArrayList;

/**
 * Created by lhadj on 2/20/2016.
 */
public class GetJsonDirecteurInfo extends GetData {
    SharedPreferences sharedPreferences ;
    private String LOG_TAG = GetJsonDirecteurInfo.class.getSimpleName() ;
    private ArrayList<Directeur> mPerssone;

    public GetJsonDirecteurInfo(String Params, Context C) {
        super(Params);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(C);
    }


    public ArrayList<Directeur> getmPerssone() {
        return mPerssone;
    }
    public class DownloadJsonData extends DownloadData {


        public void processResult(){
            mPerssone = new ArrayList<>();
            try {
                Gson gs = new Gson();

                String result = sharedPreferences.getString("DirProf","");

                Directeur[] liste= gs.fromJson(result, Directeur[].class);
//                JSONObject jsonData = new JSONObject(getmData());
//                JSONArray itemArray = jsonData.getJSONArray(PERSSONE_ITEMS);
                for(int i=0;i<=liste.length-1;i++){
                    Directeur jsonPerssone = liste[i];
                    String IDPersson= jsonPerssone.getIDPersson();
                    String username = jsonPerssone.getUsername();
                    String password = jsonPerssone.getPassword();
                    String name = jsonPerssone.getName();
                    String prenom = jsonPerssone.getPrenom();
                    String email = jsonPerssone.getEmail();
                    String IDContratPharmacie = jsonPerssone.getIDContratPharmacie();
                    String DirecteurPhone = jsonPerssone.getDirecteurPhone();
                    String gender = jsonPerssone.getGender();


                    Directeur PerssoneObject = new Directeur(IDPersson,username,password,name,prenom,email,IDContratPharmacie,DirecteurPhone,gender);

                    mPerssone.add(PerssoneObject);
                }
            }catch (Exception e){
                return;
            }

        }
        protected void onPostExecute(String webData){
            super.onPostExecute(webData);
            if(!webData.equals("Error connecting to server")){
                        if(!webData.equals("Sorry invalide acount Type !\n")){
                            if(!webData.equals("User Not Found !\n")){
                                if(!webData.equals("Please try againe later !\n")){
                                    if(!webData.isEmpty()){
                                        sharedPreferences.edit().putString("DirProf",webData).apply();
                                        processResult();
                                    }
                                }
                            }
                }
            }
            else{
                    webData=sharedPreferences.getString("DirProf","");
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
