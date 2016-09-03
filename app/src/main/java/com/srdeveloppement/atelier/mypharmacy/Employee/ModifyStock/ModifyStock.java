package com.srdeveloppement.atelier.mypharmacy.Employee.ModifyStock;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.srdeveloppement.atelier.mypharmacy.Data.GetData;
import com.srdeveloppement.atelier.mypharmacy.Data.GetJsonMedicamentListe;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.Medicament;
import com.srdeveloppement.atelier.mypharmacy.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModifyStock extends Fragment implements SearchView.OnQueryTextListener{


    public static ModifyStock newInstance() {
        return new ModifyStock();
    }

    private RecyclerView mRecyclerView;
    private MyAdapter_StorageInfo mAdapter;
    String responce;
    String IDPDir;
    ArrayList<Medicament> mMedicamentListe;
    TextView emptyView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_modify_stock, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.modifStock_list);
        emptyView=(TextView)view.findViewById(R.id.no_item_found);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        IDPDir = sharedPref.getString("IDPDirecteur", "");
        printMedicamentListe pl = new printMedicamentListe("IDPDirecteur="+IDPDir,getContext());
        pl.execute();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);



    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.user_nav, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if(mMedicamentListe!=null){
            final ArrayList<Medicament> filteredModelList = filter(mMedicamentListe, query);
            mAdapter.animateTo(filteredModelList);
            mRecyclerView.scrollToPosition(0);
            return true;
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    private ArrayList<Medicament> filter(ArrayList<Medicament> models, String query) {
        query = query.toLowerCase();

        final ArrayList<Medicament> filteredModelList = new ArrayList<>();
        for (Medicament model : models) {
            final String text = model.getNom_Med().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(mMedicamentListe!=null){
            printMedicamentListe pl = new printMedicamentListe("IDPDirecteur="+IDPDir,getContext());
            pl.execute();
            mAdapter.loadNewData(mMedicamentListe);
        }

    }

    public class printMedicamentListe extends GetJsonMedicamentListe {

        public printMedicamentListe(String Params, Context C) {
            super(Params,C);
        }

        public void execute() {
            ProcessData data = new ProcessData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/Liste_Med.php");
        }

        public class ProcessData extends DownloadJsonData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce=WebData;
                mMedicamentListe = getmMedicament();
                if(mMedicamentListe!=null){
                    if(!responce.equals("You have No Pharmacie !\n")){
                        if(!responce.isEmpty()){
                            mRecyclerView.setVisibility(View.VISIBLE);
                            emptyView.setVisibility(View.GONE);

                            mAdapter = new MyAdapter_StorageInfo(getActivity(), mMedicamentListe,getContext());
                            mRecyclerView.setAdapter(mAdapter);
                    }else{
                            Toast.makeText(getActivity(), R.string.SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                    }else{
                            mRecyclerView.setVisibility(View.GONE);
                            emptyView.setVisibility(View.VISIBLE);

                        Toast.makeText(getActivity(), R.string.no_pharm_found, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(),R.string.SERVER_ERROR,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
