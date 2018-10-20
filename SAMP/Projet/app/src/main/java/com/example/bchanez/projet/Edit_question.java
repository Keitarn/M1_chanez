package com.example.bchanez.projet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Edit_question extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);

        ((Button) findViewById(R.id.id_btn_nouveau)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Edit_question.this, Edit_reponse.class);
                startActivity(intent);
            }
        });

        ((Button) findViewById(R.id.id_btn_retour)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Edit_question.this, Edit_quizz.class);
                startActivity(intent);
            }
        });
    }
}
