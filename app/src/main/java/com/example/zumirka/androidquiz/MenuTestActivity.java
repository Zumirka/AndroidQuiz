package com.example.zumirka.androidquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuTestActivity extends AppCompatActivity {

    int IdofCategory,Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_test);
       IdofCategory= getIntent().getIntExtra("IdCategory",Id);

    }
    public void SolveTestOnClick(View view)
    {
        Intent i = new Intent(this, TestSettingsActivity.class);
        i.putExtra("IdCategory", IdofCategory);
        startActivity(i);
    }
    public void StartStatisticOnClick(View view)
    {
        startActivity(new Intent(this,StatisticActivity.class));
    }
    public void AddQuestionOnClick(View view)
    {
    startActivity(new Intent(this,AddQuestionActivity.class));
    }
}
