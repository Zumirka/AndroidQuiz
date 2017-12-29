package com.example.zumirka.androidquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MenuTestActivity extends AppCompatActivity {

    int IdCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_test);
        getIntent().getIntExtra("IdCategory",IdCategory);

    }
    public void StartTestOnClick(View view)
    {
        Intent i = new Intent(this, TestActivity.class);
        i.putExtra("IdCategory", IdCategory);
        startActivity(i);
    }
    public void StartStatisticActivity(View view)
    {
        startActivity(new Intent(this,StatisticActivity.class));
    }
}
