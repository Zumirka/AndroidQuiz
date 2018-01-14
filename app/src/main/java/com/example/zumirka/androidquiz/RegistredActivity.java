package com.example.zumirka.androidquiz;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
    Context con;
    Encryption en= new Encryption();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registred);
        login=findViewById(R.id.Login);
        password=findViewById(R.id.Password);
        repeat_password=findViewById(R.id.PasswordRepeat);
        con=this;

    }

    Boolean check()
    {
        ConnectivityManager connectivity = (ConnectivityManager) this.getSystemService(this.CONNECTIVITY_SERVICE);
        if(connectivity !=null)

        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void OnReg(View view) throws NoSuchAlgorithmException {
        if (check() == true) {
            String str_login = login.getText().toString();
            String str_password = password.getText().toString();
            String str_password_repeat = repeat_password.getText().toString();
            boolean isEqualPassword = str_password.equals(str_password_repeat);
            if (isEqualPassword) {
                String type = "register";
                String hsh = en.CalculateHash(str_password, str_login);
                RegistredBackgroundWorker backgroundWorker = new RegistredBackgroundWorker(this, con);

                backgroundWorker.execute(type, str_login, hsh);


            } else {
                alert = new AlertDialog.Builder(this).create();
                alert.setTitle("Status Logowania:");
                alert.setMessage("Hasła się różnią. \n Wpisz hasła poprawnie.");
                alert.show();
            }
        } else {
            AlertDialog alert;
            alert=new AlertDialog.Builder(this).create();
            alert.setTitle("Status Logowania:");
            alert.setMessage("Brak połączenia z internetem.");
            alert.show();
        }

    }

}