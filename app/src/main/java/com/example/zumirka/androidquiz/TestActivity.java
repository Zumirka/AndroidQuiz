package com.example.zumirka.androidquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.zumirka.androidquiz.AsyncTasks.TestDownloadBackgroundWorker;

public class TestActivity extends AppCompatActivity {

    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        category=getIntent().getStringExtra("category");
        TestDownloadBackgroundWorker test = new TestDownloadBackgroundWorker(21, 1);
        test.execute();

    }

}
