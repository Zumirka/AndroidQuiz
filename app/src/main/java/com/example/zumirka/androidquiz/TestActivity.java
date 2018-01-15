package com.example.zumirka.androidquiz;

import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zumirka.androidquiz.AsyncTasks.AddStatisticBackgroundWorker;
import com.example.zumirka.androidquiz.AsyncTasks.TestDownloadBackgroundWorker;
import com.example.zumirka.androidquiz.Model.Answer;
import com.example.zumirka.androidquiz.Model.Question;
import com.example.zumirka.androidquiz.Model.Test;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

public class TestActivity extends AppCompatActivity {

    TextView QuestionText, Clock, QuestionNumber;
    String time,userName="";
    int IdCategory, IdofCategory, index = 0, PointsCount = 0;
    int difficulty, diff, CorrectAnswear = 0;
    Test QuestionsOfTest;
    Boolean EndTest = false,canCreateTest=true;
    ArrayList<Entry> entries;
    ArrayList<String> PieEntryLabels;
    ArrayList<Question> questionsForTest;
    ArrayList<Answer> answers;
    ArrayList<Button> AnswearButtons = new ArrayList<Button>();
    Thread t;
    Date diffDate=new Date();
    PieChart pieChart;
    PieDataSet pieDataSet;
    PieData pieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences resSettings = getSharedPreferences("BYLECO", MODE_PRIVATE);
        userName = resSettings.getString("USER_NAME", "empty");
        setContentView(R.layout.activity_test);
        startClock();
        IdCategory = getIntent().getIntExtra("IdCategory", IdofCategory);
        difficulty = getIntent().getIntExtra("Difficulty", diff);
        difficulty++;
        CreateTest();
        if(canCreateTest) { InitializeControls();}


    }

    public void AddValuesToPIEENTRY() {
        int PercentValue = CorrectAnswear * 100 / PointsCount;
        entries.add(new BarEntry(PercentValue, 0));
        entries.add(new BarEntry((100 - PercentValue), 1));


    }

    public void AddValuesToPieEntryLabels() {

        PieEntryLabels.add("Dobre odpowiedzi");
        PieEntryLabels.add("Złe odpowiedzi");


    }


    void CreateTest() {

        TestDownloadBackgroundWorker test = new TestDownloadBackgroundWorker(IdCategory, difficulty, this);
        test.execute();

    }

    public void QuestionTaker(Test testGenerate) {
        this.QuestionsOfTest = testGenerate;
        questionsForTest = QuestionsOfTest.getQuestions();
       // CreateQuestion();
        if(questionsForTest.size()>0) {
            canCreateTest=true;
            CreateQuestion();
        }
        else
        {
            canCreateTest=false;
            this.finish();
            Toast.makeText(this, "Nie ma wystarczającej ilości pytań o danym poziomie trudności.", Toast.LENGTH_LONG).show();
        }

    }

    private void CreateQuestion() {
        QuestionText.setText(questionsForTest.get(index).getContent());
        answers = questionsForTest.get(index).getAnswers();
        for (int i = 0; i < answers.size(); i++) {

            if (answers.get(i).isiSTrue()) {
                PointsCount += difficulty;

            }
            AnswearButtons.get(i).setText(answers.get(i).getContent());
        }
        index++;
        QuestionNumber.setText(Integer.toString(index) + "/" + Integer.toString(questionsForTest.size()));


    }

    public void OnClickButton(View view) {
        switch (view.getId()) {
            case R.id.Answear1:
                if (answers.get(0).isiSTrue())
                {
                    CorrectAnswear+=difficulty;
                }
                break;
            case R.id.Answear2:
                if (answers.get(1).isiSTrue())
                {
                    CorrectAnswear+=difficulty;
                }
                break;
            case R.id.Answear3:
                if (answers.get(2).isiSTrue())
                {
                    CorrectAnswear+=difficulty;
                }
                break;
        }
        IfEnd();

    }


    private void IfEnd() {
        if (index != questionsForTest.size()) {
            CreateQuestion();
        } else if (index == questionsForTest.size()) {
            for (int i = 0; i < AnswearButtons.size(); i++) {
                AnswearButtons.get(i).setVisibility(View.GONE);

            }
            EndOfTest();
        }
    }

    private void EndOfTest() {

        t.interrupt();
        EndTest = true;
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        time = localDateFormat.format(diffDate);

        QuestionText.setText("Test Ukończono.\n Wynik: " + CorrectAnswear + "/" + PointsCount + " punktów.\n Czas przejścia testu: "+time);
        QuestionNumber.setText("");

        AddValuesToPIEENTRY();
        AddValuesToPieEntryLabels();
        pieChart.setVisibility(View.VISIBLE);

        pieDataSet = new PieDataSet(entries, "");

        pieData = new PieData(PieEntryLabels, pieDataSet);

        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        pieChart.setData(pieData);
        pieChart.setDescription("");
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(10f);
        pieChart.animateY(3000);
        Clock.setText("");
        AddStatistic();

    }

    private void AddStatistic()
    {
        String type="addStats";
        AddStatisticBackgroundWorker addStatisticBackgroundWorker = new AddStatisticBackgroundWorker(this);
        addStatisticBackgroundWorker.execute(type,userName,Integer.toString(IdCategory),Integer.toString(difficulty),time,Integer.toString(CorrectAnswear));
    }


    private void startClock() {


       t= new Thread() {
            long startTime=SystemClock.elapsedRealtime();

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                long now=SystemClock.elapsedRealtime();
                                long diff=now-startTime;
                                diffDate.setTime(diff);
                                diffDate.setTime(diff);
                                SimpleDateFormat format=new SimpleDateFormat("HH:MM:SS");
                                format.setTimeZone(TimeZone.getTimeZone("UTC"));
                                String curTime=format.format(diffDate);

                                Clock.setText(curTime); //change clock to your textview
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
    }

    private void InitializeControls() {

            Clock = findViewById(R.id.ClockView);
            QuestionText = findViewById(R.id.Question);
            AnswearButtons.add((Button) findViewById(R.id.Answear1));
            AnswearButtons.add((Button) findViewById(R.id.Answear2));
            AnswearButtons.add((Button) findViewById(R.id.Answear3));
            pieChart = (PieChart) findViewById(R.id.piechart);
            pieChart.setVisibility(View.GONE);
            entries = new ArrayList<>();
            PieEntryLabels = new ArrayList<String>();
            QuestionNumber = findViewById(R.id.QuestionNumber);
    }
}
