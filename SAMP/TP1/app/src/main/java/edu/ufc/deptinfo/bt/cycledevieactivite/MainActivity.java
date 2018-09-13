package edu.ufc.deptinfo.bt.cycledevieactivite;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MonActivite";
    private static final String KEY_INDEX = "index";

    int compteur;
    TextView affichage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        compteur = 0;
        affichage = (TextView) findViewById(R.id.clics);

        if (savedInstanceState != null) {
            Log.i(TAG, "restoreState");
            compteur = savedInstanceState.getInt(KEY_INDEX, 0);
        }
        miseAJour(compteur);

        ((Button) findViewById(R.id.btn_inc)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.inc();
            }
        });
        ((Button) findViewById(R.id.btn_dec)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.dec();
            }
        });
        ((Button) findViewById(R.id.btn_clear)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.clear();
            }
        });

        ((Button) findViewById(R.id.btn_googleMaps)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                double latitude = 37.4222219;
                double longitude = -122.08364;

                Uri uriLocation = Uri.parse("geo:" + new Double(latitude).toString() + "," + new Double(longitude).toString() + "?z=8");
                Intent intent = new Intent(Intent.ACTION_VIEW, uriLocation);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, compteur);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void miseAJour(int compteur) {
        affichage.setText(Integer.toString(compteur));
    }

    public void inc() {
        compteur += 1;
        miseAJour(compteur);
    }

    public void dec() {
        compteur -= 1;
        miseAJour(compteur);
    }

    public void clear() {
        compteur = 0;
        miseAJour(compteur);
    }
}

