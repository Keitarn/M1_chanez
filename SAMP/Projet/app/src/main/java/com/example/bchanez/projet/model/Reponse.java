package com.example.bchanez.projet.model;

public class Reponse {
    private int id;
    private String text;
    private boolean vrai;
    private Question question;

    public Reponse(int id, String text, boolean vrai, Question question) {
        this.id = id;
        this.text = text;
        this.vrai = vrai;
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) throws Exception {
        if (text.matches("^([a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ._\\s-]+)$")) {
            this.text = text;
        } else {
            throw new Exception("Erreur texte");
        }
    }

    public boolean getVrai() {
        return vrai;
    }

    public void setVrai(boolean vrai) {
        this.vrai = vrai;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
