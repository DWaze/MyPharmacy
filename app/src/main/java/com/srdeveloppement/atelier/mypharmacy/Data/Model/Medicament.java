package com.srdeveloppement.atelier.mypharmacy.Data.Model;

import java.io.Serializable;

/**
 * Created by lhadj on 4/8/2016.
 */
public class Medicament implements Serializable {
    private String IDMedicament;
    private String Nom_Med;
    private String Dosage;
    private String Uniter;
    private String Age;
    private String Type;
    private String Quantiter_Stocker;

    public Medicament(String IDMedicament, String nom_Med, String dosage, String uniter, String age, String type, String quantiter_Stocker) {
        this.IDMedicament = IDMedicament;
        Nom_Med = nom_Med;
        Dosage = dosage;
        Uniter = uniter;
        Age = age;
        Type = type;
        Quantiter_Stocker = quantiter_Stocker;
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

    public String getQuantiter_Stocker() {
        return Quantiter_Stocker;
    }
}
