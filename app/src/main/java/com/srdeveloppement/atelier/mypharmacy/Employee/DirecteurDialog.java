package com.srdeveloppement.atelier.mypharmacy.Employee;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.srdeveloppement.atelier.mypharmacy.Data.GetData;
import com.srdeveloppement.atelier.mypharmacy.Data.GetJsonDirecteurInfo;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.Directeur;
import com.srdeveloppement.atelier.mypharmacy.R;

import java.util.ArrayList;

public class DirecteurDialog extends AppCompatActivity {

    String responce;
    Button cancel, call;
    ArrayList<Directeur> mDirecteurInfo;
    TextView IDPersson, gender, name, prenom, email, Telephone2;
    private static final int PERMISSIONS_REQUEST_PHONE_CALL = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedpref = PreferenceManager.getDefaultSharedPreferences(this);
        String IDDirecteur = sharedpref.getString("IDPDirecteur", "");
        setContentView(R.layout.activity_directeur_dialog);
        cancel = (Button) findViewById(R.id.x3);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        IDPersson = (TextView) findViewById(R.id.IDPersson);
        gender = (TextView) findViewById(R.id.Gender);
        name = (TextView) findViewById(R.id.prenom);
        prenom = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        Telephone2 = (TextView) findViewById(R.id.Telephone2);
        showDirecteurInfo df = new showDirecteurInfo("IDPdirecteur=" + IDDirecteur);
        df.execute();

        call = (Button) findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_PHONE_CALL);
                } else {
                String phone = Telephone2.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                startActivity(intent);
                }
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_PHONE_CALL) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_PHONE_CALL);
                } else {
                    String phone = Telephone2.getText().toString().trim();
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                    startActivity(intent);
                }
            } else {
                Toast.makeText(this, R.string.call_perm, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class showDirecteurInfo extends GetJsonDirecteurInfo {

        public showDirecteurInfo(String Params) {
            super(Params,getApplicationContext());
        }

        public void execute() {
            ProcessData data = new ProcessData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/DirecteurInfo.php");
        }

        public class ProcessData extends DownloadJsonData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce=WebData;
                if(!responce.equals("Error connecting to server")){
                    if(!responce.equals("You have No Pharmacie !\n")){
                        if(!responce.isEmpty()){
                            mDirecteurInfo = getmPerssone();
                            name.setText(" "+mDirecteurInfo.get(0).getName()+" ");
                            prenom.setText(" "+mDirecteurInfo.get(0).getPrenom()+" ");
                            email.setText(" "+mDirecteurInfo.get(0).getEmail()+" ");
                            Telephone2.setText(" "+mDirecteurInfo.get(0).getDirecteurPhone()+" ");
                            gender.setText(" "+mDirecteurInfo.get(0).getGender()+" ");
                        }else{
                            Toast.makeText(getApplicationContext(), R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), R.string.u_hv_no_ph, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),R.string.SERVER_ERROR,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
