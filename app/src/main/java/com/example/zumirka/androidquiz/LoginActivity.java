package com.example.zumirka.androidquiz;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.zumirka.androidquiz.AsyncTasks.LoginBackgroundWorker;
import com.example.zumirka.androidquiz.Utilities.Encryption;

import java.security.NoSuchAlgorithmException;


public class LoginActivity extends AppCompatActivity {


    private EditText Login, Password;
    Encryption en = new Encryption();
    String login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login = findViewById(R.id.Login);
        Password = findViewById(R.id.Password);




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

    public void OnLogin(View view) throws NoSuchAlgorithmException {
        if(check()==true) {
            login = Login.getText().toString();
            String password = Password.getText().toString();
            String type = "login";
            String SHAPassword = en.CalculateHash(password, login);
            LoginBackgroundWorker backgroundWorker = new LoginBackgroundWorker(this);
            backgroundWorker.execute(type, login, SHAPassword);
            SharedPreferences saveSettings = getSharedPreferences("BYLECO", MODE_PRIVATE);
            SharedPreferences.Editor editor = saveSettings.edit();
            editor.putString("USER_NAME", login);
            editor.commit();

        }
        else
        {
            AlertDialog alert;
            alert=new AlertDialog.Builder(this).create();
            alert.setTitle("Status Logowania:");
            alert.setMessage("Brak połączenia z internetem.");
            alert.show();
        }
    }
    public void OpenReg(View view)
    {
        startActivity(new Intent(this,RegistredActivity.class));
    }


}

