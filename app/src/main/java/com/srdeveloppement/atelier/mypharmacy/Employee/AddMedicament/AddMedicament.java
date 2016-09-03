package com.srdeveloppement.atelier.mypharmacy.Employee.AddMedicament;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.srdeveloppement.atelier.mypharmacy.Data.GetData;
import com.srdeveloppement.atelier.mypharmacy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMedicament extends Fragment  {
    MaterialEditText MName,dose;
    String MedName;
    String dosage;
    Button confirm;
    public String dosage_selection;
    public String age_med,type_medicament;
    String responce;
    String AgeArray [],dosageArray [],typeArray [];

    String language;
    SharedPreferences sharedPref;

    public AddMedicament() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_medicament, container, false);

        final Spinner dosage_unit = (Spinner) view.findViewById(R.id.type_spinner);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        language=sharedPref.getString("lng","en");
        ArrayAdapter<CharSequence> unitAdapter =ArrayAdapter.createFromResource(getActivity(),
                R.array.dosage_selection, R.layout.spinner_item);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dosage_unit.setAdapter(unitAdapter);
        dosage_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // dosage_selection = parent.getItemAtPosition(position).toString();
                dosageArray=getResources().getStringArray(R.array.dosage_selection_OR);
                dosage_selection = dosageArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Spinner age = (Spinner) view.findViewById(R.id.age_spinner);
        ArrayAdapter<CharSequence> ageAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.age_med, R.layout.spinner_item_dark_text);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age.setAdapter(ageAdapter);
        age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AgeArray=getResources().getStringArray(R.array.age_med_OR);
                age_med = AgeArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        final Spinner type_med = (Spinner) view.findViewById(R.id.type_med_spinner);
        ArrayAdapter<CharSequence> TypeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.type_medicament, R.layout.spinner_item_dark_text);
        TypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_med.setAdapter(TypeAdapter);
        type_med.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // type_medicament = parent.getItemAtPosition(position).toString();
                typeArray=getResources().getStringArray(R.array.type_medicament_OR);
                type_medicament = typeArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        MName = (MaterialEditText)view.findViewById(R.id.med_Name);
        dose = (MaterialEditText)view.findViewById(R.id.med_Dosage);
        confirm = (Button)view.findViewById(R.id.createAcc);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(MName)) {
                    MName.setError(getString(R.string.med_name_is_empty));
                    Toast.makeText(v.getContext(), R.string.med_name_is_empty, Toast.LENGTH_LONG).show();
                } else if (isEmpty(dose)) {
                    dose.setError(getString(R.string.dosage_is_empty));
                    Toast.makeText(v.getContext(), R.string.dosage_is_empty, Toast.LENGTH_LONG).show();
                } else{
                    MedName=MName.getText().toString();
                    dosage=dose.getText().toString();
                    String Params="Nom_Med="+MedName+"&Dosage="+dosage+"&Age="+age_med+"&Type="+type_medicament
                            +"&Uniter="+dosage_selection;
                    AddMedicamentWeb ad = new AddMedicamentWeb(Params);
                    ad.execute();


                }
            }
        });
        return view;
    }

    boolean isEmpty(MaterialEditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public class AddMedicamentWeb extends GetData {

        public AddMedicamentWeb(String Params) {
            super(Params);
        }

        public void execute() {
            ProcessData data = new ProcessData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/Add_Medicament.php");
        }

        public class ProcessData extends DownloadData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce = WebData;

                if(!responce.equals("Error connecting to server")){
                        Toast.makeText(getContext(), R.string.suv_med, Toast.LENGTH_LONG).show();

                    MName.setText("");
                    dose.setText("");
                }else{
                    Toast.makeText(getContext(), R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
