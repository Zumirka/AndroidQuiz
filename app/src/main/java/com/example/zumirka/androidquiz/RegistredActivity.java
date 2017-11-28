package com.example.zumirka.androidquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegistredActivity extends AppCompatActivity {

    EditText login, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registred);
        login=findViewById(R.id.Login);
        password=findViewById(R.id.Password);


    }
    public void OnReg(View view)
    {
        String str_login= login.getText().toString();
        String str_password = password.getText().toString();
        String type= "register";
        BackgroundWorker backgroundWorker=new BackgroundWorker(this);
        backgroundWorker.execute(type,str_login,str_password);
    }
}