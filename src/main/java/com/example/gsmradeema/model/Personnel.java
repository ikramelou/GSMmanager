package com.example.gsmradeema.model;

public class Personnel {
    private int personnelid;
    private String nom;
    private String prenom;
    private String matricule;
    private int entiteID;
    private String entiteNom; // New attribute for Entite name

    // Getters and Setters for all attributes

    public int getPersonnelid() {
        return personnelid;
    }

    public void setPersonnelid(int personnelid) {
        this.personnelid = personnelid;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public int getEntiteID() {
        return entiteID;
    }

    public void setEntiteID(int entiteID) {
        this.entiteID = entiteID;
    }

    public String getEntiteNom() {
        return entiteNom;
    }

    public void setEntiteNom(String entiteNom) {
        this.entiteNom = entiteNom;
    }

    public void setPersonnelId(int personnelId) {
    }
}
