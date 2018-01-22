package com.example.zumirka.androidquiz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class InformtionActivity extends AppCompatActivity {

    String chooseTyp, type;
    TextView text;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        type = getIntent().getExtras().getString("Type", chooseTyp);
        text = findViewById(R.id.Information);
        checkType();
    }

    void checkType() {
        switch (type) {
            case "Creators":
                setText("c");
                break;
            case "AppInfo":
                setText("a");
                break;
            case "Suggestion":
                sendMail();
            default:
                return;
        }

    }
    void setText(String s)
    {
        if(s=="c")
        {
            text.setText("Witaj w aplikacji Quiz Edukacyjny! \n" +
                    "Aplikacja ta została stworzona przez Dominikę Bartek i Agatę Gabryel na potrzeby projektu inżynierskiego. \n" +
                    "Mamy nadzieję że aplikacja Ci się spodoba. \n" +
                    "Życzymy samych najlepszych wyników!");
        }
        if(s=="a")
        {
            text.setText("Aplikacja Quiz Edukacyjny została stworzona na potrzeby projektu inżynierskiego.\n" +
                    "Dzięki tej aplikacji możeesz sprawdzić swoją wiedzę i samodoskonalić się. \n " +
                    "Możesz również dzielić się swoją wiedzą z innymi uzytkownikami dodając pytania do naszej bazy pytań.\n" +
                    "Życzymy jak najlepszych wyników i satysfakcji z poszerzania swojej wiedzy!");
        }
    }
    void sendMail()
    {
        Log.i("Send email", "");

        String[] TO = {"dominika.bartek1207@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Propozycja zmian");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Proponuję aby...");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(InformtionActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
