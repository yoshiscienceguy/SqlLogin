package com.example.fernando.sqllogin;

/**
 * Created by Fernando on 5/12/2016.
 */
import android.os.Debug;
import android.util.Log;
import org.apache.http.HttpException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RegisterUserClass {
    public String sendPostRequest(String requestURL,HashMap<String, String> postDataParams){

        URL url;
        String response = "";
        try {
            Log.d("lol","waiting");
            url = new URL(requestURL);
            Log.d("lol","ioio");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Log.d("lol","waiting");
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();
            Log.d("lol", Integer.toString(responseCode));
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                //response = br.readLine();
                StringBuilder everything = new StringBuilder();
                String line;
                while( (line = br.readLine()) != null) {
                    everything.append(line);
                }
                response = everything.toString();
                Log.d("lol",response);
            }
            else {
                response="Error";
            }
        } catch (Exception e) {
            Log.d("lol","wow");
            e.printStackTrace();
        }
        Log.d("lol",response);
        return response;
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
