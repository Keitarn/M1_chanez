package com.example.bchanez.projet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Jouer_select_quizz extends AppCompatActivity {

    private ListView listView;

    private String[] prenoms = new String[]{
            "Antoine", "Benoit", "Cyril", "David", "Eloise", "Florent",
            "Gerard", "Hugo", "Ingrid", "Jonathan", "Kevin", "Logan",
            "Mathieu", "Noemie", "Olivia", "Philippe", "Quentin", "Romain",
            "Sophie", "Tristan", "Ulric", "Vincent", "Willy", "Xavier",
            "Yann", "Zoé"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jouer_select_quizz);

        listView = (ListView) findViewById(R.id.id_listView_jouer);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, prenoms);
        listView.setAdapter(adapter);

        ((Button) findViewById(R.id.id_btn_retour)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Jouer_select_quizz.this, Acceuil.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);

                // Display the selected item text on TextView
                Log.d("test", selectedItem);
                Log.d("test", ""+position);
                Log.d("test", ""+id);
            }
        });
    }
}
