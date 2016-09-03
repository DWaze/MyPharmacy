package com.srdeveloppement.atelier.mypharmacy.Employee.ModifyStock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.srdeveloppement.atelier.mypharmacy.Data.Model.Medicament;
import com.srdeveloppement.atelier.mypharmacy.R;

public class StorageInfoViewHolder extends RecyclerView.ViewHolder {

    private final TextView medicament_name;
    TextView age_name,Storage_name;
    TextView Dosage_name,type_name;
    final RelativeLayout myItem;
    String responce;
String TheStorage;
    String language;
    SharedPreferences sharedPref;
    Context c;
    String Uni,Typ,Ag;

    String AgeArray [],UniArray [],typeArray [];

    public StorageInfoViewHolder(View itemView) {
        super(itemView);


        medicament_name = (TextView) itemView.findViewById(R.id.medicament_name);
        type_name = (TextView) itemView.findViewById(R.id.type_name);
        age_name = (TextView) itemView.findViewById(R.id.age_name);

        Storage_name = (TextView) itemView.findViewById(R.id.storage_int);
        myItem=(RelativeLayout)itemView.findViewById(R.id.myItem);
        Dosage_name = (TextView) itemView.findViewById(R.id.dosage_name);

        UniArray=itemView.getResources().getStringArray(R.array.dosage_selection_OR);
        AgeArray=itemView.getResources().getStringArray(R.array.age_med_OR);
        typeArray=itemView.getResources().getStringArray(R.array.type_medicament_OR);

    }

    public void bind(final Medicament model_s) {
        int Unip=0;
        int Typp=0;
        int Agp=0;

        sharedPref = PreferenceManager.getDefaultSharedPreferences(itemView.getContext());
        language=sharedPref.getString("lng","en");


       Uni=model_s.getUniter();
        Typ=model_s.getType();
        Ag=model_s.getAge();
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

        medicament_name.setText(model_s.getNom_Med());
       type_name.setText(Typ);
       age_name.setText(Ag);
        Dosage_name.setText(model_s.getDosage() + " " + Uni);
        Storage_name.setText(model_s.getQuantiter_Stocker() + "");

        myItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent myInent = new Intent(v.getContext(), DialogChangeStorage.class);
                Bundle b = new Bundle();

                b.putString("id_med", model_s.getIDMedicament());
                b.putString("med_name", model_s.getNom_Med());
                if(model_s.getQuantiter_Stocker().equals("0")||model_s.getQuantiter_Stocker().equals("")){
                    b.putString("storage", "");
                }else{
                    b.putString("storage", model_s.getQuantiter_Stocker());
                }

                b.putString("age", model_s.getAge());
                b.putString("dosage", model_s.getDosage());
                b.putString("type", model_s.getType());
                b.putString("unitDosage", model_s.getUniter());

                myInent.putExtras(b);
                v.getContext().startActivity(myInent);
            }
        });
// khali mba3ed nahkiou okk hhhhh
    }

}