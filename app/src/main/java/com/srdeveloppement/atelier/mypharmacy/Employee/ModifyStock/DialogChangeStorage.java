package com.srdeveloppement.atelier.mypharmacy.Employee.ModifyStock;

import android.content.SharedPreferences;
import android.content.res.Configuration;
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

public class DialogChangeStorage extends AppCompatActivity {
    Button confirm ;
    Button x ;
    MaterialEditText storage;
    TextView med_Name;
    String name;
    //int storageN,item_pos;
    String New_Storage,storageN;
    String responce;
    String IDPDir;
    String IDMed;
    String age,dosage; boolean Storagechanged;
    String pharm_id; String type; String unitDosage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_change_storage);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        IDPDir = sharedPref.getString("IDPDirecteur", "");

        Bundle b = getIntent().getExtras();
        name = b.getString("med_name");
        storageN = b.getString("storage");
      // item_pos = b.getInt("position");
        age = b.getString("age");
        IDMed=b.getString("id_med");
        dosage = b.getString("dosage");
        Storagechanged = b.getBoolean("Storagechanged");
        pharm_id = b.getString("pharm_id");
        type = b.getString("type");
        unitDosage = b.getString("unitDosage");


        x=(Button)findViewById(R.id.x);
        confirm=(Button)findViewById(R.id.ok);
        med_Name=(TextView)findViewById(R.id.medicament_name);
        med_Name.setText(name);
        storage=(MaterialEditText)findViewById(R.id.new_storage);
        storage.setText(storageN+"");
        storage.setRawInputType(Configuration.KEYBOARD_12KEY);
        storage.setSelection(storage.getText().length());
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });// n
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                New_Storage= storage.getText().toString();
                if (isEmpty(storage)) {
                    storage.setError(getString(R.string.storage_is_empty));
                }else{
                    updateMed med = new updateMed("IDPDirecteur="+IDPDir+"&IDMedicament="+IDMed+"&Quantiter_Stocker="+New_Storage);
                    med.execute();

                    finish();
                }
            }
        });
    }
    boolean isEmpty(MaterialEditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public class updateMed extends GetData {

        public updateMed(String Params) {
            super(Params);
        }

        public void execute() {
            ProcessData data = new ProcessData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/Update_Stock.php");
        }

        public class ProcessData extends DownloadData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce = WebData;
                if(!responce.equals("Error connecting to server")){
                    if(responce.equals("Update succes!\n")){
                        Toast.makeText(getApplicationContext(), R.string.med_up_succ,Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), R.string.med_up_unsucc,Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
