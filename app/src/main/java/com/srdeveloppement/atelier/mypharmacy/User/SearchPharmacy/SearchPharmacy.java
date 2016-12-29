package com.srdeveloppement.atelier.mypharmacy.User.SearchPharmacy;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.srdeveloppement.atelier.mypharmacy.Data.Model.PharmacieSmall;
import com.srdeveloppement.atelier.mypharmacy.R;

import java.util.ArrayList;

public class SearchPharmacy extends Fragment implements SearchView.OnQueryTextListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{


    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude=0.0;
    private double currentLongitude=0.0;

    double LatitudeP,LatitudeM,LangitudeP,LangitudeM;

    private static final int LOCATION_PERMISSION_REQUEST_CODE=1;
    public static SeekBar mySeekbar;
    public Button DoneFab;
    public static TextView Kelometrage;
    public int Km_value;
    ArrayList<PharmacieSmall> mPharmacieList;
    RelativeLayout seekbarfooter;
    FrameLayout frameLayout7;
    ImageView shadowFooter;
    Bundle bundle;
    ArrayList<PharmacieSmall> FavmPharmaListUF;
    private RecyclerView mRecyclerView;
    private MyAdapter_pharmacy mAdapter;
    TextView emptyView;
    double latAdd;
    double langAdd;
    SharedPreferences isFavori;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search_pharmacy, container, false);
        mPharmacieList= new ArrayList<>();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.search_pharmacy_list);
        frameLayout7 = (FrameLayout) view.findViewById(R.id.frameLayout7);
        seekbarfooter =(RelativeLayout)view.findViewById(R.id.seekbarfooter);
        shadowFooter =(ImageView)view.findViewById(R.id.shadowFooter);
        emptyView=(TextView)view.findViewById(R.id.no_item_found);
        mySeekbar = (SeekBar)view.findViewById(R.id.seekBar);
        DoneFab=(Button)view.findViewById(R.id.done);
        Kelometrage=(TextView)view.findViewById(R.id.km);

        Kelometrage.setText(mySeekbar.getProgress()+" "+getResources().getString(R.string.km));

        isFavori = PreferenceManager.getDefaultSharedPreferences(getContext());
       int fav  = isFavori.getInt("isFavori",0);
        if(fav==0){
            frameLayout7.setVisibility(View.VISIBLE);
            seekbarfooter.setVisibility(View.VISIBLE);
            shadowFooter.setVisibility(View.VISIBLE);
        }else {
            frameLayout7.setVisibility(View.GONE);
            seekbarfooter.setVisibility(View.GONE);
            shadowFooter.setVisibility(View.GONE);
        }

        /*transforming the value of the seek bar to kilometer values*/
        mySeekbar.setProgress(10);
        Kelometrage.setText(10 +" "+ getResources().getString(R.string.km));
        mySeekbar.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN));
        mySeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               // mySeekbar.setProgress(1);
                Km_value = progress+1;

                Kelometrage.setText(Km_value +" "+ getResources().getString(R.string.km));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {Kelometrage.setText(Km_value + getResources().getString(R.string.km));
                latAdd=Km_value/110.574;
                langAdd=Km_value/(111.320)*Math.cos(latAdd);
                LatitudeP=currentLatitude+latAdd;
                LatitudeM=currentLatitude-latAdd;
                LangitudeP=currentLongitude+langAdd;
                LangitudeM=currentLongitude-langAdd;
            }

        });
        DoneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavmPharmaListUF=new ArrayList<>();
                for(int i=0;i<mPharmacieList.size();i++){
                    if(mPharmacieList.get(i).getLatitude()<=LatitudeP && mPharmacieList.get(i).getLatitude()>=LatitudeM
                            && mPharmacieList.get(i).getLangitude()<=LangitudeP &&mPharmacieList.get(i).getLangitude()>=LangitudeM){
                        FavmPharmaListUF.add(mPharmacieList.get(i));
                    }
                }
                mAdapter.loadNewData(FavmPharmaListUF);
            }
        });
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds



        mGoogleApiClient.connect();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bundle = this.getArguments();

        mPharmacieList = (ArrayList<PharmacieSmall>)bundle.getSerializable("ArrayListPharms");

        if(mPharmacieList !=null){
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            mAdapter = new MyAdapter_pharmacy(getActivity(), mPharmacieList);
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
        final ArrayList<PharmacieSmall> filteredModelList = filter(mPharmacieList, query);
        mAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private ArrayList<PharmacieSmall> filter(ArrayList<PharmacieSmall> models, String query) {
        query = query.toLowerCase();

        final ArrayList<PharmacieSmall> filteredModelList = new ArrayList<>();
        for (PharmacieSmall model : models) {
            final String text = model.getNom_Pharmacie().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();

            LatitudeP=currentLatitude+latAdd;
            LatitudeM=currentLatitude-latAdd;
            LangitudeP=currentLongitude+langAdd;
            LangitudeM=currentLongitude-langAdd;



        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }
}
