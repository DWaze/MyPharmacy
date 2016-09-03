package com.srdeveloppement.atelier.mypharmacy.Data.Model;

import java.io.Serializable;

/**
 * Created by lhadj on 2/20/2016.
 */
public class Travailleur implements Serializable {

    String IDPersson;
    String username;
    String Password;
    String name;
    String prenom;
    String email;
    String Telephone;
    String gender;
    String Address;
    String IDDiplome_Reconnaisance;

    public Travailleur(String IDPersson, String username, String password, String name, String prenom, String email, String telephone, String gender, String address, String IDDiplome_Reconnaisance) {
        this.IDPersson = IDPersson;
        this.username = username;
        Password = password;
        this.name = name;
        this.prenom = prenom;
        this.email = email;
        this.Telephone = telephone;
        this.gender = gender;
        this.Address = address;
        this.IDDiplome_Reconnaisance = IDDiplome_Reconnaisance;
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

    public String getTelephone() {
        return Telephone;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return Address;
    }

    public String getIDDiplome_Reconnaisance() {
        return IDDiplome_Reconnaisance;
    }
}
