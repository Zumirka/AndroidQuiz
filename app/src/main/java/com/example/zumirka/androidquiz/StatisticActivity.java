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
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;



public class StatisticActivity extends AppCompatActivity implements
        OnChartGestureListener,
        OnChartValueSelectedListener {



    String userName="",time1,time2,selectedGrid="";
    ArrayList<Statistic> statisticsList=new ArrayList<>();
    ArrayList<TextView> columns=new ArrayList<>();
    TableLayout l;
    ImageButton fromDate,toDate;
    Calendar cal;
    int day,month,year,idDialog=1;
    EditText fromDateTxt,toDateTxt;
    TableRow tr,trl;
    ArrayList<Entry> entries;
    ArrayList<String> PieEntryLabels;
    int CorrectAnswear=0,PointsCount=0;
    LineChart lineChart;
    TextView emptyTableTxt;


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
        lineChart = (LineChart) findViewById(R.id.linechart);
        lineChart.setOnChartGestureListener(this);
        lineChart.setOnChartValueSelectedListener(this);
        setData();
        Legend l =lineChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        emptyTableTxt = findViewById(R.id.EmptyTableTxt);


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
        if(stats.size()!=0) {
            emptyTableTxt.setText("");
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
                        if (firstText == "Łatwy") {
                            PointsCount = 10;
                        }
                        if (firstText == "Średni") {
                            PointsCount = 20;
                        }
                        if (firstText == "Trudny") {
                            PointsCount = 30;
                        }
                        CorrectAnswear = Integer.parseInt(secondTextView.getText().toString());


                    }
                });

            }
        }
        else
        {
        //    l.removeAllViewsInLayout();
            l.removeAllViews();
            emptyTableTxt.setText("Brak statystyk");
          emptyTableTxt.setGravity(Gravity.CENTER);

        }

    }
    //set x value on chart
    private ArrayList<String> setXAxisValues(){
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("10");
        xVals.add("20");
        xVals.add("30");
        xVals.add("30.5");
        xVals.add("40");

        return xVals;
    }
    //set y value on chart
    private ArrayList<Entry> setYAxisValues(){
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        yVals.add(new Entry(60, 0));
        yVals.add(new Entry(48, 1));
        yVals.add(new Entry(70.5f, 2));
        yVals.add(new Entry(100, 3));
        yVals.add(new Entry(180.9f, 4));

        return yVals;
    }
    private void setData() {
        ArrayList<String> xVals = setXAxisValues();

        ArrayList<Entry> yVals = setYAxisValues();

        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "DataSet 1");
        set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        // set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
       lineChart.setData(data);
      // lineChart.setTouchEnabled(false);
       lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);

    }
    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
