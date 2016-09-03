package com.srdeveloppement.atelier.mypharmacy.Pharmacist.myPharmacy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.srdeveloppement.atelier.mypharmacy.Data.GetData;
import com.srdeveloppement.atelier.mypharmacy.R;

public class AreYouSureDialogue extends AppCompatActivity {

    Button cancel,ok;
    String responce;
    TextView v ;
    MaterialEditText password;
    String IDPersson;String IDPDire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_are_you_sure);

        cancel = (Button)findViewById(R.id.x);
        ok = (Button)findViewById(R.id.ok);
        v = (TextView)findViewById(R.id.warrningText);
        password= (MaterialEditText) findViewById(R.id.EnterPassword);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String TextTshow = intent.getStringExtra("Info");
        IDPersson= intent.getStringExtra("IDPersson");
        IDPDire= intent.getStringExtra("IDPDirecteur");
        final String code=intent.getStringExtra("code");
        v.setText(TextTshow);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String passwordText = password.getText().toString();
                if(passwordText.trim().equals("")){
                    password.setError(getString(R.string.Password_is_empty));
                }else{
                    if(code.equals("T")){
                        String Params = "IDPTravailleur="+IDPersson+"&IDPDirecteur="+IDPDire+"&Password="+passwordText;
                        printTravailleurList pt = new printTravailleurList(Params);
                        pt.execute();
                    }else{
                        String Params ="IDPDirecteur="+IDPDire+"&Password="+passwordText;
                        DeletePharmacieInterface deletePharmacieInterface = new DeletePharmacieInterface(Params);
                        deletePharmacieInterface.execute();

                    }

                }
            }
        });
    }

    public class printTravailleurList extends GetData {

        public printTravailleurList(String Params) {
            super(Params);
        }

        public void execute() {
            ProcessData data = new ProcessData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/RemoveTravailleur.php");
        }

        public class ProcessData extends DownloadData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce = WebData;
                if(!responce.equals("Error connecting to server")){
                    if(!responce.equals("wrong password!\n")){

                        Toast.makeText(getApplicationContext(), R.string.emp_delet_succ,Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        password.setError(getString(R.string.wrng_pass));
                    }
                }else{
                    Toast.makeText(getApplicationContext(), R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    public class DeletePharmacieInterface extends GetData {

        public DeletePharmacieInterface(String Params) {
            super(Params);
        }

        public void execute() {
            GetPharmacieData data = new GetPharmacieData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/DeletePharmacie.php");
        }

        public class GetPharmacieData extends DownloadData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce = WebData;
                if (!responce.equals("Error connecting to server")){
                    if(!responce.equals("wrong password!\n")){
                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        sharedPref.edit().putInt("PharmacieState",0).commit();
                        sharedPref.edit().putString("MedListOff","").apply();
                        sharedPref.edit().putString("PharmaInfo","").apply();
                        Toast.makeText(getApplicationContext(), R.string.parm_deleted_succ,Toast.LENGTH_SHORT).show();
                        finish();
                        Intent i = getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }else{
                        password.setError(getString(R.string.wrng_pass));
                    }
                }else{
                    Toast.makeText(getApplicationContext(), R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}
