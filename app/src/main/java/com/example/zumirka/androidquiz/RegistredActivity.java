package com.example.zumirka.androidquiz;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegistredActivity extends AppCompatActivity {

    EditText login, password,repeat_password;
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registred);
        login=findViewById(R.id.Login);
        password=findViewById(R.id.Password);
        repeat_password=findViewById(R.id.PasswordRepeat);

    }

    public void OnReg(View view)
    {
        String str_login= login.getText().toString();
        String str_password = password.getText().toString();
        String str_password_repeat=repeat_password.getText().toString();
        boolean isEqualPassword=str_password.equals(str_password_repeat);
        if(isEqualPassword) {
            String type = "register";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, str_login, str_password);

        }
        else
        {
            alert=new AlertDialog.Builder(this).create();
            alert.setMessage("Hasła się różnią");
            alert.show();
        }
    }




}