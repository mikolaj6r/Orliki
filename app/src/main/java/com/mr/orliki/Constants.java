package com.mr.orliki;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.crawlink.Promise;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Constants {
    // Debug log tag.
    public static final String TAG_HTTP_URL_CONNECTION = "HTTP_URL_CONNECTION";

    public static final String API_URL = "http://orliki.mikolaj6r.usermd.net/";

    // Child thread sent message type value to activity main thread Handler.
    public static final int REQUEST_CODE_SHOW_RESPONSE_TEXT = 1;

    // The key of message stored server returned data.
    public static final String KEY_RESPONSE_TEXT = "KEY_RESPONSE_TEXT";

    // Request method GET. The value must be uppercase.
    public static final String REQUEST_METHOD_GET = "GET";
    public static final String REQUEST_METHOD_POST = "POST";


    public static Promise makeHttpRequest(final String reqUrl, final String method, final String data)
    {
        Promise p = new Promise();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // Maintain http url connection.
                HttpURLConnection httpConn = null;
                InputStream responseBody = null;


                if (method == REQUEST_METHOD_GET) {
                    try {
                        // Create a URL object use page url.
                        URL url = new URL(reqUrl);

                        // Open http connection to web server.
                        httpConn = (HttpURLConnection) url.openConnection();

                        // Set http request method to get.
                        httpConn.setRequestMethod(method);
                        httpConn.setRequestProperty("User-Agent", "orliki-app");


                        if (httpConn.getResponseCode() == 200) {
                            // Success
                            responseBody = httpConn.getInputStream();

                        } else {
                            // Error handling code goes here
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (httpConn != null) {
                            httpConn.disconnect();
                            httpConn = null;
                        }
                        if (responseBody != null) {
                            p.resolve(responseBody);
                        }

                        p.reject("error occured");
                    }
                } else {
                    URL url;
                    String response = "";
                    try {
                        url = new URL(reqUrl);

                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(15000);
                        conn.setConnectTimeout(15000);
                        conn.setRequestMethod(method);
                        conn.setDoInput(true);
                        conn.setDoOutput(true);


                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(
                                new OutputStreamWriter(os, "UTF-8"));
                        writer.write(data);

                        writer.flush();
                        writer.close();
                        os.close();
                        int responseCode = conn.getResponseCode();

                        if (responseCode == HttpsURLConnection.HTTP_OK) {
                            p.resolve(true);
                        } else {
                            p.reject(false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        return p;
    }

    public static final String SHARED_PREF_NAME = "DOC";
    public static final String ORLIKS_STRING = "ORLIKS";

    public static void saveOrliksSharedPref(Context mContext, ArrayList<Orlik> orliks){
        SharedPreferences mPrefs = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(orliks);
        prefsEditor.putString(ORLIKS_STRING, json);
        prefsEditor.commit();
    }

    public static ArrayList<Orlik> getOrliksSharedPref(Context mContext){
        SharedPreferences mPrefs = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = mPrefs.getString(ORLIKS_STRING, "");
        if(json.equalsIgnoreCase("")){
            return null;
        }
        Orlik [] orliks = gson.fromJson(json, Orlik[].class);
        ArrayList<Orlik> obj = new ArrayList<Orlik>(Arrays.asList(orliks));
        return obj;
    }

    public static ArrayList<Orlik> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readOrliksArray(reader);
        } finally {
            reader.close();
        }
    }

    public static ArrayList<Orlik> readOrliksArray(JsonReader reader) throws IOException {
        ArrayList<Orlik> orliks = new ArrayList<Orlik>();

        reader.beginArray();
        while (reader.hasNext()) {
            orliks.add(readOrlik(reader));
        }
        reader.endArray();
        return orliks;
    }

    public static Orlik readOrlik(JsonReader reader) throws IOException {

        String id = "";
        String adres = null;
        ArrayList<Reservation> reservations = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextString();
            } else if (name.equals("adres")) {
                adres = reader.nextString();
            } else if (name.equals("rezerwacjas") && reader.peek() != JsonToken.NULL) {
                reservations = readReservationsArray(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Orlik(id, adres, reservations);
    }

    public static ArrayList<Reservation> readReservationsArray(JsonReader reader) throws IOException {
        ArrayList<Reservation> reservations = new ArrayList<Reservation>();

        reader.beginArray();
        while (reader.hasNext()) {
            reservations.add(readReservation(reader));
        }
        reader.endArray();
        return reservations;
    }

    public static Reservation readReservation(JsonReader reader) throws IOException {
        String time = "";
        String date = "";
        String who = "";
        String where = "";
        String id = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("czas")) {
                time = reader.nextString();
            } else if (name.equals("data")) {
                date = reader.nextString();
            } else if (name.equals("nazwa")) {
                who = reader.nextString();
            } else if (name.equals("orlik")) {
                where = reader.nextString();
            } else if (name.equals("id")) {
                id = reader.nextString();
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Reservation(time, date, who, where, id);
    }


}


