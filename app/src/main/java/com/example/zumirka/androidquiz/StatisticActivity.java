package com.example.zumirka.androidquiz;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.zumirka.androidquiz.AsyncTasks.StatisticBackgroundWorker;
import com.example.zumirka.androidquiz.Model.Statistic;

import java.util.ArrayList;



public class StatisticActivity extends AppCompatActivity {
    String userName="",time1,time2,selectedGrid="";
    ArrayList<Statistic> statisticsList=new ArrayList<>();
    ArrayList<TextView> columns=new ArrayList<>();
    TableLayout l;
    ImageButton fromDate,toDate;
    Calendar cal;
    int day,month,year,idDialog=1;
    EditText fromDateTxt,toDateTxt;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        SharedPreferences resSettings = getSharedPreferences("BYLECO", MODE_PRIVATE);
        userName = resSettings.getString("USER_NAME", "empty");
        l = (TableLayout) findViewById(R.id.table);
        fromDate=(ImageButton) findViewById(R.id.fromDate);
        toDate=(ImageButton) findViewById(R.id.toDate);
        fromDateTxt=findViewById(R.id.fromDateTxt);
        toDateTxt=findViewById(R.id.toDateTxt);
        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);
        month=cal.get(Calendar.MONTH );
        day=cal.get(Calendar.DAY_OF_MONTH);
       // TakeStatistic();

    }
    int buttonId;
    public void onClickV(View v)
    {
        buttonId=v.getId();
        showDialog(0);
    }
    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {

        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }

    public DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year=selectedYear;
            month=selectedMonth;
            month++;
            day=selectedDay;
            if(buttonId==fromDate.getId()){
            fromDateTxt.setText(String.format("%02d",day) + "-" +String.format("%02d", month)  + "-" +String.format("%04d", year));
            time1=String.format("%04d", year)+"-"+String.format("%02d", month)+"-"+String.format("%02d",day);
            }
            if(buttonId==toDate.getId())
            {
                toDateTxt.setText(String.format("%02d",day) + "-" +String.format("%02d", month)  + "-" +String.format("%04d", year));
                time2=String.format("%04d", year)+"-"+String.format("%02d", month)+"-"+String.format("%02d",day);
                TakeStatistic();
            }

        }
    };


    private void TakeStatistic()
    {

        String type="getStats";
        StatisticBackgroundWorker addStatisticBackgroundWorker = new StatisticBackgroundWorker(this);
        addStatisticBackgroundWorker.execute(type,userName,time1,time2);
    }
    public void setCategory(ArrayList<Statistic> stats) {
        this.statisticsList = stats;
        TableRow trl=new TableRow(this);
        TextView labels=new TextView(this);
        labels.setText("Kategoria");
        labels.setGravity(Gravity.CENTER);
        trl.addView(labels);
        labels=new TextView(this);
        labels.setText("Poziom trudności");
        labels.setGravity(Gravity.CENTER);
        trl.addView(labels);
        labels=new TextView(this);
        labels.setText("Czas");
        labels.setGravity(Gravity.CENTER);
        trl.addView(labels);
        labels=new TextView(this);
        labels.setText("Punkty");
        labels.setGravity(Gravity.CENTER);
        trl.addView(labels);
        labels=new TextView(this);
        labels.setText("Data");
        labels.setGravity(Gravity.CENTER);
        trl.addView(labels);
        trl.setBackgroundColor(Color.GRAY);
        trl.setPadding(1, 4, 1, 3);
        l.addView(trl);

        for(int i=0;i<statisticsList.size();i++)
        {
            TableRow tr=new TableRow(this);
                TextView txt=new TextView(this);
                txt.setText(statisticsList.get(i).getCategoryName());
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
            tr.setBackgroundColor(Color.LTGRAY);
            tr.setPadding(2, 4, 3, 3);
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
