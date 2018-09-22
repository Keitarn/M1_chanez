package com.example.bchanez.exercice2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.bchanez.exercice2.MainActivity.KEY_REPONSE;
import static com.example.bchanez.exercice2.MainActivity.KEY_REPONSE_QUESTION;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        final TextView textView_reponse = (TextView) findViewById(R.id.tv_reponse);

        ((Button) findViewById(R.id.btn_montrezLaReponse)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = getIntent();
                textView_reponse.setText(intent.getStringExtra(KEY_REPONSE_QUESTION));
                KEY_REPONSE = true;
            }
        });
    }
}
