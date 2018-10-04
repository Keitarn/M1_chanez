package com.example.bchanez.projet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Acceuil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);

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
