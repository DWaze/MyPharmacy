package com.srdeveloppement.atelier.mypharmacy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Set_phatmacy_dialog extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
Button x;FloatingActionButton done; int a;
Button help,confirm; ToggleButton map_type,litinirair,edit;

    RelativeLayout information;
    LinearLayout tutoriel;
    Marker marker;
    LatLng algeria;
    MaterialEditText name,adress;
    String p_name,p_adress;
    LatLng myMarker;
    ImageView image_done;
    CameraUpdate cameraUpdate;
    boolean isAllright=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_set_phatmacy);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        a=0;
        x=(Button)findViewById(R.id.x_map);
        confirm=(Button)findViewById(R.id.confirm);


        name=(MaterialEditText)findViewById(R.id.p_name);

        adress=(MaterialEditText)findViewById(R.id.p_address);

        done=(FloatingActionButton)findViewById(R.id.done);
        information=(RelativeLayout)findViewById(R.id.informations);
        image_done = (ImageView)findViewById(R.id.image_done);
        map_type=(ToggleButton)findViewById(R.id.map_type);
        edit=(ToggleButton)findViewById(R.id.info_edit);
        tutoriel=(LinearLayout)findViewById(R.id.help);
        tutoriel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tutoriel.setVisibility(View.INVISIBLE);
                a=0;
            }
        });

        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        help=(Button)findViewById(R.id.help_map);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (a == 0) {
                    tutoriel.setVisibility(View.VISIBLE);
                    a = 1;
                } else {
                    tutoriel.setVisibility(View.INVISIBLE);
                    a = 0;
                }

            }
        });
        edit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    information.setVisibility(View.VISIBLE);
                    name.setText(p_name);
                    adress.setText(p_adress);
                    // The toggle is enabled
                } else {
                    information.setVisibility(View.INVISIBLE);
                    // The toggle is disabled
                }
            }
        });
    }
    //check if eddit text is not empty
    boolean isEmpty(MaterialEditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        information.setVisibility(View.VISIBLE);
        if (marker != null) {
            marker.remove();

        }

        marker= mMap.addMarker(new MarkerOptions()

                .position(latLng).draggable(true).visible(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.m_pharmacy)));
         myMarker=marker.getPosition();
        cameraUpdate = CameraUpdateFactory.newLatLng(myMarker);//ewLatLngZoom(myMarker,0);
        mMap.animateCamera(cameraUpdate);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(name)) {
                    name.setError(getString(R.string.name_is_empty));
                } else if (isEmpty(adress)) {
                    adress.setError(getString(R.string.adress_is_empty));
                } else {
                    isAllright = true;
                    confirm.setEnabled(true);
                    confirm.setTextColor(getResources().getColor(R.color.colorAccent));
                    edit.setChecked(false);
                    edit.setVisibility(View.VISIBLE);

                    information.setVisibility(View.INVISIBLE);
                    cameraUpdate = CameraUpdateFactory.newLatLngZoom(myMarker, 17);
                    mMap.animateCamera(cameraUpdate);
                    marker.setTitle(name.getText().toString());
                    marker.setSnippet(adress.getText().toString());

                    marker.showInfoWindow();
                    p_name = name.getText().toString();
                    p_adress = adress.getText().toString();


                }
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myInent = new Intent();
                Bundle b = new Bundle();
                myInent.putExtra("lat", marker.getPosition().latitude);
                myInent.putExtra("lng", marker.getPosition().longitude);
                myInent.putExtra("PharmacyName", p_name);
                myInent.putExtra("PharmacyAdress", p_adress);
                myInent.putExtra("evryThingAllright", true);
                setResult(RESULT_OK, myInent);
                finish();
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        // Add a marker in Sydney and move the camera
        //mMap.setPadding(-3,0,0,3);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        ////////////////////////////////////////
        algeria = new LatLng(28.592029,2.688174);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(algeria,4);
        mMap.animateCamera(cameraUpdate);

        map_type.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                    // The toggle is enabled
                } else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    // The toggle is disabled
                }
            }
        });

    }
}
