package com.srdeveloppement.atelier.mypharmacy.Pharmacist.TravailleurFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.srdeveloppement.atelier.mypharmacy.Data.Model.Travailleur;
import com.srdeveloppement.atelier.mypharmacy.R;

import java.util.List;

public class Travailleur_ViewHolder extends RecyclerView.ViewHolder {

    private final TextView mName;
    private final TextView mTelephone;
    final Button delete;
    final RelativeLayout Travailleur_myItem;
    public Travailleur_ViewHolder(View itemView) {
        super(itemView);

        mName = (TextView) itemView.findViewById(R.id.Name);
        delete = (Button) itemView.findViewById(R.id.delete);
        Travailleur_myItem=(RelativeLayout)itemView.findViewById(R.id.myItem);
        mTelephone = (TextView) itemView.findViewById(R.id.Telephone);
    }

    public void bind(Travailleur model, final List<Travailleur> mTravailleurList, final int position) {
        mName.setText(model.getName()+" "+model.getPrenom());
        mTelephone.setText(model.getTelephone());
        Travailleur_myItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myInent = new Intent(v.getContext(), Detaille_Travailleur.class);
                Bundle extra = new Bundle();
                extra.putSerializable("TravailleurItem", mTravailleurList.get(position));
                myInent.putExtras(extra);
                v.getContext().startActivity(myInent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
