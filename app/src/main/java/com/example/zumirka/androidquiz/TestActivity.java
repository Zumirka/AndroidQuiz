package com.example.zumirka.androidquiz;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zumirka.androidquiz.AsyncTasks.addStatisticBackgroundWorker;
import com.example.zumirka.androidquiz.AsyncTasks.TestDownloadBackgroundWorker;
import com.example.zumirka.androidquiz.Model.Answear;
import com.example.zumirka.androidquiz.Model.Question;
import com.example.zumirka.androidquiz.Model.Test;


import java.util.ArrayList;
import java.util.Date;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

public class TestActivity extends AppCompatActivity {

    TextView questionText, clock, questionNumber;
    String time,userName="";
    int idCategory, idOfCategory, index = 0, pointsCount = 0;
    int difficulty, diff, correctAnswear = 0;

    Boolean endTest = false,canCreateTest=true;
    ArrayList<Entry> entries;
    ArrayList<String> pieEntryLabels;
    ArrayList<Question> questionsFromTest;
    ArrayList<Answear> answears;
    ArrayList<Button> answearButtons = new ArrayList<Button>();
    Thread t;
    Date diffDate=new Date();
    PieChart pieChart;
    PieDataSet pieDataSet;
    PieData pieData;
    Handler handler=new Handler();
    long startTime=0L,timeMs=0L,timeSwap=0L,updateTime=0L;


    Runnable updateTimeThread=new Runnable() {
        @Override
        public void run() {
            timeMs=SystemClock.uptimeMillis()-startTime;
            updateTime=timeSwap+timeMs;
            int sec=(int)(updateTime/1000);
            int min=sec/60;
            int hour=min/60;
            sec%=60;
            clock.setText(String.format("%02d",hour)+":"+String.format("%02d",min)+":"+String.format("%02d",sec));
            handler.postDelayed(this,0);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences resSettings = getSharedPreferences("BYLECO", MODE_PRIVATE);
        userName = resSettings.getString("USER_NAME", "empty");
        setContentView(R.layout.activity_test);
        idCategory = getIntent().getIntExtra("IdCategory", idOfCategory);
        difficulty = getIntent().getIntExtra("Difficulty", diff);
        difficulty++;
        createTest();
        if(canCreateTest) { InitializeControls();}


    }

    public void addValuesToPIEENTRY() {
        int PercentValue = correctAnswear * 100 / pointsCount;
        entries.add(new BarEntry(PercentValue, 0));
        entries.add(new BarEntry((100 - PercentValue), 1));


    }

    public void addValuesToPieEntryLabels() {

        pieEntryLabels.add("Dobre odpowiedzi");
        pieEntryLabels.add("Złe odpowiedzi");


    }


    void createTest() {

        TestDownloadBackgroundWorker test = new TestDownloadBackgroundWorker(idCategory, difficulty, this,this);
        test.execute();

    }

    public void questionTaker(Test generatedTest) {
        questionsFromTest = generatedTest.getQuestions();
        if(questionsFromTest.size()>0) {
            canCreateTest=true;
            createQuestion();
            startClock();
        }
        else
        {
            canCreateTest=false;
            this.finish();
            Toast.makeText(this, "Nie ma wystarczającej ilości pytań o danym poziomie trudności.", Toast.LENGTH_LONG).show();
        }

    }

    private void createQuestion() {

        questionText.setText(questionsFromTest.get(index).getContent());
        answears = questionsFromTest.get(index).getAnswears();
        for (int i = 0; i < answears.size(); i++) {

            if (answears.get(i).isCorrect()) {
                pointsCount += difficulty;

            }
            answearButtons.get(i).setText(answears.get(i).getContent());
            answearButtons.get(i).setVisibility(View.VISIBLE);
        }
        index++;
        questionNumber.setText(Integer.toString(index) + "/" + Integer.toString(questionsFromTest.size()));


    }

    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.Answear1:
                if (answears.get(0).isCorrect())
                {
                    correctAnswear +=difficulty;
                }
                break;
            case R.id.Answear2:
                if (answears.get(1).isCorrect())
                {
                    correctAnswear +=difficulty;
                }
                break;
            case R.id.Answear3:
                if (answears.get(2).isCorrect())
                {
                    correctAnswear +=difficulty;
                }
                break;
        }
        nextQuestion();

    }


    private void nextQuestion() {
        if (index != questionsFromTest.size()) {
            createQuestion();
        } else if (index == questionsFromTest.size()) {
            for (int i = 0; i < answearButtons.size(); i++) {
                answearButtons.get(i).setVisibility(View.GONE);

            }
            EndOfTest();
        }
    }

    private void EndOfTest() {


        endTest = true;
        stopClock();
        time= clock.getText().toString();
        questionText.setText("Test Ukończono.\n Wynik: " + correctAnswear + "/" + pointsCount + " punktów.\n Czas przejścia testu: "+time);
        questionNumber.setText("");

        addValuesToPIEENTRY();
        addValuesToPieEntryLabels();
        drawChart();
        clock.setText("");
        AddStatistic();

    }

    private void drawChart() {
        pieChart.setVisibility(View.VISIBLE);

        pieDataSet = new PieDataSet(entries, "");

        pieData = new PieData(pieEntryLabels, pieDataSet);

        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        pieChart.setData(pieData);
        pieChart.setDescription("");
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(10f);
        pieChart.animateY(3000);
    }

    //TODO type!!!
    private void AddStatistic()
    {

        String type="addStats";
        addStatisticBackgroundWorker addStatisticBackgroundWorker = new addStatisticBackgroundWorker(this);
        addStatisticBackgroundWorker.execute(type,userName,Integer.toString(idCategory),Integer.toString(difficulty),time,Integer.toString(correctAnswear));
    }





    private void startClock() {

        startTime=SystemClock.uptimeMillis();
        handler.postDelayed(updateTimeThread,0);

    }
    private void stopClock()
    {
        timeSwap+=timeMs;
        handler.removeCallbacks(updateTimeThread);
    }

    private void InitializeControls() {

            clock = findViewById(R.id.ClockView);
            questionText = findViewById(R.id.Question);
            answearButtons.add((Button) findViewById(R.id.Answear1));
            answearButtons.add((Button) findViewById(R.id.Answear2));
            answearButtons.add((Button) findViewById(R.id.Answear3));
            pieChart = (PieChart) findViewById(R.id.piechart);
            pieChart.setVisibility(View.GONE);
            entries = new ArrayList<>();
            pieEntryLabels = new ArrayList<String>();
            questionNumber = findViewById(R.id.QuestionNumber);

        }

}
