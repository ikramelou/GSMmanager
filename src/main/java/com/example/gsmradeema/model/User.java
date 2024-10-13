package com.example.gsmradeema.model;

public class User {
    private String username;
    private String password;
    private Profil profil; // Add the Profil enum

    public User() {
    }

    public User(String username, String password, Profil profil) {
        this.username = username;
        this.password = password;
        this.profil = profil;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", profil=" + profil +
                '}';
    }

}
