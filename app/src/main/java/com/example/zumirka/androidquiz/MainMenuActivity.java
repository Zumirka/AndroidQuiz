package com.example.zumirka.androidquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zumirka.androidquiz.AsyncTasks.CategoryBackgroundWorker;
import com.example.zumirka.androidquiz.AsyncTasks.TestDownloadBackgroundWorker;

public class MainMenuActivity extends AppCompatActivity {
    String[] category;
    GridView gridViewButtons;
    String SelectedCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        takeCategory();
        gridViewButtons = (GridView) findViewById(R.id.GridViewButtons);

    }

    public void setCategory(String[] category) {
        this.category = category;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, category);

        gridViewButtons.setAdapter(adapter);

        gridViewButtons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    SelectedCategory=parent.getItemAtPosition(position).toString();
                MenuTestActivityStart();

            }
        });

    }

    public void takeCategory() {
        CategoryBackgroundWorker categoryBW = new CategoryBackgroundWorker(this);
        categoryBW.execute();
    }

    public void MenuTestActivityStart() {
        Intent i = new Intent(this, MenuTestActivity.class);
        i.putExtra("category", SelectedCategory);
        startActivity(i);

    }
}
