package com.srdeveloppement.atelier.mypharmacy.Data.Model;

import java.io.Serializable;

/**
 * Created by lhadj on 4/18/2016.
 */
public class PharmacieSmall implements Serializable {
    int IDPharmaci;
    String Nom_Pharmacie;
    String Address_Pharmacie;
    Double Latitude;
    Double Langitude;
    int NumberOfMeds;
    String Favoriser;

    public PharmacieSmall(int IDPharmaci, String nom_Pharmacie, String address_Pharmacie, Double latitude, Double langitude, int numberOfMeds, String favoriser) {
        this.IDPharmaci = IDPharmaci;
        Nom_Pharmacie = nom_Pharmacie;
        Address_Pharmacie = address_Pharmacie;
        Latitude = latitude;
        Langitude = langitude;
        NumberOfMeds = numberOfMeds;
        Favoriser = favoriser;
    }

    public int getIDPharmaci() {
        return IDPharmaci;
    }

    public String getNom_Pharmacie() {
        return Nom_Pharmacie;
    }

    public String getAddress_Pharmacie() {
        return Address_Pharmacie;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public Double getLangitude() {
        return Langitude;
    }

    public int getNumberOfMeds() {
        return NumberOfMeds;
    }

    public String getFavoriser() {
        return Favoriser;
    }
}
