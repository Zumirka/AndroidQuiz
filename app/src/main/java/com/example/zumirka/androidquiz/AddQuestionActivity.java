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

import java.util.ArrayList;


public class AddQuestionActivity extends AppCompatActivity {

    Spinner Difficulty;
    int difficulty,IdCategory,IdofCategory;
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
       CheckIfNotEmpty();

   }
   void CheckIfNotEmpty()
   {
       for(int i=0;i<Controls.size();i++)
       {
           if(Controls.get(i).getText().toString().trim().length()==0)
           {
               Controls.get(i).setError("Nie może być puste");
               return;
           }
       }
   }

}
