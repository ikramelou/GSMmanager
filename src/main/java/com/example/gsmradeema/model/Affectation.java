package com.example.gsmradeema.model;

import java.util.Date;

public class Affectation {
    private int affectationId;
    private int personnelid;
    private int puceId;
    private Date datteDaffectation;
    private Date dateDeDesaffectation;
    private String matricule; // Used for UI display
    private String numero; // Used for UI display

    // Constructors
    public Affectation() {}

    public Affectation(int personnelid, int puceId) {
        this.personnelid = personnelid;
        this.puceId = puceId;
        this.datteDaffectation = new Date(); // Set automatically to the current date
    }

    // Getters and Setters
    public int getAffectationId() {
        return affectationId;
    }

    public void setAffectationId(int affectationId) {
        this.affectationId = affectationId;
    }

    public int getPersonnelid() {
        return personnelid;
    }

    public void setPersonnelid(int personnelid) {
        this.personnelid = personnelid;
    }

    public int getPuceId() {
        return puceId;
    }

    public void setPuceId(int puceId) {
        this.puceId = puceId;
    }

    public Date getDatteDaffectation() {
        return datteDaffectation;
    }

    public void setDatteDaffectation(Date datteDaffectation) {
        this.datteDaffectation = datteDaffectation;
    }

    public Date getDateDeDesaffectation() {
        return dateDeDesaffectation;
    }

    public void setDateDeDesaffectation(Date dateDeDesaffectation) {
        this.dateDeDesaffectation = dateDeDesaffectation;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
