package com.example.bchanez.projet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Edit_reponse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reponse);

        ((Button) findViewById(R.id.id_btn_retour)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Edit_reponse.this, Edit_question.class);
                startActivity(intent);
            }
        });
    }
}
