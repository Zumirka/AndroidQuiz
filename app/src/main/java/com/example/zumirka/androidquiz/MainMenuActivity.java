package com.example.zumirka.androidquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zumirka.androidquiz.AsyncTasks.CategoryBackgroundWorker;

public class MainMenuActivity extends AppCompatActivity {
    public static final int PIERWSZY_ELEMENT = 1;
    public static final int DRUGI_ELEMENT = 2;
    public static final int TRZECI_ELEMENT = 3;
    String[] category;

    GridView gridViewButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
       takeCategory();
        gridViewButtons = (GridView) findViewById(R.id.GridViewButtons);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.add(0, PIERWSZY_ELEMENT, 0, "Jeden");
        menu.add(1, DRUGI_ELEMENT, 0, "Dwa");
        menu.add(2, TRZECI_ELEMENT, 0, "Trzy");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String ktoryElement = "";

        switch (item.getItemId()) {

            case PIERWSZY_ELEMENT:
                ktoryElement = "pierwszy";
                break;
            case DRUGI_ELEMENT:
                ktoryElement = "drugi";
                break;
            case TRZECI_ELEMENT:
                ktoryElement = "trzeci";
                break;
            default:
                ktoryElement = "żaden";

        }

        Toast.makeText(getApplicationContext(), "Element: " + ktoryElement, Toast.LENGTH_LONG).show();

        return true;
    }
    public void setCategory(String[] category)
    {
        this.category=category;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, category);

        gridViewButtons.setAdapter(adapter);

        gridViewButtons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }
public void takeCategory()
{
    CategoryBackgroundWorker categoryBW=new CategoryBackgroundWorker(this);
    categoryBW.execute();
}
}
