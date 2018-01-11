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

public class TestActivity extends AppCompatActivity{

    TextView QuestionText,Clock;
    int IdCategory,IdofCategory,index=0;
    int difficulty,diff,CorrectAnswear=0;
    Test QuestionsOfTest;
    ArrayList<Question> questionsForTest;
    ArrayList<Answer> answers;
    ArrayList<Button> AnswearButtons=new ArrayList<Button>();
    Timer timer=new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Clock=findViewById(R.id.Clock);
        startClock();
        IdCategory=getIntent().getIntExtra("IdCategory",IdofCategory);
        difficulty=getIntent().getIntExtra("Difficulty",diff);
        difficulty++;
        QuestionText=findViewById(R.id.Question);
        AnswearButtons.add((Button) findViewById(R.id.Answear1));
        AnswearButtons.add((Button) findViewById(R.id.Answear2));
        AnswearButtons.add((Button) findViewById(R.id.Answear3));

        CreateTest();
    }


    void CreateTest() {
        TestDownloadBackgroundWorker test = new TestDownloadBackgroundWorker(IdCategory, difficulty,this);
        test.execute();
    }
   public void QuestionTaker(Test testGenerate)
    {
        this.QuestionsOfTest=testGenerate;
        questionsForTest=QuestionsOfTest.getQuestions();
        CreateQuestion();

    }
    private void CreateQuestion()
    {

            QuestionText.setText(questionsForTest.get(index).getContent());
            answers = questionsForTest.get(index).getAnswers();

            for (int i = 0; i < answers.size(); i++) {

                if(answers.get(i).isiSTrue())
                {
                    CorrectAnswear++;
                }
                AnswearButtons.get(i).setText(answers.get(i).getContent());
            }
            index++;



    }
    public void OnClickButton(View view) {
       IfEnd();

    }


    void IfEnd()
    {
        if (index != questionsForTest.size())
        {
            CreateQuestion();
        }
        else if(index==questionsForTest.size()){
            for (int i = 0; i < AnswearButtons.size(); i++) {
                AnswearButtons.get(i).setVisibility(View.GONE);

            }
            QuestionText.setText("Test Ukończono. Wynik: "+CorrectAnswear);
        }
    }
    private void startClock(){
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

}
