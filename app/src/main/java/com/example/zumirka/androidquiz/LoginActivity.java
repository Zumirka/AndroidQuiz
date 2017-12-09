package com.example.zumirka.androidquiz;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.zumirka.androidquiz.AsyncTasks.LoginBackgroundWorker;


public class LoginActivity extends AppCompatActivity  {


    private EditText Login, Password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login=findViewById(R.id.Login);
        Password=findViewById(R.id.Password);


    }

    public void OnLogin(View view) {
        String login= Login.getText().toString();
        String password = Password.getText().toString();
        String type= "login";
        LoginBackgroundWorker backgroundWorker= new LoginBackgroundWorker(this);
        backgroundWorker.execute(type,login,password);


    }
    public void OpenReg(View view)
    {
        startActivity(new Intent(this,RegistredActivity.class));
    }

}

