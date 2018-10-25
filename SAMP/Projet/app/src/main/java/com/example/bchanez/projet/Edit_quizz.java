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

public class Edit_quizz extends AppCompatActivity {

    private ListView listView;
    private TextInputEditText textInputEditText;
    private Cursor c;

    private int id_quizz;

    private List<Integer> listIDQuestion = new ArrayList<Integer>();
    private List<String> listTextQuestion = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_quizz);

        listView = (ListView) findViewById(R.id.id_listView_edit);
        textInputEditText = (TextInputEditText) findViewById(R.id.id_input_text);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id_quizz = Integer.parseInt(extras.getString("QUIZZ_ID"));

            QuizzManager quizzManager = new QuizzManager(this);
            quizzManager.open();
            Quizz quizz = quizzManager.getQuizz(id_quizz);
            quizzManager.close();

            QuestionManager questionManager = new QuestionManager(this);
            questionManager.open();
            c = questionManager.getQuestionsByQuizz(id_quizz);
            if (c.moveToFirst()) {
                do {
                    listIDQuestion.add(c.getInt(c.getColumnIndex(QuestionManager.KEY_ID_QUESTION)));
                    listTextQuestion.add(c.getString(c.getColumnIndex(QuestionManager.KEY_TEXT_QUESTION)));
                }
                while (c.moveToNext());
            }
            c.close();
            questionManager.close();

            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, listTextQuestion);
            listView.setAdapter(adapter);

            textInputEditText.setText(quizz.getNom());
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Edit_quizz.this, Edit_question.class);
                intent.putExtra("QUIZZ_ID", "" + id_quizz);
                intent.putExtra("QUESTION_ID", "" + listIDQuestion.get(position));
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
                Intent intent = new Intent(Edit_quizz.this, Edit_question.class);
                startActivity(intent);
            }
        });

        ((Button) findViewById(R.id.id_btn_retour)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Edit_quizz.this, Gerer_select_quizz.class);
                startActivity(intent);
            }
        });
    }
}
