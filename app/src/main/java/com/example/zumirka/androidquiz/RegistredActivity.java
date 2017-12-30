package com.example.zumirka.androidquiz;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.zumirka.androidquiz.AsyncTasks.RegistredBackgroundWorker;
import com.example.zumirka.androidquiz.Utilities.Encryption;

import java.security.NoSuchAlgorithmException;

public class RegistredActivity extends AppCompatActivity {

    EditText login, password,repeat_password;
    AlertDialog alert;
    Encryption en= new Encryption();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registred);
        login=findViewById(R.id.Login);
        password=findViewById(R.id.Password);
        repeat_password=findViewById(R.id.PasswordRepeat);

    }

    public void OnReg(View view) throws NoSuchAlgorithmException {
        String str_login= login.getText().toString();
        String str_password = password.getText().toString();
        String str_password_repeat=repeat_password.getText().toString();
        boolean isEqualPassword=str_password.equals(str_password_repeat);
        if(isEqualPassword) {
            String type = "register";
            String hsh= en.CalculateHash(str_password,str_login);
            RegistredBackgroundWorker backgroundWorker = new RegistredBackgroundWorker(this);

            backgroundWorker.execute(type, str_login, hsh);


        }
        else
        {
            alert=new AlertDialog.Builder(this).create();
            alert.setTitle("Status Logowania:");
            alert.setMessage("Hasła się różnią. \n Wpisz hasła poprawnie.");
            alert.show();
        }
    }
    public void FinishActivity()
    {
        finish();
    }




}