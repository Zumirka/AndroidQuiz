package com.example.zumirka.androidquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class TestSettingsActivity extends AppCompatActivity {

    int IdCategory,IdofCategory;
    int difficulty=0;
    Spinner DifficultySpinner;
    TextView tekst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_settings);
        IdofCategory=getIntent().getIntExtra("IdCategory",IdCategory);
        tekst=findViewById(R.id.textView);
        DifficultySpinner=findViewById(R.id.spinnerDiff);
        FillSpinner();


    }
    public void StartTestOnClick(View view)
    {
        difficulty=DifficultySpinner.getSelectedItemPosition();
        Intent i=new Intent(this,TestActivity.class);
        i.putExtra("IdCategory", IdofCategory);
        i.putExtra("Difficulty",difficulty);
        startActivity(i);
    }


    void FillSpinner()
    {
      ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
            R.array.difficulty_array, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
     DifficultySpinner.setAdapter(adapter);
    }



}
