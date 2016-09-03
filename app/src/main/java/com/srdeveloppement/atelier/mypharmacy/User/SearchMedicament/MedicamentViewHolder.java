package com.srdeveloppement.atelier.mypharmacy.User.SearchMedicament;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.srdeveloppement.atelier.mypharmacy.Data.GetData;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.MedicamentUser;
import com.srdeveloppement.atelier.mypharmacy.R;

public class MedicamentViewHolder extends RecyclerView.ViewHolder {

    private final TextView medicament_name;
    TextView pharmacy_name,age_name;
    TextView Dosage_name,type_name;
    final ToggleButton fav_med;
    final RelativeLayout myItem;
    String IDUser;
    String Params;
    String IDMED;String language;
    SharedPreferences sharedPref;
    Context c;
    String Uni,Typ,Ag;
    public int Unip=0;
    public int Typp=0;
    public int Agp=0;
    String AgeArray []=itemView.getResources().getStringArray(R.array.age_med_OR);
    String UniArray []=itemView.getResources().getStringArray(R.array.dosage_selection_OR);;
    String typeArray []=itemView.getResources().getStringArray(R.array.type_medicament_OR);;
    String responce;
    public MedicamentViewHolder(View itemView) {
        super(itemView);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(itemView.getContext());



        IDUser= sharedPref.getString("IDUser", "");
        medicament_name = (TextView) itemView.findViewById(R.id.medicament_name);
        type_name = (TextView) itemView.findViewById(R.id.type_name);
        age_name = (TextView) itemView.findViewById(R.id.age_name);
        myItem=(RelativeLayout)itemView.findViewById(R.id.myItem);
        pharmacy_name = (TextView) itemView.findViewById(R.id.pharmacy_name);
        Dosage_name = (TextView) itemView.findViewById(R.id.dosage_name);
        fav_med = (ToggleButton) itemView.findViewById(R.id.fav_med);

      // UniArray =itemView.getResources().getStringArray(R.array.dosage_selection_OR);
       //  AgeArray =itemView.getResources().getStringArray(R.array.age_med_OR);
        // typeArray =itemView.getResources().getStringArray(R.array.type_medicament_OR);
    }

    public void bind(final MedicamentUser model_m) {
        Unip=0;
        Typp=0;
        Agp=0;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(itemView.getContext());
        language=sharedPref.getString("lng","en");


        Uni=model_m.getUniter();
        Typ=model_m.getType();
        Ag=model_m.getAge();

       if(language.equals("fr")||language.equals("ar")){
            for(int i=0;i<UniArray.length;i++){
                UniArray=itemView.getResources().getStringArray(R.array.dosage_selection_OR);
                if(!Uni.equals(UniArray[i])){
                    Unip++;
                }else{break;}
            }
            for(int i=0;i<typeArray.length;i++){
                typeArray=itemView.getResources().getStringArray(R.array.type_medicament_OR);
                if(!Typ.equals(typeArray[i])){Typp++;}else{break;}
            }
            for(int i=0;i<AgeArray.length;i++){
                AgeArray=itemView.getResources().getStringArray(R.array.age_med_OR);
                if(!Ag.equals(AgeArray[i])){Agp++;}else{break;}
            }

            UniArray=itemView.getResources().getStringArray(R.array.dosage_selection);
            Uni=UniArray[Unip];

            AgeArray=itemView.getResources().getStringArray(R.array.age_med);
            Ag=AgeArray[Agp];

            typeArray=itemView.getResources().getStringArray(R.array.type_medicament);
            Typ=typeArray[Typp];
        }

        this.IDMED =model_m.getIDMedicament();
        medicament_name.setText(model_m.getNom_Med());
        type_name.setText(Typ);
        age_name.setText(Ag);
        Dosage_name.setText(model_m.getDosage()+" "+Uni);

        if(model_m.getFavoriser().equals("true")){
            fav_med.setChecked(true);
        }else{
            fav_med.setChecked(false);
        }




        fav_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        if (fav_med.isChecked()) {
                            fav_med.setChecked(true);
                            Params="IDUtilisateur="+IDUser+"&IDMedicament="+model_m.getIDMedicament()+"&Operation="+"add";
                            MedicamentADFav adf = new MedicamentADFav(Params);
                            adf.execute();
                        } else {
                            fav_med.setChecked(false);
                            Params="IDUtilisateur="+IDUser+"&IDMedicament="+model_m.getIDMedicament()+"&Operation="+"delete";
                            MedicamentADFav adf = new MedicamentADFav(Params);
                            adf.execute();
                        }
            }
        });




        myItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myInent = new Intent(v.getContext(), medicamentLocation.class);
                myInent.putExtra("IDMED",IDMED);
                v.getContext().startActivity(myInent);
            }
        });
    }


    public class MedicamentADFav extends GetData {

        public MedicamentADFav(String Params) {
            super(Params);
        }

        public void execute() {
            ProcessData data = new ProcessData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/Favorie_Med.php");
        }

        public class ProcessData extends DownloadData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce=WebData;
                if(!responce.equals("Error connecting to server")){
                    if(responce.equals("Fav Added succefuly\n")){
                        Toast.makeText(itemView.getContext(), R.string.fav_added, Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(itemView.getContext(), R.string.fav_removed, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(itemView.getContext(),R.string.SERVER_ERROR,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
