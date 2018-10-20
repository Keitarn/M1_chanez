package com.example.bchanez.projet;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Gerer_select_quizz extends AppCompatActivity {

    private ListView listView;
    private List<Integer> listIDQuizz = new ArrayList<Integer>();
    private List<String> listNomQuizz = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerer_select_quizz);


        QuizzManager quizzManager = new QuizzManager(this);
        quizzManager.open();
        Cursor c;
        c = quizzManager.getQuizzs();
        if (c.moveToFirst()) {
            do {
                listIDQuizz.add(c.getInt(c.getColumnIndex(QuizzManager.KEY_ID_QUIZZ)));
                listNomQuizz.add(c.getString(c.getColumnIndex(QuizzManager.KEY_NOM_QUIZZ)));
            }
            while (c.moveToNext());
        }
        c.close();
        quizzManager.close();

        listView = (ListView) findViewById(R.id.id_listView_gerer);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listNomQuizz);
        listView.setAdapter(adapter);


        ((Button) findViewById(R.id.id_btn_nouveau)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Gerer_select_quizz.this, Edit_quizz.class);
                startActivity(intent);
            }
        });

        ((Button) findViewById(R.id.id_btn_telecharger)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //todo
            }
        });

        ((Button) findViewById(R.id.id_btn_retour)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Gerer_select_quizz.this, Acceuil.class);
                startActivity(intent);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //todo
//                Intent intent = new Intent(Gerer_select_quizz.this, Jouer.class);
//                intent.putExtra("QUIZZ_ID", "" + listIDQuizz.get(position));
//                startActivity(intent);
            }
        });
    }
}
