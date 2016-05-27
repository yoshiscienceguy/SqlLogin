package com.example.fernando.sqllogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class WelcomPage extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private static final String REGISTER_URL = "http://192.168.1.176/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom_page);

        username = (EditText) findViewById(R.id.emailId);
        password = (EditText) findViewById(R.id.pass);
    }
    public void Login(View view){
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(WelcomPage.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equals("Sucess")){
                    Intent nextPage = new Intent(WelcomPage.this,WaterInfo.class);
                    String un = username.getText().toString().trim().toLowerCase();
                    nextPage.putExtra("emailID",un);
                    WelcomPage.this.startActivity(nextPage);
                }
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("email", params[0]);
                data.put("password", params[1]);

                String result = ruc.sendPostRequest(REGISTER_URL, data);
                return result;
            }


        }

        String un = username.getText().toString().trim().toLowerCase();
        String pass = password.getText().toString().trim().toLowerCase();

        RegisterUser ru = new RegisterUser();
        ru.execute(un, pass);
        Log.d("lol","finished");


    }

}
