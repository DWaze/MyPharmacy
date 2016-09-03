package com.srdeveloppement.atelier.mypharmacy.Pharmacist.TravailleurFragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.srdeveloppement.atelier.mypharmacy.Data.Model.Travailleur;
import com.srdeveloppement.atelier.mypharmacy.R;

import java.util.List;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 24/05/15
 */
public class Travailleur_Adapter extends RecyclerView.Adapter<Travailleur_ViewHolder> {

    private final LayoutInflater mInflater;
    private List<Travailleur> mModels;
    Context c;
    public Travailleur_Adapter(Context context, List<Travailleur> models, Context c) {
        mInflater = LayoutInflater.from(context);
        mModels = models;
        this.c=c;
    }

    @Override
    public Travailleur_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.row_travailleur_item, parent, false);
        return new Travailleur_ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Travailleur_ViewHolder holder, int position) {
        Travailleur model = mModels.get(position);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(c);
        int i = sharedPref.getInt("PharmacieState", 0);
        Log.d("SHARED_PREF", "" + i);
        holder.bind(model,mModels,position);
    }

    @Override
    public int getItemCount() {
        return (null != mModels ? mModels.size() : 0);
    }



    public void  loadNewData(List<Travailleur> mTravailleur){
        mModels = mTravailleur;
        notifyDataSetChanged();
    }

}
