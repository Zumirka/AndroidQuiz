package com.example.zumirka.androidquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.example.zumirka.androidquiz.AsyncTasks.CategoryBackgroundWorker;
import com.example.zumirka.androidquiz.Model.Category;

import java.util.ArrayList;
import java.util.List;


public class MainMenuActivity extends AppCompatActivity {
    List<Category> categoryList = new ArrayList<>();
    GridView gridViewButtons;
    String selectedCategory = "";
    int idCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        takeCategory();
        gridViewButtons = findViewById(R.id.GridViewButtons);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void setCategory(List<Category> category) {
        this.categoryList = category;

        String[] cat = getCategoryNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, cat);

        gridViewButtons.setAdapter(adapter);


        gridViewButtons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();

                getCategoryId();
                menuTestActivityStart();

            }

            private void getCategoryId() {
                for (int i = 0; i < categoryList.size(); i++) {

                    if (selectedCategory == categoryList.get(i).getName())
                        idCategory = categoryList.get(i).getId();
                }
            }
        });

    }

    @NonNull
    private String[] getCategoryNames() {
        String[] cat = new String[categoryList.size()];

        for (int i = 0; i < categoryList.size(); i++) {
            cat[i] = categoryList.get(i).getName();
        }
        return cat;
    }

    public void takeCategory() {
        CategoryBackgroundWorker categoryBW = new CategoryBackgroundWorker(this);
        categoryBW.execute();
    }

    public void menuTestActivityStart() {
        Intent i = new Intent(this, MenuTestActivity.class);
        i.putExtra("idCategory", idCategory);
        startActivity(i);

    }

    public void startInformationActivity(MainMenuActivity v, String type) {
        Intent i = new Intent(this, InformtionActivity.class);
        i.putExtra("Type", type);
        startActivity(i);
    }

    public void startStatisticActivity(MainMenuActivity view) {
        startActivity(new Intent(this, StatisticActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Stats:
                startStatisticActivity(this);
                return true;
            case R.id.Creators:
                startInformationActivity(this, "Creators");
                return true;
            case R.id.AppInfo:
                startInformationActivity(this, "AppInfo");
                return true;
            case R.id.LogOut:
                Toast.makeText(getApplicationContext(), "Aplikacja została zamknięta", Toast.LENGTH_LONG).show();
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
