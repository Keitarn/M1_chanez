package com.example.bchanez.projet.model;

public class Question {
    private int id;
    private String text;
    private Quizz quizz;

    public Question(int id, String text, Quizz quizz) throws Exception {
        this.id = id;

        if (text.matches("^([a-zA-Z0-9áàâäãåçéèêëíìîïñóòôöõúùûüýÿæœÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒ.?_\\s-]+)$")) {
            this.text = text;
        } else {
            throw new Exception("Erreur texte");
        }

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

    public void setText(String text) {
        this.text = text;
    }

    public Quizz getQuizz() {
        return quizz;
    }

    public void setQuizz(Quizz quizz) {
        this.quizz = quizz;
    }
}
