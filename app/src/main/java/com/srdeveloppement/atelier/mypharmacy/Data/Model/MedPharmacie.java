package com.srdeveloppement.atelier.mypharmacy.Data.Model;

import java.io.Serializable;

/**
 * Created by lhadj on 4/18/2016.
 */
public class MedPharmacie implements Serializable {
    int IDPharmaci;
    String Nom_Pharmacie;
    String Address_Pharmacie;
    Double Latitude;
    Double Langitude;
    int Quantiter_Stocker;
    String Temps_Ouverture;
    String Temps_Fermeture;

    public MedPharmacie(int IDPharmaci, String nom_Pharmacie, String address_Pharmacie, Double latitude, Double langitude, int quantiter_Stocker, String temps_Ouverture, String temps_Fermeture) {
        this.IDPharmaci = IDPharmaci;
        Nom_Pharmacie = nom_Pharmacie;
        Address_Pharmacie = address_Pharmacie;
        Latitude = latitude;
        Langitude = langitude;
        Quantiter_Stocker = quantiter_Stocker;
        Temps_Ouverture = temps_Ouverture;
        Temps_Fermeture = temps_Fermeture;
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

    public int getQuantiter_Stocker() {
        return Quantiter_Stocker;
    }

    public String getTemps_Ouverture() {
        return Temps_Ouverture;
    }

    public String getTemps_Fermeture() {
        return Temps_Fermeture;
    }
}
