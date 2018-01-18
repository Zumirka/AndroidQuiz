package com.example.zumirka.androidquiz;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

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

    Boolean CheckIfNotEmpty()
    {
        Boolean t=false;
            if(Login.getText().toString().trim().length()==0)
            {
                Login.setError(this.getString(R.string.not_empty));
                 t=false;
            }
            if(Password.getText().toString().trim().length()==0)
            {
                Password.setError(this.getString(R.string.not_empty));
            }
            else {
                t = true;
            }

        return t;
    }


    public void OnLogin(View view) throws NoSuchAlgorithmException {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Logowanie...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);

        if(check()==true) {
            if(CheckIfNotEmpty()) {
                dialog.show();
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
                InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputManager != null) {
                    inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                dialog.hide();
            }

        }
        else
        {
            dialog.hide();
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

