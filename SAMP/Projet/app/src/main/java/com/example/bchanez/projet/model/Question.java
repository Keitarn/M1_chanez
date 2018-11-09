package com.example.bchanez.projet.model;

public class Question {
    private int id;
    private String text;
    private Quizz quizz;

    public Question(int id, String text, Quizz quizz) {
        this.id = id;
        this.text = text;
        this.quizz = quizz;
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
        if (text.matches("^([a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ.?_\\s-]+)$")) {
            this.text = text;
        } else {
            throw new Exception("Erreur texte");
        }
    }

    public Quizz getQuizz() {
        return quizz;
    }

    public void setQuizz(Quizz quizz) {
        this.quizz = quizz;
    }
}
