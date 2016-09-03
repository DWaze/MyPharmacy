package com.srdeveloppement.atelier.mypharmacy.Pharmacist.TravailleurFragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.srdeveloppement.atelier.mypharmacy.Data.GetData;
import com.srdeveloppement.atelier.mypharmacy.Data.GetJsonTravailleurList;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.Travailleur;
import com.srdeveloppement.atelier.mypharmacy.Pharmacist.myPharmacy.AreYouSureDialogue;
import com.srdeveloppement.atelier.mypharmacy.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TravailleurFragment extends Fragment {

    private String IDPDir;

    public static TravailleurFragment newInstance() {
        return new TravailleurFragment();
    }

    // ro7 ecriyi intent tefta7 biha activity ta3 dialog ok
    private RecyclerView mRecyclerView;
    private Travailleur_Adapter mAdapter;
    private List<Travailleur> mTravailleurList;
    private Button btn ;
    String Params;
    String responce;
    boolean ifFirstTime=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.travailleur_content, container, false);



        return view;
    }

    public class printTravailleurList extends GetJsonTravailleurList {

        public printTravailleurList(String Params) {
            super(Params,getContext());
        }

        public void execute() {
            ProcessData data = new ProcessData();
            data.execute(GetData.LOCALHOST+"Pharmacie_Project/Travailleur_List.php");
        }

        public class ProcessData extends DownloadJsonData{

            @Override
            protected void onPostExecute(String WebData) {
                super.onPostExecute(WebData);
                responce=WebData;
                mTravailleurList = getmPerssone();
                if(mTravailleurList!=null){
                    if(!responce.isEmpty()){
                        mAdapter = new Travailleur_Adapter(getActivity(), mTravailleurList,getContext());
                        mRecyclerView.setAdapter(mAdapter);
                    }else{
                        Toast.makeText(getActivity(),R.string.SERVER_ERROR,Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(),R.string.SERVER_ERROR,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);



        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), TravailleurDialogue.class));
            }
        });


        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        IDPDir = sharedPref.getString("IDPDirecteur", "");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Params="IDPDirecteur="+IDPDir;
        printTravailleurList pd = new printTravailleurList(Params);
        pd.execute();







        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, final int position) {
                btn =(Button) view.findViewById(R.id.delete);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String screen = getResources().getString(R.string.you_want_delete_emp)+" "+ mTravailleurList.get(position).getName().toString()+" "+mTravailleurList.get(position).getPrenom().toString()+"  "+getResources().getString(R.string.from_your_list);
                        Intent intent = new Intent(getContext(), AreYouSureDialogue.class);
                        intent.putExtra("code","T");
                        intent.putExtra("Info", screen);
                        intent.putExtra("IDPersson", mTravailleurList.get(position).getIDPersson().toString());
                        intent.putExtra("IDPDirecteur",IDPDir);
                        startActivity(intent);
                        btn.setClickable(false);
                    }
                });
            }
            @Override
            public void onItemLongClick(View view, final int position) {
            }
        }));
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mTravailleurList!=null){
            printTravailleurList pd = new printTravailleurList(Params);
            pd.execute();
            mAdapter.loadNewData(mTravailleurList);
        }
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.pharmacist_nav_without_search, menu);

    }


}
