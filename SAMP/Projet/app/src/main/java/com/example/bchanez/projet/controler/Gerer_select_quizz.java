package com.example.bchanez.projet.controler;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.bchanez.projet.R;
import com.example.bchanez.projet.bdd.QuizzManager;
import com.example.bchanez.projet.model.DownloadXmlTask;

import java.util.ArrayList;
import java.util.List;

public class Gerer_select_quizz extends AppCompatActivity {

    public static final String WIFI = "Wi-Fi";
    public static final String ANY = "Any";
    private static final String URL = "https://dept-info.univ-fcomte.fr/joomla/images/CR0700/Quizzs.xml";

    private static boolean wifiConnected = false;
    private static boolean mobileConnected = false;
    public static String sPref = null;

    private static final String LOG_TAG = "HttpClientGET";


    private ListView listView;
    private List<Integer> listIDQuizz = new ArrayList<Integer>();
    private List<String> listNomQuizz = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerer_select_quizz);

        listView = (ListView) findViewById(R.id.id_listView_gerer);

        init();
        updateView();
        gestionButton();
    }

    private void init() {
        Cursor c;
        QuizzManager quizzManager = new QuizzManager(this);
        quizzManager.open();
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
    }

    private void updateView() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listNomQuizz);
        listView.setAdapter(adapter);
    }

    private void gestionButton() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Gerer_select_quizz.this, Edit_quizz.class);
                intent.putExtra("QUIZZ_ID", "" + listIDQuizz.get(position));
                startActivity(intent);
            }
        });


        ((Button) findViewById(R.id.id_btn_nouveau)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Gerer_select_quizz.this, Edit_quizz.class);
                startActivity(intent);
            }
        });

        ((Button) findViewById(R.id.id_btn_telecharger)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadPage();
            }
        });

        ((Button) findViewById(R.id.id_btn_retour)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Gerer_select_quizz.this, Acceuil.class);
                startActivity(intent);
            }
        });
    }

    private void loadPage() {
        if ((sPref.equals(ANY)) && (wifiConnected || mobileConnected)) {
            new DownloadXmlTask().execute(URL);
        } else if ((sPref.equals(WIFI)) && (wifiConnected)) {
            new DownloadXmlTask().execute(URL);
        } else {
            com.example.bchanez.projet.tools.Toast.toast("Probleme connection internet", getApplicationContext(), getWindowManager());
        }
    }
}
