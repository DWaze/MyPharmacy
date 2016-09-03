package com.srdeveloppement.atelier.mypharmacy.Pharmacist.TravailleurFragment;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.srdeveloppement.atelier.mypharmacy.Data.GetData;
import com.srdeveloppement.atelier.mypharmacy.R;

public class TravailleurDialogue extends AppCompatActivity {

    Button ok,cancel;
    MaterialEditText employeID;
    MaterialEditText password;
    String responce;
    String IDPDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_travailleur);
        cancel = (Button)findViewById(R.id.x);
        ok = (Button)findViewById(R.id.ok);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        IDPDir= sharedPref.getString("IDPDirecteur", "");

        employeID =(MaterialEditText)findViewById(R.id.IDEmployee);
        employeID.setRawInputType(Configuration.KEYBOARD_12KEY);
        password =(MaterialEditText)findViewById(R.id.password);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EmployeeID = employeID.getText().toString();
                String passwordText = password.getText().toString();
                if (EmployeeID.trim().equals("")) {
                    employeID.setError(getString(R.string.emp_id_empty));
                }else if(passwordText.trim().equals("")){
                    password.setError(getString(R.string.Password_is_empty));
                } else {
                    String Params = "IDPTravailleur=" + EmployeeID + "&IDPDirecteur=" + IDPDir +"&Password="+passwordText;
                    printTravailleurList pt = new printTravailleurList(Params);
                    pt.execute();
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
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/AddEmployee.php");
        }

        public class ProcessData extends DownloadData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce = WebData;
                if(!responce.equals("Error connecting to server")){
                    if(!responce.equals("wrong password!\n")){
                        if(responce.equals("employee Allredy working in a pharmacie !\n")){
                            employeID.setError(getString(R.string.emp_in_work) );
                        }else{
                            finish();
                        }
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
