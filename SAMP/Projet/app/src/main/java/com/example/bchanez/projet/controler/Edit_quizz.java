package com.example.bchanez.projet.controler;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bchanez.projet.bdd.QuestionManager;
import com.example.bchanez.projet.model.Quizz;
import com.example.bchanez.projet.bdd.QuizzManager;
import com.example.bchanez.projet.R;

import java.util.ArrayList;
import java.util.List;

public class Edit_quizz extends AppCompatActivity {

    private QuizzManager quizzManager;
    private QuestionManager questionManager;

    private ListView listView;
    private TextInputEditText textInputEditText;

    private Quizz quizz;

    private List<Integer> listIDQuestion = new ArrayList<Integer>();
    private List<String> listTextQuestion = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_quizz);

        quizzManager = new QuizzManager(this);
        questionManager = new QuestionManager(this);

        listView = (ListView) findViewById(R.id.id_listView_edit);
        textInputEditText = (TextInputEditText) findViewById(R.id.id_input_text);

        getVariable();
        init();
        updateView();
        gestionButton();
    }

    private void getVariable() {
        Intent intent = getIntent();
        if (intent.hasExtra("QUIZZ_ID")) {
            quizzManager.open();
            quizz = quizzManager.getQuizz(Integer.parseInt(intent.getStringExtra("QUIZZ_ID")));
            quizzManager.close();
        } else {
            quizz = new Quizz(0, "");
            quizz.setId(0);
        }
    }

    private void init() {
        Cursor c;
        questionManager.open();
        c = questionManager.getQuestionsByQuizz(quizz.getId());
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
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listTextQuestion);
        listView.setAdapter(adapter);

        textInputEditText.setText(quizz.getNom());
    }

    private void gestionButton() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Edit_quizz.this, Edit_question.class);
                intent.putExtra("QUIZZ_ID", "" + quizz.getId());
                intent.putExtra("QUESTION_ID", "" + listIDQuestion.get(position));
                startActivity(intent);
            }
        });


        ((Button) findViewById(R.id.id_btn_modifier)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    quizz.setNom(String.valueOf(textInputEditText.getText()));
                    quizzManager.open();
                    if (quizz.getId() == 0) {
                        quizz.setId((int) quizzManager.addQuizz(quizz));
                    } else {
                        quizzManager.modQuizz(quizz);
                    }
                    quizzManager.close();
                    retour();
                } catch (Exception e) {
                    toastErrorMsg(e.getMessage());
                }
            }
        });

        ((Button) findViewById(R.id.id_btn_supprime)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                quizzManager.open();
                quizzManager.supQuizz(quizz);
                quizzManager.close();
                retour();
            }
        });

        ((Button) findViewById(R.id.id_btn_nouveau)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Edit_quizz.this, Edit_question.class);
                intent.putExtra("QUIZZ_ID", "" + quizz.getId());
                startActivity(intent);
            }
        });

        ((Button) findViewById(R.id.id_btn_retour)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                retour();
            }
        });
    }

    private void retour() {
        Intent intent = new Intent(Edit_quizz.this, Gerer_select_quizz.class);
        startActivity(intent);
    }

    private void toastErrorMsg(String errorMsg) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        Toast toast = Toast.makeText(context, errorMsg, duration);
        toast.setGravity(Gravity.TOP, 0, height / 2);
        toast.show();
    }
}
