package com.srdeveloppement.atelier.mypharmacy.Data;

import android.util.Log;

import com.google.gson.Gson;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.User;

import java.util.ArrayList;

/**
 * Created by lhadj on 2/20/2016.
 */
public class GetJsonUserInfo extends GetData {
    private String LOG_TAG = GetJsonUserInfo.class.getSimpleName() ;
    private ArrayList<User> mPerssone;

    public GetJsonUserInfo(String Params) {
        super(Params);
    }


    public ArrayList<User> getmPerssone() {
        return mPerssone;
    }
    public class DownloadJsonData extends DownloadData {


        public void processResult(){
            mPerssone = new ArrayList<>();
            try {
                Gson gs = new Gson();

                String result = getmData();

                User[] liste= gs.fromJson(result, User[].class);
//                JSONObject jsonData = new JSONObject(getmData());
//                JSONArray itemArray = jsonData.getJSONArray(PERSSONE_ITEMS);
                for(int i=0;i<=liste.length-1;i++){
                    User jsonPerssone = liste[i];
                    String IDPersson= jsonPerssone.getIDPersson();
                    String username = jsonPerssone.getUsername();
                    String name = jsonPerssone.getName();
                    String prenom = jsonPerssone.getPrenom();
                    String email = jsonPerssone.getEmail();
                    String Date_Naissance  = jsonPerssone.getDate_Naissance();
                    String Countery = jsonPerssone.getCountery();


                    User PerssoneObject = new User(IDPersson,username,name,prenom,email,Date_Naissance,Countery);

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
                                        processResult();
                                    }
                                }
                            }
                }
            }
            else{
                Log.d("WebData==Error","ConnectionProblem");
            }
        }
        protected String doInBackground(String... params) {
            return super.doInBackground(params[0]);
        }
}
}
