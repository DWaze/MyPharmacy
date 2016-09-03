package com.srdeveloppement.atelier.mypharmacy.Data.Model;

import java.io.Serializable;

/**
 * Created by lhadj on 4/4/2016.
 */
public class Pharmacie implements Serializable {
    int IDPharmaci;
    String Nom_Pharmacie;
    String Address_Pharmacie;
    Double Latitude;
    Double Langitude;
    int NumberOfMeds;
    String cb_SundayB,cb_MondayB,cb_TuesdayB,cb_WednesdayB,cb_ThursdayB,cb_FridayB,cb_SaturdayB;

    String Sunday_t,Sunday_f,Monday_t,Monday_f,Tuesday_t,Tuesday_f,Wednesday_t,Wednesday_f,Thursday_t,Thursday_f,Friday_t,Friday_f,Saturday_t,Saturday_f;

    public Pharmacie(int IDPharmaci, String nom_Pharmacie, String address_Pharmacie, Double latitude, Double langitude,int NumberOfMeds, String cb_SundayB, String cb_MondayB, String cb_TuesdayB, String cb_WednesdayB, String cb_ThursdayB, String cb_FridayB, String cb_SaturdayB, String sunday_t, String sunday_f, String monday_t, String monday_f, String tuesday_t, String tuesday_f, String wednesday_t, String wednesday_f, String thursday_t, String thursday_f, String friday_t, String friday_f, String saturday_t, String saturday_f) {
        this.IDPharmaci = IDPharmaci;
        Nom_Pharmacie = nom_Pharmacie;
        Address_Pharmacie = address_Pharmacie;
        Latitude = latitude;
        Langitude = langitude;
        this.NumberOfMeds=NumberOfMeds;

        this.cb_SundayB = cb_SundayB;
        this.cb_MondayB = cb_MondayB;
        this.cb_TuesdayB = cb_TuesdayB;
        this.cb_WednesdayB = cb_WednesdayB;
        this.cb_ThursdayB = cb_ThursdayB;
        this.cb_FridayB = cb_FridayB;
        this.cb_SaturdayB = cb_SaturdayB;
        Sunday_t = sunday_t;
        Sunday_f = sunday_f;
        Monday_t = monday_t;
        Monday_f = monday_f;
        Tuesday_t = tuesday_t;
        Tuesday_f = tuesday_f;
        Wednesday_t = wednesday_t;
        Wednesday_f = wednesday_f;
        Thursday_t = thursday_t;
        Thursday_f = thursday_f;
        Friday_t = friday_t;
        Friday_f = friday_f;
        Saturday_t = saturday_t;
        Saturday_f = saturday_f;
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

    public String getCb_SundayB() {
        return cb_SundayB;
    }

    public String getCb_MondayB() {
        return cb_MondayB;
    }

    public String getCb_TuesdayB() {
        return cb_TuesdayB;
    }

    public String getCb_WednesdayB() {
        return cb_WednesdayB;
    }

    public String getCb_ThursdayB() {
        return cb_ThursdayB;
    }

    public String getCb_FridayB() {
        return cb_FridayB;
    }

    public String getCb_SaturdayB() {
        return cb_SaturdayB;
    }

    public String getSunday_t() {
        return Sunday_t;
    }

    public String getSunday_f() {
        return Sunday_f;
    }

    public String getMonday_t() {
        return Monday_t;
    }

    public String getMonday_f() {
        return Monday_f;
    }

    public String getTuesday_t() {
        return Tuesday_t;
    }

    public String getTuesday_f() {
        return Tuesday_f;
    }

    public String getWednesday_t() {
        return Wednesday_t;
    }

    public String getWednesday_f() {
        return Wednesday_f;
    }

    public String getThursday_t() {
        return Thursday_t;
    }

    public String getThursday_f() {
        return Thursday_f;
    }

    public String getFriday_t() {
        return Friday_t;
    }

    public String getFriday_f() {
        return Friday_f;
    }

    public String getSaturday_t() {
        return Saturday_t;
    }

    public String getSaturday_f() {
        return Saturday_f;
    }

    public int getNumberOfMeds() {
        return NumberOfMeds;
    }

}
