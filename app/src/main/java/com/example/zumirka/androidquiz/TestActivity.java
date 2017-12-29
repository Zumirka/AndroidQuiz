package com.example.zumirka.androidquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zumirka.androidquiz.AsyncTasks.TestDownloadBackgroundWorker;

public class TestActivity extends AppCompatActivity {

    int IdCategory;
    int difficulty=0;
    Button LatwyBtn,SredniBtn,TrudnyBtn;
    TextView tekst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getIntent().getIntExtra("IdCategory",IdCategory);
        LatwyBtn =(Button) findViewById(R.id.LatwyBtn);
        LatwyBtn.setOnClickListener(ClickButtons);
        SredniBtn= findViewById(R.id.SredniBtn);
        SredniBtn.setOnClickListener(ClickButtons);
        TrudnyBtn= findViewById(R.id.TrudnyBtn);
        TrudnyBtn.setOnClickListener(ClickButtons);
        tekst=findViewById(R.id.textView);


    }
    void CreateTest()
    {
        TestDownloadBackgroundWorker test = new TestDownloadBackgroundWorker(IdCategory, difficulty);
        test.execute();
    }
    void HideChoose()
    {
        LatwyBtn.setVisibility(View.GONE);
        SredniBtn.setVisibility(View.GONE);
        TrudnyBtn.setVisibility(View.GONE);
        tekst.setText("");


    }
    View.OnClickListener ClickButtons = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.LatwyBtn:
                   difficulty=1;
                    HideChoose();
                    CreateTest();
                    break;
                case R.id.SredniBtn:
                    difficulty=2;
                    HideChoose();
                    CreateTest();
                    break;
                case R.id.TrudnyBtn:
                    difficulty=3;
                    HideChoose();
                    CreateTest();
                    break;
            }
        }
    };
}
