package com.example.zumirka.androidquiz;

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

public class TestActivity extends AppCompatActivity {

    TextView QuestionText;
    int IdCategory,IdofCategory,index=0;
    int difficulty,diff;
    Test QuestionsOfTest;
    ArrayList<Question> questionsForTest;
    ArrayList<Answer> answers;
    ArrayList<Button> AnswearButtons=new ArrayList<Button>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
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
                AnswearButtons.get(i).setText(answers.get(i).getContent());
            }
            index++;



    }
    public void OnClickButton(View view)
    {
        if(index<=answers.size()+1)
            CreateQuestion();
        else
        {
            for(int i=0;i<=AnswearButtons.size();i++)
            {
                AnswearButtons.get(i).setVisibility(View.GONE);
            }
        }

    }



}
