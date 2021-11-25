package com.example.sporreg;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class login extends AppCompatActivity {
    EditText email, password;

    class POST extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            String id = null;
            String nickName = null;
            String token = null;
            String avatar = null;
            String email = null;
            try {
                JSONObject jsonObject = new JSONObject(s);
                id= jsonObject.getString("id");
                nickName= jsonObject.getString("nickName");
                token= jsonObject.getString("token");
                avatar= jsonObject.getString("avatar");
                email= jsonObject.getString("email");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            TextView textView = findViewById(R.id.textView);
            textView.setText(id);
            textView.append(nickName);
            textView.append(token);
            textView.append(avatar);
            textView.append(email);
        }

        @Override
        protected String doInBackground(URL... urls) {
            HttpURLConnection urlConnection = null;

            try {
                urlConnection = (HttpURLConnection) urls[0].openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                urlConnection.connect();

                try (OutputStream os = urlConnection.getOutputStream()) {
                    if (email.getText().toString().equals("wsr") && password.getText().toString().equals("wsr")) {
                        byte[] out = "{\"email\":\"wsr\",\"password\":\"wsr\"}".getBytes(StandardCharsets.UTF_8);
                        os.write(out);
                    }

                }
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = urlConnection.getInputStream();
                    Scanner scanner = new Scanner(inputStream);
                    scanner.useDelimiter("\\A");
                    return scanner.next();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        String surl = "http://demo-wsr2.herokuapp.com/api/user/login";
        URL url = null;
        try {
            url = new URL(surl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Button button = findViewById(R.id.SignIn1);
        URL finalUrl = url;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new POST().execute(finalUrl);

            }
        });
    }
}
