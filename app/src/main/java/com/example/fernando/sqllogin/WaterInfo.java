package com.example.fernando.sqllogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class WaterInfo extends AppCompatActivity {
    private TextView name;
    private TextView waterAmount;
    private static final String QUERY_URL = "http://192.168.1.176/query.php";
    private String emailID;
    public void logout(View view){
        finish();
    }
    public void Query() {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(WaterInfo.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                String[] splited = s.split(" ");
                name.setText(splited[0] + " " + splited[1]);
                waterAmount.setText(splited[2]);
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("email", params[0]);

                String result = ruc.sendPostRequest(QUERY_URL, data);
                return result;
            }


        }


        RegisterUser ru = new RegisterUser();
        ru.execute(emailID);
        Log.d("lol", "finished");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_info);
        name = (TextView) findViewById(R.id.fullName);
        waterAmount = (TextView) findViewById(R.id.waterInfo);

        emailID = getIntent().getStringExtra("emailID");
        Log.d("lol", emailID);
        Query();
    }
}
