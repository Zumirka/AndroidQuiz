package com.example.zumirka.androidquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MenuTestActivity extends AppCompatActivity {

    int idOfCategory, id, difficulty = 0;
    Spinner difficultySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_test);
        idOfCategory = getIntent().getIntExtra("idCategory", id);
        difficultySpinner = findViewById(R.id.spinnerDiff);
        fillSpinner();

    }

    public void startTestOnClick(View view) {
        difficulty = difficultySpinner.getSelectedItemPosition();
        Intent i = new Intent(this, TestActivity.class);
        i.putExtra("IdCategory", idOfCategory);
        i.putExtra("Difficulty", difficulty);
        startActivity(i);
    }

    public void addQuestionOnClick(View view) {
        Intent i = new Intent(this, AddQuestionActivity.class);
        i.putExtra("IdCategory", idOfCategory);
        startActivity(i);
    }

    void fillSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.difficulty_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        difficultySpinner.setAdapter(adapter);
    }


}
