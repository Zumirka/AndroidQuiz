package com.example.zumirka.androidquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class InformtionActivity extends AppCompatActivity {

   String ChooseTyp ,type,C="Creators",I="AppInfo";
    TextView tekst;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        type=getIntent().getExtras().getString("Type",ChooseTyp);
        tekst=findViewById(R.id.textView);

       // tekst.setText(type);
        WhatTypeChoosen();


    }
    void WhatTypeChoosen()
    {
        if(type.equals(C))
        {
            tekst.setText("Twórcy");
        }
        else if(type.equals(I))
        {
            tekst.setText("Info o apce");
        }
    }



}
