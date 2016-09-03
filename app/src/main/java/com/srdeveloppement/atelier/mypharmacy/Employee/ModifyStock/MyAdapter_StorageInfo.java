package com.srdeveloppement.atelier.mypharmacy.Employee.ModifyStock;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.srdeveloppement.atelier.mypharmacy.Data.Model.Medicament;
import com.srdeveloppement.atelier.mypharmacy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 24/05/15
 */
public class MyAdapter_StorageInfo extends RecyclerView.Adapter<StorageInfoViewHolder> {
    public boolean first_click=true;
    public boolean second_click=false;
    private final LayoutInflater mInflater;
    Context c;
    TextView Storage_name;// heda howa
    private ArrayList<Medicament> mStorageInfo;

    public MyAdapter_StorageInfo(Context context, ArrayList<Medicament> models,Context c) {
        mInflater = LayoutInflater.from(context);
        mStorageInfo = new ArrayList<>(models);
        this.c=c;
    }//ouiani

    @Override
    public StorageInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.row_modif_stock, parent, false);
        Storage_name = (TextView) itemView.findViewById(R.id.storage_int);
        return new StorageInfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StorageInfoViewHolder holder, int position) {
        final Medicament model_s = mStorageInfo.get(position);
       // final Medicament model_s = mStorageInfo.get(position);
        holder.bind(model_s);
    }

    @Override
    public int getItemCount() {
        return mStorageInfo.size();
    }

    public void animateTo(ArrayList<Medicament> models_m) {
        applyAndAnimateRemovals(models_m);
        applyAndAnimateAdditions(models_m);
        applyAndAnimateMovedItems(models_m);
    }

    private void applyAndAnimateRemovals(List<Medicament> newModels) {
        for (int i = mStorageInfo.size() - 1; i >= 0; i--) {
            final Medicament model = mStorageInfo.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(ArrayList<Medicament> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Medicament model_s = newModels.get(i);
            if (!mStorageInfo.contains(model_s)) {
                addItem(i, model_s);
            }
        }
    }

    private void applyAndAnimateMovedItems(ArrayList<Medicament> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Medicament model_s = newModels.get(toPosition);
            final int fromPosition = mStorageInfo.indexOf(model_s);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Medicament removeItem(int position) {
        final Medicament model_s = mStorageInfo.remove(position);
        notifyItemRemoved(position);
        return model_s;
    }

    public void addItem(int position, Medicament model_s) {
        mStorageInfo.add(position, model_s);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Medicament model_s = mStorageInfo.remove(fromPosition);
        mStorageInfo.add(toPosition, model_s);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void  loadNewData(ArrayList<Medicament> mMedList){
        mStorageInfo = mMedList;
        notifyDataSetChanged();
    }


}
