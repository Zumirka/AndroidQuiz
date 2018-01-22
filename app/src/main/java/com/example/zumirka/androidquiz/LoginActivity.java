package com.example.zumirka.androidquiz;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.zumirka.androidquiz.AsyncTasks.LoginBackgroundWorker;
import com.example.zumirka.androidquiz.Utilities.ConnectionChecker;
import com.example.zumirka.androidquiz.Utilities.Encryption;


public class LoginActivity extends AppCompatActivity {


    Encryption en = new Encryption();
    String login;
    String password;
    private EditText loginEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginEditText = findViewById(R.id.Login);
        passwordEditText = findViewById(R.id.Password);


    }

    // Sprawdzanie czy kontrolki nie są puste
    Boolean CheckIfNotEmpty() {
        if (loginEditText.getText().toString().trim().length() == 0) {
            loginEditText.setError(this.getString(R.string.not_empty));
            return false;
        }

        if (passwordEditText.getText().toString().trim().length() == 0) {
            passwordEditText.setError(this.getString(R.string.not_empty));
            return false;
        }
        return true;
    }

    // obsługiwanie kliknięcia buttona
    public void OnLogin(View view) {
        ProgressDialog dialog = prepareDialog();

        if (ConnectionChecker.checkInternetConnection(this)) {
            if (CheckIfNotEmpty()) {
                dialog.show();
                sendLoginAndPassword();
                saveInSharedPref();

                InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputManager != null) {
                    inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                dialog.hide();
            }

        } else {
            dialog.hide();
            showErrorDialog();
        }
    }

    //pokazanie komunikatu o braku połączenia z internetem
    private void showErrorDialog() {
        AlertDialog alert;
        alert = new AlertDialog.Builder(this).create();
        alert.setTitle(this.getString(R.string.status));
        alert.setMessage(this.getString(R.string.internetCommunicat));
        alert.show();
    }
    //zapisanie nazwy użytkownika w danych aplikacji

    private void saveInSharedPref() {
        //inicjalizacja obiektu SharedPreferences. Przyjmuje dwa parametry:
        // nazwę pliku w któym chcemy przechowywać dane o tryb w jakim ma byc otwarty plik z naszymi danymi
        SharedPreferences saveSettings = getSharedPreferences("userName", MODE_PRIVATE);
        // zapisanie zmian
        SharedPreferences.Editor editor = saveSettings.edit();
        editor.putString("USER_NAME", login);
        editor.commit();
    }

    // Przepisanie tekstów z kontrolek, stworzenie SHA hasła i stworzenie obiektu LoginBackgroundWorker
    private void sendLoginAndPassword() {
        login = loginEditText.getText().toString();
        password = passwordEditText.getText().toString();
        String SHAPassword = en.CalculateHash(password, login);
        LoginBackgroundWorker backgroundWorker = new LoginBackgroundWorker(this, login, SHAPassword);
        backgroundWorker.execute();
    }

    // wyświetlenie postępu logowania
    @NonNull
    private ProgressDialog prepareDialog() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(this.getString(R.string.loginProgress));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        return dialog;
    }

    //obsługa buttona otwierającego okno rejestracji
    public void OpenReg(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }


}

