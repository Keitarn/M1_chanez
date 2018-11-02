package com.example.bchanez.projet.controler;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.bchanez.projet.R;
import com.example.bchanez.projet.bdd.QuizzManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Gerer_select_quizz extends AppCompatActivity {
    private static final String LOG_TAG = "HttpClientGET";

    private ListView listView;
    private List<Integer> listIDQuizz = new ArrayList<Integer>();
    private List<String> listNomQuizz = new ArrayList<String>();

    private HttpPage tacheHttpPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerer_select_quizz);

        tacheHttpPage = new HttpPage();

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
//                tacheHttpPage.execute();
                com.example.bchanez.projet.tools.Toast.toast("test", getApplicationContext(), getWindowManager());
            }
        });

        ((Button) findViewById(R.id.id_btn_retour)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Gerer_select_quizz.this, Acceuil.class);
                startActivity(intent);
            }
        });
    }

//    private void loadPage() {
//        if((sPref.equals(ANY)) && (wifiConnected || mobileConnected)) {
//            new DownloadXmlTask().execute(URL);
//        }
//        else if ((sPref.equals(WIFI)) && (wifiConnected)) {
//            new DownloadXmlTask().execute(URL);
//        } else {
//            // show error
//        }
//    }

    private void getPage(String adresse) {
        BufferedReader bufferedReader = null;
        HttpURLConnection urlConnection = null;
        try {

            URL url = new URL(adresse);

            urlConnection = (HttpURLConnection) url.openConnection();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String ligneLue = bufferedReader.readLine();
                while (ligneLue != null) {
                    Log.i(LOG_TAG, ligneLue);
                    ligneLue = bufferedReader.readLine();
                }

			/*
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse (inputStream);
			doc.getDocumentElement().normalize ();


			NodeList nodeList ;
			Node channelElement = doc.getElementsByTagName("channel").item(0);

			nodeList = ((Element)channelElement).getElementsByTagName("title");
			if(nodeList.getLength() != 0){
				stringBuffer.append (nodeList.item(0).getChildNodes().item(0).getNodeValue()) ;
			}
			else{
				//
			}
			*/
            } else {
                Log.i(LOG_TAG, "Response : " + responseCode);
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, e.getMessage());
                }
            }
            if (urlConnection != null) urlConnection.disconnect();
        }
    }

    private class HttpPage extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            getPage("https://dept-info.univ-fcomte.fr/joomla/images/CR0700/Quizzs.xml");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
//             System.out.println (page) ;
//             Log.i("log", page.toString());
        }
    }
}
