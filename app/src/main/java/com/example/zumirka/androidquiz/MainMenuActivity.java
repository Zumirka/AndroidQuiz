package com.example.zumirka.androidquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;


import com.example.zumirka.androidquiz.AsyncTasks.CategoryBackgroundWorker;
import com.example.zumirka.androidquiz.Model.Category;

import java.util.ArrayList;
import java.util.List;


public class MainMenuActivity extends AppCompatActivity {
   List<Category> categoryList=new ArrayList<>();
    GridView gridViewButtons;
    String SelectedCategory="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        takeCategory();
        gridViewButtons = (GridView) findViewById(R.id.GridViewButtons);
    }

    public void setCategory(List<Category> category) {
        this.categoryList = category;

        String[] cat = new String[categoryList.size()];

        for(int i=0;i<categoryList.size();i++) {
           cat[i]= categoryList.get(i).getCategory();
        }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, cat);

            gridViewButtons.setAdapter(adapter);

            gridViewButtons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    SelectedCategory = parent.getItemAtPosition(position).toString();
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
