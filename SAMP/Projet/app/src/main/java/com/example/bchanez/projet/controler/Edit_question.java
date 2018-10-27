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

import com.example.bchanez.projet.bdd.QuizzManager;
import com.example.bchanez.projet.model.Question;
import com.example.bchanez.projet.bdd.QuestionManager;
import com.example.bchanez.projet.R;
import com.example.bchanez.projet.bdd.ReponseManager;
import com.example.bchanez.projet.model.Quizz;

import java.util.ArrayList;
import java.util.List;

public class Edit_question extends AppCompatActivity {

    public QuizzManager quizzManager;
    public QuestionManager questionManager;
    public ReponseManager reponseManager;

    private ListView listView;
    private TextInputEditText textInputEditText;

    private Quizz quizz;
    private Question question;

    private List<Integer> listIDReponse = new ArrayList<Integer>();
    private List<String> listTextReponse = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);

        quizzManager = new QuizzManager(this);
        questionManager = new QuestionManager(this);
        reponseManager = new ReponseManager(this);

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
        }

        if (intent.hasExtra("QUESTION_ID")) {
            questionManager.open();
            question = questionManager.getQuestion(Integer.parseInt(intent.getStringExtra("QUESTION_ID")));
            questionManager.close();
        } else {
            question = new Question(0, "", quizz);
            question.setId(0);
        }
    }

    private void init() {
        Cursor c;
        reponseManager.open();
        c = reponseManager.getReponsesByQuestion(question.getId());
        if (c.moveToFirst()) {
            do {
                listIDReponse.add(c.getInt(c.getColumnIndex(ReponseManager.KEY_ID_REPONSE)));
                listTextReponse.add(c.getString(c.getColumnIndex(ReponseManager.KEY_TEXT_REPONSE)));
            }
            while (c.moveToNext());
        }
        c.close();
        reponseManager.close();
    }

    private void updateView() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listTextReponse);
        listView.setAdapter(adapter);

        textInputEditText.setText(question.getText());
    }

    private void gestionButton() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Edit_question.this, Edit_reponse.class);
                intent.putExtra("QUIZZ_ID", "" + quizz.getId());
                intent.putExtra("QUESTION_ID", "" + question.getId());
                intent.putExtra("REPONSE_ID", "" + listIDReponse.get(position));
                startActivity(intent);
            }
        });


        ((Button) findViewById(R.id.id_btn_modifier)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    question.setText(String.valueOf(textInputEditText.getText()));

                    questionManager.open();
                    if (question.getId() == 0) {
                        question.setId((int) questionManager.addQuestion(question));
                    } else {
                        questionManager.modQuestion(question);
                    }
                    questionManager.close();
                    retour();
                } catch (Exception e) {
                    toastErrorMsg(e.getMessage());
                }
            }
        });

        ((Button) findViewById(R.id.id_btn_supprime)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                questionManager.open();
                questionManager.supQuestion(question);
                questionManager.close();
                retour();
            }
        });

        ((Button) findViewById(R.id.id_btn_nouveau)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Edit_question.this, Edit_reponse.class);
                intent.putExtra("QUIZZ_ID", "" + quizz.getId());
                intent.putExtra("QUESTION_ID", "" + question.getId());
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
        Intent intent = new Intent(Edit_question.this, Edit_quizz.class);
        intent.putExtra("QUIZZ_ID", "" + quizz.getId());
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
