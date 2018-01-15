package com.example.zumirka.androidquiz;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zumirka.androidquiz.AsyncTasks.AddQuestionBackgroundWorker;

import java.util.ArrayList;


public class AddQuestionActivity extends AppCompatActivity {

    Spinner Difficulty;
    int difficulty,IdCategory,IdofCategory;
    String question,answ1,answ2,answ3;
    ArrayList<EditText> Controls =new ArrayList<EditText>();
    Button AddQuestions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        IdCategory=getIntent().getIntExtra("IdCategory",IdofCategory);
        Difficulty=findViewById(R.id.Difficulty);
       Controls.add((EditText) findViewById(R.id.QuestionText));
        Controls.add((EditText) findViewById(R.id.Answear1C));
       Controls.add((EditText) findViewById(R.id.Answear2W));
        Controls.add((EditText) findViewById(R.id.Answear3W));
        AddQuestions=findViewById(R.id.AddQuestion);
        ChooseSpinner();
    }

    void ChooseSpinner()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.difficulty_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Difficulty.setAdapter(adapter);
    }

    public void AddQuestionOnClick(View v)
   {
       difficulty=Difficulty.getSelectedItemPosition();
       difficulty++;
       RewriteControls();
       if(CheckIfNotEmpty())
       {
           String type="addQuestion";
          for(int i=0;i<Controls.size();i++)
          {
              Controls.get(i).setText("");
          }
           Toast.makeText(this, "Pytanie zostało dodane.", Toast.LENGTH_LONG).show();
           AddQuestionBackgroundWorker addqBackgroundWorker = new AddQuestionBackgroundWorker(this);
           addqBackgroundWorker.execute(type,Integer.toString(IdCategory),Integer.toString(difficulty),question,answ1,answ2,answ3);
       }


   }
   void RewriteControls()
   {
               question=Controls.get(0).getText().toString();
               answ1=Controls.get(1).getText().toString();
               answ2=Controls.get(2).getText().toString();
               answ3=Controls.get(3).getText().toString();
   }

    Boolean CheckIfNotEmpty()
   {
       Boolean t=false;
       for(int i=0;i<Controls.size();i++)
       {
           if(Controls.get(i).getText().toString().trim().length()==0)
           {
               Controls.get(i).setError(this.getString(R.string.not_empty));
               t= false;
           }
           else
           t= true;
       }
       return t;
   }

}
