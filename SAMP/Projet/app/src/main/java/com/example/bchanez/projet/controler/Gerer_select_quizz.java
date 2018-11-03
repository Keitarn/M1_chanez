package com.example.bchanez.projet.controler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.bchanez.projet.R;
import com.example.bchanez.projet.bdd.QuizzManager;
import com.example.bchanez.projet.tools.QuizzXmlParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Gerer_select_quizz extends AppCompatActivity {

    public static final String WIFI = "Wi-Fi";
    public static final String ANY = "Any";
    private static final String URL_QUIZZS = "https://dept-info.univ-fcomte.fr/joomla/images/CR0700/Quizzs.xml";

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

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        sPref = sharedPrefs.getString("listPref", "Any");

        updateConnectedFlags();
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

    private void updateConnectedFlags() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        } else {
            wifiConnected = false;
            mobileConnected = false;
        }
    }

    private void loadPage() {
        if ((sPref.equals(ANY)) && (wifiConnected || mobileConnected)) {
            new DownloadXmlTask().execute(URL_QUIZZS);
        } else if ((sPref.equals(WIFI)) && (wifiConnected)) {
            new DownloadXmlTask().execute(URL_QUIZZS);
        } else {
            com.example.bchanez.projet.tools.Toast.toast(getResources().getString(R.string.connection_error), getApplicationContext(), getWindowManager());
        }
    }

    private class DownloadXmlTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                return loadXmlFromNetwork(urls[0]);
            } catch (IOException e) {
                return getResources().getString(R.string.connection_error);
            } catch (XmlPullParserException e) {
                return getResources().getString(R.string.xml_error);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            com.example.bchanez.projet.tools.Toast.toast(result, getApplicationContext(), getWindowManager());
        }
    }

    private String loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
        InputStream stream = null;

        QuizzXmlParser quizzXmlParser = new QuizzXmlParser();

        try {
            stream = downloadUrl(urlString);
            quizzXmlParser.parse(stream, getApplicationContext());
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        return getResources().getString(R.string.download_finished);
    }

    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        return conn.getInputStream();
    }
}
