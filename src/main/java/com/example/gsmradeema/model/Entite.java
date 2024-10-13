package com.example.gsmradeema.model;

public class Entite {
    private int id;
    private String nom;
    private String type;
    private String entiteMere;

    // Default constructor
    public Entite() {
    }

    // Constructor with all fields (including ID)
    public Entite(int id, String nom, String type, String entiteMere) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.entiteMere = entiteMere;
    }

    // Constructor without ID (for insert operations)
    public Entite(String nom, String type, String entiteMere) {
        this.nom = nom;
        this.type = type;
        this.entiteMere = entiteMere;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEntiteMere() {
        return entiteMere;
    }

    public void setEntiteMere(String entiteMere) {
        this.entiteMere = entiteMere;
    }
}
