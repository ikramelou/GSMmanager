package com.example.gsmradeema.model;

public class Puce {
    private int puceId;
    private String numero;
    private String code;
    private String etat;
    private String type;

    // Constructors, getters, and setters
    public Puce() {}

    public Puce(int puceId, String numero, String code, String etat) {
        this.puceId = puceId;
        this.numero = numero;
        this.code = code;
        this.etat = etat;
        // Initialize type to avoid null issues
        this.type = "";
    }

    public Puce(int puceId, String numero, String code, String etat, String type) {
        this.puceId = puceId;
        this.numero = numero;
        this.code = code;
        this.etat = etat;
        this.type = type;
    }

    public int getPuceId() {
        return puceId;
    }

    public void setPuceId(int puceId) {
        this.puceId = puceId;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
