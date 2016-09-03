package com.srdeveloppement.atelier.mypharmacy.Data.Model;

import java.io.Serializable;

/**
 * Created by lhadj on 4/16/2016.
 */
public class MedicamentUser implements Serializable {
    private String IDMedicament;
    private String Nom_Med;
    private String Dosage;
    private String Uniter;
    private String Age;
    private String Type;
    private String Favoriser;

    public MedicamentUser(String IDMedicament,String nom_Med, String dosage, String uniter, String age, String type, String favoriser) {
        this.IDMedicament = IDMedicament;
        Nom_Med = nom_Med;
        Dosage = dosage;
        Uniter = uniter;
        Age = age;
        Type = type;
        Favoriser = favoriser;
    }

    public String getIDMedicament() {
        return IDMedicament;
    }

    public String getNom_Med() {
        return Nom_Med;
    }

    public String getDosage() {
        return Dosage;
    }

    public String getUniter() {
        return Uniter;
    }

    public String getAge() {
        return Age;
    }

    public String getType() {
        return Type;
    }

    public String getFavoriser() {
        return Favoriser;
    }

    public void setIDMedicament(String IDMedicament) {
        this.IDMedicament = IDMedicament;
    }

    public void setNom_Med(String nom_Med) {
        Nom_Med = nom_Med;
    }

    public void setDosage(String dosage) {
        Dosage = dosage;
    }

    public void setUniter(String uniter) {
        Uniter = uniter;
    }

    public void setAge(String age) {
        Age = age;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setFavoriser(String favoriser) {
        Favoriser = favoriser;
    }
}
