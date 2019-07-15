package com.mr.orliki;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private int selected_orlik = 0;
    public static final String fieldExtra = "fieldExtra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Constants.makeHttpRequest(Constants.API_URL + "orliks", Constants.REQUEST_METHOD_GET, "").then( res -> {
            try {
                ArrayList<Orlik> orliks = Constants.readJsonStream( (InputStream) res );
                Constants.saveOrliksSharedPref( MainActivity.this, orliks);


                Button fieldSelectButton = (Button)  findViewById(R.id.fieldSelectButton);
                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                fieldSelectButton.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);


            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } );
    }


    public void fieldSelectOnClick(View view) {
        Intent intent = new Intent(getApplicationContext(), FieldSelect.class);
        startActivityForResult(intent,  1);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == FieldSelect.RESULT_OK) {
                String id = data.getStringExtra("idRet");

                ArrayList<Orlik> orliks = Constants.getOrliksSharedPref(this);

                Orlik orlik = null;
                for(int i=0; i< orliks.size(); i++){
                    Orlik temp = orliks.get(i);
                    Log.i("te", temp.id);
                    Log.i("ret", id);
                    if(temp.id.equals(id)){
                        orlik = temp;
                        break;
                    }
                }
                if(orlik == null){
                    Log.e("error", "orlik not found");
                    return;
                }
                else{

                    Intent intent = new Intent(getApplicationContext(), DisplayOrlikDetails.class);
                    intent.putExtra(fieldExtra, orlik);
                    startActivity(intent);
                }

            }

        }

    }
}


