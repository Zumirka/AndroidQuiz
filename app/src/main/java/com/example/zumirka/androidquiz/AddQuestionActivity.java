package com.example.zumirka.androidquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class AddQuestionActivity extends AppCompatActivity {

    Spinner Difficulty;
    int difficulty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        Difficulty=findViewById(R.id.Difficulty);
        ChooseSpinner();
    }

    void ChooseSpinner()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.difficulty_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        Difficulty.setAdapter(adapter);
    }
   public void AddQuestion()
   {
       difficulty=Difficulty.getSelectedItemPosition();

   }

}
