package com.example.zumirka.androidquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class InformtionActivity extends AppCompatActivity {

    String chooseTyp, type;
    TextView text;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        type = getIntent().getExtras().getString("Type", chooseTyp);
        text = findViewById(R.id.textView);
        checkType();
    }

    void checkType() {
        switch (type) {
            case "Creators":
                text.setText("Twórcy");
                break;
            case "AppInfo":
                text.setText("Info");
                break;
        }

    }

}
