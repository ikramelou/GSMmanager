package com.example.gsmradeema.model;

import java.sql.Date;

public class Dotation {
    private int dotationId;       // Hidden in the UI
    private String solde;
    private Date dateAffectation; // Will be set to the current date
    private int puceId;           // Stored in the database
    private String numero;    // Displayed in the UI

    // Constructors
    public Dotation() {
    }

    public Dotation(int dotationId, String solde, Date dateAffectation, int puceId, String numero) {
        this.dotationId = dotationId;
        this.solde = solde;
        this.dateAffectation = dateAffectation;
        this.puceId = puceId;
        this.numero = numero;  // Corrected assignment
    }

    // Getters and Setters
    public int getDotationId() {
        return dotationId;
    }

    public void setDotationId(int dotationId) {
        this.dotationId = dotationId;
    }

    public String getSolde() {
        return solde;
    }

    public void setSolde(String solde) {
        this.solde = solde;
    }

    public Date getDateAffectation() {
        return dateAffectation;
    }

    public void setDateAffectation(Date dateAffectation) {
        this.dateAffectation = dateAffectation;
    }

    public int getPuceId() {
        return puceId;
    }

    public void setPuceId(int puceId) {
        this.puceId = puceId;
    }

    public String getNumero() {  // Updated the getter method
        return numero;
    }

    public void setNumero(String numero) {  // Updated the setter method
        this.numero = numero;
    }
}
