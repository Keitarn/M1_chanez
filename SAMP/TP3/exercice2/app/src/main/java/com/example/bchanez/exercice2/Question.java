package com.example.bchanez.exercice2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Question extends AppCompatActivity {

    private static final String KEY_INDEX = "index";
    static final String KEY_REPONSE_QUESTION = "question";
    static Boolean KEY_REPONSE = false;

    private String question[][] = {
            {"Le diable de Tasmanie vit dans la jungle du Bresil", "faux"},
            {"La sauterelle saute l'equivalent de 200 fois sa taille", "vrai"},
            {"les pandas hibernent", "faux"},
            {"on trouve des dromadaires en liberte en Australie", "vrai"},
            {"Le papillon monarque vole plus de 4000km", "vrai"},
            {"Les gorilles males dorment dans les arbres", "faux"}
    };

    private TextView textView_question;
    private int indice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);

        textView_question = (TextView) findViewById(R.id.tv_question);
        indice = 0;

        if (savedInstanceState != null) {
            indice = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        updateAffichageQuestion(indice);

        ((Button) findViewById(R.id.btn_vrai)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (KEY_REPONSE) {
                    toastDejaconsulte();
                } else if (question[indice][1].equals("vrai")) {
                    toastVrai();
                } else {
                    toastFaux();
                }
                next();
            }
        });

        ((Button) findViewById(R.id.btn_faux)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (KEY_REPONSE) {
                    toastDejaconsulte();
                } else if (question[indice][1].equals("faux")) {
                    toastVrai();

                } else {
                    toastFaux();
                }
                next();
            }
        });

        ((Button) findViewById(R.id.btn_next)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                next();
            }
        });

        ((Button) findViewById(R.id.btn_voirReponse)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Question.this, ConsulterReponse.class);
                intent.putExtra(KEY_REPONSE_QUESTION, question[indice][1]);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, indice);
    }

    private void toastFaux() {
        Context context = getApplicationContext();
        CharSequence text = "erreur !";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void toastVrai() {
        Context context = getApplicationContext();
        CharSequence text = "Correct !";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void toastDejaconsulte() {
        KEY_REPONSE = false;
        Context context = getApplicationContext();
        CharSequence text = "Reponse deja consulte";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    private void updateAffichageQuestion(int indice) {
        textView_question.setText(question[indice][0]);
    }

    private void next() {
        indice++;
        if (indice == question.length) {
            indice = 0;
        }
        updateAffichageQuestion(indice);
    }
}
