package com.example.zumirka.androidquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MenuTestActivity extends AppCompatActivity {

    String SelectedCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_test);
        SelectedCategory=getIntent().getStringExtra("category");

    }
    public void StartTestOnClick(View view)
    {
        Toast.makeText(getApplicationContext(),
                SelectedCategory, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, TestActivity.class);
        i.putExtra("category", SelectedCategory);
        startActivity(i);
    }
}
