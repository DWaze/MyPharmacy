package com.srdeveloppement.atelier.mypharmacy.Data.Model;

import java.io.Serializable;

/**
 * Created by lhadj on 2/20/2016.
 */
public class TravailleurD implements Serializable {

    String IDPersson;
    String username;
    String name;
    String prenom;
    String email;
    String Telephone;
    String Address;
    String IDDiplome_Reconnaisance;
    String IDDirecteur;

    public TravailleurD(String IDPersson, String username, String name, String prenom, String email, String telephone, String address, String IDDiplome_Reconnaisance, String IDDirecteur) {
        this.IDPersson = IDPersson;
        this.username = username;
        this.name = name;
        this.prenom = prenom;
        this.email = email;
        Telephone = telephone;
        Address = address;
        this.IDDiplome_Reconnaisance = IDDiplome_Reconnaisance;
        this.IDDirecteur = IDDirecteur;
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

    public String getTelephone() {
        return Telephone;
    }

    public String getAddress() {
        return Address;
    }

    public String getIDDiplome_Reconnaisance() {
        return IDDiplome_Reconnaisance;
    }

    public String getIDDirecteur() {
        return IDDirecteur;
    }
}
