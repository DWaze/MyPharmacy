package com.srdeveloppement.atelier.mypharmacy.Data.Model;

import java.io.Serializable;

/**
 * Created by lhadj on 4/6/2016.
 */
public class Directeur implements Serializable {

    String IDPersson;
    String username;
    String Password;
    String name;
    String prenom;
    String email;
    String IDContratPharmacie;
    String DirecteurPhone;
    String gender;

    public Directeur(String IDPersson, String username, String password, String name, String prenom, String email, String IDContratPharmacie, String directeurPhone, String gender) {
        this.IDPersson = IDPersson;
        this.username = username;
        this.Password = password;
        this.name = name;
        this.prenom = prenom;
        this.email = email;
        this.IDContratPharmacie = IDContratPharmacie;
        DirecteurPhone = directeurPhone;
        this.gender = gender;
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

    public String getIDContratPharmacie() {
        return IDContratPharmacie;
    }

    public String getDirecteurPhone() {
        return DirecteurPhone;
    }

    public String getGender() {
        return gender;
    }
}
