package com.example.bchanez.exercice2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String question[][] = {
            {"Le diable de Tasmanie vit dans la jungle du Bresil", "faux"},
            {"La sauterelle saute l'equivalent de 200 fois sa taille", "vrai"},
            {"les pandas hibernent", "faux"},
            {"on trouve des dromadaires en liberte en Australie", "vrai"},
            {"Le papillon monarque vole plus de 4000km", "vrai"},
            {"Les gorilles males dorment dans les arbres", "faux"}
    };

    private TextView textView_question;
    private TextView textView_reponse;
    private int indice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_question = (TextView) findViewById(R.id.tv_question);
        indice = 0;

        updateAffichageQuestion(indice);

        ((Button) findViewById(R.id.btn_vrai)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (question[indice][1].equals("vrai")) {
                    toastVrai();
                } else {
                    toastFaux();
                }
                next();
            }
        });

        ((Button) findViewById(R.id.btn_faux)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (question[indice][1].equals("faux")) {
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

            }
        });


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

    private void updateAffichageQuestion(int indice) {
        textView_question.setText(question[indice][0]);
    }

    private void next() {
        indice++;
        updateAffichageQuestion(indice);
    }
}
