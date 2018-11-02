package com.example.bchanez.projet.model;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadXmlTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
        getPage("");
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
//             System.out.println (page) ;
//             Log.i("log", page.toString());
    }
}

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