package com.srdeveloppement.atelier.mypharmacy.Data.Model;

import java.io.Serializable;

/**
 * Created by lhadj on 4/15/2016.
 */
public class User implements Serializable {

    String IDPersson;
    String username;
    String name;
    String prenom;
    String email;
    String Date_Naissance;
    String Countery;

    public User(String IDPersson, String username, String name, String prenom, String email, String date_Naissance, String countery) {
        this.IDPersson = IDPersson;
        this.username = username;
        this.name = name;
        this.prenom = prenom;
        this.email = email;
        Date_Naissance = date_Naissance;
        Countery = countery;
    }

    public String getIDPersson() {
        return IDPersson;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getDate_Naissance() {
        return Date_Naissance;
    }

    public String getCountery() {
        return Countery;
    }
}
