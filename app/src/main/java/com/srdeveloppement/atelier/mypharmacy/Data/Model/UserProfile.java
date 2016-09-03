package com.srdeveloppement.atelier.mypharmacy.Data.Model;

/**
 * Created by lhadj on 4/26/2016.
 */
public class UserProfile {
    String IDPersson,username,Password,name,prenom,email,gender,Date_Naissance,Countery;

    public UserProfile(String IDPersson, String username, String password, String name, String prenom, String email, String gender, String date_Naissance, String countery) {
        this.IDPersson = IDPersson;
        this.username = username;
        Password = password;
        this.name = name;
        this.prenom = prenom;
        this.email = email;
        this.gender = gender;
        Date_Naissance = date_Naissance;
        Countery = countery;
    }

    public String getIDPersson() {
        return IDPersson;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return Password;
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

    public String getGender() {
        return gender;
    }

    public String getDate_Naissance() {
        return Date_Naissance;
    }

    public String getCountery() {
        return Countery;
    }
}
