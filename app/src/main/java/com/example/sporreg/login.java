package com.example.sporreg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class login extends AppCompatActivity {
    EditText username, password;
    Button signin, signup, back;
    com.example.sporreg.DB DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username1);
        password = findViewById(R.id.password1);
        signin = findViewById(R.id.SignIn1);
        signup = findViewById(R.id.SignUp);
        DB = new DB (this);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass))
                    alert.setMessage("Заполните все поля").show();
                else{
                    Boolean checkuserpass = DB.checkusernamepassword(user,pass);
                    if(checkuserpass==true){
                        alert.setMessage("Авторизация прошла успешно").show();
                        Intent i = new Intent(getApplicationContext(), menu.class);
                        startActivity(i);
                        finish();
                    }else{
                        alert.setMessage("Ошибка авторизации").show();
                    }
                }
            }
        });
    }

    public void SignUp(View view)
    {
        Intent i = new Intent(login.this, registration.class);
        startActivity(i);
        finish();
    }

    public void Perehod (View view)
    {
        Intent i = new Intent(login.this, menu.class);
        startActivity(i);
        finish();
    }
}
