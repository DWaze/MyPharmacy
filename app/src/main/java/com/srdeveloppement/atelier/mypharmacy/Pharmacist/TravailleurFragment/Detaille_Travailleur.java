package com.srdeveloppement.atelier.mypharmacy.Pharmacist.TravailleurFragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.srdeveloppement.atelier.mypharmacy.Data.Model.Travailleur;
import com.srdeveloppement.atelier.mypharmacy.R;

public class Detaille_Travailleur extends AppCompatActivity {

    Button cancel,call;
    TextView IDPersson;
    TextView username;
    TextView name;
    TextView prenom;
    TextView email;
    TextView Telephone;
    TextView Address,Gender;
    TextView IDDiplome_Reconnaisance;
    private static final int PERMISSIONS_REQUEST_PHONE_CALL = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_detaille__pharmacien);


        IDPersson =(TextView)findViewById(R.id.IDPersson);
        username =(TextView)findViewById(R.id.username);
        name =(TextView)findViewById(R.id.name);
        prenom =(TextView)findViewById(R.id.prenom);
        email =(TextView)findViewById(R.id.email);
        Telephone =(TextView)findViewById(R.id.Telephone2);
        Address =(TextView)findViewById(R.id.Address);
        Gender =(TextView)findViewById(R.id.Gender);
        IDDiplome_Reconnaisance =(TextView)findViewById(R.id.IDDiplome_Reconnaisance2);
        cancel=(Button)findViewById(R.id.x3);


        Intent intent = getIntent();
        Travailleur travailleur = (Travailleur)intent.getSerializableExtra("TravailleurItem");
        IDPersson.setText(" "+travailleur.getIDPersson()+" ");
        username.setText(" "+travailleur.getUsername()+" ");
        name.setText(" "+travailleur.getName()+" ");
        prenom.setText(" "+travailleur.getPrenom()+" ");
        email.setText(" "+travailleur.getEmail()+" ");
        Telephone.setText(" "+travailleur.getTelephone()+" ");
        Address.setText(" "+travailleur.getAddress()+" ");
        Gender.setText(" "+travailleur.getGender()+" ");
        IDDiplome_Reconnaisance.setText(" "+travailleur.getIDDiplome_Reconnaisance()+" ");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        call = (Button) findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_PHONE_CALL);
                } else {
                    String phone = Telephone.getText().toString().trim();
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
                    String phone = Telephone.getText().toString().trim();
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                    startActivity(intent);
                }
            } else {
                Toast.makeText(this, R.string.call_perm, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
