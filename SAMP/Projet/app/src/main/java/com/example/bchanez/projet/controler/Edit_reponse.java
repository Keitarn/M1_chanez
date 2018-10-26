package com.example.bchanez.projet.controler;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.bchanez.projet.R;
import com.example.bchanez.projet.bdd.QuestionManager;
import com.example.bchanez.projet.model.Question;
import com.example.bchanez.projet.model.Quizz;
import com.example.bchanez.projet.model.Reponse;
import com.example.bchanez.projet.bdd.ReponseManager;

public class Edit_reponse extends AppCompatActivity {

    private QuestionManager questionManager;
    private ReponseManager reponseManager;

    private ListView listView;
    private TextInputEditText textInputEditText;
    private Switch aSwitch;

    private int id_quizz;
    private int id_question;
    private int id_reponse;

    private String text_reponse;
    private Boolean vrai_reponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reponse);

        textInputEditText = (TextInputEditText) findViewById(R.id.id_input_text);
        aSwitch = (Switch) findViewById(R.id.id_switch);

        text_reponse = "";
        vrai_reponse = false;

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
        if (intent.hasExtra("QUESTION_ID")) {
            id_question = Integer.parseInt(intent.getStringExtra("QUESTION_ID"));
        }
        if (intent.hasExtra("REPONSE_ID")) {
            id_reponse = Integer.parseInt(intent.getStringExtra("REPONSE_ID"));
        }
    }

    private void init() {
        questionManager = new QuestionManager(this);

        reponseManager = new ReponseManager(this);
        reponseManager.open();
        Reponse reponse = reponseManager.getReponse(id_reponse);
        reponseManager.close();
        if (reponse != null) {
            text_reponse = reponse.getText();
            vrai_reponse = reponse.getVrai();
        }
    }

    private void updateView() {
        aSwitch.setChecked(vrai_reponse);
        textInputEditText.setText(text_reponse);
    }

    private void gestionButton() {
        ((Button) findViewById(R.id.id_btn_modifier)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Reponse reponse;
                try {
                    questionManager.open();
                    reponse = new Reponse(id_reponse, String.valueOf(textInputEditText.getText()), aSwitch.isChecked(), questionManager.getQuestion(id_question));
                    questionManager.close();

                    reponseManager.open();
                    if (id_reponse == 0) {
                        id_reponse = (int) reponseManager.addReponse(reponse);
                    } else {
                        reponseManager.modReponse(reponse);
                    }
                    reponseManager.close();
                    Intent intent = new Intent(Edit_reponse.this, Edit_question.class);
                    intent.putExtra("QUIZZ_ID", "" + id_quizz);
                    intent.putExtra("QUESTION_ID", "" + id_question);
                    startActivity(intent);
                } catch (Exception e) {
                    toastErrorMsg(e.getMessage());
                }
            }
        });

        ((Button) findViewById(R.id.id_btn_supprime)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reponseManager.open();
                reponseManager.supReponse(reponseManager.getReponse(id_reponse));
                reponseManager.close();
            }
        });

        ((Button) findViewById(R.id.id_btn_retour)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Edit_reponse.this, Edit_question.class);
                intent.putExtra("QUIZZ_ID", "" + id_quizz);
                intent.putExtra("QUESTION_ID", "" + id_question);
                startActivity(intent);
            }
        });
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
