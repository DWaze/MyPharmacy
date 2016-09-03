package com.srdeveloppement.atelier.mypharmacy.User.SearchMedicament;


import android.os.Bundle;
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

import com.srdeveloppement.atelier.mypharmacy.Data.Model.MedicamentUser;
import com.srdeveloppement.atelier.mypharmacy.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchMedicament extends Fragment implements SearchView.OnQueryTextListener {

    public static SearchMedicament newInstance() {
        return new SearchMedicament();
    }

    private RecyclerView mRecyclerView;
    private MyAdapter_medicament mAdapter;
    Bundle bundle;
    private TextView emptyView;
    ArrayList<MedicamentUser> mMedicamentListeU;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search_medicament, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.search_medicament_list);
        emptyView = (TextView) view.findViewById(R.id.no_item_found);


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bundle = this.getArguments();

        mMedicamentListeU = (ArrayList<MedicamentUser>)bundle.getSerializable("ArrayListMeds");

        if(mMedicamentListeU!=null){
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            mAdapter = new MyAdapter_medicament(getActivity(), mMedicamentListeU);
            mRecyclerView.setAdapter(mAdapter);
        }else{
            mRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }


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
        if(mMedicamentListeU!=null){
        final ArrayList<MedicamentUser> filteredModelList = filter(mMedicamentListeU, query);
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

    private ArrayList<MedicamentUser> filter(ArrayList<MedicamentUser> models, String query) {
        query = query.toLowerCase();

        final ArrayList<MedicamentUser> filteredModelList = new ArrayList<>();
        for (MedicamentUser model : models) {
            final String text = model.getNom_Med().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
