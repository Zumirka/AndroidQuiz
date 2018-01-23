package com.example.zumirka.androidquiz;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.zumirka.androidquiz.AsyncTasks.RegistredBackgroundWorker;
import com.example.zumirka.androidquiz.Utilities.ConnectionChecker;
import com.example.zumirka.androidquiz.Utilities.Encryption;

import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {

    EditText login, password, repeat_password;
    Context con;
    Encryption en = new Encryption();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registred);
        login = findViewById(R.id.Login);
        password = findViewById(R.id.Password);
        repeat_password = findViewById(R.id.PasswordRepeat);
        con = this;

    }


    Boolean CheckIfNotEmpty() {
        if (login.getText().toString().trim().length() == 0) {
            login.setError(this.getString(R.string.not_empty));
            return false;
        }

        if (password.getText().toString().trim().length() == 0) {
            password.setError(this.getString(R.string.not_empty));
            return false;
        }
        if(repeat_password.getText().toString().trim().length()==0)
        {
            repeat_password.setError(this.getString(R.string.not_empty));
            return false;
        }

        return true;
    }

    public void OnReg(View view) throws NoSuchAlgorithmException {
        if (ConnectionChecker.checkInternetConnection(this)) {
            if (CheckIfNotEmpty()) {
                String str_login = login.getText().toString();
                String str_password = password.getText().toString();
                String str_password_repeat = repeat_password.getText().toString();
                boolean isEqualPassword = str_password.equals(str_password_repeat);
                if (isEqualPassword) {
                    sendData(str_login, str_password);

                    InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputManager != null) {
                        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                } else {
                    repeat_password.setError(this.getString(R.string.not_equal));
                }

            }
        } else {
            showErrorDialog(this.getString(R.string.internetCommunicat));
        }

    }

    public void showErrorDialog(String dialog) {
        AlertDialog alert;
        alert = new AlertDialog.Builder(this).create();
        alert.setTitle(this.getString(R.string.status));
        alert.setMessage(dialog);
        alert.show();
    }

    private void sendData(String str_login, String str_password) {
        String hsh = en.CalculateHash(str_password, str_login);
        RegistredBackgroundWorker backgroundWorker = new RegistredBackgroundWorker(this, con,str_login,hsh);
        backgroundWorker.execute();
    }

}