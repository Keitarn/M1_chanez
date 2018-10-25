package com.example.bchanez.projet;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Edit_reponse extends AppCompatActivity {

    private ListView listView;
    private TextInputEditText textInputEditText;

    private int id_question;
    private int id_reponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reponse);

        textInputEditText = (TextInputEditText) findViewById(R.id.id_input_text);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id_question = Integer.parseInt(extras.getString("QUESTION_ID"));
            id_reponse = Integer.parseInt(extras.getString("REPONSE_ID"));
            ReponseManager reponseManager = new ReponseManager(this);
            reponseManager.open();
            Reponse reponse = reponseManager.getReponse(id_reponse);
            reponseManager.close();

            textInputEditText.setText(reponse.getText());
        }

        ((Button) findViewById(R.id.id_btn_supprime)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //todo
            }
        });

        ((Button) findViewById(R.id.id_btn_modifier)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //todo
            }
        });

        ((Button) findViewById(R.id.id_btn_retour)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Edit_reponse.this, Edit_question.class);
                intent.putExtra("QUESTION_ID", "" + id_question);
                startActivity(intent);
            }
        });
    }
}
