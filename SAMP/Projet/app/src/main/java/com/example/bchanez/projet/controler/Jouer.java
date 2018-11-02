package com.example.bchanez.projet.controler;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bchanez.projet.R;
import com.example.bchanez.projet.bdd.QuestionManager;
import com.example.bchanez.projet.bdd.ReponseManager;

import java.util.ArrayList;
import java.util.List;

public class Jouer extends AppCompatActivity {

    private ListView listView;
    private Cursor c;

    private int id_quizz;
    private int score;
    private int nbQuestion;

    private List<Integer> listIDQuestion = new ArrayList<Integer>();
    private List<String> listTextQuestion = new ArrayList<String>();
    private List<Integer> listIDReponse = new ArrayList<Integer>();
    private List<String> listTextReponse = new ArrayList<String>();
    private List<Boolean> listVraiReponse = new ArrayList<Boolean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jouer);

        score = 0;
        nbQuestion = 0;
        listView = (ListView) findViewById(R.id.id_listView_reponse);

        getVariable();
        init();
        updateView();
        gestionButton();
    }

    private void getVariable() {
        Intent intent = getIntent();
        if (intent.hasExtra("QUIZZ_ID")) {
            id_quizz = Integer.parseInt(intent.getStringExtra("QUIZZ_ID"));
        }
    }

    private void init() {
        QuestionManager questionManager = new QuestionManager(this);
        questionManager.open();
        c = questionManager.getQuestionsByQuizz(id_quizz);
        if (c.moveToFirst()) {
            do {
                listIDQuestion.add(c.getInt(c.getColumnIndex(QuestionManager.KEY_ID_QUESTION)));
                listTextQuestion.add(c.getString(c.getColumnIndex(QuestionManager.KEY_TEXT_QUESTION)));
            }
            while (c.moveToNext());
        }
        c.close();
        questionManager.close();
    }

    private void updateView() {
        listIDReponse.clear();
        listTextReponse.clear();
        listVraiReponse.clear();

        ((TextView) findViewById(R.id.id_tv_score)).setText("Score " + score + "/" + nbQuestion);
        ((TextView) findViewById(R.id.id_tv_question)).setText("Fin");

        if (nbQuestion < listIDQuestion.size()) {
            ((TextView) findViewById(R.id.id_tv_question)).setText(String.valueOf(listTextQuestion.get(nbQuestion)));

            ReponseManager reponseManager = new ReponseManager(this);
            reponseManager.open();
            c = reponseManager.getReponsesByQuestion(listIDQuestion.get(nbQuestion));
            if (c.moveToFirst()) {
                do {
                    listIDReponse.add(c.getInt(c.getColumnIndex(ReponseManager.KEY_ID_REPONSE)));
                    listTextReponse.add(c.getString(c.getColumnIndex(ReponseManager.KEY_TEXT_REPONSE)));
                    listVraiReponse.add(c.getInt(c.getColumnIndex(ReponseManager.KEY_VRAI_REPONSE)) > 0);
                }
                while (c.moveToNext());
            }
            c.close();
            reponseManager.close();
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listTextReponse);
        listView.setAdapter(adapter);
    }

    private void gestionButton() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listVraiReponse.get(position)) {
                    com.example.bchanez.projet.tools.Toast.toast("Correct !", getApplicationContext(), getWindowManager());
                    score++;
                } else {
                    com.example.bchanez.projet.tools.Toast.toast("erreur !", getApplicationContext(), getWindowManager());
                }

                nbQuestion++;

                updateView();
            }
        });

        ((Button) findViewById(R.id.id_btn_retour)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Jouer.this, Jouer_select_quizz.class);
                startActivity(intent);
            }
        });
    }
}
