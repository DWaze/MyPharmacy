package com.srdeveloppement.atelier.mypharmacy.Data;

import android.util.Log;

import com.google.gson.Gson;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.TravailleurD;

import java.util.ArrayList;

/**
 * Created by lhadj on 2/20/2016.
 */
public class GetJsonTravailleurLog extends GetData {
    private String LOG_TAG = GetJsonTravailleurLog.class.getSimpleName() ;
    private ArrayList<TravailleurD> mPerssone;

    public GetJsonTravailleurLog(String Params) {
        super(Params);
    }


    public ArrayList<TravailleurD> getmPerssone() {
        return mPerssone;
    }
    public class DownloadJsonData extends DownloadData {


        public void processResult(){
            mPerssone = new ArrayList<>();
            try {
                Gson gs = new Gson();

                String result = getmData();

                TravailleurD[] liste= gs.fromJson(result, TravailleurD[].class);
//                JSONObject jsonData = new JSONObject(getmData());
//                JSONArray itemArray = jsonData.getJSONArray(PERSSONE_ITEMS);
                for(int i=0;i<=liste.length-1;i++){
                    TravailleurD jsonPerssone = liste[i];
                    String IDPersson= jsonPerssone.getIDPersson();
                    String username = jsonPerssone.getUsername();
                    String name = jsonPerssone.getName();
                    String prenom = jsonPerssone.getPrenom();
                    String email = jsonPerssone.getEmail();
                    String Telephone =jsonPerssone.getTelephone();
                    String Address =jsonPerssone.getAddress();
                    String IDDiplome_Reconnaisance =jsonPerssone.getIDDiplome_Reconnaisance();
                    String IDDirecteur =jsonPerssone.getIDDirecteur();


                    TravailleurD PerssoneObject = new TravailleurD(IDPersson,username,name,prenom,email,Telephone,Address,IDDiplome_Reconnaisance,IDDirecteur);

                    mPerssone.add(PerssoneObject);
                }
            }catch (Exception e){
                return;
            }

            for(TravailleurD singlePhoto:mPerssone){
                Log.v(LOG_TAG, singlePhoto.toString());
            }
        }
        protected void onPostExecute(String webData){
            super.onPostExecute(webData);
            if(!webData.equals("Error connecting to server")){
                if(!webData.isEmpty()){
                    processResult();
                }else{

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
