package com.example.bchanez.projet;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Acceuil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);

        MySQLite.getInstance(this);

        QuizzManager quizzManager = new QuizzManager(this);
        quizzManager.open();
        Cursor c;
        c = quizzManager.getQuizzs();
        if (c.moveToFirst()) {
            do {
                Log.d("log affichage quizz",
                        c.getInt(c.getColumnIndex(QuizzManager.KEY_ID_QUIZZ)) + "," +
                                c.getString(c.getColumnIndex(QuizzManager.KEY_NOM_QUIZZ))
                );
            }
            while (c.moveToNext());

        }
        c.close();
        quizzManager.close();

        QuestionManager questionManager = new QuestionManager(this);
        questionManager.open();
        c = questionManager.getQuestions();
        if (c.moveToFirst()) {
            do {
                Log.d("log affichage question",
                        c.getInt(c.getColumnIndex(QuestionManager.KEY_ID_QUESTION)) + "," +
                                c.getString(c.getColumnIndex(QuestionManager.KEY_TEXT_QUESTION)) + "," +
                                c.getInt(c.getColumnIndex(QuestionManager.KEY_ID_QUIZZ_QUESTION))
                );
            }
            while (c.moveToNext());

        }
        c.close();
        questionManager.close();

        ReponseManager reponseManager = new ReponseManager(this);
        reponseManager.open();
        c = reponseManager.getReponses();
        if (c.moveToFirst()) {
            do {
                Log.d("log affichage reponse",
                        c.getInt(c.getColumnIndex(ReponseManager.KEY_ID_REPONSE)) + "," +
                                c.getString(c.getColumnIndex(ReponseManager.KEY_TEXT_REPONSE)) + "," +
                                c.getString(c.getColumnIndex(ReponseManager.KEY_VRAI_REPONSE)) + "," +
                                c.getInt(c.getColumnIndex(ReponseManager.KEY_ID_QUESTION_REPONSE))
                );
            }
            while (c.moveToNext());

        }
        c.close();
        reponseManager.close();

        ((Button) findViewById(R.id.id_btn_jouer)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Acceuil.this, Jouer_select_quizz.class);
                startActivity(intent);
            }
        });

        ((Button) findViewById(R.id.id_btn_gerer)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Acceuil.this, Gerer_select_quizz.class);
                startActivity(intent);
            }
        });

        ((Button) findViewById(R.id.id_btn_quitter)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }
}
