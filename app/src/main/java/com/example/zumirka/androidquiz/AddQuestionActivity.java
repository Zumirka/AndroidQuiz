package com.example.zumirka.androidquiz;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zumirka.androidquiz.AsyncTasks.AddQuestionBackgroundWorker;
import com.example.zumirka.androidquiz.Utilities.ConnectionChecker;

import java.util.ArrayList;


public class AddQuestionActivity extends AppCompatActivity {

    Spinner difficultySpinner;
    int difficulty, idCategory, idofCategory;
    String question, answ1, answ2, answ3;
    ArrayList<EditText> controls = new ArrayList<EditText>();
    Button addQuestionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        idCategory = getIntent().getIntExtra("IdCategory", idofCategory);
        difficultySpinner = findViewById(R.id.Difficulty);
        controls.add((EditText) findViewById(R.id.QuestionText));
        controls.add((EditText) findViewById(R.id.Answear1C));
        controls.add((EditText) findViewById(R.id.Answear2W));
        controls.add((EditText) findViewById(R.id.Answear3W));
        addQuestionBtn = findViewById(R.id.AddQuestion);
        chooseSpinner();
    }

    void chooseSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.difficulty_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(adapter);
    }

    public void addQuestionOnClick(View v) {
        if (ConnectionChecker.checkInternetConnection(this)) {
            difficulty = difficultySpinner.getSelectedItemPosition();
            difficulty++;
            rewriteControls();
            if (areTextFieldNotEmpty()) {
                for (int i = 0; i < controls.size(); i++) {
                    controls.get(i).setText("");
                }
                Toast.makeText(this, "Pytanie zostało dodane", Toast.LENGTH_LONG).show();
                AddQuestionBackgroundWorker addqBackgroundWorker = new AddQuestionBackgroundWorker(this, Integer.toString(idCategory), Integer.toString(difficulty), question, answ1, answ2, answ3);
                addqBackgroundWorker.execute();
            }

        }
        else {
            showErrorDialog();
        }
    }

    void rewriteControls() {
        question = controls.get(0).getText().toString();
        answ1 = controls.get(1).getText().toString();
        answ2 = controls.get(2).getText().toString();
        answ3 = controls.get(3).getText().toString();
    }
// TODO
    Boolean areTextFieldNotEmpty() {
        Boolean t = false;
        for (int i = 0; i < controls.size(); i++) {
            if (controls.get(i).getText().toString().trim().length() == 0) {
                controls.get(i).setError(this.getString(R.string.not_empty));
                t = false;
            } else
                t = true;
        }
        return t;
    }
    private void showErrorDialog() {
        AlertDialog alert;
        alert = new AlertDialog.Builder(this).create();
        alert.setTitle(this.getString(R.string.status));
        alert.setMessage(this.getString(R.string.internetCommunicat));
        alert.show();
    }
}
