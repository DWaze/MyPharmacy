package com.srdeveloppement.atelier.mypharmacy.Data;

import android.util.Log;

import com.google.gson.Gson;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.MedPharmacie;

import java.util.ArrayList;

/**
 * Created by lhadj on 2/20/2016.
 */
public class GetJsonMedPharmacieList extends GetData {
    private String LOG_TAG = GetJsonMedPharmacieList.class.getSimpleName() ;
    private ArrayList<MedPharmacie> mMedPharmacie;

    public GetJsonMedPharmacieList(String Params) {
        super(Params);
    }


    public ArrayList<MedPharmacie> getmMedPharmacie() {
        return mMedPharmacie;
    }
    public class DownloadJsonData extends DownloadData {


        public void processResult(){
            mMedPharmacie = new ArrayList<>();
            try {
                Gson gs = new Gson();

                String result = getmData();

                MedPharmacie[] liste= gs.fromJson(result, MedPharmacie[].class);

                for(int i=0;i<=liste.length-1;i++){
                    MedPharmacie jsonmMedPharmacie = liste[i];
                    int IDPharmaci= jsonmMedPharmacie.getIDPharmaci();
                    String Nom_Pharmacie = jsonmMedPharmacie.getNom_Pharmacie();
                    String Address_Pharmacie = jsonmMedPharmacie.getAddress_Pharmacie();
                    double Latitude = jsonmMedPharmacie.getLatitude();
                    double Langitude = jsonmMedPharmacie.getLangitude();
                    int Quantiter_Stocker = jsonmMedPharmacie.getQuantiter_Stocker();
                    String Temps_Ouverture =jsonmMedPharmacie.getTemps_Ouverture();
                    String Temps_Fermeture =jsonmMedPharmacie.getTemps_Fermeture();

                    MedPharmacie mMedPharmacieObject = new MedPharmacie(IDPharmaci,Nom_Pharmacie,Address_Pharmacie,Latitude,Langitude,Quantiter_Stocker,Temps_Ouverture,Temps_Fermeture);

                    mMedPharmacie.add(mMedPharmacieObject);
                }
            }catch (Exception e){
                return;
            }

            for(MedPharmacie singlePhoto: mMedPharmacie){
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
