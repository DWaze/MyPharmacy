package com.srdeveloppement.atelier.mypharmacy.User.SearchMedicament;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.srdeveloppement.atelier.mypharmacy.Data.Model.MedicamentUser;
import com.srdeveloppement.atelier.mypharmacy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 24/05/15
 */
public class MyAdapter_medicament extends RecyclerView.Adapter<MedicamentViewHolder> {

    private final LayoutInflater mInflater;
    private ArrayList<MedicamentUser> mMedicament;

    public MyAdapter_medicament(Context context, ArrayList<MedicamentUser> models) {
        mInflater = LayoutInflater.from(context);
        mMedicament = new ArrayList<>(models);
    }

    @Override
    public MedicamentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.row_search_medicament, parent, false);
        return new MedicamentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MedicamentViewHolder holder, int position) {
        final MedicamentUser model_m = mMedicament.get(position);
        holder.bind(model_m);
    }

    @Override
    public int getItemCount() {
        return mMedicament.size();
    }

    public void animateTo(ArrayList<MedicamentUser> models_m) {
        applyAndAnimateRemovals(models_m);
        applyAndAnimateAdditions(models_m);
        applyAndAnimateMovedItems(models_m);
    }

    private void applyAndAnimateRemovals(List<MedicamentUser> newModels) {
        for (int i = mMedicament.size() - 1; i >= 0; i--) {
            final MedicamentUser model = mMedicament.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(ArrayList<MedicamentUser> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final MedicamentUser model_m = newModels.get(i);
            if (!mMedicament.contains(model_m)) {
                addItem(i, model_m);
            }
        }
    }

    private void applyAndAnimateMovedItems(ArrayList<MedicamentUser> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final MedicamentUser model_m = newModels.get(toPosition);
            final int fromPosition = mMedicament.indexOf(model_m);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public MedicamentUser removeItem(int position) {
        final MedicamentUser model_m = mMedicament.remove(position);
        notifyItemRemoved(position);
        return model_m;
    }

    public void addItem(int position, MedicamentUser model_m) {
        mMedicament.add(position, model_m);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final MedicamentUser model_m = mMedicament.remove(fromPosition);
        mMedicament.add(toPosition, model_m);
        notifyItemMoved(fromPosition, toPosition);
    }
    public void  loadNewData(ArrayList<MedicamentUser> mMedList){
        mMedicament = mMedList;
        notifyDataSetChanged();
    }
}
