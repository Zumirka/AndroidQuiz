package com.example.zumirka.androidquiz;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.zumirka.androidquiz.AsyncTasks.addStatisticBackgroundWorker;
import com.example.zumirka.androidquiz.AsyncTasks.getStatisticBackgroundWorker;
import com.example.zumirka.androidquiz.Model.Statistic;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

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
    TableRow tr,trl;

    PieChart pieChart;
    PieDataSet pieDataSet;
    PieData pieData;
    ArrayList<Entry> entries;
    ArrayList<String> PieEntryLabels;
    int CorrectAnswear=0,PointsCount=0;


    @RequiresApi(api = Build.VERSION_CODES.N)
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

        pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setVisibility(View.GONE);


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
        getStatisticBackgroundWorker getStatisticBackgroundWorker = new getStatisticBackgroundWorker(this);
        getStatisticBackgroundWorker.execute(type,userName,time1,time2);
        l.removeAllViewsInLayout();
    }
    public void setCategory(ArrayList<Statistic> stats) {
        this.statisticsList = stats;
        trl = new TableRow(this);
        TextView labels = new TextView(this);
        labels.setText("Kategoria");
        labels.setGravity(Gravity.CENTER);
        trl.addView(labels);
        labels = new TextView(this);
        labels.setText("Poziom trudności");
        labels.setGravity(Gravity.CENTER);
        trl.addView(labels);
        labels = new TextView(this);
        labels.setText("Czas");
        labels.setGravity(Gravity.CENTER);
        trl.addView(labels);
        labels = new TextView(this);
        labels.setText("Punkty");
        labels.setGravity(Gravity.CENTER);
        trl.addView(labels);
        labels = new TextView(this);
        labels.setText("Data");
        labels.setGravity(Gravity.CENTER);
        trl.addView(labels);
        trl.setBackgroundColor(Color.GRAY);
        trl.setPadding(1, 4, 1, 3);
        l.addView(trl);

        for (int i = 0; i < statisticsList.size(); i++) {
            tr = new TableRow(this);
            TextView txt = new TextView(this);
            txt.setText(statisticsList.get(i).getCategoryName());
            txt.setGravity(Gravity.CENTER);
            tr.addView(txt);
            txt = new TextView(this);
            txt.setText(statisticsList.get(i).getDifficulty());
            txt.setGravity(Gravity.CENTER);
            tr.addView(txt);
            txt = new TextView(this);
            txt.setText(statisticsList.get(i).getTime());
            txt.setGravity(Gravity.CENTER);
            tr.addView(txt);
            txt = new TextView(this);
            txt.setText(statisticsList.get(i).getPoints());
            txt.setGravity(Gravity.CENTER);
            tr.addView(txt);
            txt = new TextView(this);
            txt.setText(statisticsList.get(i).getDate());
            txt.setGravity(Gravity.CENTER);
            tr.addView(txt);
            tr.setBackgroundColor(Color.LTGRAY);
            tr.setPadding(2, 4, 3, 3);
            l.addView(tr);
            tr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    entries = new ArrayList<>();
                    PieEntryLabels = new ArrayList<String>();
                    TableRow clickRow = (TableRow) view;
                    TextView firstTextView = (TextView) clickRow.getChildAt(1);
                    TextView secondTextView = (TextView) clickRow.getChildAt(3);
                    String firstText = firstTextView.getText().toString();
                    if(firstText=="Łatwy")
                    {
                        PointsCount=10;
                    }
                    if(firstText=="Średni")
                    {
                        PointsCount=20;
                    }
                    if(firstText=="Trudny")
                    {
                        PointsCount=30;
                    }
                    CorrectAnswear = Integer.parseInt(secondTextView.getText().toString());
                    AddValuesToPIEENTRY();
                    AddValuesToPieEntryLabels();
                    pieChart.setVisibility(View.VISIBLE);

                    pieDataSet = new PieDataSet(entries, "");

                    pieData = new PieData(PieEntryLabels, pieDataSet);
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieChart.setDrawSliceText(false);
                    pieChart.setData(pieData);
                    pieChart.setDescription("");
                    pieData.setValueFormatter(new PercentFormatter());
                    pieData.setValueTextSize(10f);
                    pieChart.animateY(3000);
                }
            });

        }

    }
    public void AddValuesToPIEENTRY() {
        int PercentValue = CorrectAnswear * 100 / PointsCount;
        entries.add(new BarEntry(PercentValue, 0));
        entries.add(new BarEntry((100 - PercentValue), 1));


    }

    public void AddValuesToPieEntryLabels() {

        PieEntryLabels.add("Dobre odpowiedzi");
        PieEntryLabels.add("Złe odpowiedzi");


    }


}
