package com.srdeveloppement.atelier.mypharmacy.User.SearchPharmacy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.srdeveloppement.atelier.mypharmacy.Data.Model.PharmacieSmall;
import com.srdeveloppement.atelier.mypharmacy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 24/05/15
 */
public class MyAdapter_pharmacy extends RecyclerView.Adapter<PharmacyViewHolder> {
    private final LayoutInflater mInflater;
    private  ArrayList<PharmacieSmall> mPharmacy;

    public MyAdapter_pharmacy(Context context, ArrayList<PharmacieSmall> models) {
        mInflater = LayoutInflater.from(context);
        mPharmacy = new ArrayList<>(models);
    }

    @Override
    public PharmacyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.row_search_pharmacy, parent, false);
        return new PharmacyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PharmacyViewHolder holder, int position) {
        final PharmacieSmall model = mPharmacy.get(position);
        holder.bind(model);

    }

    @Override
    public int getItemCount() {
        return mPharmacy.size();
    }

    public void animateTo(ArrayList<PharmacieSmall> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<PharmacieSmall> newModels) {
        for (int i = mPharmacy.size() - 1; i >= 0; i--) {
            final PharmacieSmall model = mPharmacy.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(ArrayList<PharmacieSmall> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final PharmacieSmall model = newModels.get(i);
            if (!mPharmacy.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(ArrayList<PharmacieSmall> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final PharmacieSmall model = newModels.get(toPosition);
            final int fromPosition = mPharmacy.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public PharmacieSmall removeItem(int position) {
        final PharmacieSmall model = mPharmacy.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, PharmacieSmall model) {
        mPharmacy.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final PharmacieSmall model = mPharmacy.remove(fromPosition);
        mPharmacy.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }
    public void  loadNewData(ArrayList<PharmacieSmall> mPharmaList){
        mPharmacy = mPharmaList;
        notifyDataSetChanged();
    }
}
