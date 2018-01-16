package com.example.zumirka.androidquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.zumirka.androidquiz.AsyncTasks.StatisticBackgroundWorker;
import com.example.zumirka.androidquiz.Model.Category;
import com.example.zumirka.androidquiz.Model.Statistic;

import java.util.ArrayList;
import java.util.List;

public class StatisticActivity extends AppCompatActivity {
    String userName="",time1,time2,selectedGrid="";
    ArrayList<Statistic> statisticsList=new ArrayList<>();
    ArrayList<TextView> columns=new ArrayList<>();
    TableLayout l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        SharedPreferences resSettings = getSharedPreferences("BYLECO", MODE_PRIVATE);
        userName = resSettings.getString("USER_NAME", "empty");
        l = (TableLayout) findViewById(R.id.table);
        TakeStatistic();


    }

    private void TakeStatistic()
    {

        String type="getStats";
        time1="2018-01-14 17:33:28";
        time2="2018-01-16 20:59:51";
        StatisticBackgroundWorker addStatisticBackgroundWorker = new StatisticBackgroundWorker(this);
        addStatisticBackgroundWorker.execute(type,userName,time1,time2);
    }
    public void setCategory(ArrayList<Statistic> stats) {
        this.statisticsList = stats;

        for(int i=0;i<statisticsList.size();i++)
        {
            TableRow tr=new TableRow(this);

                TextView txt=new TextView(this);
                txt.setText(statisticsList.get(i).getCategoryId());
                txt.setGravity(Gravity.CENTER);
                tr.addView(txt);
                txt=new TextView(this);
                txt.setText(statisticsList.get(i).getDifficulty());
                txt.setGravity(Gravity.CENTER);
                tr.addView(txt);
                txt=new TextView(this);
                txt.setText(statisticsList.get(i).getTime());
                txt.setGravity(Gravity.CENTER);
                tr.addView(txt);
                txt=new TextView(this);
                txt.setText(statisticsList.get(i).getPoints());
                txt.setGravity(Gravity.CENTER);
                tr.addView(txt);
                txt=new TextView(this);
                txt.setText(statisticsList.get(i).getDate());
                txt.setGravity(Gravity.CENTER);
                tr.addView(txt);

            l.addView(tr);
        }

/* for(int i=0;i<statisticsList.size();i++)
        {
            TableRow tr=new TableRow(this);
                for(int j=0;j<cols;j++) {
                    columns.add(new TextView(this));
                }
                columns.get(0).setText(statisticsList.get(i).getCategoryId());
                columns.get(0).setGravity(Gravity.CENTER);
                columns.get(1).setText(statisticsList.get(i).getDifficulty());
                columns.get(1).setGravity(Gravity.CENTER);
                columns.get(2).setText(statisticsList.get(i).getTime());
                columns.get(2).setGravity(Gravity.CENTER);
                columns.get(3).setText(statisticsList.get(i).getPoints());
                columns.get(3).setGravity(Gravity.CENTER);
                columns.get(4).setText(statisticsList.get(i).getDate());
                columns.get(4).setGravity(Gravity.CENTER);

            l.addView(tr);
        }*/

    }
    private void createTable(Context ct)
    {



    }
}
