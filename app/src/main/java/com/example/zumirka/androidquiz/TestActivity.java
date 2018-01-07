package com.example.zumirka.androidquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zumirka.androidquiz.AsyncTasks.TestDownloadBackgroundWorker;

public class TestActivity extends AppCompatActivity {

    int IdCategory,IdofCategory;
    int difficulty=0;
    Spinner DifficultySpinner;

    TextView tekst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        IdofCategory=getIntent().getIntExtra("IdCategory",IdCategory);
        tekst=findViewById(R.id.textView);
        DifficultySpinner=findViewById(R.id.spinnerDiff);
        FillSpinner();


    }
    void CreateTest()
    {
        TestDownloadBackgroundWorker test = new TestDownloadBackgroundWorker(IdCategory, difficulty);
        test.execute();
    }

    void FillSpinner()
    {
      ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
            R.array.planets_array, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
     DifficultySpinner.setAdapter(adapter);
    }
    /*
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


    spinner.setOnItemSelectedListener(this);
    */
}
