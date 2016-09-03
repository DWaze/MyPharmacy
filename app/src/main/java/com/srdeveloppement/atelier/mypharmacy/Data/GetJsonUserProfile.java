package com.srdeveloppement.atelier.mypharmacy.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.UserProfile;

import java.util.ArrayList;

/**
 * Created by lhadj on 2/20/2016.
 */
public class GetJsonUserProfile extends GetData {
    SharedPreferences sharedPreferences ;
    private String LOG_TAG = GetJsonUserProfile.class.getSimpleName() ;
    private ArrayList<UserProfile> mPerssone;
    private Context C;
    public GetJsonUserProfile(String Params,Context C) {
        super(Params);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(C);
    }


    public ArrayList<UserProfile> getmPerssone() {
        return mPerssone;
    }
    public class DownloadJsonData extends DownloadData {


        public void processResult(){
            mPerssone = new ArrayList<>();
            try {
                Gson gs = new Gson();

                String result = sharedPreferences.getString("UsrProf","");

                UserProfile[] liste= gs.fromJson(result, UserProfile[].class);
//                JSONObject jsonData = new JSONObject(getmData());
//                JSONArray itemArray = jsonData.getJSONArray(PERSSONE_ITEMS);
                for(int i=0;i<=liste.length-1;i++){
                    UserProfile jsonPerssone = liste[i];
                    String IDPersson= jsonPerssone.getIDPersson();
                    String username = jsonPerssone.getUsername();
                    String Password = jsonPerssone.getPassword();
                    String name = jsonPerssone.getName();
                    String prenom = jsonPerssone.getPrenom();
                    String email = jsonPerssone.getEmail();
                    String gender = jsonPerssone.getGender();
                    String Date_Naissance  = jsonPerssone.getDate_Naissance();
                    String Countery = jsonPerssone.getCountery();


                    UserProfile PerssoneObject = new UserProfile(IDPersson,username,Password,name,prenom,email,gender,Date_Naissance,Countery);

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
                                        sharedPreferences.edit().putString("UsrProf",webData).apply();
                                        processResult();
                                    }
                                }
                            }
                }
            }
            else{
                webData=sharedPreferences.getString("UsrProf","");
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