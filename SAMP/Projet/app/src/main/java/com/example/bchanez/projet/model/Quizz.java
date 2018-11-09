package com.example.bchanez.projet.model;

public class Quizz {
    private int id;
    private String nom;

    public Quizz(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) throws Exception {
        if (nom.matches("^([a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._\\s-]+)$")) {
            this.nom = nom;
        } else {
            throw new Exception("Erreur nom");
        }
    }
}
