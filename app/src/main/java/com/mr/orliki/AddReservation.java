package com.mr.orliki;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddReservation extends AppCompatActivity {
    String orlik_id;
    Activity act;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);


        act = (Activity) this;
        orlik_id  = "";
        Intent intent = getIntent();
        try{
            orlik_id = ((Intent) intent).getStringExtra(DisplayOrlikDetails.addExtra);
        }catch(Exception e){
            Log.e("error", "bad orlik id");
        }


    }

    public void sendClicked(View view) throws UnsupportedEncodingException {
        Button btn = (Button )findViewById(R.id.sendBtn);
        EditText ktoInput = (EditText )findViewById(R.id.ktoInput);
        EditText kiedyInput = (EditText ) findViewById(R.id.kiedyInput);
        EditText ileInput = (EditText )  findViewById(R.id.ileInput);

        String ktoInputTxt = String.valueOf(ktoInput.getText());
        String kiedyInputTxt = String.valueOf(kiedyInput.getText());
        String ileInputTxt = String.valueOf(ileInput.getText());

        HashMap<String, String> postDataParams  = new HashMap<>();
        postDataParams.put("data", kiedyInputTxt);
        postDataParams.put("czas", ileInputTxt);
        postDataParams.put("nazwa", ktoInputTxt);
        postDataParams.put("orlik", orlik_id);

        String data = getPostDataString(postDataParams);
        Constants.makeHttpRequest(Constants.API_URL + "rezerwations/", Constants.REQUEST_METHOD_POST, data).then( res -> {

                if((Boolean)res != false){
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.addReserv),
                            "Dodano. Zrestartuj aplikacje", Snackbar.LENGTH_SHORT);
                    mySnackbar.setAction("RESTART", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            act.recreate();
                        }
                    });
                    mySnackbar.show();
                }
                else
                    onBackPressed();

                return false;

        } );
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
