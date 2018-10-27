package com.example.bchanez.projet.controler;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bchanez.projet.R;
import com.example.bchanez.projet.bdd.QuestionManager;
import com.example.bchanez.projet.bdd.QuizzManager;
import com.example.bchanez.projet.model.Question;
import com.example.bchanez.projet.model.Quizz;
import com.example.bchanez.projet.model.Reponse;
import com.example.bchanez.projet.bdd.ReponseManager;

public class Edit_reponse extends AppCompatActivity {

    private QuizzManager quizzManager;
    private QuestionManager questionManager;
    private ReponseManager reponseManager;

    private TextInputEditText textInputEditText;
    private Switch aSwitch;

    private Quizz quizz;
    private Question question;
    private Reponse reponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reponse);

        quizzManager = new QuizzManager(this);
        questionManager = new QuestionManager(this);
        reponseManager = new ReponseManager(this);

        textInputEditText = (TextInputEditText) findViewById(R.id.id_input_text);
        aSwitch = (Switch) findViewById(R.id.id_switch);

        getVariable();
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
        }

        if (intent.hasExtra("REPONSE_ID")) {
            reponseManager.open();
            reponse = reponseManager.getReponse(Integer.parseInt(intent.getStringExtra("REPONSE_ID")));
            reponseManager.close();
        } else {
            reponse = new Reponse(0, "", false, question);

            ((TextView) findViewById(R.id.textView)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.id_btn_modifier)).setText(getString(R.string.btn_modifier_nouveau));
            ((Button) findViewById(R.id.id_btn_supprime)).setVisibility(View.GONE);
        }
    }

    private void updateView() {
        textInputEditText.setText(reponse.getText());
        aSwitch.setChecked(reponse.getVrai());
    }

    private void gestionButton() {
        ((Button) findViewById(R.id.id_btn_modifier)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    reponse.setText(String.valueOf(textInputEditText.getText()));
                    reponse.setVrai(aSwitch.isChecked());

                    reponseManager.open();
                    if (reponse.getId() == 0) {
                        reponse.setId((int) reponseManager.addReponse(reponse));
                    } else {
                        reponseManager.modReponse(reponse);
                    }
                    reponseManager.close();
                    retour();
                } catch (Exception e) {
                    toastErrorMsg(e.getMessage());
                }
            }
        });

        ((Button) findViewById(R.id.id_btn_supprime)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reponseManager.open();
                reponseManager.supReponse(reponse);
                reponseManager.close();
                retour();
            }
        });

        ((Button) findViewById(R.id.id_btn_retour)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                retour();
            }
        });
    }

    private void retour() {
        Intent intent = new Intent(Edit_reponse.this, Edit_question.class);
        intent.putExtra("QUIZZ_ID", "" + quizz.getId());
        intent.putExtra("QUESTION_ID", "" + question.getId());
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
