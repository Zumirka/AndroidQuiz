package com.example.zumirka.androidquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.zumirka.androidquiz.AsyncTasks.TestDownloadBackgroundWorker;

public class TestActivity extends AppCompatActivity {

    TextView QuestionText;
    int IdCategory,IdofCategory;
    int difficulty,diff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        IdCategory=getIntent().getIntExtra("IdCategory",IdofCategory);
        difficulty=getIntent().getIntExtra("Difficulty",diff);
        difficulty++;
        QuestionText=findViewById(R.id.Question);
        CreateTest();


    }
    void CreateTest()
    {
        TestDownloadBackgroundWorker test = new TestDownloadBackgroundWorker(IdCategory, difficulty);
        test.execute();
    }


}
