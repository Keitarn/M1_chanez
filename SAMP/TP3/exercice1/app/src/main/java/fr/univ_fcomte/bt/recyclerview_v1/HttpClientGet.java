package fr.univ_fcomte.bt.recyclerview_v1;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class HttpClientGet{
    private static final String LOG_TAG = "HttpClientGET";
    public List<String> returnList;
    public static HttpPage tacheHttpPage;


    HttpClientGet(){
        Log.d("OUI", "HttpClientGet");
        this.returnList = new ArrayList<>();
        tacheHttpPage = new HttpPage();
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

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(inputStream);
                doc.getDocumentElement().normalize();


                NodeList nodeList;
                Node channelElement = doc.getElementsByTagName("channel").item(0);

                nodeList = ((Element) channelElement).getElementsByTagName("title");
                StringBuffer stringBuffer = new StringBuffer();
                if (nodeList.getLength() != 0) {
                    stringBuffer.append(nodeList.item(0).getChildNodes().item(0).getNodeValue());

                    for (int i = 0; i< nodeList.getLength(); i++){
                        this.returnList.add(nodeList.item(i).getFirstChild().getNodeValue());
                    }
                    Log.d(LOG_TAG, "KONO DIO DA");

                } else {
                    //
                }
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

    public class HttpPage extends AsyncTask<Void, Void, List> {

        @Override
        protected List doInBackground(Void... params) {
            // getPage("https://www.lemonde.fr/rss/tag/enseignement-superieur.xml") ;
            getPage("https://www.programmez.com/rss/rss_actu.php");
            return returnList;
        }


        @Override
        public void onPostExecute(List result) { ;
            Log.i(LOG_TAG, "pizza mozzarella");
        }
    }
}