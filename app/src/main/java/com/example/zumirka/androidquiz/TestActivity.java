package com.example.zumirka.androidquiz;

import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zumirka.androidquiz.AsyncTasks.TestDownloadBackgroundWorker;
import com.example.zumirka.androidquiz.Model.Answer;
import com.example.zumirka.androidquiz.Model.Question;
import com.example.zumirka.androidquiz.Model.Test;


import java.util.ArrayList;
import java.util.List;
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
    int IdCategory, IdofCategory, index = 0, PointsCount = 0;
    int difficulty, diff, CorrectAnswear = 0;
    Test QuestionsOfTest;
    Boolean EndTest = false;
    ArrayList<Entry> entries;
    ArrayList<String> PieEntryLabels;
    ArrayList<Question> questionsForTest;
    ArrayList<Answer> answers;
    ArrayList<Button> AnswearButtons = new ArrayList<Button>();
    Timer timer = new Timer();


    PieChart pieChart;
    PieDataSet pieDataSet;
    PieData pieData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        InitializeControls();
        startClock();
        IdCategory = getIntent().getIntExtra("IdCategory", IdofCategory);
        difficulty = getIntent().getIntExtra("Difficulty", diff);
        difficulty++;
        CreateTest();


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
        CreateQuestion();

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
                    CorrectAnswear++;
                }
                break;
            case R.id.Answear2:
                if (answers.get(1).isiSTrue())
                {
                    CorrectAnswear++;
                }
                break;
            case R.id.Answear3:
                if (answers.get(2).isiSTrue())
                {
                    CorrectAnswear++;
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

        EndTest = true;
        QuestionText.setText("Test Ukończono.\n Wynik: " + CorrectAnswear + "/" + PointsCount + " punktów.\n Czas przejścia testu: ");
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

    }

    private void startClock() {
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Calendar c = Calendar.getInstance();

                                int hours = c.get(Calendar.HOUR_OF_DAY);
                                int minutes = c.get(Calendar.MINUTE);
                                int seconds = c.get(Calendar.SECOND);

                                String curTime = String.format("%02d  %02d  %02d", hours, minutes, seconds);
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
        Clock = findViewById(R.id.Clock);
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
