package com.example.bchanez.projet;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Edit_question extends AppCompatActivity {

    private ListView listView;
    private TextInputEditText textInputEditText;
    private Cursor c;

    private int id_quizz;
    private int id_question;

    private List<Integer> listIDReponse = new ArrayList<Integer>();
    private List<String> listTextReponse = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);

        listView = (ListView) findViewById(R.id.id_listView_edit);
        textInputEditText = (TextInputEditText) findViewById(R.id.id_input_text);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id_quizz = Integer.parseInt(extras.getString("QUIZZ_ID"));
            id_question = Integer.parseInt(extras.getString("QUESTION_ID"));
            QuestionManager questionManager = new QuestionManager(this);
            questionManager.open();
            Question question = questionManager.getQuestion(id_question);
            questionManager.close();

            ReponseManager reponseManager = new ReponseManager(this);
            reponseManager.open();
            c = reponseManager.getReponsesByQuestion(id_question);
            if (c.moveToFirst()) {
                do {
                    listIDReponse.add(c.getInt(c.getColumnIndex(ReponseManager.KEY_ID_REPONSE)));
                    listTextReponse.add(c.getString(c.getColumnIndex(ReponseManager.KEY_TEXT_REPONSE)));
                }
                while (c.moveToNext());
            }
            c.close();
            reponseManager.close();

            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, listTextReponse);
            listView.setAdapter(adapter);

            textInputEditText.setText(question.getText());
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Edit_question.this, Edit_reponse.class);
                intent.putExtra("QUESTION_ID", "" + id_question);
                intent.putExtra("REPONSE_ID", "" + listIDReponse.get(position));
                startActivity(intent);
            }
        });

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

        ((Button) findViewById(R.id.id_btn_nouveau)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Edit_question.this, Edit_reponse.class);
                startActivity(intent);
            }
        });

        ((Button) findViewById(R.id.id_btn_retour)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Edit_question.this, Edit_quizz.class);
                intent.putExtra("QUIZZ_ID", "" + id_quizz);
                startActivity(intent);
            }
        });
    }
}
